package com.jacstuff.spacearmada.weapons;

import java.util.ArrayList;
import java.util.List;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.actors.projectiles.Bullet;
import com.jacstuff.spacearmada.actors.ships.Spaceship;

/**
 * Created by John on 30/08/2017.
 */

public class WeaponsManagerImpl implements WeaponsManager {

    private List<Bullet> bullets;
    private Direction bulletDirection;
    private int bulletEnergy;

    public WeaponsManagerImpl(Direction direction, int bulletEnergy){
        this.bulletDirection = direction;
        this.bulletEnergy = bulletEnergy;
    }


    public WeaponsManagerImpl(){
        bullets = new ArrayList<>();
    }
    public void update(){
        for(Bullet bullet: bullets){
            bullet.update();
        }
    }

    public void fire(Spaceship ship){
       // bullets.add(new Bullet(ship.getX(), ship.getY(),bulletEnergy,bulletDirection));
    }
}
