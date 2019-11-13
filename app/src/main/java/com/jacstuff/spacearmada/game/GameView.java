package com.jacstuff.spacearmada.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import com.jacstuff.spacearmada.DrawableItem;
import com.jacstuff.spacearmada.DrawableItemGroup;
import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.actors.DrawInfo;
import com.jacstuff.spacearmada.actors.background.BackgroundTiles;
import com.jacstuff.spacearmada.actors.background.Tile;
import com.jacstuff.spacearmada.actors.ships.player.Energy;
import com.jacstuff.spacearmada.actors.ships.player.Score;
import com.jacstuff.spacearmada.controls.CircularControl;
import com.jacstuff.spacearmada.image.BitmapManager;
import com.jacstuff.spacearmada.managers.InputControlsManager;

/**
 * Created by John on 18/09/2017.
 * Responsible for drawing everything on the canvas
 */

class GameView {


    private InputControlsManager controls;
   // private List<DrawableItemGroup> drawableItemGroups;
    private List<DrawableItemGroup> bitmapItemGroups;
    private List<DrawableItem> drawableItems;

    private int gameScreenTop;
    private int gameScreenBottom, gameScreenWidth;
    private BackgroundTiles backgroundTiles;
    private String scoreLabel, gameOverText;
    private Drawable dpadDrawable, fireButtonDrawable;
    private Paint gameOverTextPaint;
    private Context context;
    private Paint topPanelPaint;
    private Paint bottomPanelPaint;
    private BitmapManager bitmapManager;
    private Score score;
    private Energy energy;

    GameView(Context context, BitmapManager bitmapManager, int gameScreenTop, int gameScreenBottom, int gameScreenWidth){

        this.context = context;
        this.bitmapManager = bitmapManager;
        initBounds(gameScreenTop, gameScreenBottom, gameScreenWidth);
        //drawableItemGroups = new ArrayList<>(); // we need a list of item groups because a DrawableItemGroup
                                                // (such as EnemyManager) will take care of the adding and deleting of DrawableItem actors from itself
        drawableItems = new ArrayList<>();
        bitmapItemGroups = new ArrayList<>();
        initLabels();
        initGameOverTextPaint();
        initPanelPaint();
        Log.i("GameView", "exiting constructor!");
    }

    private void initBounds(int gameScreenTop, int gameScreenBottom, int gameScreenWidth){

        this.gameScreenTop = gameScreenTop;
        this.gameScreenBottom = gameScreenBottom;
        this.gameScreenWidth = gameScreenWidth;

    }

    private void initLabels(){

        scoreLabel = context.getResources().getString(R.string.score_text);
        gameOverText = context.getResources().getString(R.string.game_over_text);

    }

    private void initPanelPaint(){
        int darkGrey = Color.rgb(66,66,66);
        //int blueGrey = Color.rgb(100,100,123);
        int darkerGrey = Color.rgb(34,34,40);
        Shader bottomPanelShader = new LinearGradient(0, gameScreenBottom, 0, gameScreenBottom +50, darkerGrey, darkGrey, Shader.TileMode.CLAMP);
        Shader topPanelShader = new LinearGradient(0, gameScreenTop-40, 0, gameScreenTop, darkGrey, darkerGrey, Shader.TileMode.CLAMP);
        topPanelPaint= new Paint();
        topPanelPaint.setShader(topPanelShader);
        bottomPanelPaint= new Paint();
        bottomPanelPaint.setShader(bottomPanelShader);
    }


    private void initControlDrawables(Context context){
        dpadDrawable  = context.getDrawable(R.drawable.dpad1);
        fireButtonDrawable  = context.getDrawable(R.drawable.fire_button3);

        initDrawableFromCircleCoordinates(dpadDrawable, controls.getDPad());
        initDrawableFromCircleCoordinates(fireButtonDrawable, controls.getFireButton());
    }

    private void initDrawableFromCircleCoordinates(Drawable drawable, CircularControl circularControl){
        int centreX = (int)circularControl.getCentreX();
        int centreY = (int)circularControl.getCentreY();
        int radius = (int)circularControl.getRadius();

        Rect bounds = new Rect(centreX - radius, centreY - radius, centreX + radius, centreY + radius);
        drawable.setBounds(bounds);
    }



    void register(InputControlsManager inputControlsManager){
        this.controls = inputControlsManager;
        initControlDrawables(context);
    }




    void register(DrawableItem drawableItem){
        drawableItems.add(drawableItem);
    }

    void register(Score score){ this.score = score;}
    void register(Energy energy){ this.energy = energy;}


    void registerBitmapGroup(DrawableItemGroup bitmapGroup){
        this.bitmapItemGroups.add(bitmapGroup);
    }

    void setBackgroundTiles(BackgroundTiles backgroundTiles){
        this.backgroundTiles = backgroundTiles;
    }


    private void drawBitmaps(Canvas canvas, Paint paint){
        if(canvas == null){
            return;
        }
        drawItemsInGroupsList(canvas, paint);
        drawItemsInList(canvas, paint, drawableItems);
    }

