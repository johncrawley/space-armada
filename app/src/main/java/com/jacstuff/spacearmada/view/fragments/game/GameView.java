package com.jacstuff.spacearmada.view.fragments.game;

import android.graphics.Point;

import java.util.List;

public interface GameView {

    void updateShip(float x, float y);
    void updateEnemyShip(int x, int y);
    void updateStars(List<Point> starCoordinates);
}
