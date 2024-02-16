package com.jacstuff.spacearmada.controls;

/*
 * Created by John on 28/05/2017.
 * Represents a d-pad controller - links a motion event in a specific area of the draw
 *  surface to a controllable actor.
 *
 * Represents an 8-way directional pad, and invokes specific commands
 * based on whether or not an x,y falls between particular segments of the dpad circle;
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.commands.Command;


public class DPad extends CircularControl {

    private CircleSegmentLine topRightLine, topLeftLine, leftTopLine, rightTopLine;
    private final Map<Direction, Command> commandMap;


    public DPad(int xPos, int yPos, int radius) {
        super(xPos, yPos, radius);
        commandMap = new HashMap<>();
        calculateSegmentLines();
    }


    @Override
    public void setCentrePosition(int x, int y, int radius){
        super.setCentrePosition(x, y, radius);
        calculateSegmentLines();
    }


    public void assignCommand(Direction direction, Command command){
        commandMap.put(direction, command);
    }


    private void calculateSegmentLines() {
        leftTopLine = initSegmentLine(202.5f, "leftTopLine");
        topLeftLine = initSegmentLine(247.5f, "topLeftLine");
        topRightLine = initSegmentLine(292.5f, "topRightLine");
        rightTopLine = initSegmentLine(337.5f, "rightTopLine");
    }


    private CircleSegmentLine initSegmentLine(float angle, String label) {
        return new CircleSegmentLine(circleCentreX, circleCentreY, radius, angle, label);
    }


    public void process(float x, float y, boolean isUpEvent){
        if(contains(x,y)){
            if(isUpEvent){
               Command command = commandMap.get(Direction.NONE);
               if(command != null){
                   command.release();
               }
            }
            calculateDirection(x,y);
        }
    }


    public void process(List<TouchPoint> touchPoints){
        for(TouchPoint touchPoint : touchPoints) {
            if(handleTouchPoint(touchPoint)){
                return;
            }
        }
        releaseCommand();
    }


    private boolean handleTouchPoint(TouchPoint touchPoint){
        float x = touchPoint.getX();
        float y = touchPoint.getY();
        if ( (!touchPoint.isRelease()) && contains(x, y) ) {
            calculateDirection(x,y);
            return true;
        }
        return false;
    }


    private void releaseCommand(){
        Command command = commandMap.get(Direction.NONE);
        if(command != null){
            command.release();
        }//it actually doesn't matter which MoveCommand you call release on.
    }


    private void invoke(Direction d){
        Command command = commandMap.get(d);
        if(command != null){
            command.invoke();
        }
    }


    private void calculateDirection(float x, float y){
        boolean isPointLeftOfTopLeftLine = pointIsLeftOf(x,y, topLeftLine);
        boolean isPointLeftOfTopRightLine = pointIsLeftOf(x,y,topRightLine);
        boolean isPointLeftOfLeftTopLine = pointIsLeftOf(x,y, leftTopLine);
        boolean isPointLeftOfRightTopLine = pointIsLeftOf(x,y, rightTopLine);

        if(isPointLeftOfTopLeftLine && !isPointLeftOfTopRightLine){
            invoke(Direction.DOWN);
        }
        else if(!isPointLeftOfTopLeftLine && isPointLeftOfTopRightLine){
            invoke(Direction.UP);
        }
        else if(isPointLeftOfTopLeftLine && !isPointLeftOfLeftTopLine){
            invoke(Direction.UP_LEFT);
        }
        else if(isPointLeftOfLeftTopLine && isPointLeftOfRightTopLine){
            invoke(Direction.LEFT);
        }
        else if(!isPointLeftOfLeftTopLine && !isPointLeftOfRightTopLine){
            invoke(Direction.RIGHT);
        }
        else if(!isPointLeftOfTopLeftLine && isPointLeftOfLeftTopLine){
            invoke(Direction.DOWN_RIGHT);
        }
        else if(!isPointLeftOfTopRightLine){
            invoke(Direction.UP_RIGHT);
        }
        else invoke(Direction.DOWN_LEFT);
    }


    private boolean pointIsLeftOf(float pointX, float pointY, CircleSegmentLine line){
        return !line.isRightOf(pointX, pointY);
    }
}


