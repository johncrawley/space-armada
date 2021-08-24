package com.jacstuff.spacearmada.game;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.spacearmada.controls.TouchPoint;

import java.util.List;

interface GameStateHandler{
    void handleTouchPoints(List<TouchPoint> touchPoints);
    void draw(Canvas canvas, Paint paint);
    void update();
}