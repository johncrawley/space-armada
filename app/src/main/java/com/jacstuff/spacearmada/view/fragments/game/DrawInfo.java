package com.jacstuff.spacearmada.view.fragments.game;

public class DrawInfo {

    private float x, y;
    private final long id;
    private int width, height;
    private final ItemType itemType;
    public boolean isScheduledForRemoval;
    public float rotation;

    public DrawInfo(ItemType itemType, long id){
        this.itemType = itemType;
        this.id = id;
    }


    public void setXY(float x, float y){
        this.x = x;
        this.y = y;
    }


    public void incrementRotation(float degrees){
        rotation += degrees;
        rotation %= 360;
    }


    public float getRotation(){
        return rotation;
    }

    public boolean isScheduledForRemoval(){
        return isScheduledForRemoval;
    }


    public void scheduleForRemoval(){
        isScheduledForRemoval = true;
    }


    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
    }


    public float getX(){
        return x;
    }


    public float getY(){
        return y;
    }


    public int getWidth(){
        return width;
    }


    public int getHeight(){
        return height;
    }


    public long getId(){
        return id;
    }


    public ItemType getItemType(){
        return itemType;
    }


}
