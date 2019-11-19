package com.jacstuff.spacearmada.actors.animation;

import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.image.BitmapDimension;

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

    public void register(ActorState actorState, AnimationDefinition animationDefinition){

        animationInfoMap.put(actorState, animationDefinition);
    }


    public void registerState(ActorState actorState, int frameLimit, boolean doesLoop){
        animationInfoMap.put(actorState, new AnimationDefinition(frameLimit, doesLoop));
    }


    public int getBitmapWidth(ActorState actorState){
        AnimationDefinition animationDefinition = animationInfoMap.get(actorState);
        return animationDefinition != null ? animationDefinition.getWidth() : -10;

    }

    public int getBitmapHeight(ActorState actorState){

        AnimationDefinition animationDefinition = animationInfoMap.get(actorState);
        return animationDefinition != null ? animationDefinition.getHeight() : 0;
    }


    public ActorState getNextState(ActorState currentState){
        AnimationDefinition animationDefinition = animationInfoMap.get(currentState);
        if(animationDefinition == null){
            return null;
        }
        return animationDefinition.getNextState();

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
