package com.jacstuff.spacearmada.controls;

/*
 * Created by John on 29/08/2017.
 * Represents an 8-way directinal pad, and invokes specific commands
 * based on whether or not an x,y falls between particular segments of the dpad circle;
 */

        import android.util.Log;

        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import com.jacstuff.spacearmada.Direction;
        import com.jacstuff.spacearmada.TouchPoint;
        import com.jacstuff.spacearmada.commands.Command;

/**
 * Created by John on 28/05/2017.
 *
 * Represents a d-pad controller - links a motion event in a specific area of the draw surface to a controllable actor.
 */
public class DPad extends CircularControl {


    private CircleSegmentLine topRightLine, topLeftLine, leftTopLine, rightTopLine;
    private Map<Direction, Command> commandMap;

    public DPad(int xPos, int yPos, int radius) {
        super(xPos, yPos, radius);
        commandMap = new HashMap<>();
        calculateSegmentLines();
    }

    @Override
    public void setCentrePosition(int x, int y, int radius){
        super.setCentrePosition(x,y,radius);
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

/*
    public String getLineColour(int index){
        if(lines[index] == null){
            return "#BBBBBB";
        }
        return lines[index].getColour();
    }

    public float getLine(int lineNumber, String coordinate){
        CircleSegmentLine line = lines[lineNumber];
        if(line == null){
            return 0.0f;
        }
        return line.getCoordinate(coordinate);
    }
    */


    private CircleSegmentLine initSegmentLine(float angle, String label) {
        return new CircleSegmentLine(circleCentreX, circleCentreY, radius, angle, label);
    }

    public void process(float x, float y, boolean isUpEvent){

        if(contains(x,y)){
            if(isUpEvent){
                commandMap.get(Direction.NONE).release(); //0, because
            }
            calculateDirection(x,y);
        }
    }
    // what's going wrong
    // It's not that there's some touchPoints not getting passed into this method
    // This method only gets called when there is an event
    // Calculate direction only gets called when the touchpoint is not a release
    // It's getting called when
    public void process(List<TouchPoint> touchPoints){
      //  boolean containsAReleaseTP = false;
      //  for(TouchPoint tp : touchPoints){
       //     if(tp.isRelease())containsAReleaseTP = true;}
      //  if(containsAReleaseTP){
           // Log.i("DPad process()", "Release TP detected: Data:");
          //      for (TouchPoint tp : touchPoints) {
             //       Log.i("DPad process()", "TP x,y,isRelease:" + tp.getX() + " " + tp.getY() + " " + tp.isRelease());
            //    }
       // }
        for(TouchPoint touchPoint : touchPoints) {
            float x = touchPoint.getX();
            float y = touchPoint.getY();
            if ( (!touchPoint.isRelease()) && contains(x, y) ) {
                calculateDirection(x,y);
                return;
            }

        }

        commandMap.get(Direction.NONE).release(); //it actually doesn't matter which MoveCommand you call release on.
    }
/*
    public boolean containsEvent(MotionEvent e){
        float x = e.getX();
        float y = e.getY();
        return intersects(x, y);
    }
    */

    private void invoke(Direction d){

        commandMap.get(d).invoke();
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
        else if(!isPointLeftOfTopRightLine){// && isPointLeftOfRightTopLine){
            invoke(Direction.UP_RIGHT);
        }
        else invoke(Direction.DOWN_LEFT);

    }

    private boolean pointIsLeftOf(float pointX, float pointY, CircleSegmentLine line){
        return !line.isRightOf(pointX, pointY);
    }
}


class CircleSegmentLine {
    private float x1, y1, x2, y2;
    //private String colour;


    CircleSegmentLine(float circleX, float circleY, float radius, float angle, String colour, String label) {
        this.x1 = circleX;
        this.y1 = circleY;
        float pi = (float)Math.PI;
        float angleInRadians = angle * pi/ 180.0f;
        this.x2 = (float) (circleX + radius * Math.cos(angleInRadians));
        this.y2 = (float) (circleY + radius * Math.sin(angleInRadians));
        //this.colour = colour;
    }
    CircleSegmentLine(float circleX, float circleY, float radius, float angle, String label) {
        this.x1 = circleX;
        this.y1 = circleY;
        float pi = (float)Math.PI;
        float angleInRadians = angle * pi/ 180.0f;
        this.x2 = (float) (circleX + radius * Math.cos(angleInRadians));
        this.y2 = (float) (circleY + radius * Math.sin(angleInRadians));
        //this.colour = colour;
    }



    // returns true if the line is to the right of the point
    boolean isRightOf(float pointX, float pointY) {
        return (x2 - x1) * (pointY - y1) - ((y2 - y1) * (pointX - x1)) > 0;
    }

}