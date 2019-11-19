package com.jacstuff.spacearmada.actor;

import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.animation.AnimationDefinitionGroup;
import com.jacstuff.spacearmada.actors.DrawInfo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DrawInfoTest {

    private DrawInfo drawInfo;
    private final int initialX = 10;
    private final int initialY = 10;
    private final String FAMILY_NAME = "test family";
    private final int INITIAL_FRAME = 0;


    @Before
    public void setup(){
        AnimationDefinitionGroup animationInfoService = new AnimationDefinitionGroup(FAMILY_NAME);
        drawInfo = new DrawInfo(animationInfoService, initialX, initialY);
    }

    @Test
    public void setupIsSuccessful(){

        assertEquals("Animation state was not as expected", ActorState.DEFAULT, drawInfo.getState());

        assertEquals("Family was not as expected", FAMILY_NAME, drawInfo.getFamily());
        assertEquals("Initial Frame was not as expected", INITIAL_FRAME, drawInfo.getFrame());

        assertEquals("Initial X was not as expected", initialX, drawInfo.getX());
        assertEquals("Initial Y was not as expected", initialY, drawInfo.getY());

    }


    @Test
    public void savesState(){

        assertStateUpdate(ActorState.DESTROYING);
        assertStateUpdate(ActorState.FIRING);
        assertStateUpdate(ActorState.TAKING_HIT);

    }

    private void assertStateUpdate(ActorState state){

        drawInfo.setState(state);
        assertEquals("Animation state is not getting assigned correctly", state, drawInfo.getAnimationState());
    }


    @Test
    public void moveCoord(){

        drawInfo.moveX(1);
        drawInfo.moveY(1);
        assertCoord("X", initialX + 1, drawInfo.getX());
        assertCoord("Y", initialY +1, drawInfo.getY());

        int updatedX = drawInfo.getX() + 5;
        int updatedY = drawInfo.getY() + 10;
        drawInfo.setXY(updatedX, updatedY);
        assertCoord("X", updatedX, drawInfo.getX());
        assertCoord("Y", updatedY, drawInfo.getY());

    }


    private void assertCoord(String coordName, int expected, int actual){
        String msg = coordName + " coord is not as expected. ";
        assertEquals( msg, expected, actual);

    }

    @Test
    public void frameCanBeSetIncrementedAndReset(){

        AnimationDefinitionGroup animationInfoService = new AnimationDefinitionGroup("test");
        animationInfoService.registerState(ActorState.DEFAULT, 10);
        drawInfo.setAnimationInfoService(animationInfoService);
        final int newFrame = 5;
        drawInfo.setFrame(newFrame);
        assertEquals("Frame is not what was set", newFrame, drawInfo.getFrame());

        drawInfo.incFrame();
        assertEquals("Frame was not updated correctly", newFrame + 1, drawInfo.getFrame());

        drawInfo.resetFrame();
        assertEquals("Frame was not reset correctly", 0, drawInfo.getFrame());

        drawInfo.setFrame(-5);
        assertEquals("Frame should not be below 0", 0, drawInfo.getFrame());
    }


    @Test
    public void stateCanBeSet(){

        assertStateChanged(ActorState.DESTROYING);
        assertStateChanged(ActorState.FIRING);

    }

    private void assertStateChanged(ActorState state){

        drawInfo.setState(state);
        assertEquals("Saved state is incorrect", state, drawInfo.getState());
    }

    /*
        If the animation is set to looping = true

       TODO: If the animation state isn't looping, should move to the next state
         AnimationInfo object should have a nextState field


        Current version: if looping is false and incState, frame will == (framelimit -1)
            (NB framelimit is a number, currentframe is an index)
     */
    @Test
    public void framesSetOnDifferentStates(){

        int frameLimit = 3;
        AnimationDefinitionGroup animationInfoService = new AnimationDefinitionGroup("NOT_IMPORTANT");
        animationInfoService.registerState(ActorState.DESTROYING, frameLimit, false);
        animationInfoService.registerState(ActorState.FIRING, frameLimit, true);
        drawInfo.setAnimationInfoService(animationInfoService);
        drawInfo.setState(ActorState.DESTROYING);


        assertFrameLimits("Frame should not exceed limit", ActorState.DESTROYING, frameLimit,frameLimit -1);
        assertFrameLimits("Frame should be back to 0", ActorState.FIRING, frameLimit,0);


    }

    private void assertFrameLimits(String msg, ActorState state, int frameLimit, int expectedValue){

        drawInfo.setState(state);
        assertEquals("Frame should be reset on state change", 0, drawInfo.getFrame());
        for(int i = 0; i < frameLimit; i++) {
            drawInfo.incFrame();
        }
        assertEquals(msg, expectedValue, drawInfo.getFrame());



    }
}
