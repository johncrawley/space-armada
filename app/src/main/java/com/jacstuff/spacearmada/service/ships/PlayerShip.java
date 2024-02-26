package com.jacstuff.spacearmada.service.ships;

import android.graphics.RectF;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.actors.ships.ControllableShip;
import com.jacstuff.spacearmada.service.ships.weapons.ProjectileManager;
import com.jacstuff.spacearmada.service.ships.weapons.Weapon;
import com.jacstuff.spacearmada.view.fragments.game.ItemType;

public class PlayerShip extends AbstractItem implements ControllableShip {

    private final RectF moveBounds = new RectF();
    private float previousX, previousY;
    private Direction currentDirection = Direction.NONE;
    private Weapon mainWeapon;


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
                .setBounds(moveBounds)
                .build();
        mainWeapon.addBarrel(0, -100);
    }


    public void setScreenBounds(RectF screenBounds){
        moveBounds.left = screenBounds.left;
        moveBounds.top = screenBounds.top;
        moveBounds.right = screenBounds.right;
        moveBounds.bottom = screenBounds.bottom;
        mainWeapon.setBounds(moveBounds);
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
        log("Entered fire()");
        mainWeapon.startFiring();
    }


    private void log(String msg){
        System.out.println("^^^ PlayerShip: " + msg);
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
        this.x = centreX - (width / 2f);
        this.y = centreY - (height / 2f);
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
        if(y + height > moveBounds.bottom){
            y = moveBounds.bottom - height;
        }
    }


    public void moveUp(){
        y -= speed;
        if(y < moveBounds.top){
            y = moveBounds.top;
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
