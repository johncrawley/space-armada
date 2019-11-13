package com.jacstuff.spacearmada.actors.projectiles;


import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.actors.AnimationInfoService;
import com.jacstuff.spacearmada.actors.CollidableActor;
import com.jacstuff.spacearmada.actors.ships.ArmedShip;
import com.jacstuff.spacearmada.actors.ships.player.Energy;
import com.jacstuff.spacearmada.utils.ImageLoader;

/**
 * Created by John on 30/08/2017.
 * Represents a bullet projectile that has a position, direction and energyInt
 */

public class Bullet extends CollidableActor implements Projectile{

    private Direction direction;
    private ArmedShip ship;

    Bullet(int x, int y, int width, int height, int speed, int energy, Direction direction, AnimationInfoService animationInfoService,  ArmedShip ship, int drawableId,ImageLoader imageLoader){
        super(animationInfoService,
                imageLoader,
                x,
                y,
                width,
                height,
                30,
                drawableId);

        this.speed = speed;
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
