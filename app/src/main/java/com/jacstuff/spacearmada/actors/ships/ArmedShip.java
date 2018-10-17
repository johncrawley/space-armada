package com.jacstuff.spacearmada.actors.ships;

import android.graphics.Rect;

import com.jacstuff.spacearmada.actors.CollidableActor;
import com.jacstuff.spacearmada.utils.ImageLoader;

/**
 * Created by John on 30/08/2017.
 */

public interface ArmedShip extends Spaceship{
    void fire();

}

/*extends CollidableActor {
   // public ArmedShip(ImageLoader imageLoader, Rect rect){
        super(imageLoader,rect);
    }
*/

