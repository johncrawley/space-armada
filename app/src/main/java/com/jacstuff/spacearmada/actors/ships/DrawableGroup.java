package com.jacstuff.spacearmada.actors.ships;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrawableGroup {

    private List<Drawable> drawableList;
    private int currentIndex;

    DrawableGroup(List<Drawable> drawables){

        drawableList = new ArrayList<>(drawables);
    }

    public void next(){
        currentIndex++;
    }

    public boolean isLast(){
     return currentIndex >= (drawableList.size()-1);
    }

    public Drawable get(){
        if(isLast()){
            return drawableList.get(drawableList.size()-1);
        }
        return drawableList.get(currentIndex);
    }


}
