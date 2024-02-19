package com.jacstuff.spacearmada.service.ships;


import com.jacstuff.spacearmada.view.fragments.game.ItemType;

public class AbstractItem {

    float x, y;
    float width, height;
    final ItemType itemType;
    final int speed;
    final float sizeFactor;
    final float heightWidthRatio;
    boolean hasChanged = false;


    public AbstractItem(ItemType itemType, int speed, float sizeFactor, float heightWidthRatio){
        this.itemType = itemType;
        this.speed = speed;
        this.sizeFactor = sizeFactor;
        this.heightWidthRatio = heightWidthRatio;
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
