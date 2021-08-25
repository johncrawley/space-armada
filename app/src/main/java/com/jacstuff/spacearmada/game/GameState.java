package com.jacstuff.spacearmada.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jacstuff.spacearmada.music.MusicPlayer;
import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.controls.TouchPoint;
import com.jacstuff.spacearmada.actors.background.BackgroundTiles;
import com.jacstuff.spacearmada.actors.ships.player.PlayerShip;
import com.jacstuff.spacearmada.actors.ships.player.PlayerShipFactory;
import com.jacstuff.spacearmada.image.BitmapLoader;
import com.jacstuff.spacearmada.image.BitmapManager;
import com.jacstuff.spacearmada.image.SimpleBitmapLoader;
import com.jacstuff.spacearmada.image.SimpleBitmapManagerImpl;
import com.jacstuff.spacearmada.managers.CollisionDetectionManager;
import com.jacstuff.spacearmada.actors.ships.enemies.EnemyShipManager;
import com.jacstuff.spacearmada.managers.InputControlsManager;
import com.jacstuff.spacearmada.actors.projectiles.ProjectileManager;
import com.jacstuff.spacearmada.state.State;
import com.jacstuff.spacearmada.state.StateManager;
import com.jacstuff.spacearmada.state.timed.TimedActionManager;
import com.jacstuff.spacearmada.tasks.AnimatorTask;
import com.jacstuff.spacearmada.tasks.EnemyCreatorTask;
import com.jacstuff.spacearmada.utils.ImageLoader;
import com.jacstuff.spacearmada.view.TransparentView;

/**
 * Created by John on 29/08/2017.
 * Handles all the game logic.
 */

public class GameState implements State {

    private final StateManager stateManager;
    private EnemyShipManager enemyShipManager;
    private InputControlsManager inputControlsManager;
    private CollisionDetectionManager collisionDetectionManager;
    private ProjectileManager projectileManager;
    private final ImageLoader imageLoader;
    private GameView gameView;
    private ExecutorService enemySpawningService = Executors.newCachedThreadPool();
    private final ScheduledExecutorService animationService = Executors.newScheduledThreadPool(4);
    private BackgroundTiles backgroundTiles;
    private int canvasWidth, canvasHeight;
    private PlayerShip playerShip;
    private final Activity activity;
    private MusicPlayer musicPlayer;
    private Rect gameScreenBounds;

    private GameStateHandler currentGameStateHandler;
    private EnemyCreatorTask enemyCreatorTask;
    private final BitmapManager bitmapManager;
    private int currentLog = 0;


    public GameState(StateManager stateManager, Activity activity, int canvasWidth, int canvasHeight){
        this.stateManager = stateManager;
        setupScreenBounds(0, canvasWidth, canvasHeight);
        this.activity = activity;
        imageLoader = new ImageLoader(activity, canvasWidth, canvasHeight);
        bitmapManager = new SimpleBitmapManagerImpl();
        BitmapLoader bitmapLoader = new SimpleBitmapLoader(activity, bitmapManager);
        TimedActionManager timedActionManager = new TimedActionManager();

        initShipsControlsAndProjectiles(bitmapLoader);
        createEnemyThread();
        initAnimtionThread();
        initBackgroundView();
        initView();
        initMusicPlayer(activity);
       this.currentGameStateHandler = new GamePlay(activity, this);
       enemyShipManager.createShip(400,100);

       initBackgroundView();
    }



    private void initBackgroundView(){
        TransparentView backgroundView = activity.findViewById(R.id.backgroundView);

        backgroundTiles = new BackgroundTiles(activity, backgroundView, gameScreenBounds.top, gameScreenBounds.bottom);
    }


    private void setupScreenBounds(int top, int width, int bottom){
        int gameScreenTop = 120;
        int gameScreenBottom = bottom - 480;
        this.canvasHeight = bottom;
        this.canvasWidth = width;
        gameScreenBounds = new Rect(0,gameScreenTop, canvasWidth, gameScreenBottom);

    }


    public void update(){
        currentGameStateHandler.update();
    }


    private void logDraw(){
        int logLimit = 30;
        currentLog+=1;
        if(currentLog > logLimit){
            //Log.i("GameState draw", "calling draw()");
            currentLog = 0;
        }
    }


    public void draw(Canvas canvas, Paint paint){
        logDraw();
        currentGameStateHandler.draw(canvas, paint);
    }


    public void onPause(){
        log("GameState onPause()");
        musicPlayer.pause();
        enemyCreatorTask.setInactive();
        enemySpawningService.shutdown();
    }


