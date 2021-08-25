package com.jacstuff.spacearmada.actors.background;

import android.graphics.Bitmap;

import com.jacstuff.spacearmada.view.DrawItem;

public class Tile implements DrawItem {

    private final Bitmap bitmap;
    private int y;
    private final int resetY;

    Tile(Bitmap bitmap, int initialY, int resetY){
        this.y = initialY;
        this.resetY = resetY;
        this.bitmap = bitmap;
    }

    void offsetY(int amount){
        y += amount;
    }

    public Bitmap getBitmap(){
        return this.bitmap;
    }

    public void resetY(){
        this.y = resetY;
    }

    public int getX(){ return 0; }

    public int getY(){
        return this.y;
    }

    public boolean isVisible(){ return true;}

}
