package com.jacstuff.spacearmada.actors.background;

import com.jacstuff.spacearmada.actors.AbstractActor;
import com.jacstuff.spacearmada.actors.animation.AnimationDefinitionGroup;

/**
 * Created by John on 07/10/2017.
 */

public class Star extends AbstractActor {


    public Star(int x, int y){
        super(new AnimationDefinitionGroup("Star"), x,y);
    }


}
