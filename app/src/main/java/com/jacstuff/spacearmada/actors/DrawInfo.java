package com.jacstuff.spacearmada.actors;


import androidx.annotation.NonNull;

import com.jacstuff.spacearmada.actors.animation.AnimationDefinitionGroup;

public class DrawInfo {

    private ActorState actorState;
    private int x, y;
    private int frame;
    private AnimationDefinitionGroup animationDefinitionGroup;


    public DrawInfo(AnimationDefinitionGroup animationDefinitionGroup, int x, int y){
        actorState = ActorState.DEFAULT;
        this.animationDefinitionGroup = animationDefinitionGroup;
        this.x = x;
        this.y = y;
    }

    public void setState(ActorState actorState){
        this.actorState = actorState;
        this.frame = 0;
    }

    public ActorState getState(){
        return this.actorState;
    }

    public void setAnimationInfoService(AnimationDefinitionGroup animationInfoService){
        this.animationDefinitionGroup = animationInfoService;
    }

    public String getFamily(){
        return this.animationDefinitionGroup.getGroupName();
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

    @NonNull
    public String toString(){
        return " x,y: " + x + " " + y + " family: " + animationDefinitionGroup.getGroupName() + " actorState: " + actorState + " frame: " + frame;
    }

    public void moveX(int amount){
        this.x += amount;
    }
    public void moveY(int amount){

        this.y+= amount;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public ActorState getAnimationState() {
        return this.actorState;
    }


    public int getFrame(){
        return this.frame;
    }

    public void setFrame(int updatedFrame){

        this.frame = updatedFrame < 0 ? 0 : updatedFrame;
    }

    public void resetFrame(){
        this.frame = 0;
    }

    public void incFrame() {
        if (isOnLastFrameAndNoLoop()) {
            moveToNextStateIfOneExists();
            return;
        }
        frame++;
        resetFrameIfBeyondLimit();
    }


    private void moveToNextStateIfOneExists() {
        ActorState nextState = animationDefinitionGroup.getNextState(actorState);
        if(nextState != null) {
            resetFrame();
            actorState = nextState;
        }
    }


    private void resetFrameIfBeyondLimit() {
        if(frame >= animationDefinitionGroup.getFrameLimit(actorState)) {
            resetFrame();
        }
    }

    private boolean isOnLastFrameAndNoLoop() {

        return frame == animationDefinitionGroup.getFrameLimit(actorState) -1 && !animationDefinitionGroup.doesLoop(actorState);
    }


}
