package com.jacstuff.spacearmada.service.ships.collisions;

import com.jacstuff.spacearmada.service.Game;
import com.jacstuff.spacearmada.service.ships.AbstractItem;
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


    public void detect() {
        if (isReadyToDetect()) {
            detectPlayerAndEnemyShipCollisions();
           // detectPlayerAndEnemyBulletCollisions();
            detectEnemyShipsAndPlayerBulletCollisions();
        }
    }


    private boolean isReadyToDetect(){
        int detectionFrequency = 5;
        counter++;
        if(counter >= detectionFrequency){
            counter = 0;
            return true;
        }
        return false;
    }


    private void detectPlayerAndEnemyShipCollisions(){
        for(EnemyShip enemyShip: enemyShipManager.getEnemyShips()) {
            checkForCollision(playerShip, enemyShip);
        }
    }


    private void detectPlayerAndEnemyBulletCollisions(){
        for(Projectile projectile : projectileManager.getProjectiles()){
          //  playerShip.checkForCollision(projectile);
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
            checkForCollision(projectile, enemyShip);
        }
    }


    private void checkForCollision(Projectile projectile, EnemyShip enemyShip){
        if(enemyShip == null){
            return;
        }
        checkForCollision(enemyShip, projectile);
        if(enemyShip.isEnergyDepleted()){
            game.addToScore(enemyShip.getPoints());
        }
    }


    public void checkForCollision(AbstractItem item1, AbstractItem item2){
        if(item1 == null || item2 == null){
            return;
        }
        if(isIntersecting(item1, item2)){
            item1.getEnergy().collideWith(item2.getEnergy());
        }
    }


    public boolean isIntersecting(AbstractItem item1, AbstractItem item2){
        return  item1.getBounds().intersect(item2.getBounds());
    }


    private void log(String msg){
        System.out.println("^^^ CollisionDetector : " + msg);
    }

}