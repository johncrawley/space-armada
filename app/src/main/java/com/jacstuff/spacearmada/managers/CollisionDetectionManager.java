package com.jacstuff.spacearmada.managers;

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

    private final EnemyShipManager enemyShipManager;
    private final PlayerShip playerShip;
    private final ProjectileManager projectileManager;
    private int currentIteration = 0;

    public CollisionDetectionManager(PlayerShip playerShip, EnemyShipManager enemyShipManager, ProjectileManager projectileManager){
        this.playerShip = playerShip;
        this.enemyShipManager = enemyShipManager;
        this.projectileManager = projectileManager;
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
        int detectionFrequency = 3;
        if(currentIteration < detectionFrequency){
            return true;
        }
        currentIteration = 0;
        return false;
    }


    private void detectPlayerAndEnemyShipCollisions(){
        for(EnemyShip enemyShip: enemyShipManager.getEnemyShips()) {
            playerShip.checkForCollision(enemyShip);
        }
    }


    private void detectPlayerAndEnemyBulletCollisions(){
        for(Projectile projectile : projectileManager.getProjectiles()){
            playerShip.checkForCollision(projectile);
        }
    }


    private void detectEnemyShipsAndPlayerBulletCollisions() {
        for (Projectile projectile : projectileManager.getProjectiles()) {
            detectPlayerProjectileCollisionWithEnemies(projectile);
        }
    }


    private void detectPlayerProjectileCollisionWithEnemies(Projectile projectile){
        if(projectile == null){
            return;
        }
        for (EnemyShip enemyShip : enemyShipManager.getEnemyShips()) {
            if(enemyShip == null){
                continue;
            }
            checkForCollision(projectile, enemyShip);
        }
    }


    private void checkForCollision(Projectile projectile, EnemyShip enemyShip){
        enemyShip.checkForCollision(projectile);
        if(enemyShip.getEnergy().isDepleted() && enemyShip.isAlive()){
            playerShip.addToScore(enemyShip.getPoints());
        }
    }

}
