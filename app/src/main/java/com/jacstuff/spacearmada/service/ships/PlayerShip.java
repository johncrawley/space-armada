package com.jacstuff.spacearmada.service.ships;

import android.graphics.RectF;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.actors.ships.ControllableShip;
import com.jacstuff.spacearmada.view.fragments.game.ItemType;

public class PlayerShip extends AbstractItem implements ControllableShip {

    private final RectF moveBounds;
    private float previousX, previousY;
    private Direction currentDirection = Direction.NONE;


    public PlayerShip(int initialX, int initialY, RectF moveBounds){
        super(1, ItemType.PLAYER_SHIP, 5,.05f,2.4f);
        this.x = initialX;
        this.y = initialY;
        this.moveBounds = moveBounds;
    }


    public void setScreenBounds(RectF screenBounds){
        moveBounds.left = screenBounds.left;
        moveBounds.top = screenBounds.top;
        moveBounds.right = screenBounds.right;
        moveBounds.bottom = screenBounds.bottom;

    }


    public void setDimensions(float width, float height){
        this.width = width;
        this.height = height;
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


    public void moveCentreTo(float x, float y){
        this.x = x - (width / 2f);
        this.y = y - (height / 2f);
    }


    public boolean hasPositionChanged(){
        boolean hasChanged = x != previousX || y != previousY;
        previousX = x;
        previousY = y;
        return hasChanged;
    }


    public float getCentreX(){
        return x + (width / 2f);
    }


    public float getCentreY(){
        return y + (height / 2f);
    }


    public void moveRight(){
        x += speed;
        if((x + width) > moveBounds.right){
            x = moveBounds.right - width;
        }
    }


    public void moveLeft(){
        x -= speed;
        if((x< moveBounds.left)){
            x = moveBounds.left;
        }
    }


    public void moveDown(){
        y += speed;
        if(y > moveBounds.bottom){
            y = moveBounds.bottom;
        }
    }


    public void moveUp(){
        y -= speed;
        if(y < moveBounds.top){
            y = moveBounds.top;
        }
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
