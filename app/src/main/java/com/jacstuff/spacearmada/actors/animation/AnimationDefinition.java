package com.jacstuff.spacearmada.actors.animation;

import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.image.BitmapDimension;



/*
    Provides information to the owner actor about how many frames of animation a given state has, whether it loops though those frames
     and what state the owner should go into next if it's reached the end of its frame count

 */
public class AnimationDefinition {


    private int frame;
    private boolean doesLoop;
    private ActorState nextState;
    private int width, height;


    AnimationDefinition(int frame, boolean doesLoop){
        this(frame, doesLoop, null);
    }

    AnimationDefinition(int frame, boolean doesLoop, ActorState nextState){
        this.frame = frame;
        this.doesLoop = doesLoop;
        this.nextState = nextState;
    }

    public AnimationDefinition(int frame, boolean doesLoop, BitmapDimension bitmapDimension, ActorState nextState){

        this.frame = frame;
        this.doesLoop = doesLoop;
        this.width = bitmapDimension.getWidth();
        this.height = bitmapDimension.getHeight();
        this.nextState = nextState;
    }

    int getFrame(){
        return this.frame;
    }

    ActorState getNextState(){
        return this.nextState;
    }

    boolean doesLoop(){
        return doesLoop;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

}
