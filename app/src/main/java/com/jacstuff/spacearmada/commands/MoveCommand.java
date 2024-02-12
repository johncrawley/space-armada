package com.jacstuff.spacearmada.commands;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.actors.ships.ControllableShip;

/**
 * Created by John on 29/08/2017.
 * The command issued when moving the spaceship
 */
public class MoveCommand extends AbstractSpaceshipCommand implements Command {

    private Direction direction;


    public MoveCommand(ControllableShip spaceship){
        this.spaceship = spaceship;
    }


    public void assignDirection(Direction d){
        this.direction = d;
    }


    public void invoke(){
        spaceship.setDirection(direction);
    }


    public void release(){
        spaceship.stopMoving();
    }
}
