package com.jacstuff.spacearmada.service.ships.collisions;


import com.jacstuff.spacearmada.service.Game;
import com.jacstuff.spacearmada.service.ships.EnemyShip;
import com.jacstuff.spacearmada.service.ships.EnemyShipManager;
import com.jacstuff.spacearmada.service.ships.PlayerShip;
import com.jacstuff.spacearmada.service.ships.weapons.Projectile;
import com.jacstuff.spacearmada.service.ships.weapons.ProjectileManager;

public class CollisionDetector {

    private final EnemyShipManager enemyShipManager;
    private final PlayerShip playerShip;
    private final ProjectileManager projectileManager;
    private int counter = 0;
    private final Game game;


    public CollisionDetector(Game game, PlayerShip playerShip, EnemyShipManager enemyShipManager, ProjectileManager projectileManager){
        this.game = game;
        this.playerShip = playerShip;
        this.enemyShipManager = enemyShipManager;
        this.projectileManager = projectileManager;
    }


    private void  detectAtIntervals(){
        int detectionFrequency = 5;
        counter++;
        if(counter >= detectionFrequency){
            counter = 0;
            detect();
        }
    }


    public void detect(){
        detectPlayerAndEnemyShipCollisions();
        detectPlayerAndEnemyBulletCollisions();
        detectEnemyShipsAndPlayerBulletCollisions();
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
        if(enemyShip.isEnergyDepleted()){
            game.addToScore(enemyShip.getPoints());
        }
    }

}