    private void drawItemsInGroupsList(Canvas canvas, Paint paint){
        for(DrawableItemGroup group : bitmapItemGroups){
            if(group == null || group.getDrawableItems() == null){
                continue;
            }
            drawItemsInList(canvas, paint, group.getDrawableItems());
        }
    }


    private void drawItemsInList(Canvas canvas, Paint paint, List<? extends DrawableItem> drawableItems){
        DrawInfo drawInfo;
        for(DrawableItem item : drawableItems) {
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

    private int logLimit = 30;
    private int currentLog = 0;

    void logDraw(){
        currentLog+=1;
        if(currentLog > logLimit){
            //Log.i("GameView draw", "calling draw()");
            currentLog = 0;
        }
    }

    void draw(Canvas canvas, Paint paint){
        //logDraw();
        drawPlainBackground(canvas, paint);
        drawBackground(canvas, paint);
        drawBitmaps(canvas, paint);
        drawTopPanel(canvas);
        drawScore(canvas, paint);
        drawShipHealth(canvas, paint);
        drawBottomPanel(canvas);
        drawControls(canvas);
       // drawInfo(canvas, paint);
    }

    private void drawInfo(Canvas canvas, Paint paint){

        int color = paint.getColor();
        paint.setColor(Color.WHITE);
        canvas.drawText(" drawableItems count: " + drawableItems.size(), 100, 100, paint);
        paint.setColor(color);
    }

    private void drawPlainBackground(Canvas canvas, Paint paint){

        int currentColor = paint.getColor();
        paint.setColor(Color.BLACK);
        Rect r = new Rect(0,0, gameScreenWidth, gameScreenBottom);
        canvas.drawRect(r, paint);
        paint.setColor(currentColor);


    }




    void drawGameOver(Canvas canvas, Paint paint){
        //drawBackground(canvas, paint);
        int currentColor = paint.getColor();
        paint.setColor(currentColor);
        drawTopPanel(canvas);
        drawScore(canvas, paint);
        drawGameOverMessage(canvas);
    }


    private void initGameOverTextPaint(){
        gameOverTextPaint = new Paint();
        gameOverTextPaint.setTextAlign(Paint.Align.CENTER);
        gameOverTextPaint.setTextSize(70);
    }


    private void drawGameOverMessage(Canvas canvas){
        // first paint the shadow
        gameOverTextPaint.setColor(Color.BLACK);
        int textX = canvas.getWidth() / 2;
        int textY = (int) ((canvas.getHeight() / 2) - ((gameOverTextPaint.descent() + gameOverTextPaint.ascent()) / 2)) ;

        canvas.drawText(gameOverText, textX, textY, gameOverTextPaint);
        // then adjust the coordinates slightly, and paint the text over the shadow
        gameOverTextPaint.setColor(Color.LTGRAY);
        canvas.drawText(gameOverText, textX - 3, textY -2, gameOverTextPaint);
    }


    private void drawBackground(Canvas canvas, Paint paint){

          for(Tile tile : backgroundTiles.getTiles()){
              canvas.drawBitmap(tile.getBitmap(),0, tile.getY(), paint);
          }
    }

    private void drawControls(Canvas canvas){
       dpadDrawable.draw(canvas);
       fireButtonDrawable.draw(canvas);
    }



    private void drawTopPanel(Canvas canvas){
        canvas.drawRect(0,0,canvas.getWidth(),gameScreenTop, topPanelPaint);
    }

    private void drawBottomPanel(Canvas canvas){
        int panelHeight = canvas.getHeight() - gameScreenBottom;
        canvas.drawRect(0, canvas.getHeight() - panelHeight, canvas.getWidth(), canvas.getHeight(), bottomPanelPaint);
    }


    private void drawScore(Canvas canvas, Paint paint){
        paint.setColor(Color.LTGRAY);
        paint.setTextSize(40);
        canvas.drawText(scoreLabel + score.get(), canvas.getWidth() - 350, gameScreenTop/2 + 12, paint);

    }

    private void drawShipHealth(Canvas canvas, Paint paint){
        int healthChunkWidth = 15;
        int healthChunkHeight = 30;
        int gapBetweenHealthChunks = 4;
        int healthBarX = 120;
        int healthBarY = 80;

        for(int i =0; i < energy.get()/10; i++){
            int healthChunkX = healthBarX + (healthChunkWidth + gapBetweenHealthChunks) * i;
            setColorForEnergyLevel(paint);
            canvas.drawRect(healthChunkX, healthBarY, healthChunkX + healthChunkWidth, healthChunkHeight, paint);
        }


    }



    private void setColorForEnergyLevel(Paint paint){
        final int DARK_GREEN = Color.rgb(10,151,16);
        paint.setColor(DARK_GREEN);
        if(energy.isEnergyLevelLow()){
            paint.setColor(Color.YELLOW);
        }
        if(energy.isEnergyLevelDangerouslyLow()){
            paint.setColor(Color.RED);
        }

    }



}
