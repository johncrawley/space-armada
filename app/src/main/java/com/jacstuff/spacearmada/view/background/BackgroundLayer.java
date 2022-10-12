package com.jacstuff.spacearmada.view.background;

import com.jacstuff.spacearmada.DrawableItem;
import com.jacstuff.spacearmada.actors.DrawInfo;
import com.jacstuff.spacearmada.view.TransparentView;

public class BackgroundLayer implements DrawableItem {

    private final TransparentView transparentView;

    public BackgroundLayer(TransparentView transparentView){
        this.transparentView = transparentView;
    }


    public void draw(){
      //  transparentView.addDrawableItem(this);
        transparentView.invalidate();
    }

    @Override
    public DrawInfo getDrawInfo() {
        return null;
    }
}
