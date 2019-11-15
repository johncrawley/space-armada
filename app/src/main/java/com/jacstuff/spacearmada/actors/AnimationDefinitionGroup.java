package com.jacstuff.spacearmada.actors;

import java.util.HashMap;
import java.util.Map;

// shared among all the Actor's DrawInfo objects
// 1 of these for each type of actor
// contains a name and the number of frames in the animation
public class AnimationDefinitionGroup {

    private Map<ActorState, AnimationDefinition> animationInfoMap;
    private String groupName;

    public AnimationDefinitionGroup(String groupName){
        this.groupName = groupName;
        animationInfoMap = new HashMap<>();
    }

    public void registerState(ActorState actorState, int frameLimit){
        animationInfoMap.put(actorState, new AnimationDefinition(frameLimit, false));
    }

    public void registerState(ActorState actorState, int frameLimit, boolean doesLoop){
        animationInfoMap.put(actorState, new AnimationDefinition(frameLimit, doesLoop));
    }


    public int getFrameLimit(ActorState actorState){
        AnimationDefinition animationInfo = animationInfoMap.get(actorState);
        if(animationInfo == null){
            return 1;
        }
        return animationInfo.getFrame();
    }

    public String getGroupName(){
        return this.groupName;
    }

    public boolean doesLoop(ActorState actorState){
        AnimationDefinition animationInfo = animationInfoMap.get(actorState);
        if(animationInfo == null){
            return false;
        }
        return animationInfo.doesLoop();
    }


}

/*
    Provides information to the owner actor about how many frames of animation a given state has, whether it loops though those frames
     and what state the owner should go into next if it's reached the end of its frame count

 */

class AnimationDefinition {

    private int frame;
    private boolean doesLoop;
    private ActorState nextState;


    AnimationDefinition(int frame, boolean doesLoop){
        this(frame, doesLoop, null);
    }

    AnimationDefinition(int frame, boolean doesLoop, ActorState nextState){
        this.frame = frame;
        this.doesLoop = doesLoop;
        this.nextState = nextState;
    }

    public int getFrame(){
        return this.frame;
    }

    public ActorState getNextState(){
        return this.nextState;
    }

    boolean doesLoop(){
        return doesLoop;
    }
}