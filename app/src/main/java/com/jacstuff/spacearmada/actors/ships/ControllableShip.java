package com.jacstuff.spacearmada.actors.ships;

import com.jacstuff.spacearmada.Direction;

/**
 * Created by John on 30/08/2017.
 * Defines the responsibilities of a player-controllable space ship
 */

public interface ControllableShip extends ArmedShip {

    void setDirection(Direction direction);
    void releaseFire();
    void stopMoving();
}
