package com.jacstuff.spacearmada.state;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.List;

import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.controls.TouchPoint;
import com.jacstuff.spacearmada.game.GameState;

/**
 * Created by john on 11/04/18.
 * Handles the actorState of the game
 */

public class StateManager {

    private final Activity context;
    private final int width, height;
    private State currentState;
    public enum StateCode {GAME, TITLE, HIGH_SCORE, CREDITS, OPTIONS}


    public StateManager(Activity context, int width, int height){
        this.context = context;
        this.width = width;
        this.height = height;
        //currentState = new TitleState(context, width, height, this);
        currentState = new GameState(this, context, width, height);
        context.findViewById(R.id.titleLayout).setVisibility(View.GONE);
        context.findViewById(R.id.gameLayout).setVisibility(View.VISIBLE);
    }


    public void destroy(){
        currentState.destroy();
    }


    public void onPause(){
        if(currentState == null){
            return;
        }
        currentState.onPause();
    }


    public void onResume(){
        if(currentState == null){
            return;
        }
        currentState.onResume();
    }


    public  void handleTouchPoints(List<TouchPoint> touchPoints){
        currentState.handleTouchPoints(touchPoints);
    }


    public void update(){
        currentState.update();
    }


    public void draw(Canvas canvas, Paint paint){
        currentState.draw(canvas,paint);
    }


    public void setState(StateCode stateCode){
        switch(stateCode){
            case GAME:
                currentState = new GameState(this, context, width, height);
                break;
            case TITLE:
                currentState = new TitleState(context, width, height, this);
                break;
        }
    }

}
