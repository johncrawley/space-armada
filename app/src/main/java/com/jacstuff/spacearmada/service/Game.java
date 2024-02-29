package com.jacstuff.spacearmada.service;

import android.graphics.Rect;
import android.graphics.RectF;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.actors.ships.ControllableShip;
import com.jacstuff.spacearmada.service.ships.EnemyShipManager;
import com.jacstuff.spacearmada.service.ships.PlayerShip;
import com.jacstuff.spacearmada.service.collisions.CollisionDetector;
import com.jacstuff.spacearmada.service.ships.weapons.ProjectileManager;
import com.jacstuff.spacearmada.service.sound.MusicPlayer;
import com.jacstuff.spacearmada.service.sound.SoundPlayer;
import com.jacstuff.spacearmada.view.fragments.game.GameView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Game implements ControllableShip {

        private GameService gameService;
        private final PlayerShip playerShip;
        private GameView gameView;
        private ScheduledFuture<?> gameUpdateFuture;
        private final ScheduledExecutorService scheduledExecutorService;
        private final AtomicBoolean isRunning = new AtomicBoolean(false);

        private RectF screenBounds;
        private final StarManager starManager;
        private final EnemyShipManager enemyShipManager;
        private final ProjectileManager projectileManager;
        private final CollisionDetector collisionDetector;
        private int fireCounter = 0;
        private int score;
        private SoundPlayer soundPlayer;
        private MusicPlayer musicPlayer;


        public Game(){
                projectileManager = new ProjectileManager();
                playerShip = new PlayerShip(50,50);
                playerShip.initWeapons(projectileManager);
                starManager = new StarManager();
                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                enemyShipManager = new EnemyShipManager();
                collisionDetector = new CollisionDetector(this, playerShip, enemyShipManager, projectileManager);
        }


        public void init(GameService gameService, MusicPlayer musicPlayer, SoundPlayer soundPlayer){
                this.musicPlayer = musicPlayer;
                this.soundPlayer = soundPlayer;
                this.gameService = gameService;
        }


        public void setBounds(Rect gamePaneRect, int smallestDimension){
                initBounds(gamePaneRect);
                enemyShipManager.setScreenBounds(screenBounds);
                playerShip.setScreenBoundsAndSize(screenBounds, smallestDimension);
                gameView.setShipSize((int)playerShip.getWidth(), (int)playerShip.getHeight());
                starManager.setBoundsAndGenerateStars(screenBounds);
                projectileManager.setBounds(screenBounds);
        }


        private void initBounds(Rect gamePaneRect){
                screenBounds = new RectF();
                screenBounds.left = gamePaneRect.left;
                screenBounds.top = gamePaneRect.top;
                screenBounds.right = gamePaneRect.right;
                screenBounds.bottom = gamePaneRect.bottom;
        }


        public void start(){
                if(isRunning.get()){
                        return;
                }
                isRunning.set(true);
                gameUpdateFuture = scheduledExecutorService.scheduleAtFixedRate(this::updateItems, 0,16, TimeUnit.MILLISECONDS);
        }


        private void updateItems(){
                if(gameView == null){
                        return;
                }
                updateEnemyShips();
                updateStars();
                updateShip();
                updateProjectiles();
                enemyShipManager.removeEnemiesIfDestroyed();
                projectileManager.removeProjectilesIfDestroyed();
                collisionDetector.detect();
        }


        public int getScore(){
                return score;
        }


        private void setGameOver(){
                gameView.onGameOver();
                musicPlayer.playGameOverMusic();
        }


        private void updateStars(){
               gameView.updateStars(starManager.updateAndGetStars());
        }


        private void updateEnemyShips(){
              gameView.updateItems(enemyShipManager.updateAndGetChanges());
        }


        private void updateProjectiles(){
                gameView.updateProjectiles(projectileManager.update());
        }


        private void updateShip(){
                playerShip.update();
                if(playerShip.hasPositionChanged()){
                       gameView.updateShipPosition(playerShip.getX(), playerShip.getY());
                }
        }


        public void quit(){
                isRunning.set(false);
                if(gameUpdateFuture != null && !gameUpdateFuture.isCancelled()){
                        gameUpdateFuture.cancel(false);
                }
        }


        public void onUnbind(){
                if(gameUpdateFuture != null && !gameUpdateFuture.isCancelled()){
                        gameUpdateFuture.cancel(false);
                }
        }


        public void addToScore(int value){
                this.score += value;
        }


        public void setGameView(GameView gameView){
                this.gameView = gameView;
                start();
        }


        private void log(String msg){
                System.out.println("^^^ Game: " + msg);
        }


        @Override
        public void fire() {
                log("Entered fire()");
                playerShip.fire();
        }


        @Override
        public void setDirection(Direction direction) {
                playerShip.setDirection(direction);
        }


        @Override
        public void releaseFire() {
                log("Entered releaseFire()");
                playerShip.releaseFire();
        }


        @Override
        public void stopMoving() {
                playerShip.stopMoving();
        }


        @Override
        public void update() {

        }
}
