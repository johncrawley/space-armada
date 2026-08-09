package com.jacstuff.spacearmada.view.fragments.game;

import android.graphics.Point;

import java.util.List;

public interface GameView {
    void updateShipPosition(float x, float y);
    void setShipSize(int width, int height);
    void updateStars(List<Point> starCoordinates);
    void updateItems(List<DrawInfoOLD> drawInfoList);
    void updateProjectiles(List<DrawInfoOLD> drawInfoList);
    void onGameOver();
    void updateShipHealth(int remainingHealth);
}
