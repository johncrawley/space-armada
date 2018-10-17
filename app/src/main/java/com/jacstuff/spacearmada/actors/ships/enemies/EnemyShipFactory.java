package com.jacstuff.spacearmada.actors.ships.enemies;


import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.projectiles.ProjectileManager;
import com.jacstuff.spacearmada.utils.ImageLoader;


import java.util.HashMap;

import java.util.Map;

public class EnemyShipFactory {

    private ImageLoader imageLoader;
    private ProjectileManager projectileManager;
    private int speed;
    Map<EnemyType, EnemyShip> prototypes;
    enum EnemyType { Grunt}


    public EnemyShipFactory(ImageLoader imageLoader, ProjectileManager projectileManager){

        this.imageLoader = imageLoader;
        this.projectileManager = projectileManager;
        speed = 5;
        prototypes = new HashMap<>();
    }


    public EnemyShip createShip(int initialX, int initialY){

       EnemyShip enemyShip = new EnemyShip(initialX, initialY, speed, projectileManager, imageLoader, R.drawable.ship2);

        enemyShip.addAnimation(ActorState.DESTROYING,
                R.drawable.ship2e1,
                R.drawable.ship2e2,
                R.drawable.ship2e3,
                R.drawable.ship2e4);
        enemyShip.addAnimation(ActorState.DESTROYED, R.drawable.ship2e4);
        return enemyShip;
    }


}
