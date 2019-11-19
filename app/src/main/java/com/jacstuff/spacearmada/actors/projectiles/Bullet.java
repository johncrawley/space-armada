package com.jacstuff.spacearmada.actors.projectiles;


import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.actors.animation.AnimationDefinitionGroup;
import com.jacstuff.spacearmada.actors.CollidableActor;
import com.jacstuff.spacearmada.actors.ships.ArmedShip;

/**
 * Created by John on 30/08/2017.
 * Represents a bullet projectile that has a position, direction and energyInt
 */

public class Bullet extends CollidableActor implements Projectile{

    private Direction direction;
    private ArmedShip ownerShip;

    Bullet(int x, int y, int speed, int energy, Direction direction, AnimationDefinitionGroup animationInfoService, ArmedShip ownerShip){
        super(animationInfoService,
                x,
                y,
                energy);

        this.speed = speed;
        this.direction = direction;
        this.ownerShip = ownerShip;
    }
    public void update(){
       int moveDirection = direction == Direction.DOWN ? 1 : -1;
       int yDiff = moveDirection * speed;
       offsetBounds(0, yDiff);
    }

    public ArmedShip getOwner(){
        return ownerShip;
    }

}
