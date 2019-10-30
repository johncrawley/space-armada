package com.jacstuff.spacearmada.actors;


import android.util.Log;

public class DrawInfo {

    private ActorState actorState;
    private int x, y;
    private int frame;
    private AnimationInfoService animationInfoService;


    public DrawInfo(AnimationInfoService animationInfoService, int x, int y){
        actorState = ActorState.DEFAULT;
        this.animationInfoService = animationInfoService;
        this.x = x;
        this.y = y;
    }

    public void setState(ActorState actorState){
        this.actorState = actorState;
        Log.i("DrawInfo", "Setting ActorState to " + actorState);
        this.frame = 0;
    }

    public ActorState getState(){
        return this.actorState;
    }

    public void setAnimationInfoService(AnimationInfoService animationInfoService){
        this.animationInfoService = animationInfoService;
    }

    public String getFamily(){
        return this.animationInfoService.getFamily();
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

    public void incFrame(){
        this.frame++;
        if(isCurrentFrameBeyondLimit()){
            if(animationInfoService.doesLoop(this.actorState)){
                resetFrame();
            }
            else{
                this.frame = animationInfoService.getFrameLimit(this.actorState) -1;
            }
        }
    }

    public boolean isCurrentFrameBeyondLimit(){
        return this.frame >= animationInfoService.getFrameLimit(this.actorState);
    }

}
