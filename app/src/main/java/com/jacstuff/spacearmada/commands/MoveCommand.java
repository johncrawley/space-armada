package com.jacstuff.spacearmada.commands;

import android.util.Log;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.actors.ships.ControllableShip;

/**
 * Created by John on 29/08/2017.
 * The command issued when moving the spaceship
 */

public class MoveCommand extends AbstractSpaceshipCommand implements Command {

    private Direction direction;
    public void assignDirection(Direction d){
        this.direction = d;
    }

    public MoveCommand(ControllableShip spaceship){
        this.spaceship = spaceship;
    }

    public void invoke(){
        //Log.i("MoveCommand invoke()", "invoked, direction: " +  direction);
        spaceship.setDirection(direction);
    }

    public void release(){
        spaceship.stopMoving();
    }
}
