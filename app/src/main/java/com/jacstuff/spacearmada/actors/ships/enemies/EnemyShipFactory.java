package com.jacstuff.spacearmada.actors.ships.enemies;


import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.animation.AnimationDefinitionGroup;
import com.jacstuff.spacearmada.actors.projectiles.ProjectileManager;
import com.jacstuff.spacearmada.image.BitmapLoader;


import java.util.HashMap;

import java.util.Map;

class EnemyShipFactory {

    private BitmapLoader bitmapLoader;
    private ProjectileManager projectileManager;
    private int speed;

    EnemyShipFactory(BitmapLoader bitmapLoader, ProjectileManager projectileManager){

        this.bitmapLoader = bitmapLoader;
        this.projectileManager = projectileManager;
        speed = 5;
    }


    EnemyShip createShip(int initialX, int initialY){
        int initialWidth = 20;
        int initialHeight = 35;

       return new EnemyShip(initialX, initialY, speed, projectileManager, bitmapLoader.getAnimationDefinitionGroup("ENEMY_SHIPS"));

    }


}
