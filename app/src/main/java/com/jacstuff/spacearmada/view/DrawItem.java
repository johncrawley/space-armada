package com.jacstuff.spacearmada.view;

import android.graphics.Bitmap;

public interface DrawItem {

    Bitmap getBitmap();
    int getX();
    int getY();
    boolean isVisible();
}
