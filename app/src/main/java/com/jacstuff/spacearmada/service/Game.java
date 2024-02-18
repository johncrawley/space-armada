package com.jacstuff.spacearmada.service;

import android.graphics.RectF;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.actors.ships.ControllableShip;
import com.jacstuff.spacearmada.view.fragments.game.DrawInfo;
import com.jacstuff.spacearmada.view.fragments.game.GameView;
import com.jacstuff.spacearmada.view.fragments.game.ItemType;

import java.util.Random;
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

        private final RectF screenBounds;
        private final StarManager starManager;
        private final Random enemyShipRandom;


        public Game(){
                screenBounds = createScreenBounds();
                playerShip = new PlayerShip(50,50,screenBounds );
                starManager = new StarManager(screenBounds);
                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                enemyShipRandom = new Random(2);
        }


        private RectF createScreenBounds(){
                RectF bounds = new RectF();
                bounds.left = 0f;
                bounds.right = 1000f;
                bounds.top = 0f;
                bounds.bottom = 1000f;
                return bounds;
        }


        public void adjustSizesBasedOn(int smallestDimension){
                playerShip.setSizeBasedOn(smallestDimension);
                gameView.setShipSize((int)playerShip.getWidth(), (int)playerShip.getHeight());
        }


        public void start(){
                if(isRunning.get()){
                        return;
                }
                isRunning.set(true);
                gameUpdateFuture = scheduledExecutorService.scheduleAtFixedRate(this::updateItems, 0,16, TimeUnit.MILLISECONDS);
        }


        private void updateItems(){
                updateEnemyShip();
                starManager.updateStarsOnView();
                updateShip();
        }


        private void createEnemyShip(){
                DrawInfo drawInfo = new DrawInfo(ItemType.ENEMY_SHIP_1, System.currentTimeMillis());
                drawInfo.setXY(getEnemyShipRandomStartingX(), -150);
                drawInfo.setDimensions(100, 170);
                gameView.createItem(drawInfo);
        }


        private int getEnemyShipRandomStartingX(){
           return enemyShipRandom.nextInt(((int)screenBounds.right - 50));
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
                       gameView.updateShipPosition(playerShip.getX(), playerShip.getY());
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
