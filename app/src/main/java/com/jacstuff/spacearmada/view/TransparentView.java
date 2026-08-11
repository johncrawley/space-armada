package com.jacstuff.spacearmada.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class TransparentView extends View {

    private Paint paint;
    private Canvas canvasBitmap;
    private boolean isViewDrawn = false;


    private List<SimpleDrawableItem> simpleDrawableItems;


    public TransparentView(Context context) {
        super(context);
    }


    public TransparentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        simpleDrawableItems = new ArrayList<>();
    }


    public TransparentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        simpleDrawableItems = new ArrayList<>();
    }


    public void addDrawableItem(SimpleDrawableItem drawableItem){
        this.simpleDrawableItems.add(drawableItem);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        defaultAttributes();
        isViewDrawn = true;
    }


    protected void onDraw(@androidx.annotation.NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (!isViewDrawn) {
            defaultAttributes();
        }
        isViewDrawn = true;
        Bitmap bitmap = createViewBitmap();
        float bitmapX = 0;
        int bitmapY = 0;
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, null);
    }


    private void initPaint(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
    }


    private void defaultAttributes() {
    }


    private Bitmap createViewBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.TRANSPARENT);
        canvasBitmap = new Canvas(bitmap);
        canvasBitmap.save();
        drawItems();
        canvasBitmap.restore();
        return bitmap;
    }


    private void drawItems(){
        for(SimpleDrawableItem item : simpleDrawableItems){
            item.draw(canvasBitmap, paint);
        }
    }

}