package com.jacstuff.spacearmada.game;

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

import com.jacstuff.spacearmada.MusicPlayer;
import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.TouchPoint;
import com.jacstuff.spacearmada.actors.background.BackgroundTiles;
import com.jacstuff.spacearmada.actors.ships.player.PlayerShip;
import com.jacstuff.spacearmada.actors.ships.player.PlayerShipFactory;
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

/**
 * Created by John on 29/08/2017.
 * Handles all the game logic.
 */

public class GameState implements State {

    private StateManager stateManager;
    private EnemyShipManager enemyShipManager;
    private InputControlsManager inputControlsManager;
    private CollisionDetectionManager collisionDetectionManager;
    private ProjectileManager projectileManager;
    private ImageLoader imageLoader;
    private GameView gameView;
    private ExecutorService enemySpawningService = Executors.newCachedThreadPool();
    private ScheduledExecutorService animationService = Executors.newScheduledThreadPool(4);
    private BackgroundTiles backgroundTiles;
    private int canvasWidth, canvasHeight;
    private PlayerShip playerShip;
    private Context context;
    private MusicPlayer musicPlayer;
    private Rect gameScreenBounds;
    private TimedActionManager timedActionManager;
    private GameStateHandler currentGameStateHandler;
    private EnemyCreatorTask enemyCreatorTask;

    public GameState(StateManager stateManager, Context context, int canvasWidth, int canvasHeight){
        this.stateManager = stateManager;
        musicPlayer = new MusicPlayer(context);
        int gameScreenTop = 120;
        int gameScreenBottom = canvasHeight - 480;
        gameScreenBounds = new Rect(0,gameScreenTop, canvasWidth, gameScreenBottom);
        this.context = context;
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        imageLoader = new ImageLoader(context, canvasWidth, canvasHeight);
        timedActionManager = new TimedActionManager();
        initShipsControlsAndProjectiles();
        createEnemyThread();
        initAnimtionThread();
        initBackgroundTiles();
        initView();
        initMusicPlayer();
       this.currentGameStateHandler = new GamePlay(context, this);
    }


    public void update(){
        currentGameStateHandler.update();
    }


    public void draw(Canvas canvas, Paint paint){
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


    private void initShipsControlsAndProjectiles(){
        projectileManager = new ProjectileManager(context, gameScreenBounds, imageLoader);
        playerShip = PlayerShipFactory.createPlayerShip(context, gameScreenBounds, projectileManager, imageLoader);
        initControls();
        enemyShipManager = new EnemyShipManager(projectileManager, imageLoader, canvasHeight);
        collisionDetectionManager = new CollisionDetectionManager(playerShip, enemyShipManager, projectileManager);
    }

    private void initControls(){
        int dpadRadius = 150;
        int dpadCentreY = gameScreenBounds.bottom + ( canvasHeight - gameScreenBounds.bottom ) /2;
        int dpadCentreX = canvasWidth / 2;
        inputControlsManager = new InputControlsManager(context, canvasWidth, canvasHeight, playerShip);
        inputControlsManager.setDpadPosition(dpadCentreX, dpadCentreY, dpadRadius);
    }

    private void initBackgroundTiles(){
        Log.i("GameState", "Entered init background tiles");
        backgroundTiles = new BackgroundTiles(context, 4,1, canvasWidth, gameScreenBounds.top, gameScreenBounds.bottom);

        backgroundTiles.addTiles(
                R.drawable.level1_bg_1,
                R.drawable.level1_bg_2,
                R.drawable.level1_bg_3,
                R.drawable.level1_bg_4,
                R.drawable.level1_bg_5,
                R.drawable.level1_bg_6,
                R.drawable.level1_bg_7,
                R.drawable.level1_bg_8,
                R.drawable.level1_bg_9,
                R.drawable.level1_bg_10,
                R.drawable.level1_bg_11,
                R.drawable.level1_bg_12);
    }

    private void initMusicPlayer(){
        musicPlayer.playTrack(R.raw.bensound_straight);
    }

    private void log(String msg){
        Log.i("GameState", msg);
    }

    private void initView(){
        log("Entered initView()");
        gameView = new GameView(context, gameScreenBounds.top, gameScreenBounds.bottom);
        gameView.register(playerShip);
        gameView.register(inputControlsManager);
        gameView.register(enemyShipManager);
        gameView.register(projectileManager);
        gameView.setBackgroundTiles(backgroundTiles);
        log("background tiles assigned to view");
    }


    private void initAnimtionThread(){
        Log.i("GameState", "Entered initAnimationThread");
        AnimatorTask animatorTask = new AnimatorTask(enemyShipManager);
        animationService.scheduleAtFixedRate(animatorTask, 0,100, TimeUnit.MILLISECONDS);
    }

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

    public void handleTouchPoints(List<TouchPoint> touchPoints){
        currentGameStateHandler.handleTouchPoints(touchPoints);
    }

    public void setCurrentGameStateHandler(GameStateHandler gameStateHandler){
        this.currentGameStateHandler = gameStateHandler;
    }

    public BackgroundTiles getBackgroundTiles() {
        return backgroundTiles;
    }
    public CollisionDetectionManager getCollisionDetectionManager() {
        return collisionDetectionManager;

    }
    public EnemyShipManager getEnemyShipManager() {
        return enemyShipManager;
    }
    public ImageLoader getImageLoader() {
        return imageLoader;
    }
    public GameView getGameView() {
        return gameView;
    }
    public InputControlsManager getInputControlsManager() {
        return inputControlsManager;
    }
    public ProjectileManager getProjectileManager() {
        return projectileManager;
    }
    public StateManager getStateManager() {
        return stateManager;
    }
    public GameStateHandler getCurrentGameStateHandler() {
        return currentGameStateHandler;
    }
    public PlayerShip getPlayerShip() {
        return playerShip;
    }
    public MusicPlayer getMusicPlayer(){
        return this.musicPlayer;
    }
}


interface GameStateHandler{
    void handleTouchPoints(List<TouchPoint> touchPoints);
    void draw(Canvas canvas, Paint paint);
    void update();
}


abstract class AbstractGameStateHandlerImpl implements GameStateHandler{

