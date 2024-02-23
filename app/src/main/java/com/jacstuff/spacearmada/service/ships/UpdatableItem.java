package com.jacstuff.spacearmada.service.ships;

import android.graphics.PointF;

import com.jacstuff.spacearmada.service.ships.navigation.NavigationPath;
import com.jacstuff.spacearmada.view.fragments.game.ItemType;

public class UpdatableItem extends AbstractItem{

    private NavigationPath navigationPath;

    public UpdatableItem(long id, ItemType itemType, int speed, float sizeFactor, float heightWidthRatio){
        super(id, itemType, speed, sizeFactor, heightWidthRatio);
    }

    public void setPath(NavigationPath navigationPath){
        this.navigationPath = navigationPath;
    }

    public void update(){
       PointF nextCoordinate = navigationPath.getNextCoordinate();
       setX(nextCoordinate.x);
       setY(nextCoordinate.y);
    }
}
