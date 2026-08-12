package com.jacstuff.spacearmada.model.ships.player;

import com.jacstuff.spacearmada.model.ships.ArmedShip;

/**
 * Created by John on 30/08/2017.
 * Defines the responsibilities of a player-controllable space ship
 */

public interface ControllableShip extends ArmedShip {

    void setDirection(Direction direction);
    void releaseFire();
    void stopMoving();
}
