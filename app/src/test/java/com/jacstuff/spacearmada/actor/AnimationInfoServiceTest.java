package com.jacstuff.spacearmada.actor;

import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.AnimationInfoService;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AnimationInfoServiceTest {

    private AnimationInfoService animationInfoService;
    private String family = "Enemy_Ship";

    @Before
    public void setup(){
        animationInfoService = new AnimationInfoService(family);

    }

    @Test
    public void canSaveFamily(){
        assertEquals("Family was not saved correctly", family, animationInfoService.getFamily());
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



    private void assertAnimationLoopState(AnimationInfoService animationInfoService, boolean doesAnimationLoop){

        int frameLimit = 10;
        ActorState state = ActorState.DESTROYING;
        animationInfoService.registerState(state, frameLimit, doesAnimationLoop);
        assertEquals("Loop state is incorrect", doesAnimationLoop, animationInfoService.doesLoop(state));
    }



}
