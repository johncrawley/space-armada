package com.jacstuff.spacearmada.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.jacstuff.spacearmada.view.DrawableBitmap;

/**
 * Created by John on 13/09/2017.
 */

public class DefaultDrawableLoader implements DrawableLoader {
    private final Context context;
    public DefaultDrawableLoader(Context context){
        this.context = context;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public Drawable getDrawable(int id){
        return context.getResources().getDrawable(id);
    }


    public DrawableBitmap getDrawableBitmap(int id, int x, int y){
        return new DrawableBitmap(getBitmap(id), x, y);
    }

    public Bitmap getBitmap(int id){
        return BitmapFactory.decodeResource(context.getResources(), id);
    }
}
