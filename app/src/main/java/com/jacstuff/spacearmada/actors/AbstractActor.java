package com.jacstuff.spacearmada.actors;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.jacstuff.spacearmada.DrawableItem;
import com.jacstuff.spacearmada.utils.ImageLoader;

/**
 * Created by John on 30/08/2017.
 * Represents a visual object on the canvas.
 *
 */

public class AbstractActor implements DrawableItem {

    private ActorState actorState;
    protected AnimationManager animationManager;
    private Rect boundingBox;
    private Drawable currentDrawable;
    private DrawInfo drawInfo;
    protected int speed = 2;

    public AbstractActor(){

    }
    public AbstractActor(AnimationInfoService animationInfoService, ImageLoader imageLoader, Rect rect, int defaultResourceId){
        actorState = ActorState.DEFAULT;
        animationManager = new AnimationManager(imageLoader);
        addAnimation(actorState, defaultResourceId);
        this.boundingBox = new Rect(rect);
        this.drawInfo = new DrawInfo(animationInfoService, this.boundingBox.left, this.boundingBox.top);
    }


/*
    public AbstractActor(ImageLoader imageLoader,  Rect rect, int defaultResourceId){
        actorState = ActorState.DEFAULT;
        animationManager = new AnimationManager(imageLoader);
        addAnimation(actorState, defaultResourceId);
        this.boundingBox = new Rect(rect);
        AnimationInfoService animationInfoService = new AnimationInfoService("ENEMY_SHIPS");
        this.drawInfo = new DrawInfo(animationInfoService, this.boundingBox.left, this.boundingBox.top);

    }
*/

    private void log(String msg){
        Log.i("AbstractActor", msg);
    }

    public void offsetBounds(int dx, int dy){
        this.boundingBox.offset(dx,dy);
        this.drawInfo.setXY(boundingBox.left, boundingBox.top);

    }


    public ActorState getState(){
        return this.actorState;
    }

    protected boolean doesBoundingBoxIntersectWith(Rect otherBoundingBox){
        return new Rect(boundingBox).intersect(otherBoundingBox);
    }

    public DrawInfo getDrawInfo(){
        return this.drawInfo;
    }

    public ActorState getActorState(){
        return this.actorState;
    }

    public void setActorState(ActorState actorState){

        drawInfo.setState(actorState);
        this.actorState = actorState;
    }

    public Rect getBounds(){
        return new Rect(boundingBox);
    }

    public void setBounds(Rect newBounds){
        this.boundingBox = new Rect(newBounds);
    }

    public Drawable getDrawable(){
        //log("Entered getDrawable()");
        currentDrawable = animationManager.getCurrentDrawable(actorState);
        //log("about to set bounds");
        if(currentDrawable == null){
            log("Current drawable is null!");
        }
        currentDrawable.setBounds(boundingBox);
        return currentDrawable;
    }

    public void addAnimation(ActorState actorState, int...resIds){
        animationManager.addAnimation(actorState, resIds);
    }


    public void updateAnimation(){
        animationManager.updateFrame();
        drawInfo.incFrame();
        try {
            if (actorState == ActorState.DESTROYING) {
                if(animationManager.isLastFrame(actorState)){
                    this.actorState = ActorState.DESTROYED;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.i("PrintTaskRun", "error: " + e.getMessage());
        }
    }


}
