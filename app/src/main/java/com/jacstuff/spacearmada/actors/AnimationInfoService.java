package com.jacstuff.spacearmada.actors;

import java.util.HashMap;
import java.util.Map;

// shared among all the Actor's DrawInfo objects
// 1 of these for each type of actor
// contains a name and the number of frames in the animation
public class AnimationInfoService {

    private Map<ActorState, AnimationInfo> animationInfoMap;
    private String family;

    public AnimationInfoService(String family){
        this.family = family;
        animationInfoMap = new HashMap<>();
    }

    public void registerState(ActorState actorState, int frameLimit){
        animationInfoMap.put(actorState, new AnimationInfo(frameLimit, false));
    }

    public void registerState(ActorState actorState, int frameLimit, boolean doesLoop){
        animationInfoMap.put(actorState, new AnimationInfo(frameLimit, doesLoop));
    }


    public int getFrameLimit(ActorState actorState){
        AnimationInfo animationInfo = animationInfoMap.get(actorState);
        if(animationInfo == null){
            return 1;
        }
        return animationInfo.getFrame();
    }

    public String getFamily(){
        return this.family;
    }

    public boolean doesLoop(ActorState actorState){
        AnimationInfo animationInfo = animationInfoMap.get(actorState);
        if(animationInfo == null){
            return false;
        }
        return animationInfo.doesLoop();
    }


}


class AnimationInfo{

    private int frame;
    private boolean doesLoop;

    AnimationInfo(int frame, boolean doesLoop){
        this.frame = frame;
        this.doesLoop = doesLoop;
    }

    public int getFrame(){
        return this.frame;
    }

    boolean doesLoop(){
        return doesLoop;
    }
}