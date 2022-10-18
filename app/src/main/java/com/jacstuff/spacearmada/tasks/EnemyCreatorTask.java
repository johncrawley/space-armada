package com.jacstuff.spacearmada.tasks;

import android.util.Log;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.jacstuff.spacearmada.actors.ships.enemies.EnemyShipManager;

/**
 * Created by John on 15/09/2017.
 * adds random enemies via the EnemyShipManager.
 * the timing of creation is random and the
 */

public class EnemyCreatorTask implements Runnable{

    private final Random randomEnemyStartingX;
    private final Random randomNumberOfEnemiesToSpawn;
    private final Random randomEnemyStartingYOffset;
    private int gameWindowBeginX;
    private int gameWindowEndX;
    private int maxPauseBetweenEnemies;
    private final EnemyShipManager enemyShipManager;
    private int currentMaxNumberOfEnemies;
    private final int minWidthBetweenEnemies = 50;
    private boolean isGameStillActive = true;
    private int minPauseBetweenEnemies = 2000;

    public EnemyCreatorTask(EnemyShipManager enemyShipManager, int gameWindowWidth, int borderWidth){
        randomEnemyStartingX = new Random(1);
        randomNumberOfEnemiesToSpawn = new Random(5432);
        randomEnemyStartingYOffset = new Random(9876);
        determineGameWindowCoordinates(gameWindowWidth, borderWidth);
        this.enemyShipManager = enemyShipManager;
    }


    public void run(){
        while(isGameStillActive){
            spawnEnemies();
            try {
                Thread.sleep(getNextRandomSleepTime());
            }catch (InterruptedException e){
                Log.i("EnemyCreatorTask run()", "thread was interrupted");
            }
        }
    }


    public void setMinPauseBetweenEnemies(int seconds){
        minPauseBetweenEnemies = seconds * 1000;
    }


    public void setMaxPauseBetweenEnemies(int seconds){
        maxPauseBetweenEnemies = seconds * 1000;
    }


    public void setInactive(){
        isGameStillActive = false;
    }


    private void spawnEnemies(){
        int numberOfEnemiesToSpawn = getNextRandomNumberOfEnemies();
        int firstShipX =  getNextRandomStartingX(numberOfEnemiesToSpawn);
        int defaultStartingY = -70;
        int startingY = defaultStartingY - generateRandomShipYOffset();
        for(int i=1 ; i <= numberOfEnemiesToSpawn; i++) {
            spawnEnemy(i, firstShipX, startingY);
        }
    }


    private void spawnEnemy(int index, int firstShipX, int startingY){
        int shipWidth = 50;
        int shipWidthFactor = shipWidth * index;
        int spaceBetweenFactor = minWidthBetweenEnemies * index;
        int shipStartingX = firstShipX + spaceBetweenFactor + shipWidthFactor;
        enemyShipManager.createShip(shipStartingX, startingY);
    }


    private int generateRandomShipYOffset(){
        return randomEnemyStartingYOffset.nextInt(10) * 10;
    }


    private int getNextRandomNumberOfEnemies(){
        int maxEnemiesPerSpawn = 5;
        currentMaxNumberOfEnemies = 1 + randomNumberOfEnemiesToSpawn.nextInt(maxEnemiesPerSpawn);
        return currentMaxNumberOfEnemies;
    }


    // 1 enemy : place anywhere within boundaries
    // 2 enemies: place equal distance from the centre
    /*
        alternative: generate d1: distance value between ships
        ship1x = random between left border and (rightBorder - (ship1Width + ship2Width + d1 ships)
        ship2x = ship1x + ship1Width + d1

        3 enemies:
            enemy 1 in centre
            generate d1 : distance between enemies
                           random(minDistance -(shipWidth + borderWidth)
            enemy 2  = enemy1x - d1
            enemy 3 = enemy1 x + d1

         3 enemies alternative
            generate d1: distance value between ships
            ship1x = random beween left border and (rightBorder - ( sum(shipWidths) + d1 * x
     */


    private int getNextRandomStartingX(int currentNumEnemies){
        currentMaxNumberOfEnemies = currentNumEnemies;
        int maximumX = (gameWindowEndX/currentMaxNumberOfEnemies) + minWidthBetweenEnemies;
        return gameWindowBeginX + randomEnemyStartingX.nextInt(maximumX);
    }


    private int getNextRandomSleepTime(){
        return  ThreadLocalRandom.current().nextInt(minPauseBetweenEnemies, maxPauseBetweenEnemies);
    }


    private void determineGameWindowCoordinates(int gameWindowWidth, int borderWidth){
        gameWindowBeginX = borderWidth;
        gameWindowEndX = gameWindowWidth - borderWidth;
    }

}
