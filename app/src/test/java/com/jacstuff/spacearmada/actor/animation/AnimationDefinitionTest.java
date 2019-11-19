package com.jacstuff.spacearmada.actor.animation;

import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.animation.
        AnimationDefinition;
import com.jacstuff.spacearmada.image.BitmapDimension;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class AnimationDefinitionTest {


    @Test
    public void canSaveAndRecallDimensions(){
        int width = 50;
        int height = 60;
        BitmapDimension bitmapDimensions = new BitmapDimension(50, 60);
        AnimationDefinition animationDefinition = new AnimationDefinition(1, false, bitmapDimensions, null);

        assertEquals(width, animationDefinition.getWidth());
        assertEquals(height, animationDefinition.getHeight());

    }

}
