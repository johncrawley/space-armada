package com.jacstuff.spacearmada.service;

import android.graphics.Point;
import android.graphics.Rect;

import com.jacstuff.spacearmada.view.fragments.GameView;

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
        private List<Point> slowStars, fastStars;
        private Random random;
        private final Rect screenBounds;

        public Game(){
                screenBounds = new Rect(0,0,1000,1000);
                playerShip = new PlayerShip(50,50,screenBounds );
                random = new Random();
                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                generateStars();
        }


        public void start(){
                if(isRunning.get()){
                        return;
                }
                isRunning.set(true);
               gameUpdateFuture = scheduledExecutorService.scheduleAtFixedRate(this::updateEnemyShip, 0,20, TimeUnit.MILLISECONDS);
        }


        private void generateStars(){
                int numberOfStars = 20;
                slowStars = new ArrayList<>(numberOfStars);
                for(int i=0;i<numberOfStars; i++){
                        slowStars.add(createStarAtRandomCoordinate());
                }
        }


        private Point createStarAtRandomCoordinate(){
                int starX = getRandomXInBounds();
                int starY = getRandomNumberBetween(screenBounds.top, screenBounds.bottom);
                return new Point(starX, starY);
        }

        private int getRandomXInBounds(){
                return getRandomNumberBetween(screenBounds.left, screenBounds.right);
        }


        private void updateStars(){
                int starMovement = 2;
                for(Point star : slowStars){
                        star.y = star.y + starMovement;
                        if(star.y > screenBounds.bottom){
                                star.y = screenBounds.top - random.nextInt(20);
                                star.x = getRandomXInBounds();
                        }
                }
        }


        private int getRandomNumberBetween(int a, int b){
                return a + random.nextInt(b - a);
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
