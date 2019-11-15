package com.jacstuff.spacearmada.actor;

import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.AnimationDefinitionGroup;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AnimationDefinitionGroupTest {

    private AnimationDefinitionGroup animationInfoService;
    private String family = "Enemy_Ship";

    @Before
    public void setup(){
        animationInfoService = new AnimationDefinitionGroup(family);

    }

    @Test
    public void canSaveFamily(){
        assertEquals("Family was not saved correctly", family, animationInfoService.getGroupName());
    }

    @Test
    public void setFramesForState(){

        int frameLimit = 5;
        ActorState state = ActorState.DESTROYING;
        animationInfoService.registerState(state, frameLimit);
        assertEquals("Frame Limit wasn't stored correctly", frameLimit, animationInfoService.getFrameLimit(state));
    }


    @Test
    public void canSaveLoopState(){

        assertAnimationLoopState(animationInfoService, true);
        assertAnimationLoopState(animationInfoService, false);
    }



    private void assertAnimationLoopState(AnimationDefinitionGroup animationInfoService, boolean doesAnimationLoop){

        int frameLimit = 10;
        ActorState state = ActorState.DESTROYING;
        animationInfoService.registerState(state, frameLimit, doesAnimationLoop);
        assertEquals("Loop state is incorrect", doesAnimationLoop, animationInfoService.doesLoop(state));
    }



}