    InputControlsManager inputControlsManager;
    StateManager stateManager;
    MusicPlayer musicPlayer;
    GameView    gameView;
    BackgroundTiles backgroundTiles;
    EnemyShipManager enemyShipManager;
    PlayerShip playerShip;
    ProjectileManager projectileManager;
    CollisionDetectionManager collisionDetectionManager;
    Context context;
    GameState gameState;


    AbstractGameStateHandlerImpl(Context context, GameState gameState){
        this.gameState = gameState;
        this.context = context;
        this.backgroundTiles = gameState.getBackgroundTiles();
        this.gameView = gameState.getGameView();
        this.enemyShipManager = gameState.getEnemyShipManager();
        this.collisionDetectionManager = gameState.getCollisionDetectionManager();
        this.projectileManager = gameState.getProjectileManager();
        this.playerShip = gameState.getPlayerShip();
        this.inputControlsManager = gameState.getInputControlsManager();
        this.musicPlayer = gameState.getMusicPlayer();
        this.stateManager = gameState.getStateManager();
    }
}


class GamePlay extends AbstractGameStateHandlerImpl{

    public GamePlay(Context context, GameState gameState){
        super(context, gameState);
    }

    public void handleTouchPoints(List<TouchPoint> touchPoints){
        if(inputControlsManager == null){
            return;
        }
        inputControlsManager.process(touchPoints);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        gameView.draw(canvas,paint);
    }

    int gameOverTimer = 150;

    @Override
    public void update() {

        enemyShipManager.update();
        playerShip.update();
        projectileManager.update();
        collisionDetectionManager.detect();
        playerShip.updateAnimation();
        if(playerShip.isDead()){
            gameOverTimer--;
        }
        else{
            backgroundTiles.update();
        }
        if(gameOverTimer <= 0){
            gameState.setCurrentGameStateHandler( new GameEnding(context, gameState));
        }
    }

}
/*

    player ship has been destroyed
        controls are locked
        control panel disappears
        main music stops
        game-over music plays
        game-over message appears x seconds after ship has been destroyed
 */
class GameEnding extends AbstractGameStateHandlerImpl{

    GameEnding(Context context, GameState gameState){
        super(context, gameState);
        init();
    }

    public void init(){

        musicPlayer.release();
        musicPlayer.playTrack(R.raw.game_over_1, false);
    }

    @Override
    public void handleTouchPoints(List<TouchPoint> touchPoints) {
        if(nextStateCountdown <= 0){
            musicPlayer.release();
            gameState.destroy();
            Log.i("GameEnding", "gameState.destroy() called.");
            stateManager.setState(StateManager.StateCode.TITLE);

        }
    }

    private int nextStateCountdown = 20;

    @Override
    public void update() {
        nextStateCountdown--;
        enemyShipManager.update();
        projectileManager.update();
        if(nextStateCountdown <= 0){

           // gameState.setCurrentGameStateHandler(new GameOverScreen(context, gameState));
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        gameView.drawGameOver(canvas, paint);
    }
}
