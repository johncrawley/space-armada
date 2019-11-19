package com.jacstuff.spacearmada.image;

import com.jacstuff.spacearmada.actors.animation.AnimationDefinitionGroup;

public interface BitmapLoader {

    void load();
    AnimationDefinitionGroup getAnimationDefinitionGroup(String family);
}
