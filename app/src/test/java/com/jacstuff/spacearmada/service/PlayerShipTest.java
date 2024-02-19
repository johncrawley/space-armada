package com.jacstuff.spacearmada.service;

import static org.junit.Assert.assertEquals;

import android.graphics.RectF;

import com.jacstuff.spacearmada.service.ships.PlayerShip;

import org.junit.Before;
import org.junit.Test;

public class PlayerShipTest {

    private PlayerShip playerShip;
    private final float screenMinX = 0, screenMinY = 0;
    private final float screenMaxX = 200;
    private final float screenMaxY = 200;
    private final int initialX = 50;
    private final int initialY = 50;

    @Before
    public void setup(){
        RectF screenBounds = new RectF();
        screenBounds.left = screenMinX;
        screenBounds.right = screenMaxX;
        screenBounds.top = screenMinY;
        screenBounds.bottom = screenMaxY;
        System.out.println("setup screenBounds : " + screenBounds.left + ","
                + screenBounds.top + ","
                + screenBounds.right + ","
                + screenBounds.bottom);
        playerShip = new PlayerShip(initialX, initialY, screenBounds);
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
        float halfWidth = playerShip.getWidth() / 2f;
        float halfHeight = playerShip.getHeight() / 2f;

        playerShip.moveCentreTo(screenMinX + halfWidth, screenMinY +halfHeight);
        assertFloat(screenMinX, playerShip.getX());
        assertFloat(screenMinY, playerShip.getY());

        playerShip.moveLeft();
        assertFloat(screenMinX, playerShip.getX());
        playerShip.moveUp();
        assertFloat(screenMinY, playerShip.getY());


        playerShip.moveCentreTo(screenMaxX - halfWidth, screenMaxY - halfHeight);

        float expectedX = screenMaxX - playerShip.getWidth();
        float expectedY = screenMaxY - playerShip.getHeight();

        assertFloat(expectedX, playerShip.getX());
        assertFloat(expectedY, playerShip.getY());

        playerShip.moveRight();
        assertFloat(expectedX, playerShip.getX());
        playerShip.moveDown();
        assertFloat(expectedY, playerShip.getY());
    }


    private void assertFloat(float expected, float actual){
        assertEquals(expected, actual, 0.001f);
    }


}
