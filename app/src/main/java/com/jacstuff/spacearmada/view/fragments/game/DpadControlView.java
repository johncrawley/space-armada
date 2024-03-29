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
import com.jacstuff.spacearmada.managers.InputControlsManager;
import com.jacstuff.spacearmada.view.TransparentView;

import java.util.ArrayList;
import java.util.List;

public class DpadControlView{

    private final TransparentView dpadView;
    private InputControlsManager inputControlsManager;
    private final Context context;

    public DpadControlView(Context context, TransparentView dpadView){
        this.context = context;
        this.dpadView = dpadView;
    }


    @SuppressLint("ClickableViewAccessibility")
    public void initControls(ControllableShip controllableShip, int width, int height){
        int radius = getPixelsFrom(50);
        int centreX = width / 2;
        int centreY = height / 2;

        inputControlsManager = new InputControlsManager(width, height, controllableShip);
        inputControlsManager.setupDpad(centreX - radius, centreY - radius, radius);
        dpadView.setOnTouchListener(this::onTouchEvent);
        drawCircleOnDpad(centreX, centreY, radius);
    }


    private void drawCircleOnDpad(int centreX, int centreY, int radius){
        dpadView.addDrawableItem((canvas, paint) -> {
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawCircle(centreX, centreY, radius, paint);
            paint.setStrokeWidth(10f);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.DKGRAY);
            canvas.drawCircle(centreX, centreY, radius - 20, paint);
        });
    }


    public int getPixelsFrom(int dp){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }


    private boolean onTouchEvent(View view, MotionEvent motionEvent) {
        List<TouchPoint> touchPoints = new ArrayList<>();
        for (int i = 0; i < motionEvent.getPointerCount(); i++) {
            //printCoordinatesToTextView(motionEvent);
            touchPoints.add(createTouchPoint(motionEvent, i));
        }
        inputControlsManager.process(touchPoints);
        return true;
    }


    private TouchPoint createTouchPoint(MotionEvent motionEvent, int index){
        float x = motionEvent.getX(index);
        float y = motionEvent.getY(index);
        return new TouchPoint(x,y, isReleasedTouchPoint(motionEvent, index));
    }


    private boolean isReleasedTouchPoint(MotionEvent motionEvent, int pointIndex){
        return isUpEvent(motionEvent) && motionEvent.getActionIndex() == pointIndex;
    }


    private boolean isUpEvent(MotionEvent event){
        return event.getActionMasked() == MotionEvent.ACTION_POINTER_UP || event.getAction() == MotionEvent.ACTION_UP ;
    }

}
