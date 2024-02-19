package com.jacstuff.spacearmada.service.ships;

import com.jacstuff.spacearmada.view.fragments.game.DrawInfo;
import com.jacstuff.spacearmada.view.fragments.game.ItemType;

public class EnemyShip extends AbstractItem{

    private final DrawInfo drawInfo;

    public EnemyShip(float initialX, float initialY, long id, int speed, float sizeFactor, float heightToWidthRatio){
        super(ItemType.ENEMY_SHIP_1, speed, sizeFactor, heightToWidthRatio);
        this.x = initialX;
        this.y = initialY;
        drawInfo = new DrawInfo(getItemType(), id);
        drawInfo.setDimensions((int)width, (int)height);
    }


    public DrawInfo getDrawInfo(){
        drawInfo.setXY(x,y);
        return drawInfo;
    }


    public void update(){
        setY(y + speed);
    }

}
