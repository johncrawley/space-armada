package com.jacstuff.spacearmada.actors.ships.enemies;


import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import com.jacstuff.spacearmada.DrawableItem;
import com.jacstuff.spacearmada.DrawableItemGroup;
import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.projectiles.ProjectileManager;
import com.jacstuff.spacearmada.image.BitmapLoader;
import com.jacstuff.spacearmada.utils.ImageLoader;

/**
 * Created by John on 30/08/2017.
 * Takes care of the creation of enemy ships, updating them and destroying them if they exceed bounds
 */

public class EnemyShipManager implements DrawableItemGroup {

    private List<EnemyShip> enemyShips;
    private EnemyShipFactory enemyShipFactory;
    private int gameScreenBottom;

    public EnemyShipManager(ProjectileManager projectileManager, BitmapLoader bitmapLoader, int gameScreenBottom) {
        enemyShips = new ArrayList<>();
        enemyShipFactory = new EnemyShipFactory(bitmapLoader, projectileManager);

        this.gameScreenBottom = gameScreenBottom;
    }


    public List<DrawableItem> getDrawableItems(){
       return new ArrayList<DrawableItem>(enemyShips);
    }


    public void update(){
        List <EnemyShip> tempShips = new ArrayList<>(enemyShips);
        for(EnemyShip ship: tempShips){
            ship.update();
            Rect bounds = ship.getBounds();
            if(bounds == null){
                enemyShips.remove(ship);
                continue;
            }

            if(bounds.top > gameScreenBottom){
                enemyShips.remove(ship);
            }
            if(ship.getEnergy().isDepleted() && ship.isAlive()) {
                ship.setState(ActorState.DESTROYING);
            }
            else if(ship.getState() == ActorState.DESTROYED){
                enemyShips.remove(ship);
            }
        }
    }

    //we want to change this at the end of the game, when the control panel is removed.
    public void setGameScreenBottom(int newGameScreenBottom){
        gameScreenBottom = newGameScreenBottom;
    }

    public int updateAnimations(int x){
        List<EnemyShip> tempShips = new ArrayList<>(enemyShips);
        for(EnemyShip ship : tempShips){
            ship.updateAnimation();
        }
        return enemyShips.size();
    }


    public void createShip(int initialX, int initialY){
        enemyShips.add(enemyShipFactory.createShip(initialX,initialY));

        //logShipXs();
        //Log.i("EnemyShipMngr", "enemy ship created! - current enemyShips: " + this.enemyShips.size());
    }

    public EnemyShip createTestShip(){
        EnemyShip ship = enemyShipFactory.createShip(0,0);
        enemyShips.add(ship);
        //logShipXs();
        //Log.i("EnemyShipMngr", "enemy ship created! - current enemyShips: " + this.enemyShips.size());
        return ship;
    }

    public List<EnemyShip> getEnemyShips(){
        return new ArrayList<>(enemyShips);
    }
}
