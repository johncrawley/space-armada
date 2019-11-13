package com.jacstuff.spacearmada.actors;

import android.graphics.Rect;

import com.jacstuff.spacearmada.actors.ships.player.Energy;

/**
 * Created by John on 01/09/2017.
 * Something that can collide with something else
 */

public interface Collidable {

    boolean checkForCollision(Collidable collidable); //returns true if a collision occurred
    Rect getBounds();
    Energy getEnergy();
    boolean intersects(Collidable otherCollidable); // returns true if this collidable intersects with the specified collidable
}
