package com.jacstuff.spacearmada.service.ships;


import android.graphics.RectF;

import com.jacstuff.spacearmada.view.fragments.game.DrawInfo;
import com.jacstuff.spacearmada.view.fragments.game.ItemType;

public class AbstractItem {

    float x, y;
    float width, height;
    final ItemType itemType;
    final int speed;
    final float sizeFactor;
    final float heightWidthRatio;
    boolean hasChanged = false;
    DrawInfo drawInfo;

    public AbstractItem(long id, ItemType itemType, int speed, float sizeFactor, float heightWidthRatio){
        this.itemType = itemType;
        this.speed = speed;
        this.sizeFactor = sizeFactor;
        this.heightWidthRatio = heightWidthRatio;
        drawInfo = new DrawInfo(getItemType(), id);
    }


    public float getX(){
        return x;
    }


    public float getY(){
        return y;
    }


    public float getWidth(){
        return width;
    }


    public float getHeight(){
        return height;
    }


    public boolean hasChanged(){
        return this.hasChanged;
    }


    public void resetChangedStatus(){
        this.hasChanged = false;
    }


    public ItemType getItemType(){
        return itemType;
    }


    public int getSpeed(){
        return speed;
    }


    public void setSizeBasedOn(int smallestScreenDimension){
        width = smallestScreenDimension * sizeFactor;
        height = width * heightWidthRatio;
        drawInfo.setDimensions((int)width, (int)height);
    }


    public void setX(float x){
        this.x = x;
        hasChanged = true;
    }


    public void setY(float y){
        this.y = y;
        hasChanged = true;
    }

}
