package com.jacstuff.spacearmada.service;

import android.graphics.Rect;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.actors.ships.ControllableShip;

public class PlayerShip implements ControllableShip {

    private int x,y;
    private Rect moveBounds;
    public static final int distanceToMove = 5;


    public PlayerShip(int initialX, int initialY, Rect moveBounds){
        this.x = initialX;
        this.y = initialY;
        this.moveBounds = moveBounds;
    }


    @Override
    public void setDirection(Direction direction){

    }


    @Override
    public void releaseFire(){

    }

    @Override
    public void stopMoving(){

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

    @Override
    public void fire() {

    }

    @Override
    public void update() {

    }
}
