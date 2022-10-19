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

import com.jacstuff.spacearmada.actors.DrawInfo;
import com.jacstuff.spacearmada.image.BitmapManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class TransparentView extends View {

    private int canvasTranslateX,canvasTranslateY;
    private int angle = 0;
    private List<SimpleDrawItem> items;
    private List<TextItem> textItems;
    private Paint paint;
    private Canvas canvasBitmap;
    private boolean isViewDrawn = false;
    private BitmapManager bitmapManager;

    private List<DrawableItem> drawableItems;


    public TransparentView(Context context) {
        super(context);
    }

    public TransparentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        drawableItems = new ArrayList<>();
    }


    public void setBitmapManager(BitmapManager bitmapManager){
        this.bitmapManager = bitmapManager;
    }


    public TransparentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        drawableItems = new ArrayList<>();
    }


    public void clearDrawableItems(){
        this.drawableItems.clear();
    }


    public void setTextSize(float size){
        paint.setTextSize(size);

    }

    public void setTextColor(int color){
        paint.setColor(color);
    }


    public void setTranslateY(int y){
        this.canvasTranslateY = y;
    }

    public void setTranslateX(int x){
        this.canvasTranslateX = x;
    }


    public void updateAndDraw(){
        //angle = (angle + 15) % 360;
    }

    public void addDrawableItem(DrawableItem drawableItem){
        this.drawableItems.add(drawableItem);
    }


    public void setDrawItems(List<SimpleDrawItem> items){
        this.items = items;
    }


    public void setTextItems(List<TextItem> items){
        this.textItems = items;
    }


    public void translateXToMiddle(){
        this.canvasTranslateX = getWidth() / 2;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        defaultAttributes();
        isViewDrawn = true;
    }


    //@Override
    protected void onDraw(Canvas canvas) {
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
        canvasBitmap.translate(canvasTranslateX, canvasTranslateY);
        rotateCanvasBitmap();
        drawItems();
        canvasBitmap.restore();
        return bitmap;
    }


    private void rotateCanvasBitmap(){
        if(angle != 0){
            canvasBitmap.rotate(angle);
        }
    }


    private void drawItems(){
        for(DrawableItem item : drawableItems){
            item.draw(canvasBitmap, paint);
        }
    }


    private void drawItemsInList(Canvas canvas, Paint paint, List<? extends com.jacstuff.spacearmada.DrawableItem> drawableItems){
        DrawInfo drawInfo;
        for(com.jacstuff.spacearmada.DrawableItem item : drawableItems) {
            if (item == null) {
                continue;
            }
            drawInfo = item.getDrawInfo();
            if (drawInfo == null) {
                continue;
            }
            drawBitmap(canvas, paint, drawInfo);
        }
    }


    private void drawBitmap(Canvas canvas, Paint paint, DrawInfo drawInfo){
        Bitmap bitmap = bitmapManager.getBitmap(drawInfo);
        if(bitmap == null || canvas == null || paint == null){
            return;
        }
        canvas.drawBitmap(bitmap , drawInfo.getX(), drawInfo.getY(), paint);
    }


}