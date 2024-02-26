package com.jacstuff.spacearmada.view.fragments.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.jacstuff.spacearmada.actors.ships.ControllableShip;
import com.jacstuff.spacearmada.controls.TouchPoint;
import com.jacstuff.spacearmada.view.TransparentView;

import java.util.ArrayList;
import java.util.List;

public class FireButtonControlView {

    private final TransparentView fireButtonView;
    private ControllableShip controllableShip;
    private int centreX, centreY;
    private final int radius, radiusSquared;


    @SuppressLint("ClickableViewAccessibility")
    public FireButtonControlView(Context context, TransparentView fireButtonView){
        radius = getPixelsFrom(context, 30);
        radiusSquared = (int)Math.pow(radius, 2);
        this.fireButtonView = fireButtonView;
        fireButtonView.setOnTouchListener(this::onTouchEvent);
    }


    @SuppressLint("ClickableViewAccessibility")
    public void init(ControllableShip controllableShip, int width, int height){
        centreX = width / 2;
        centreY = height / 2;

        this.controllableShip = controllableShip;
        drawView(centreX, centreY, radius);
    }


    private void drawView(int centreX, int centreY, int radius){
        fireButtonView.addDrawableItem((canvas, paint) -> {
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawCircle(centreX, centreY, radius, paint);
            paint.setStrokeWidth(5f);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.DKGRAY);
            canvas.drawCircle(centreX, centreY, radius - 20, paint);
        });
    }


    public int getPixelsFrom(Context context, int dp){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }


    private boolean onTouchEvent(View view, MotionEvent motionEvent) {
        List<TouchPoint> touchPoints = new ArrayList<>();
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        if(action == MotionEvent.ACTION_DOWN){
            if(isWithinButtonBounds(x,y)) {
                controllableShip.fire();
            }
        }
        else if(isUpEvent(motionEvent)){
            controllableShip.releaseFire();
        }
        return true;
    }


    boolean isWithinButtonBounds(float x, float y){
        return  squareOf(x - centreX)
                + squareOf(y - centreY)
                <= radiusSquared;
    }


    private float squareOf(float value){
        return value * value;
    }


    private boolean isUpEvent(MotionEvent event){
        return event.getActionMasked() == MotionEvent.ACTION_POINTER_UP || event.getAction() == MotionEvent.ACTION_UP ;
    }

}