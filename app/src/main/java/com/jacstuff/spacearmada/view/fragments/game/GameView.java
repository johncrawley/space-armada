package com.jacstuff.spacearmada.view.fragments.game;

import android.graphics.Point;
import android.widget.ImageView;

import java.util.List;

public interface GameView {
    void updateShipPosition(float x, float y);
    void setShipSize(int width, int height);
    void updateStars(List<Point> starCoordinates);
    void updateItems(List<DrawInfo> drawInfoList);
    void updateProjectiles(List<DrawInfo> drawInfoList);
    void onGameOver();
}
