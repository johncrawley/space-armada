package com.jacstuff.spacearmada.service;

import static org.junit.Assert.assertEquals;

import android.graphics.Rect;

import org.junit.Before;
import org.junit.Test;

public class PlayerShipTest {

    private PlayerShip playerShip;
    private int screenMinX, screenMinY;
    private int screenMaxX = 200;
    private int screenMaxY = 200;
    private int initialX = 50;
    private int initialY = 50;

    @Before
    public void setup(){
        playerShip = new PlayerShip(50, 50, new Rect(screenMinX, screenMinY, screenMaxX, screenMaxY));
    }


    @Test
    public void shipCanMove(){
        playerShip.moveDown();
        assertEquals(initialX, playerShip.getX());
        assertEquals(initialY + PlayerShip.distanceToMove, playerShip.getY());


        playerShip.moveUp();
        assertEquals(initialX, playerShip.getX());
        assertEquals(initialY, playerShip.getY());


        playerShip.moveLeft();
        assertEquals(initialX - PlayerShip.distanceToMove, playerShip.getX());
        assertEquals(initialY, playerShip.getY());

        playerShip.moveRight();
        assertEquals(initialX, playerShip.getX());
        assertEquals(initialY, playerShip.getY());
    }


}
