package com.jacstuff.spacearmada.service;

import android.graphics.Rect;

import com.jacstuff.spacearmada.service.PlayerShip;
import com.jacstuff.spacearmada.view.fragments.GameView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Game {

        private GameService gameService;
        private PlayerShip playerShip;
        private GameView gameView;
        private ScheduledFuture<?> gameUpdateFuture;
        private ScheduledExecutorService scheduledExecutorService;
        private int enemyX = 800;
        private int enemyY;
        private int enemyDirection = 1;
        private int enemyYLimit = 1600;
        private AtomicBoolean isRunning = new AtomicBoolean(false);


        public Game(){
                Rect screenBounds = new Rect(0,0,1000,1000);
                playerShip = new PlayerShip(50,50,screenBounds );
                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        }


        public void start(){
                if(isRunning.get()){
                        return;
                }
                isRunning.set(true);
               gameUpdateFuture = scheduledExecutorService.scheduleAtFixedRate(this::updateEnemyShip, 0,20, TimeUnit.MILLISECONDS);
        }


        private void updateEnemyShip(){
                int enemyProgressPerFrame = 5;
                enemyY += enemyDirection * enemyProgressPerFrame;
                if(enemyY > enemyYLimit || enemyY < 0){
                        enemyDirection *= -1;
                }
//                log("Entered updateEnemyShip()");
                gameView.updateEnemyShip(enemyX, enemyY);
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

        private void log(String msg){
                System.out.println("^^^ Game: " + msg);
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
                start();
        }
}
