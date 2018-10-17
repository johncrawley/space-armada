package com.jacstuff.spacearmada.tasks;

import android.util.Log;

import com.jacstuff.spacearmada.actors.ships.enemies.EnemyShipManager;

public class AnimatorTask implements Runnable{


    private EnemyShipManager enemyShipManager;
    int logInterval = 100;
    int logCounter = 0;

    public AnimatorTask(EnemyShipManager enemyShipManager){
        this.enemyShipManager = enemyShipManager;
    }


    public void run(){

        try {
            int shipCount = enemyShipManager.updateAnimations(1);
            logOnInterval("Animator execution");
        }catch(Exception e){
            Log.i("AnimatorTask", "Exception : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void logOnInterval(String msg) {

        if (logCounter >= logInterval) {
            Log.i("Animator Task", msg);
            logCounter = 0;
        }
        logCounter++;
    }
}

