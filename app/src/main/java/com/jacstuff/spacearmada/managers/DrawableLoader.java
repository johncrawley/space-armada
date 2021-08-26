package com.jacstuff.spacearmada.managers;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.jacstuff.spacearmada.view.DrawableBitmap;

/**
 * Created by John on 13/09/2017.
 */

public interface DrawableLoader {
    Drawable getDrawable(int id);
    Bitmap getBitmap(int id);
    DrawableBitmap getDrawableBitmap(int id, int x, int y);
}
