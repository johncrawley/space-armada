package com.jacstuff.spacearmada.actors;

import android.graphics.Rect;
import android.util.Log;

import com.jacstuff.spacearmada.DrawableItem;
import com.jacstuff.spacearmada.utils.ImageLoader;

/**
 * Created by John on 30/08/2017.
 * Represents a visual object on the canvas.
 *
 */

public class AbstractActor implements DrawableItem {

    private Rect boundingBox;
    private DrawInfo drawInfo;
    protected int speed = 2;

    public AbstractActor(){

    }

    AbstractActor(AnimationDefinitionGroup animationInfoService, ImageLoader imageLoader, int x, int y, int width, int height, int defaultResourceId){
        this.boundingBox = new Rect(x,y, x + width, y + height);
        this.drawInfo = new DrawInfo(animationInfoService, this.boundingBox.left, this.boundingBox.top);
    }


    private void log(String msg){
        Log.i("AbstractActor", msg);
    }

    protected void offsetBounds(int dx, int dy){
        this.boundingBox.offset(dx,dy);
        this.drawInfo.setXY(boundingBox.left, boundingBox.top);
    }


    boolean doesBoundingBoxIntersectWith(Rect otherBoundingBox){
        return new Rect(boundingBox).intersect(otherBoundingBox);
    }

    public DrawInfo getDrawInfo(){
        return this.drawInfo;
    }

    public ActorState getState(){
        return drawInfo.getState();
    }

    public void setState(ActorState actorState){

        drawInfo.setState(actorState);
    }

    public Rect getBounds(){
        return new Rect(boundingBox);
    }


    public void updateAnimation(){
        drawInfo.incFrame();
    }


}
