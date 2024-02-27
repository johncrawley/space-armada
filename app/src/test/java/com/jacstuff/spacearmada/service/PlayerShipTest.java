package com.jacstuff.spacearmada.service;

import static org.junit.Assert.assertEquals;

import android.graphics.RectF;

import com.jacstuff.spacearmada.service.ships.PlayerShip;

import org.junit.Before;
import org.junit.Test;

public class PlayerShipTest {

    private PlayerShip playerShip;
    private final int initialX = 50;
    private final int initialY = 50;

    @Before
    public void setup(){
        float screenMaxX = 200;
        float screenMaxY = 200;
        float screenMinX = 0;
        float screenMinY = 0;
        RectF screenBounds = createBounds(screenMinX, screenMinY, screenMaxX, screenMaxY);

        playerShip = new PlayerShip(initialX, initialY);
        playerShip.setScreenBoundsAndSize(screenBounds, 200);
        playerShip.setSizeBasedOn(1000);
    }


    @Test
    public void shipCanBePlacedAtPosition(){
        int centreX = 400;
        int centreY = 400;
        playerShip.moveCentreTo(centreX, centreY);
        float expectedX = centreX - (playerShip.getWidth() / 2f);
        assertFloat(expectedX, playerShip.getX());
        assertFloat(centreX, playerShip.getCentreX());

        float expectedY = centreY - (playerShip.getHeight() / 2f);
        assertFloat(expectedY, playerShip.getY());
        assertFloat(centreY, playerShip.getCentreY());
    }


    @Test
    public void shipHasCorrectDimensions(){
        float width = 100;
        float height = 100;
        playerShip.setDimensions(width, height);
        assertFloat(width, playerShip.getWidth());
        assertFloat(height, playerShip.getHeight());
    }


    @Test
    public void shipCanMove(){
        playerShip.moveDown();
        assertFloat(initialX, playerShip.getX());
        assertFloat(initialY + playerShip.getSpeed(), playerShip.getY());


        playerShip.moveUp();
        assertFloat(initialX, playerShip.getX());
        assertFloat(initialY, playerShip.getY());


        playerShip.moveLeft();
        assertFloat(initialX - playerShip.getSpeed(), playerShip.getX());
        assertFloat(initialY, playerShip.getY());

        playerShip.moveRight();
        assertFloat(initialX, playerShip.getX());
        assertFloat(initialY, playerShip.getY());
    }


    @Test
    public void shipDoesNotEscapeBounds(){
        float left = 100;
        float top = 100;
        float right = 900;
        float bottom = 900;
        RectF bounds = createBounds(left, top, right, bottom);
        playerShip.setScreenBoundsAndSize(bounds, 900);
        float halfWidth = playerShip.getWidth() / 2f;
        float halfHeight = playerShip.getHeight() / 2f;
        log("player ship half height and half width: " + halfHeight + ", " + halfWidth);

        playerShip.moveCentreTo(left + halfWidth, top + halfHeight);
        assertShipHasNotMovedAfter(()->{
            playerShip.moveLeft();
            playerShip.moveUp();
        });

        playerShip.moveCentreTo(right - halfWidth, bottom - halfHeight);
        assertShipHasNotMovedAfter(()->{
            playerShip.moveRight();
            playerShip.moveDown();
        });
    }

    private void log(String msg){
        System.out.println("^^^ PlayerShipTest: " + msg);
    }


    private RectF createBounds(float left, float top, float right, float bottom){
        RectF bounds = new RectF();
        bounds.left = left;
        bounds.top = top;
        bounds.right = right;
        bounds.bottom = bottom;
        return bounds;
    }


    private void assertShipHasNotMovedAfter(Runnable runnable){
        float expectedX = playerShip.getX();
        float expectedY = playerShip.getY();
        runnable.run();
        assertFloat(expectedX, playerShip.getX());
        assertFloat(expectedY, playerShip.getY());

    }


    private void assertFloat(float expected, float actual){
        assertEquals(expected, actual, 0.001f);
    }


}
