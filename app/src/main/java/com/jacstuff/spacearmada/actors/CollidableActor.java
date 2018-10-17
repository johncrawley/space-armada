package com.jacstuff.spacearmada.actors;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.jacstuff.spacearmada.utils.ImageLoader;

/**
 * Created by John on 30/08/2017.
 * Defines an actor that can collide with another actor
 */

public abstract class CollidableActor extends AbstractActor implements Collidable {


    public CollidableActor(ImageLoader imageLoader, Rect rect, int defaultResourceId){
        super(imageLoader,rect, defaultResourceId);
    }
    protected int energy;

    public int getEnergy(){
        return this.energy;
    }

    public void subtractEnergy(int amount){
        energy -= amount;
    }

    public void depleteEnergy() {
        this.energy = 0;
    }

    public boolean hasNoEnergy(){
        return this.energy < 1;
    }

    public boolean checkForCollision(Collidable otherCollidable){
        if(otherCollidable == null || !this.intersects(otherCollidable)){
            return false;
        }
        if(otherCollidable.getEnergy() < this.getEnergy()) {
            subtractEnergyFrom(this, otherCollidable);
        }
        else{
            subtractEnergyFrom(otherCollidable, this);
        }
        return true;
    }

    //where the first collidable has more energy than the second
    private void subtractEnergyFrom(Collidable c1, Collidable c2) {
        c1.subtractEnergy(c2.getEnergy());
        c2.depleteEnergy();
    }


    public boolean intersects(Collidable otherCollidable){
        //log("Entering intersects, current bounds :" + getBounds().flattenToString());

        if( otherCollidable == null){
            return false;
        }
        Rect otherBoundingBox = otherCollidable.getBounds();
        //return boundingBox.intersect(otherBoundingBox);

        //log("Before intersect, current bounds :" + getBounds().flattenToString());
        boolean areBoundsIntersecting =  doesBoundingBoxIntersectWith(otherBoundingBox);

       // log("After intersect, current bounds :" + getBounds().flattenToString());
        return areBoundsIntersecting;
    }

    private void log(String msg){
        Log.i("CollidableActor", msg);
    }
}
