package com.jacstuff.spacearmada.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class DrawableBitmap implements DrawableItem{

    private Bitmap bitmap;
    private int x,y;
    private boolean isVisible;

    public DrawableBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        this.isVisible = true;
    }


    public DrawableBitmap(Bitmap bitmap, int x, int y){
        this(bitmap);
        this.x = x;
        this.y = y;
    }


    public void setVisible(boolean isVisible){
        this.isVisible = isVisible;
    }


    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }


    public void draw(Canvas canvas, Paint paint){
        if(isVisible) {
            canvas.drawBitmap(bitmap, x, y, paint);
        }
    }
}
