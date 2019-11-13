package com.jacstuff.spacearmada;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.jacstuff.spacearmada.actors.DrawInfo;

public interface DrawableItem {

   // Drawable getDrawable(); //TODO: need to remove this when rendering via bitmaps is complete
    DrawInfo getDrawInfo();

}
