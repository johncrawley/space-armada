package com.jacstuff.spacearmada.actors.ships.enemies;

import android.graphics.Rect;
import android.util.Log;

import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.AnimationInfoService;
import com.jacstuff.spacearmada.actors.CollidableActor;
import com.jacstuff.spacearmada.actors.projectiles.ProjectileManager;
import com.jacstuff.spacearmada.actors.ships.ArmedShip;
import com.jacstuff.spacearmada.utils.ImageLoader;

/**
 * Created by John on 30/08/2017.
 * Represents the enemy spaceships that the player must destroy.
 */

public class EnemyShip extends CollidableActor implements ArmedShip {

    private ProjectileManager projectileManager;
    private int points;
    private int logInterval = 0;
    EnemyShip(int initialX, int initialY, int speed, ProjectileManager projectileManager, ImageLoader imageLoader, AnimationInfoService animationInfoService, int defaultDrawableId){

        super(animationInfoService,
                imageLoader,
                new Rect(initialX, initialY, initialX + 40, initialY + 70),
                defaultDrawableId);

        this.projectileManager = projectileManager;
        points = 100;
        this.energy = 60;
        this.speed = speed;
        addAnimation(ActorState.DEFAULT, defaultDrawableId);
    }


    public boolean isAlive(){
        return getActorState() != ActorState.DESTROYING && getActorState() != ActorState.DESTROYED;
    }

    public void update(){
        if(animationManager.getCurrentDrawable(getActorState()) == null){
            logStatus("EnemyShip drawable is null!");
            return;
        }
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
