package com.jacstuff.spacearmada.service.ships;

import android.graphics.RectF;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.actors.ships.ControllableShip;
import com.jacstuff.spacearmada.service.ships.weapons.ProjectileManager;
import com.jacstuff.spacearmada.service.ships.weapons.Weapon;
import com.jacstuff.spacearmada.view.fragments.game.ItemType;

public class PlayerShip extends AbstractItem implements ControllableShip {

    private final RectF screenBounds = new RectF();
    private float previousX, previousY;
    private Direction currentDirection = Direction.NONE;
    private Weapon mainWeapon;
    private int maxX, maxY;


    public PlayerShip(int initialX, int initialY){
        super(1, ItemType.PLAYER_SHIP, 5,.05f,2.4f);
        this.x = initialX;
        this.y = initialY;
    }


    public void initWeapons(ProjectileManager projectileManager){
        mainWeapon = Weapon.Builder.newInstance()
                .setOwner(this)
                .setProjectileManager(projectileManager)
                .setRate(20)
                .setHeightWidthRatio(4f)
                .setSpeed(20)
                .setSizeFactor(0.01f)
                .setProjectileType(ItemType.PLAYER_BULLET)
                .setBounds(screenBounds)
                .build();
        mainWeapon.addBarrel(0, -100);
    }


    public void setScreenBoundsAndSize(RectF screenBounds, int smallestDimension){
        this.screenBounds.left = screenBounds.left;
        this.screenBounds.top = screenBounds.top;
        this.screenBounds.right = screenBounds.right;
        this.screenBounds.bottom = screenBounds.bottom;
        setWeaponBounds();
        setSizeBasedOn(smallestDimension);
        maxX = (int)(screenBounds.right - width);
        maxY = (int)(screenBounds.bottom - height);
    }


    private void setWeaponBounds(){
        if(mainWeapon != null) {
            mainWeapon.setBounds(this.screenBounds);
        }
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
    public void fire() {
        mainWeapon.startFiring();
    }


    @Override
    public void releaseFire(){
        mainWeapon.stopFiring();
    }


    @Override
    public void stopMoving(){
        this.currentDirection = Direction.NONE;
    }


    public void moveCentreTo(float centreX, float centreY){
        setX(centreX - (width / 2f));
        setY(centreY - (height / 2f));
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
        setX(getMovedCoordinate(x, Movement.RIGHT, maxX));
    }


    public void moveLeft(){
        setX(getMovedCoordinate(x, Movement.LEFT, screenBounds.left));
    }


    public void moveDown(){
        setY(getMovedCoordinate(y, Movement.DOWN, maxY));
    }


    public void moveUp(){
        setY(getMovedCoordinate(y, Movement.UP, screenBounds.top));
    }


    private float getMovedCoordinate(float value, Movement direction, float limit){
        float result = getMoved(value, direction);
        return direction.isIncreasing() ? Math.min(result, limit) : Math.max(result, limit);
    }


    private float getMoved(float value, Movement direction){
        return value + getPixelsToMove(direction);
    }


    public int getPixelsToMove(Movement direction){
        return speed * direction.getValue();
    }


    private enum Movement {

        UP(-1), DOWN(1), LEFT(-1), RIGHT(1);
        final int value;

        Movement(int value){
            this.value = value;
        }

        int getValue(){
            return value;
        }

        boolean isIncreasing(){
            return value > 0;
        }
    }

    @Override
    public void update() {
        updateDirection();
        mainWeapon.update();
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
