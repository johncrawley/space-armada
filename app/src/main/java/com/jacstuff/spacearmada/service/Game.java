package com.jacstuff.spacearmada.service;

import android.graphics.Rect;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.actors.ships.ControllableShip;
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
        private int enemyX = 800;
        private int enemyY;
        private int enemyDirection = 1;
        private int enemyYLimit = 1600;
        private final AtomicBoolean isRunning = new AtomicBoolean(false);

        private final Rect screenBounds;
        private StarManager starManager;


        public Game(){
                screenBounds = new Rect(0,0,1000,1000);
                playerShip = new PlayerShip(50,50,screenBounds );
                starManager = new StarManager( screenBounds);
                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        }


        public void start(){
                if(isRunning.get()){
                        return;
                }
                isRunning.set(true);
               gameUpdateFuture = scheduledExecutorService.scheduleAtFixedRate(this::updateItems, 0,20, TimeUnit.MILLISECONDS);
        }


        private void updateItems(){
                updateEnemyShip();
                starManager.updateStarsOnView();
                updateShip();
        }


        private void updateEnemyShip(){
                int enemyProgressPerFrame = 5;
                enemyY += enemyDirection * enemyProgressPerFrame;
                if(enemyY > enemyYLimit || enemyY < 0){
                        enemyDirection *= -1;
                }
                gameView.updateEnemyShip(enemyX, enemyY);
        }


        private void updateShip(){
                playerShip.update();
                if(playerShip.hasPositionChanged()){
                       gameView.updateShip(playerShip.getX(), playerShip.getY());
                }
        }


        public void quit(){
                isRunning.set(false);
                gameUpdateFuture.cancel(false);
        }


        public void init(GameService gameService){
            this.gameService = gameService;
        }


        public void moveUp(){
                playerShip.moveUp();
                updatePlayerShip();
        }


        public void moveDown(){
                log("Entered moveDown()");
                playerShip.moveDown();
                updatePlayerShip();
        }


        public void moveLeft(){
                playerShip.moveLeft();
                updatePlayerShip();
        }


        public void moveRight(){
                playerShip.moveRight();
                updatePlayerShip();
        }


        public void updatePlayerShip(){
                gameService.updatePlayerShip(playerShip.getX(), playerShip.getY());
        }


        public void setGameView(GameView gameView){
                this.gameView = gameView;
                starManager.setGameView(gameView);
                start();
        }


        private void log(String msg){
                System.out.println("^^^ Game: " + msg);
        }


        @Override
        public void fire() {
                playerShip.fire();
        }


        @Override
        public void setDirection(Direction direction) {
                log("Entered setDirection() direction: " + direction.name());
                playerShip.setDirection(direction);
        }


        @Override
        public void releaseFire() {
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
