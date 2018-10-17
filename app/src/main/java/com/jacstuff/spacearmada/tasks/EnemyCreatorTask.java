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


    private Random randomEnemyStartingX;
    private Random randomNumberOfEnemiesToSpawn;
    private Random randomEnemyStartingYOffset;
    private int gameWindowBeginX;
    private int gameWindowEndX;
    private int maxPauseBetweenEnemies;
    private EnemyShipManager enemyShipManager;
    private int maxEnemiesPerSpawn = 5;
    private int currentMaxNumberOfEnemies;
    private int currentStartingX;
    private int minSpaceBetweenEnemies = 20;
    private int minWidthBetweenEnemies = 50;
    private int minPauseBetweenEnemies = 2000;
    private boolean isGameStillActive = true;

    public EnemyCreatorTask(EnemyShipManager enemyShipManager, int gameWindowWidth, int borderWidth){
        randomEnemyStartingX = new Random(1);
        randomNumberOfEnemiesToSpawn = new Random(5432);
        randomEnemyStartingYOffset = new Random(9876);
        determineGameWindowCoordinates(gameWindowWidth, borderWidth);

        this.enemyShipManager = enemyShipManager;
    }

    public void setMinPauseBetweenEnemies(int seconds){
        this.minSpaceBetweenEnemies = seconds * 1000;
    }

    public void setMaxPauseBetweenEnemies(int seconds){
        this.maxPauseBetweenEnemies = seconds * 1000;
    }

    public void setInactive(){
        isGameStillActive = false;
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

    private void spawnEnemies(){
        int numberOfEnemiesToSpawn = getNextRandomNumberOfEnemies();

        int firstShipX =  getNextRandomStartingX(numberOfEnemiesToSpawn);
        int shipWidth = 50;
        int defaultStartingY = -70;
        int startingY = defaultStartingY - generateRandomShipYOffset();
        for(int i=1 ; i <= numberOfEnemiesToSpawn; i++) {
            int shipWidthFactor = shipWidth * i;
            int spaceBetweenFactor = minWidthBetweenEnemies * i;
            int shipStartingX = firstShipX + spaceBetweenFactor + shipWidthFactor;

           // Log.i("EnemyCreatorTask spawn", "shipX: " + shipStartingX + "  widthFactor: "+ shipWidthFactor + " spaceFactor: "+  spaceBetweenFactor);
            //enemyShipManager.createShip(shipStartingX);
            enemyShipManager.createShip(shipStartingX, startingY);
        }
    }

    // will give a
    private int generateRandomShipYOffset(){
        return randomEnemyStartingYOffset.nextInt(10) * 10;
    }
    private int getNextRandomNumberOfEnemies(){
        currentMaxNumberOfEnemies = 1 + randomNumberOfEnemiesToSpawn.nextInt(maxEnemiesPerSpawn);
        return currentMaxNumberOfEnemies;
    }

    // This is an involved problem
    // For each spawning, the idea of randomness is attractive
    //  because we dont want to have to specify each initial x individually
    // However, symmetry between spawning enemies is also desired
    //

    // 1 enemy : place anywhere within boundaries
    // 2 enemies: place equal distace from the centre
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
       // int maxEnemiesPerSpawn
        currentMaxNumberOfEnemies = currentNumEnemies;
        int maximumX = (gameWindowEndX/currentMaxNumberOfEnemies) + minWidthBetweenEnemies;
        currentStartingX = gameWindowBeginX + randomEnemyStartingX.nextInt(maximumX);
        return currentStartingX;
    }

    private int getNextRandomSleepTime(){
        return  ThreadLocalRandom.current().nextInt(minPauseBetweenEnemies, maxPauseBetweenEnemies);
    }

    private void determineGameWindowCoordinates(int gameWindowWidth, int borderWidth){
        gameWindowBeginX = borderWidth;
        gameWindowEndX = gameWindowWidth - borderWidth;
    }

}
