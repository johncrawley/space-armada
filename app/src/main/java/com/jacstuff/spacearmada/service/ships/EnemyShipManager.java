package com.jacstuff.spacearmada.service.ships;

import static com.jacstuff.spacearmada.service.ships.Utils.getChangedDrawInfoList;
import static com.jacstuff.spacearmada.service.ships.Utils.setSmallestDimension;

import android.graphics.RectF;

import com.jacstuff.spacearmada.view.fragments.game.DrawInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EnemyShipManager {

    private final List<EnemyShip> enemyShips;
    private final Random enemyShipRandom;
    private final RectF screenBounds = new RectF();
    private int smallestBoundsDimension;

    public EnemyShipManager(){
        enemyShips = new ArrayList<>();
        enemyShipRandom = new Random(2);
        createTempBounds();
    }


    private void createTempBounds(){
        this.screenBounds.left = 0;
        this.screenBounds.top = 0;
        this.screenBounds.right = 1000;
        this.screenBounds.bottom = 1000;
        smallestBoundsDimension = setSmallestDimension(screenBounds);
    }


    public void setScreenBounds(RectF screenBounds){
        this.screenBounds.left = screenBounds.left;
        this.screenBounds.top = screenBounds.top;
        this.screenBounds.right = screenBounds.right;
        this.screenBounds.bottom = screenBounds.bottom;
        smallestBoundsDimension = setSmallestDimension(screenBounds);
    }


    public void add(EnemyShip enemyShip){
        enemyShips.add(enemyShip);
    }


    public List<DrawInfo> updateAndGetChanges(){
        enemyShips.forEach(EnemyShip::update);
        createEnemyShipIfListIsEmpty();
        removeEnemiesIfBeyondBounds();
        return getChangedDrawInfoList(enemyShips);
    }


    private void createEnemyShipIfListIsEmpty(){
        if(enemyShips.isEmpty()){
            EnemyShip enemyShip = new EnemyShip(getEnemyShipRandomStartingX(), -50, System.currentTimeMillis(), 5, 0.07f, 1.5f);
            enemyShip.setSizeBasedOn(smallestBoundsDimension);
            add(enemyShip);
        }
    }


    private void removeEnemiesIfBeyondBounds(){
        enemyShips.removeIf(e -> e.getY() > screenBounds.bottom);
    }


    private int getEnemyShipRandomStartingX(){
        return enemyShipRandom.nextInt(((int)screenBounds.right - 50));
    }


}
