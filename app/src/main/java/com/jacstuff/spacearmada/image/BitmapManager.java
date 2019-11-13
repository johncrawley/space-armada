package com.jacstuff.spacearmada.image;

import android.graphics.Bitmap;

import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.DrawInfo;

import java.util.List;

public interface BitmapManager {
    Bitmap getBitmap(DrawInfo drawInfo);
    void register(String family, ActorState state, List<Bitmap> bitmaps);

}
