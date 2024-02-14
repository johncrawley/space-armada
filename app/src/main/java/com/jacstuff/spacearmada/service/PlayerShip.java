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
        this.currentDirection = Direction.NONE;
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
            case UP -> moveUp();
            case DOWN -> moveDown();
            case LEFT -> moveLeft();
            case RIGHT -> moveRight();
            case UP_LEFT -> { moveUp(); moveLeft(); }
            case UP_RIGHT -> { moveUp(); moveRight(); }
            case DOWN_LEFT -> { moveDown(); moveLeft(); }
            case DOWN_RIGHT -> { moveDown(); moveRight(); }
            case NONE -> {}
        }
    }
}
