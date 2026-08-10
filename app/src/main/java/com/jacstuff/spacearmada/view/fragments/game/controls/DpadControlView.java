package com.jacstuff.spacearmada.view.fragments.game.controls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.service.Game;
import com.jacstuff.spacearmada.service.ships.ControllableShip;
import com.jacstuff.spacearmada.view.TransparentView;
import com.jacstuff.spacearmada.view.custom.DPad;
import com.jacstuff.spacearmada.view.custom.MoveCommand;
import com.jacstuff.spacearmada.view.custom.TouchPoint;

import java.util.ArrayList;

public class DpadControlView{

    private final TransparentView dpadView;
    private final Context context;
    private DPad dpad;
    private Game game;

    public DpadControlView(Context context, TransparentView dpadView, Game game){
        this.context = context;
        this.dpadView = dpadView;
        this.game = game;
    }


    @SuppressLint("ClickableViewAccessibility")
    public void initControls(int width, int height){
        int radius = getPixelsFrom(50);
        int centreX = width / 2;
        int centreY = height / 2;

       // setupDpad(ship, centreX - radius, centreY - radius, radius);

        dpad = new DPad(centreX - radius, centreY - radius, radius);
        dpadView.setOnTouchListener(this::onTouchEvent);
        drawCircleOnDpad(centreX, centreY, radius);
    }


    public void setupDpad(ControllableShip ship, int centreX, int centreY, int radius){
        dpad = new DPad(centreX, centreY, radius);
        for(var direction : Direction.values()){
            assignDPadCommand(ship, direction);
        }
    }


    private void assignDPadCommand(ControllableShip ship, Direction d){
        var command = new MoveCommand(ship);
        command.assignDirection(d);
        dpad.assignCommand(d, command);
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
        var touchPoints = new ArrayList<TouchPoint>();
        if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            game.move(Direction.NONE);
        }
        for (int i = 0; i < motionEvent.getPointerCount(); i++) {
            touchPoints.add(createTouchPoint(motionEvent, i));
        }
        // dpad.process(touchPoints);
        var direction = dpad.getDirectionFor(touchPoints.get(0));
        game.move(direction);

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
