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
import com.jacstuff.spacearmada.service.Game;
import com.jacstuff.spacearmada.view.TransparentView;

import java.util.ArrayList;
import java.util.List;

public class FireButtonControlView {

    private final TransparentView fireButtonView;
    private InputControlsManager inputControlsManager;
    private final Context context;
    private ControllableShip controllableShip;


    public FireButtonControlView(Context context, TransparentView fireButtonView){
        this.context = context;
        this.fireButtonView = fireButtonView;
    }



    @SuppressLint("ClickableViewAccessibility")
    public void initControls(ControllableShip controllableShip){
        int width = fireButtonView.getMeasuredWidth();
        int height = fireButtonView.getMeasuredHeight();
        width = 500;
        height = 500;
        int radius = getPixelsFrom(30);
        int centreX = width / 2;
        int centreY = height / 2;

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


    public int getPixelsFrom(int dp){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }


    private boolean onTouchEvent(View view, MotionEvent motionEvent) {
        List<TouchPoint> touchPoints = new ArrayList<>();
        int action = motionEvent.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            controllableShip.fire();
        }
        else if(isUpEvent(motionEvent)){
            controllableShip.releaseFire();
        }
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