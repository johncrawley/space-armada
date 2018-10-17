package com.jacstuff.spacearmada.actors;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.util.Log;

import com.jacstuff.spacearmada.utils.ImageLoader;

import java.util.HashMap;
import java.util.Map;

public class AnimationManager {


    private Map<ActorState, Animation> animationMap;
    private ImageLoader imageLoader;
    private int currentFrame;
    private ActorState currentActorState; // necessary to keep track of current actorState, so we can reset the current frame when actorState is changed

    public AnimationManager(ImageLoader imageLoader, int ...defaultResourceIds){
        animationMap = new HashMap<>();
        this.imageLoader = imageLoader;
        animationMap.put(ActorState.DEFAULT, new Animation(imageLoader, defaultResourceIds));
    }


    public void addAnimation(ActorState actorState, int... resIds){

        animationMap.put(actorState, new Animation(imageLoader, resIds));

    }


    private void log(String msg){
        Log.i("AnimationMngr", msg);
    }

    public Drawable getCurrentDrawable(ActorState actorState){

       // log("entered getCurrentDrawable() currentFrame: " + currentFrame);
        if(!animationMap.containsKey(actorState)){
            actorState = ActorState.DEFAULT;
            //return animationMap.get(ActorState.DEFAULT).getFrame(currentFrame);
        }
        Animation animation = animationMap.get(actorState);
        resetFrameIfStateHasChanged(actorState);

        //Drawable drawable = animation.getFrame(currentFrame);
       // return drawable;
        return animation.getFrame(currentFrame);
    }

    private void resetFrameIfStateHasChanged(ActorState actorState){
        if(!actorState.equals(currentActorState)){
            currentFrame = 0;
            currentActorState = actorState;
        }

    }

    public void updateFrame(){
        currentFrame++;
    }

    public boolean isLastFrame(ActorState actorState){
        return animationMap.get(actorState).isLastFrame(currentFrame);
    }


}


class Animation{

    private int maxFrameIndex;
    private LevelListDrawable levelListDrawable;


    Animation(ImageLoader imageLoader, int ... drawableIds){
        maxFrameIndex = drawableIds.length - 1;
        levelListDrawable = createAnimatedDrawable(imageLoader, drawableIds);
        levelListDrawable.setLevel(0);
        if(levelListDrawable.getCurrent() == null){
           // Log.i("Animation", "constructor() , the image is null");
        }

    }

    public boolean isLastFrame(int currentFrame){

        return currentFrame >= maxFrameIndex;
    }

    public Drawable getFrame(int currentFrame){
        int selectedFrame = maxFrameIndex == 0 ? 0 : currentFrame % maxFrameIndex + 1;
        levelListDrawable.setLevel(selectedFrame);

        if(levelListDrawable.getCurrent() == null){
            Log.i("Animation", "getFrame(), the image is null");
        }
        return levelListDrawable.getCurrent();
    }

    private LevelListDrawable createAnimatedDrawable(ImageLoader imageLoader, int... drawableIds){

        LevelListDrawable drawable = new LevelListDrawable();
        for(int i=0;i<drawableIds.length;i++){
            drawable.addLevel(i, i, imageLoader.loadDrawable(drawableIds[i]));
        }
        drawable.setLevel(0);

        return drawable;
    }

}

