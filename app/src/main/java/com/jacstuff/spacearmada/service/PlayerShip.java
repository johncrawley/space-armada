package com.jacstuff.spacearmada.service;

import android.graphics.Rect;

public class PlayerShip {

    private int x,y;
    private Rect moveBounds;
    public static final int distanceToMove = 5;


    public PlayerShip(int initialX, int initialY, Rect moveBounds){
        this.x = initialX;
        this.y = initialY;
        this.moveBounds = moveBounds;
    }

    public int getX(){
        return x;
    }


    public int getY(){
        return y;
    }


    public void moveRight(){
        x += distanceToMove;
    }


    public void moveLeft(){
        x -= distanceToMove;
    }


    public void moveDown(){
        y += distanceToMove;
    }


    public void moveUp(){
        y -= distanceToMove;
    }
}
