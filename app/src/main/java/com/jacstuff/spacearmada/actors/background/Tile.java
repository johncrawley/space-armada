package com.jacstuff.spacearmada.actors.background;

import android.graphics.Bitmap;

public class Tile {

    private Bitmap bitmap;
    private int y;
    private int initialY;
    private int resetY;

    Tile(Bitmap bitmap, int initialY, int resetY){
        this.initialY = initialY;
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

    public int getY(){
        return this.y;
    }

}
