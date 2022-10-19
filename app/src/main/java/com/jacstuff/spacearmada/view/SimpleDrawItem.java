package com.jacstuff.spacearmada.view;

import android.graphics.Bitmap;

public interface SimpleDrawItem {

    Bitmap getBitmap();
    int getX();
    int getY();
    boolean isVisible();
}
