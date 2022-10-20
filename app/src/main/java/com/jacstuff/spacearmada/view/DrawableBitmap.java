package com.jacstuff.spacearmada.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class DrawableBitmap implements SimpleDrawableItem {

    private final Bitmap bitmap;
    private int x,y;
    private boolean isVisible;
    private boolean isRotatedForLandscape;

    public DrawableBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        this.isVisible = true;
        isRotatedForLandscape = false;
    }


    public DrawableBitmap(Bitmap bitmap, int x, int y){
        this(bitmap);
        this.x = x;
        this.y = y;
    }

    public void setRotatedForLandscape(boolean rotatedForLandscape){
        this.isRotatedForLandscape = rotatedForLandscape;
    }

    public boolean isRotatedForLandscape(){
        return isRotatedForLandscape;
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
            canvas.save();
            canvas.translate(x,y);
            canvas.drawBitmap(bitmap, 0,0, paint);
            canvas.restore();
        }
    }


    void drawBitmapToScale(Canvas canvas, Paint paint){
      //  Rect src = new Rect(0,0, item.getWidth(), item.getHeight());
      //  Rect dest = new Rect(0,0, getWidth(), getHeight());
      //  canvas.drawBitmap(bitmap, src, dest, paint);
    }

}
