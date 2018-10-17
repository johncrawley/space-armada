package com.jacstuff.spacearmada.managers;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by John on 13/09/2017.
 */

public class DefaultDrawableLoader implements DrawableLoader {
    private  Context context;
    public DefaultDrawableLoader(Context context){
        this.context = context;
    }

    public Drawable getDrawable(int id){
        return context.getResources().getDrawable(id);
    }
}
