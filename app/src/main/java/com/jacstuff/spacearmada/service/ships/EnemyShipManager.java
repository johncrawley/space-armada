package com.jacstuff.spacearmada.service.ships;

import android.graphics.RectF;

import com.jacstuff.spacearmada.view.fragments.game.DrawInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EnemyShipManager {

    private final List<EnemyShip> enemyShips;
    private final Random enemyShipRandom;
    private final RectF screenBounds;
    private int smallestBoundsDimension;

    public EnemyShipManager(RectF screenBounds){
        enemyShips = new ArrayList<>();
        enemyShipRandom = new Random(2);
        this.screenBounds = screenBounds;
        setSmallestDimension();
    }

    private void setSmallestDimension(){
        smallestBoundsDimension = (int)Math.min(screenBounds.bottom - screenBounds.top, screenBounds.right - screenBounds.left);
    }


    public void add(EnemyShip enemyShip){
        enemyShips.add(enemyShip);
    }


    public List<DrawInfo> updateAndGetChanges(){
        enemyShips.forEach(EnemyShip::update);
        if(enemyShips.isEmpty()){
            EnemyShip enemyShip = new EnemyShip(getEnemyShipRandomStartingX(), -50, System.currentTimeMillis(), 5, 0.07f, 1.5f);
            enemyShip.setSizeBasedOn(smallestBoundsDimension);
            add(enemyShip);
        }
        removeEnemiesIfBeyondBounds();
        return getChangedDrawInfoList();
    }


    private void removeEnemiesIfBeyondBounds(){
        enemyShips.removeIf(e -> e.getY() > screenBounds.bottom);
    }


    private int getEnemyShipRandomStartingX(){
        return enemyShipRandom.nextInt(((int)screenBounds.right - 50));
    }


    public List<DrawInfo> getChangedDrawInfoList(){
        return enemyShips.stream()
                .filter(EnemyShip::hasChanged)
                .map(e -> {
                    e.resetChangedStatus();
                    return e.getDrawInfo();
                })
                .collect(Collectors.toList());
    }

}
