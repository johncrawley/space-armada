package com.jacstuff.spacearmada.controls;

import java.util.List;


import com.jacstuff.spacearmada.TouchPoint;
import com.jacstuff.spacearmada.commands.Command;

/**
 * Created by John on 29/08/2017.
 *
 *
 */

public class ControlButton extends CircularControl {

    private Command command;
    public ControlButton(int x, int y, int radius){
        super(x,y,radius);
    }

    public void assignCommand(Command command){
        this.command = command;
    }

    public void process(float x, float y, boolean isUpEvent){
        if(contains(x,y)){
            if(isUpEvent){
                command.release();
            }
            command.invoke();
        }
    }
    public void process(List<TouchPoint> touchPoints){
        boolean isTouchDetected = false;
        for(TouchPoint touchPoint : touchPoints) {
            float x = touchPoint.getX();
            float y = touchPoint.getY();
            if (contains(x, y) && !touchPoint.isRelease()) {
                isTouchDetected = true;
                command.invoke();
                break;
            }
        }
        if(!isTouchDetected){
            command.release();
        }
    }
}
