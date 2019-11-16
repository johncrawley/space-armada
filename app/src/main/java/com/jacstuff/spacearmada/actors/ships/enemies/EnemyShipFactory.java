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
    private AnimationDefinitionGroup animationDefinitionGroup;

    public EnemyShipFactory(ImageLoader imageLoader, ProjectileManager projectileManager){

        this.imageLoader = imageLoader;
        this.projectileManager = projectileManager;
        speed = 5;
        prototypes = new HashMap<>();
        setupAnimationInfoService();
    }

    private void setupAnimationInfoService(){

        animationDefinitionGroup = new AnimationDefinitionGroup("ENEMY_SHIPS");
        animationDefinitionGroup.registerState(ActorState.DEFAULT, 1, false);
        animationDefinitionGroup.registerState(ActorState.DESTROYING, 4, ActorState.DESTROYED);
        animationDefinitionGroup.registerState(ActorState.DESTROYED, 1, false);
    }


    public AnimationDefinitionGroup getAnimationDefinitionGroup(){
        return this.animationDefinitionGroup;
    }


    public EnemyShip createShip(int initialX, int initialY){
        int initialWidth = 20;
        int initialHeight = 35;

       EnemyShip enemyShip = new EnemyShip(initialX, initialY, initialWidth, initialHeight, speed, projectileManager, imageLoader, getAnimationDefinitionGroup(), R.drawable.ship2);

        return enemyShip;
    }


}
