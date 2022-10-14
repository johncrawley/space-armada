package com.jacstuff.spacearmada.game;

import android.view.ViewGroup;

import com.jacstuff.spacearmada.actors.ships.enemies.EnemyShip;
import com.jacstuff.spacearmada.view.TransparentView;

import java.util.WeakHashMap;

public class EnemyViewManager {

    ViewGroup parentView;
    WeakHashMap<EnemyShip, TransparentView> shipViewMap;

    public EnemyViewManager(ViewGroup parentView){
        this.parentView = parentView;
    }


    public void createViewFor(EnemyShip enemyShip){
        TransparentView transparentView = new TransparentView(parentView.getContext());
        parentView.addView(transparentView);
        //transparentView.addDrawableItem(enemyShip.getDrawInfo());
    }
}