    public void onResume(){
        log("GameState onResume()");
        musicPlayer.resume();
        createEnemyThread();
    }


    @Override
    public void destroy(){
        Log.i("GameState", "Entered destroy() - shutting down threads");
        enemyCreatorTask.setInactive();
        enemySpawningService.shutdownNow();
        try {
            animationService.shutdownNow();
            enemySpawningService.awaitTermination(200L, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            log("Interrupted shutting down enemy spawning service: " + e.getMessage());
        }
    }


    private void createEnemyThread(){
        enemyCreatorTask = new EnemyCreatorTask(enemyShipManager, canvasWidth, 90);
        enemyCreatorTask.setMinPauseBetweenEnemies(2);
        enemyCreatorTask.setMaxPauseBetweenEnemies(4);
        enemySpawningService = Executors.newCachedThreadPool();
        enemySpawningService.execute(enemyCreatorTask);
    }


    private void initShipsControlsAndProjectiles(BitmapLoader bitmapLoader){
        projectileManager = new ProjectileManager(gameScreenBounds, bitmapLoader);
        playerShip = PlayerShipFactory.createPlayerShip(activity, gameScreenBounds, projectileManager, bitmapLoader);
        initControls();
        enemyShipManager = new EnemyShipManager(projectileManager, bitmapLoader, canvasHeight);
        collisionDetectionManager = new CollisionDetectionManager(playerShip, enemyShipManager, projectileManager);
    }


    private void initControls(){
        int dpadRadius = 150;
        int dpadCentreY = gameScreenBounds.bottom + ( canvasHeight - gameScreenBounds.bottom ) /2;
        int dpadCentreX = canvasWidth / 2;
        inputControlsManager = new InputControlsManager(activity, canvasWidth, canvasHeight, playerShip);
        inputControlsManager.setDpadPosition(dpadCentreX, dpadCentreY, dpadRadius);
    }


    private void initMusicPlayer(Context context){
        musicPlayer = new MusicPlayer(context);
        musicPlayer.playTrack(R.raw.bensound_straight);
    }


    private void log(String msg){
        Log.i("GameState", msg);
    }


    private void initView(){
        log("Entered initView()");
        gameView = new GameView(activity, bitmapManager, gameScreenBounds.top, gameScreenBounds.bottom, gameScreenBounds.right);
        gameView.register(playerShip.getEnergy());
        gameView.register(playerShip.getScore());
        gameView.register(playerShip);
        gameView.register(inputControlsManager);
        gameView.registerBitmapGroup(enemyShipManager);
        gameView.registerBitmapGroup(projectileManager);
        gameView.setBackgroundTiles(backgroundTiles);
        log("background tiles assigned to view");
    }


    private void initAnimtionThread(){
        Log.i("GameState", "Entered initAnimationThread");
        AnimatorTask animatorTask = new AnimatorTask(enemyShipManager);
        animationService.scheduleAtFixedRate(animatorTask, 0,100, TimeUnit.MILLISECONDS);
    }

    /*
    public void finish(){
        this.stateManager = null;
        animationService.shutdown();
        enemySpawningService.shutdown();
        this.timedActionManager = null;
        this.gameView = null;
        this.playerShip = null;
        this.enemyShipManager = null;
        this.currentGameStateHandler = null;
    }

     */


    public void handleTouchPoints(List<TouchPoint> touchPoints){
        currentGameStateHandler.handleTouchPoints(touchPoints);
    }


    void setCurrentGameStateHandler(GameStateHandler gameStateHandler){
        this.currentGameStateHandler = gameStateHandler;
    }


    BackgroundTiles getBackgroundTiles() {
        return backgroundTiles;
    }
    CollisionDetectionManager getCollisionDetectionManager() { return collisionDetectionManager; }
    EnemyShipManager getEnemyShipManager() {
        return enemyShipManager;
    }


    public ImageLoader getImageLoader() {
        return imageLoader;
    }


    GameView getGameView() {
        return gameView;
    }


    InputControlsManager getInputControlsManager() {
        return inputControlsManager;
    }


    ProjectileManager getProjectileManager() {
        return projectileManager;
    }


    StateManager getStateManager() {
        return stateManager;
    }


    public GameStateHandler getCurrentGameStateHandler() {
        return currentGameStateHandler;
    }


    PlayerShip getPlayerShip() {
        return playerShip;
    }


    MusicPlayer getMusicPlayer(){
        return this.musicPlayer;
    }
}


