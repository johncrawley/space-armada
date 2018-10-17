package com.jacstuff.spacearmada.managers;

import android.util.Log;

import com.jacstuff.spacearmada.actors.projectiles.ProjectileManager;
import com.jacstuff.spacearmada.actors.ships.enemies.EnemyShip;
import com.jacstuff.spacearmada.actors.ships.enemies.EnemyShipManager;
import com.jacstuff.spacearmada.actors.ships.player.PlayerShip;
import com.jacstuff.spacearmada.actors.projectiles.Projectile;

/**
 * Created by John on 30/08/2017.
 * Detects collisions between ships and projectiles
 */

public class CollisionDetectionManager {

    private EnemyShipManager enemyShipManager;
    private PlayerShip playerShip;
    private ProjectileManager projectileManager;
    private int detectionFrequency = 3; // the number of iterations that detections will be done - for efficiency
    private int currentIteration = 0;

    public CollisionDetectionManager(PlayerShip playerShip, EnemyShipManager enemyShipManager, ProjectileManager projectileManager){
        this.playerShip = playerShip;
        this.enemyShipManager = enemyShipManager;
        this.projectileManager = projectileManager;

        if(playerShip == null){
            log("player ship is null!");
        }
        if(enemyShipManager == null){
            log("Enemy ship manager is null!");
        }
        if(projectileManager == null){
            log("projectile manager is null!");
        }
        if(playerShip != null && enemyShipManager != null && projectileManager != null){
            log("ColisionDetection manager is initiated ok!");
        }
    }

    private void log(String msg){

        Log.i("CollisionMngr", msg);
    }


    public void detect(){
        if(isDetectionSkippedOnThisUpdate()){
            return;
        }
        detectPlayerAndEnemyShipCollisions();
        detectPlayerAndEnemyBulletCollisions();
        detectEnemyShipsAndPlayerBulletCollisions();
    }

    private boolean isDetectionSkippedOnThisUpdate(){
        currentIteration++;
        if(currentIteration < detectionFrequency){
            return true;
        }
        currentIteration = 0;
        return false;
    }


    private void detectPlayerAndEnemyShipCollisions(){
        for(EnemyShip enemyShip: enemyShipManager.getEnemyShips()) {
           // log("checking for playerShip collision with enemyShip...");
            playerShip.checkForCollision(enemyShip);
           // log("Checking for playerShip collision with enemyShip complete!");
        }
    }

    private void detectPlayerAndEnemyBulletCollisions(){
        for(Projectile projectile : projectileManager.getProjectiles()){
            playerShip.checkForCollision(projectile);
        }
    }
    private void detectEnemyShipsAndPlayerBulletCollisions() {
        for (Projectile projectile : projectileManager.getProjectiles()) {

            for (EnemyShip enemyShip : enemyShipManager.getEnemyShips()) {
                if(enemyShip == null || projectile == null){
                    continue;
                }
                enemyShip.checkForCollision(projectile);
                if(enemyShip.hasNoEnergy() && enemyShip.isAlive()){
                  playerShip.addToScore(enemyShip.getPoints());
                }
            }
        }
    }



}
