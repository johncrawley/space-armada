package com.jacstuff.spacearmada.actors.ships.enemies;


import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.AnimationDefinitionGroup;
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
    private AnimationDefinitionGroup animationInfoService;

    public EnemyShipFactory(ImageLoader imageLoader, ProjectileManager projectileManager){

        this.imageLoader = imageLoader;
        this.projectileManager = projectileManager;
        speed = 5;
        prototypes = new HashMap<>();
        setupAnimationInfoService();
    }

    private void setupAnimationInfoService(){

        animationInfoService = new AnimationDefinitionGroup("ENEMY_SHIPS");
        animationInfoService.registerState(ActorState.DEFAULT, 1, false);
        animationInfoService.registerState(ActorState.DESTROYING, 4, false);
        animationInfoService.registerState(ActorState.DESTROYED, 1, false);
    }


    public AnimationDefinitionGroup getAnimationInfoService(){
        return this.animationInfoService;
    }


    public EnemyShip createShip(int initialX, int initialY){
        int initialWidth = 20;
        int initialHeight = 35;

       EnemyShip enemyShip = new EnemyShip(initialX, initialY, initialWidth, initialHeight, speed, projectileManager, imageLoader, getAnimationInfoService(), R.drawable.ship2);

        enemyShip.addAnimation(ActorState.DESTROYING,
                R.drawable.ship2e1,
                R.drawable.ship2e2,
                R.drawable.ship2e3,
                R.drawable.ship2e4);
        enemyShip.addAnimation(ActorState.DESTROYED, R.drawable.ship2e4);
        return enemyShip;
    }


}
