package com.jacstuff.spacearmada.service.ships.navigation;

import android.graphics.PointF;

public class LinearNavigationPath implements NavigationPath{


    private final float x;
    private float y;
    private final boolean isMovingUp;
    private final float distancePerUpdate;


    public LinearNavigationPath(float initialX, float initialY, float distancePerUpdate, boolean isMovingUp){
        this.x = initialX;
        this.y = initialY;
        this.distancePerUpdate = distancePerUpdate;
        this.isMovingUp = isMovingUp;
    }


    @Override
    public PointF getNextCoordinate() {
        y += (distancePerUpdate * (isMovingUp ? -1 : 1));
        return new PointF(x,y);
    }
}
