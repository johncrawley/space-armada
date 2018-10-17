package com.jacstuff.spacearmada.state.timed;

/*
    Executes after a certain number of frames

 */
public abstract class TimedAction {

    private int framesToWait;
    private int currentFramesLeft;
    public TimedAction(int framesToWait){

        this.framesToWait = framesToWait;
        this.currentFramesLeft = framesToWait;
    }


    public void decrementTimer(int amount){
        currentFramesLeft -= amount;
    }

    public boolean isReady(){
        return this.currentFramesLeft < 1;
    }

    public abstract void executeAction();


}
