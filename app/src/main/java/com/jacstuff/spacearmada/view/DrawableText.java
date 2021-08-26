package com.jacstuff.spacearmada.view;

import android.graphics.Canvas;
import android.graphics.Paint;

public class DrawableText implements DrawableItem{

    private String text;
    private int x,y;
    private boolean isVisible;

    public DrawableText(String text){
        this.text = text;
        this.isVisible = true;
    }

    public void setText(String text){
        this.text = text;
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
            canvas.drawText(text, x, y, paint);
        }
    }
}
