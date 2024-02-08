package com.jacstuff.spacearmada.actors;

import android.graphics.Rect;
import android.util.Log;

import com.jacstuff.spacearmada.actors.animation.AnimationDefinitionGroup;
import com.jacstuff.spacearmada.actors.ships.player.Energy;

/**
 * Created by John on 30/08/2017.
 * Defines an actor that can collide with another actor
 */

public abstract class CollidableActor extends AbstractActor implements Collidable {

    protected Energy energy;


    public CollidableActor(AnimationDefinitionGroup animationDefinitionGroup, int x, int y, int initialEnergy){
        super(animationDefinitionGroup,x,y);
        energy = new Energy(initialEnergy, (initialEnergy / 5) * 3, initialEnergy / 4);
    }


    public Energy getEnergy(){
        return this.energy;
    }


    public boolean checkForCollision(Collidable otherCollidable){
        if(otherCollidable == null || !this.intersects(otherCollidable)){
            return false;
        }
        this.energy.collideWith(otherCollidable.getEnergy());
        return true;
    }


    public boolean intersects(Collidable otherCollidable){
        if( otherCollidable == null){
            return false;
        }
        Rect otherBoundingBox = otherCollidable.getBounds();
        return  doesBoundingBoxIntersectWith(otherBoundingBox);
    }

}
