package com.jacstuff.spacearmada.service.collisions;

import com.jacstuff.spacearmada.service.Game;
import com.jacstuff.spacearmada.service.ships.AbstractItem;
import com.jacstuff.spacearmada.service.ships.EnemyShip;
import com.jacstuff.spacearmada.service.ships.EnemyShipManager;
import com.jacstuff.spacearmada.service.ships.PlayerShip;
import com.jacstuff.spacearmada.service.ships.weapons.Projectile;
import com.jacstuff.spacearmada.service.ships.weapons.ProjectileManager;
import com.jacstuff.spacearmada.service.sound.SoundPlayer;

public class CollisionDetector {

    private final EnemyShipManager enemyShipManager;
    private final PlayerShip playerShip;
    private final ProjectileManager projectileManager;
    private int counter = 0;
    private final Game game;
    private SoundPlayer soundPlayer;


    public CollisionDetector(Game game, PlayerShip playerShip, EnemyShipManager enemyShipManager, ProjectileManager projectileManager){
        this.game = game;
        this.playerShip = playerShip;
        this.enemyShipManager = enemyShipManager;
        this.projectileManager = projectileManager;
    }


    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }


    public void detect() {
        if (isReadyToDetect()) {
            detectPlayerShipAndEnemyShipCollisions();
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


    private void detectPlayerShipAndEnemyShipCollisions(){
        for(EnemyShip enemyShip: enemyShipManager.getEnemyShips()) {
            checkForCollision(playerShip, enemyShip, ()->{
                handleEnemyShipEnergyDepleted(enemyShip);
                game.updatePlayerHealthOnView();
            });
        }
    }


    private void detectPlayerAndEnemyBulletCollisions(){
        for(Projectile projectile : projectileManager.getProjectiles()){
            //playerShip.checkForCollision(projectile);
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
        handleEnemyShipEnergyDepleted(enemyShip);
        if(projectile.isEnergyDepleted()){
            projectile.getDrawInfo().markAsDestroyed();
        }
    }


    public void checkForCollision(AbstractItem item1, AbstractItem item2){
        checkForCollision(item1, item2, ()->{});
    }



    public void checkForCollision(AbstractItem item1, AbstractItem item2, Runnable runnable){
        if(item1 == null || item2 == null){
            return;
        }
        if(isIntersecting(item1, item2)){
            item1.getEnergy().collideWith(item2.getEnergy());
            runnable.run();
        }
    }


    private void handleEnemyShipEnergyDepleted(AbstractItem abstractItem){
        EnemyShip enemyShip = (EnemyShip) abstractItem;
        if(enemyShip.isEnergyDepleted()){
            enemyShip.getDrawInfo().markAsDestroyed();
            game.addToScore(enemyShip.getPoints());
            soundPlayer.playSound(enemyShip.getExplosionSound());
        }
    }


    public boolean isIntersecting(AbstractItem item1, AbstractItem item2){
        return  item1.getBounds().intersect(item2.getBounds());
    }


    private void log(String msg){
        System.out.println("^^^ CollisionDetector : " + msg);
    }

}