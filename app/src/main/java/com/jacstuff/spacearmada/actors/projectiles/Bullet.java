package com.jacstuff.spacearmada.actors.projectiles;


import android.graphics.Rect;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.actors.AnimationInfoService;
import com.jacstuff.spacearmada.actors.CollidableActor;
import com.jacstuff.spacearmada.actors.ships.ArmedShip;
import com.jacstuff.spacearmada.utils.ImageLoader;

/**
 * Created by John on 30/08/2017.
 * Represents a bullet projectile that has a position, direction and energy
 */

public class Bullet extends CollidableActor implements Projectile{

    private Direction direction;
    private ArmedShip ship;
    private int bulletSpeed = 15;

    Bullet(int x, int y, int width, int height, int energy, Direction direction, ImageLoader imageLoader,  ArmedShip ship, int drawableId){
        super(new AnimationInfoService("BULLET"), imageLoader, new Rect(x,y,x+width, y+height), drawableId);

        this.speed = bulletSpeed;
        this.energy = energy;
        this.direction = direction;
        this.ship = ship;
    }
    public void update(){
       int moveDirection = direction == Direction.DOWN ? 1 : -1;
       int yDiff = moveDirection * speed;
       offsetBounds(0, yDiff);
    }

    public ArmedShip getOwner(){
        return ship;
    }

}
