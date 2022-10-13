package com.jacstuff.spacearmada.game;

import com.jacstuff.spacearmada.actors.ships.enemies.EnemyShipManager;
import com.jacstuff.spacearmada.tasks.EnemyCreatorTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EnemySpawner {

    private final ExecutorService enemySpawningService;
    private final EnemyShipManager enemyShipManager;
    private final int canvasWidth, borderWidth;
    private EnemyCreatorTask enemyCreatorTask;

    public EnemySpawner(EnemyShipManager enemyShipManager, int canvasWidth, int borderWidth){
        this.enemyShipManager = enemyShipManager;
        this.canvasWidth = canvasWidth;
        this.borderWidth = borderWidth;
        enemySpawningService = Executors.newCachedThreadPool();
        createEnemyTask();
    }

    public void startTask(){
        enemySpawningService.execute(enemyCreatorTask);
    }

    public void onPause(){
        enemySpawningService.shutdown();
    }


    public void onResume(){
        startTask();
    }


    private void createEnemyTask(){
        enemyCreatorTask = new EnemyCreatorTask(enemyShipManager, canvasWidth, borderWidth);
        enemyCreatorTask.setMinPauseBetweenEnemies(2);
        enemyCreatorTask.setMaxPauseBetweenEnemies(4);
    }


    public void onDestroy(){
        enemySpawningService.shutdownNow();
        boolean result = false;
        try {
            result = enemySpawningService.awaitTermination(200L, TimeUnit.MILLISECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("termination result: " + result);
    }

}
