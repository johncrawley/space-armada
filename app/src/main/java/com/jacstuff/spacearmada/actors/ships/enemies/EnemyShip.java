package com.jacstuff.spacearmada.actors.ships.enemies;

import android.graphics.Rect;
import android.util.Log;

import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.AnimationDefinitionGroup;
import com.jacstuff.spacearmada.actors.CollidableActor;
import com.jacstuff.spacearmada.actors.projectiles.ProjectileManager;
import com.jacstuff.spacearmada.actors.ships.ArmedShip;
import com.jacstuff.spacearmada.actors.ships.player.Energy;
import com.jacstuff.spacearmada.utils.ImageLoader;

/**
 * Created by John on 30/08/2017.
 * Represents the enemy spaceships that the player must destroy.
 */

public class EnemyShip extends CollidableActor implements ArmedShip {

    private ProjectileManager projectileManager;
    private int points;
    private int logInterval = 0;

    EnemyShip(int initialX, int initialY, int initialWidth, int initialHeight, int speed, ProjectileManager projectileManager, ImageLoader imageLoader, AnimationDefinitionGroup animationInfoService, int defaultDrawableId){

        super(animationInfoService, imageLoader, initialX, initialY, initialWidth, initialHeight, 80,
                defaultDrawableId);

        this.projectileManager = projectileManager;
        points = 100;
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


    private void logStatus(String msg){
        logInterval++;
        if(logInterval % 2000 == 0){
            Log.i("EnemyShip logStatus", msg);
        }
    }


    public int getPoints(){
        return this.points;
    }

    public void fire(){
        Rect bounds = getBounds();
        int x = bounds.centerX()-2;
        int y = bounds.bottom;

        projectileManager.createProjectile(x,y,100, this);
    }
}
