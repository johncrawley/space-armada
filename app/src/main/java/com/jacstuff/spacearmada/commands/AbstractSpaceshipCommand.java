package com.jacstuff.spacearmada.commands;

import com.jacstuff.spacearmada.actors.ships.ControllableShip;

/**
 * Created by John on 29/08/2017.
 */

public class AbstractSpaceshipCommand {
    protected ControllableShip spaceship;

    public void assignShip(ControllableShip spaceship){
        this.spaceship = spaceship;
    }
}
