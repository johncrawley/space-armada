package com.jacstuff.spacearmada.service;

import android.graphics.Rect;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.actors.ships.ControllableShip;

public class PlayerShip implements ControllableShip {

    private int x,y;
    private Rect moveBounds;
    public static final int distanceToMove = 5;
    private int previousX, previousY;
    private Direction currentDirection = Direction.NONE;



    public PlayerShip(int initialX, int initialY, Rect moveBounds){
        this.x = initialX;
        this.y = initialY;
        this.moveBounds = moveBounds;
    }


    @Override
    public void setDirection(Direction direction){
        this.currentDirection = direction;
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




    public boolean hasPositionChanged(){
        boolean hasChanged = x != previousX || y != previousY;
        previousX = x;
        previousY = y;
        return hasChanged;
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
        updateDirection();
    }

    private void updateDirection(){

        switch(currentDirection){
            case UP:
                moveUp();break;
            case DOWN:
                moveDown();break;
            case LEFT:
                moveLeft();break;
            case RIGHT:
                moveRight();break;
            case UP_LEFT:
                moveUp(); moveLeft();break;
            case UP_RIGHT:
                moveUp(); moveRight();break;
            case DOWN_LEFT:
                moveDown(); moveLeft();break;
            case DOWN_RIGHT:
                moveDown(); moveRight();break;

            // default: Log.i("PlayerShip updateDir()", "No direction detected");
        }
    }
}
