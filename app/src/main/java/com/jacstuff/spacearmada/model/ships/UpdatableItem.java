package com.jacstuff.spacearmada.model.ships;

import com.jacstuff.spacearmada.model.ships.navigation.NavigationPath;
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
       var nextCoordinate = navigationPath.getNextCoordinate();
       setX(nextCoordinate.x);
       setY(nextCoordinate.y);
    }
}
