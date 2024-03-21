package com.jacstuff.spacearmada.service.ships;

import static com.jacstuff.spacearmada.service.ships.Utils.getChangedDrawInfoList;
import static com.jacstuff.spacearmada.service.ships.Utils.setSmallestDimension;

import android.graphics.RectF;

import com.jacstuff.spacearmada.service.sound.Sound;
import com.jacstuff.spacearmada.view.fragments.game.DrawInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


    public List<EnemyShip> getEnemyShips(){
        return enemyShips;
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
        notifyEnemiesIfBeyondBounds();
        return getChangedDrawInfoList(enemyShips);
    }


    private void createEnemyShipIfListIsEmpty(){
        if(enemyShips.isEmpty()){

            EnemyShip enemyShip = EnemyShip.Builder.newInstance()
                    .initialX(getEnemyShipRandomStartingX())
                    .initialY(-50)
                    .id(System.nanoTime())
                    .speed(5)
                    .sizeFactor(0.07f)
                    .heightToWidthRatio(1.5f)
                    .explosionSound(Sound.ENEMY_SHIP_1_EXPLOSION)
                    .points(100)
                    .build();
            enemyShip.setSizeBasedOn(smallestBoundsDimension);
            add(enemyShip);
        }
    }


    private void notifyEnemiesIfBeyondBounds(){
        enemyShips.stream()
                .filter(es -> es.getY() > screenBounds.bottom)
                .forEach(ship -> ship.getDrawInfo().markAsOutOfBounds());
    }


    public void removeAnyDestroyedOrOutOfBounds(){
        enemyShips.removeIf(e -> e.getDrawInfo().shouldBeRemoved());
    }


    private int getEnemyShipRandomStartingX(){
        return enemyShipRandom.nextInt(((int)screenBounds.right - 50));
    }


}
