package com.jacstuff.spacearmada.view.fragments.game.controls;

/*
 * Created by John on 28/05/2017.
 * Represents a d-pad controller - links a motion event in a specific area of the draw
 *  surface to a controllable actor.
 *
 * Represents an 8-way directional pad, and invokes specific commands if the coordinates fall
 *  between particular segments of the dpad circle;
 */

import static com.jacstuff.spacearmada.model.ships.player.Direction.*;

import com.jacstuff.spacearmada.model.ships.player.Direction;


public class DPad {

    private CircleSegmentLine topRightLine, topLeftLine, leftTopLine, rightTopLine;
    float radius, circleCentreX, circleCentreY;
    private final float radiusSquared;



    public DPad(int xPos, int yPos, int radius) {
        this.radius = radius;
        radiusSquared = radius * radius;
        circleCentreX = xPos + radius;
        circleCentreY = yPos + radius;
        calculateSegmentLines();

    }


    public float getCentreX() {
        return this.circleCentreX;
    }


    public float getCentreY() {
        return this.circleCentreY;
    }


    public float getRadius() { return this.radius;}


    boolean contains(float x, float y){
        return  squareOf(x - circleCentreX)
                + squareOf(y - circleCentreY)
                <= radiusSquared;
    }


    private float squareOf(float value){
        return value * value;
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


    public Direction getDirectionFor(TouchPoint touchPoint){
        var x = touchPoint.x;
        var y = touchPoint.y;
        boolean isPointLeftOfTopLeftLine = pointIsLeftOf(x,y, topLeftLine);
        boolean isPointLeftOfTopRightLine = pointIsLeftOf(x,y,topRightLine);
        boolean isPointLeftOfLeftTopLine = pointIsLeftOf(x,y, leftTopLine);
        boolean isPointLeftOfRightTopLine = pointIsLeftOf(x,y, rightTopLine);

        if(isPointLeftOfTopLeftLine && !isPointLeftOfTopRightLine){
            return DOWN;
        }
        else if(!isPointLeftOfTopLeftLine && isPointLeftOfTopRightLine){
            return UP;
        }
        else if(isPointLeftOfTopLeftLine && !isPointLeftOfLeftTopLine){
            return UP_LEFT;
        }
        else if(isPointLeftOfLeftTopLine && isPointLeftOfRightTopLine){
            return LEFT;
        }
        else if(!isPointLeftOfLeftTopLine && !isPointLeftOfRightTopLine){
            return RIGHT;
        }
        else if(!isPointLeftOfTopLeftLine && isPointLeftOfLeftTopLine){
            return DOWN_RIGHT;
        }
        else if(!isPointLeftOfTopRightLine){
            return UP_RIGHT;
        }
        return DOWN_LEFT;
    }


    private boolean pointIsLeftOf(float pointX, float pointY, CircleSegmentLine line){
        return !line.isRightOf(pointX, pointY);
    }
}


