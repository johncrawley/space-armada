package com.jacstuff.spacearmada.view.fragments.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.jacstuff.spacearmada.controls.TouchPoint;
import com.jacstuff.spacearmada.managers.InputControlsManager;
import com.jacstuff.spacearmada.service.Game;
import com.jacstuff.spacearmada.view.TransparentView;

import java.util.ArrayList;
import java.util.List;

public class DpadControlView{

    private final TransparentView dpadView;
    private final InputControlsManager inputControlsManager;


    @SuppressLint("ClickableViewAccessibility")
    public DpadControlView(Context context, Game game, View parentView, int viewId){
        dpadView = parentView.findViewById(viewId);
        int width = dpadView.getMeasuredWidth();
        int height = dpadView.getMeasuredHeight();
        dpadView.setOnTouchListener(this::onTouchEvent);
        inputControlsManager = new InputControlsManager(context, dpadView.getMeasuredWidth(), dpadView.getMeasuredHeight(), game);
    }


    private boolean onTouchEvent(View view, MotionEvent motionEvent) {
        List<TouchPoint> touchPoints = new ArrayList<>();
        for (int i = 0; i < motionEvent.getPointerCount(); i++) {
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
