package com.jacstuff.spacearmada.service;

import android.graphics.RectF;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.actors.ships.ControllableShip;
import com.jacstuff.spacearmada.service.ships.EnemyShipManager;
import com.jacstuff.spacearmada.service.ships.PlayerShip;
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


        public Game(){
                screenBounds = createScreenBounds();
                playerShip = new PlayerShip(50,50, screenBounds);
                starManager = new StarManager();
                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                enemyShipManager = new EnemyShipManager(screenBounds);
        }


        public void setBounds(float x, float y, int width, int height){
                initBounds(x, y, width, height);
                enemyShipManager.setScreenBounds(screenBounds);
                playerShip.setScreenBounds(screenBounds);
                starManager.setBoundsAndGenerateStars(screenBounds);
        }


        private void initBounds(float x, float y, int width, int height){
                screenBounds = new RectF();
                screenBounds.left = x;
                screenBounds.top = y;
                screenBounds.right = x + width;
                screenBounds.bottom = y + height;
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
                updateEnemyShips();
                starManager.updateStarsOnView();
                updateShip();
        }


        private void updateEnemyShips(){
                gameView.updateItems(enemyShipManager.updateAndGetChanges());
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
