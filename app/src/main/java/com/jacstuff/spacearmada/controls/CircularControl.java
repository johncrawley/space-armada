package com.jacstuff.spacearmada.controls;

/**
 * Created by John on 29/08/2017.
 *
 *
 */

public abstract class CircularControl {


    float radius, circleCentreX, circleCentreY;
    private float radiusSquared;

    CircularControl(int xPos, int yPos, int radius) {
        this.radius = radius;
        this.radiusSquared = radius * radius;
        circleCentreX = xPos + radius;
        circleCentreY = yPos + radius;
    }

    public void setCentrePosition(int centreX, int centreY, int radius){

        this.radius = radius;
        this.circleCentreX = centreX;
        this.circleCentreY = centreY;

    }


    public float getCentreX() {
        return this.circleCentreX;
    }
    public float getCentreY() {
        return this.circleCentreY;
    }
    public float getRadius() { return this.radius;}

    boolean contains(float x, float y){
        return  squareOf(x - circleCentreX) + squareOf(y - circleCentreY)  <= radiusSquared;
    }

    private float squareOf(float value){
        return value * value;
    }
}