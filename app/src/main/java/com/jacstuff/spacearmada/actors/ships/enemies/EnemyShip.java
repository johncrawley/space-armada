package com.jacstuff.spacearmada.actors.ships.enemies;

import android.graphics.Rect;
import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.animation.AnimationDefinitionGroup;
import com.jacstuff.spacearmada.actors.CollidableActor;
import com.jacstuff.spacearmada.actors.projectiles.ProjectileManager;
import com.jacstuff.spacearmada.actors.ships.ArmedShip;
import com.jacstuff.spacearmada.actors.ships.player.Energy;

/**
 * Created by John on 30/08/2017.
 * Represents the enemy spaceships that the player must destroy.
 */

public class EnemyShip extends CollidableActor implements ArmedShip {

    private final ProjectileManager projectileManager;
    private final int points;

    EnemyShip(int initialX, int initialY, int speed, ProjectileManager projectileManager, AnimationDefinitionGroup animationDefinitionGroup){
        super(animationDefinitionGroup, initialX, initialY, 80);
        this.projectileManager = projectileManager;
        points = 100;
        this.energy = new Energy(60, 30, 10);
        this.speed = speed;
    }


    public boolean isAlive(){
        return getState() != ActorState.DESTROYING && getState() != ActorState.DESTROYED;
    }


    public void update(){
        for(int i = 0; i < speed; i++) {
            offsetBounds(0, 1);
        }
    }


    public int getPoints(){
        return this.points;
    }


    public void fire(){
        Rect bounds = getBounds();
        int x = bounds.centerX()-2;
        int y = bounds.bottom;
        projectileManager.createProjectile(x, y,100, this);
    }
}
