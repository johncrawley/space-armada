package com.jacstuff.spacearmada.service.ships;

import com.jacstuff.spacearmada.view.fragments.game.DrawInfo;
import com.jacstuff.spacearmada.view.fragments.game.ItemType;

public class EnemyShip extends AbstractItem{


    public EnemyShip(float initialX, float initialY, long id, int speed, float sizeFactor, float heightToWidthRatio){
        super(id, ItemType.ENEMY_SHIP_1, speed, sizeFactor, heightToWidthRatio);
        this.x = initialX;
        this.y = initialY;
        drawInfo.setDimensions((int)width, (int)height);
    }


    public void update(){
        setY(y + speed);
    }

}
