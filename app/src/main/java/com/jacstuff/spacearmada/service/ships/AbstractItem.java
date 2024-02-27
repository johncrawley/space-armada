package com.jacstuff.spacearmada.service.ships;


import android.graphics.RectF;

import com.jacstuff.spacearmada.actors.ships.player.Energy;
import com.jacstuff.spacearmada.view.fragments.game.DrawInfo;
import com.jacstuff.spacearmada.view.fragments.game.ItemType;

public class AbstractItem {

    float x, y;
    float width, height;
    private final RectF bounds = new RectF();
    final ItemType itemType;
    final int speed;
    final float sizeFactor;
    final float heightWidthRatio;
    boolean hasChanged = false;
    DrawInfo drawInfo;
    protected Energy energy;
    private boolean haveBoundsChanged;

    public AbstractItem(long id, ItemType itemType, int speed, float sizeFactor, float heightWidthRatio){
        this.itemType = itemType;
        this.speed = speed;
        this.sizeFactor = sizeFactor;
        this.heightWidthRatio = heightWidthRatio;
        drawInfo = new DrawInfo(getItemType(), id);
        energy = new Energy(100);
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
        haveBoundsChanged = true;
    }


    public void setY(float y){
        this.y = y;
        hasChanged = true;
        haveBoundsChanged = true;
    }


    public RectF getBounds(){
        if(haveBoundsChanged){
            bounds.left = x;
            bounds.top = y;
            bounds.right = x + width;
            bounds.bottom = y + height;
        }
        haveBoundsChanged = false;
        return bounds;
    }


    public DrawInfo getDrawInfo(){
        drawInfo.setXY(x,y);
        drawInfo.setDimensions((int)width, (int) height);
        return drawInfo;
    }

    public Energy getEnergy(){
        return this.energy;
    }


    private void log(String msg){
        System.out.println("^^^ AbstractItem : " + msg);
    }


    public boolean isEnergyDepleted(){
        return energy.isDepleted();
    }



}
