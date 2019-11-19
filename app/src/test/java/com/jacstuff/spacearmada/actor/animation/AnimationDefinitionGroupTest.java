package com.jacstuff.spacearmada.actor.animation;

import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.animation.AnimationDefinition;
import com.jacstuff.spacearmada.actors.animation.AnimationDefinitionGroup;
import com.jacstuff.spacearmada.image.BitmapDimension;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AnimationDefinitionGroupTest {

    private AnimationDefinitionGroup animationDefinitionGroup;
    private String family = "Enemy_Ship";

    @Before
    public void setup(){
        animationDefinitionGroup = new AnimationDefinitionGroup(family);

    }

    @Test
    public void canSaveFamily(){
        assertEquals("Family was not saved correctly", family, animationDefinitionGroup.getGroupName());
    }

    @Test
    public void setFramesForState(){

        int frameLimit = 5;
        ActorState state = ActorState.DESTROYING;
        animationDefinitionGroup.registerState(state, frameLimit);
        assertEquals("Frame Limit wasn't stored correctly", frameLimit, animationDefinitionGroup.getFrameLimit(state));
    }

    @Test
    public void canSaveAndRecallDimensions(){
        int width = 100;
        int height = 150;

        assertDimensions(width, height, ActorState.DEFAULT);
        assertDimensions(30,20, ActorState.MOVIG_UP_LEFT);

        // making sure the 2nd assertion doesn't tamper with the first setting
        assertEquals(width, animationDefinitionGroup.getBitmapWidth(ActorState.DEFAULT));
        assertEquals(height, animationDefinitionGroup.getBitmapHeight(ActorState.DEFAULT));


    }

    private void assertDimensions(int width, int height, ActorState actorState){
        BitmapDimension bitmapDimension = new BitmapDimension(width, height);
        AnimationDefinition defaultDefinition = new AnimationDefinition(3, false, bitmapDimension, null);
        animationDefinitionGroup.register(actorState, defaultDefinition);

        assertEquals("Saved Width is incorrect", width, animationDefinitionGroup.getBitmapWidth(actorState));
        assertEquals("Saved Height is incoorect", height, animationDefinitionGroup.getBitmapHeight(actorState));
    }

    @Test
    public void canSaveLoopState(){

        assertAnimationLoopState(animationDefinitionGroup, true);
        assertAnimationLoopState(animationDefinitionGroup, false);
    }



    private void assertAnimationLoopState(AnimationDefinitionGroup animationInfoService, boolean doesAnimationLoop){

        int frameLimit = 10;
        ActorState state = ActorState.DESTROYING;
        animationInfoService.registerState(state, frameLimit, doesAnimationLoop);
        assertEquals("Loop state is incorrect", doesAnimationLoop, animationInfoService.doesLoop(state));
    }



}
