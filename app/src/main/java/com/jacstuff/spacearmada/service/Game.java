package com.jacstuff.spacearmada.service;

import android.graphics.Point;
import android.graphics.Rect;

import com.jacstuff.spacearmada.view.fragments.game.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Game {

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
        private final StarManager starManager;

        public Game(){
                screenBounds = new Rect(0,0,1000,1000);
                playerShip = new PlayerShip(50,50,screenBounds );
                starManager = new StarManager(gameView, screenBounds);
                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                starManager.generateStars();
        }


        public void start(){
                if(isRunning.get()){
                        return;
                }
                isRunning.set(true);
               gameUpdateFuture = scheduledExecutorService.scheduleAtFixedRate(this::update, 0,20, TimeUnit.MILLISECONDS);
        }


        private void update(){
                updateEnemyShip();
                starManager.updateStarsOnView();
        }


        private void updateEnemyShip(){
                int enemyProgressPerFrame = 5;
                enemyY += enemyDirection * enemyProgressPerFrame;
                if(enemyY > enemyYLimit || enemyY < 0){
                        enemyDirection *= -1;
                }
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


        private void log(String msg){
                System.out.println("^^^ Game: " + msg);
        }

}
