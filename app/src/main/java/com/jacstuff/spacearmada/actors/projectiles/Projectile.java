package com.jacstuff.spacearmada.actors.projectiles;

import android.graphics.drawable.Drawable;

import com.jacstuff.spacearmada.DrawableItem;
import com.jacstuff.spacearmada.actors.Collidable;
import com.jacstuff.spacearmada.actors.ships.ArmedShip;

/**
 * Created by John on 30/08/2017.
 * Represents a behavior of a projectile
 */

public interface Projectile extends Collidable, DrawableItem {
    //Drawable getDrawable();
    void update();
    ArmedShip getOwner();

}
