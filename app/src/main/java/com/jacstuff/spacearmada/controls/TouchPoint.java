package com.jacstuff.spacearmada.controls;

/**
 * Created by John on 18/09/2017.
 */

public class TouchPoint {
    float x,y;
    boolean isRelease;
    public TouchPoint(float x, float y, boolean isUp){
        this.x = x;
        this.y = y;
        this.isRelease = isUp;
    }

    public float getX(){return this.x;}
    public float getY(){return this.y;}
    public boolean isRelease(){return this.isRelease;}
}
