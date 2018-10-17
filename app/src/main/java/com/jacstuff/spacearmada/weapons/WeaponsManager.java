package com.jacstuff.spacearmada.weapons;

import com.jacstuff.spacearmada.actors.ships.Spaceship;

/**
 * Created by John on 30/08/2017.
 */

public interface WeaponsManager {
    void fire(Spaceship spaceship);
    void update();

}
