package com.jacstuff.spacearmada.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.jacstuff.spacearmada.DrawableItem;
import com.jacstuff.spacearmada.DrawableItemGroup;
import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.actors.background.BackgroundTiles;
import com.jacstuff.spacearmada.actors.background.Tile;
import com.jacstuff.spacearmada.actors.ships.player.PlayerShip;
import com.jacstuff.spacearmada.controls.CircularControl;
import com.jacstuff.spacearmada.managers.InputControlsManager;

/**
 * Created by John on 18/09/2017.
 * Responsible for drawing everything on the canvas
 */

public class GameView {


    private PlayerShip playerShip;
    private InputControlsManager controls;
    private List<DrawableItemGroup> drawableItemGroups;

    private int gameScreenTop;
    private int gameScreenBottom;
    private BackgroundTiles backgroundTiles;
    private String scoreLabel, gameOverText;
    private Drawable dpadDrawable, fireButtonDrawable;
    private Paint gameOverTextPaint;
    private Context context;
    private Paint topPanelPaint;
    private Paint bottomPanelPaint;
    private Shader topPanelShader;
    private Shader bottomPanelShader;

    GameView(Context context, int gameScreenTop, int gameScreenBottom){
        this.gameScreenTop = gameScreenTop;
        this.gameScreenBottom = gameScreenBottom;
        drawableItemGroups = new ArrayList<>();
        scoreLabel = context.getResources().getString(R.string.score_text);
        gameOverText = context.getResources().getString(R.string.game_over_text);
        initGameOverTextPaint();
        this.context = context;
        initPanelPaint();
    }

    private void initPanelPaint(){
        //Color color = ResourcesCompat.getColor(context.getResources(), R.color.colorPrimaryDark, null);
        int darkGrey = Color.rgb(66,66,66);
        int blueGrey = Color.rgb(100,100,123);
        int darkerGrey = Color.rgb(34,34,40);
        bottomPanelShader = new LinearGradient(0, gameScreenBottom, 0, gameScreenBottom +50, darkerGrey, darkGrey, Shader.TileMode.CLAMP);
        topPanelShader = new LinearGradient(0, gameScreenTop-40, 0, gameScreenTop, darkGrey, darkerGrey, Shader.TileMode.CLAMP);
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

    private void log(String msg){
        Log.i("GameView", msg);
    }
    public void register(PlayerShip playerShip){
        this.playerShip = playerShip;
    }


    public void register(InputControlsManager inputControlsManager){
        this.controls = inputControlsManager;
        initControlDrawables(context);
    }

    public void register(DrawableItemGroup drawableItemGroup){
        this.drawableItemGroups.add(drawableItemGroup);
    }

    public void setBackgroundTiles(BackgroundTiles backgroundTiles){
        this.backgroundTiles = backgroundTiles;
    }

    private void drawItems(Canvas canvas){
        Drawable drawable;
        if(canvas == null){
            return;
        }
        for(DrawableItemGroup dig: drawableItemGroups){
            if(dig == null || dig.getDrawableItems() == null){
                continue;
            }
            for(DrawableItem item : dig.getDrawableItems()){
                if(item == null) {
                    continue;
                }
                drawable = item.getDrawable();
                if(drawable == null){
                     continue;
                }
                drawable.draw(canvas);
            }
        }
    }


    public void draw(Canvas canvas, Paint paint){
        drawBackground(canvas, paint);
        playerShip.getDrawable().draw(canvas);
        int currentColor = paint.getColor();
        drawItems(canvas);
        paint.setColor(currentColor);
        drawTopPanel(canvas, paint);
        drawScore(canvas, paint);
        drawShipHealth(canvas, paint);
        drawBottomPanel(canvas, paint);
        drawControls(canvas);
    }


    public void drawGameOver(Canvas canvas, Paint paint){
        drawBackground(canvas, paint);
        int currentColor = paint.getColor();
        drawItems(canvas);
        paint.setColor(currentColor);
        drawTopPanel(canvas, paint);
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



    private void drawTopPanel(Canvas canvas, Paint paint){
        canvas.drawRect(0,0,canvas.getWidth(),gameScreenTop, topPanelPaint);
    }

    private void drawBottomPanel(Canvas canvas, Paint paint){
        int panelHeight = canvas.getHeight() - gameScreenBottom;
        canvas.drawRect(0, canvas.getHeight() - panelHeight, canvas.getWidth(), canvas.getHeight(), bottomPanelPaint);
    }


    private void drawScore(Canvas canvas, Paint paint){
        paint.setColor(Color.LTGRAY);
        paint.setTextSize(40);
        canvas.drawText(getScoreString(), canvas.getWidth() - 350, gameScreenTop/2 + 12, paint);

    }

    private String getScoreString(){

        int score = playerShip.getScore();
        int numberOfDisplayedDigits = 7;
        StringBuilder strbldr = new StringBuilder();

        for(int i = 1; i <= numberOfDisplayedDigits; i++){
            String digit = "" + score % 10;
            strbldr.append(digit);
            score /= 10;
        }
        return scoreLabel + strbldr.reverse().toString();
    }

    private void drawShipHealth(Canvas canvas, Paint paint){
        int healthChunkWidth = 15;
        int healthChunkHeight = 30;
        int gapBetweenHealthChunks = 4;
        int healthBarX = 120;
        int healthBarY = 80;
        for(int i =0; i < playerShip.getEnergy()/10; i++){
            int healthChunkX = healthBarX + (healthChunkWidth + gapBetweenHealthChunks) * i;
            setColorForEnergyLevel(paint);
            canvas.drawRect(healthChunkX, healthBarY, healthChunkX + healthChunkWidth, healthChunkHeight, paint);
        }
    }

    private void setColorForEnergyLevel(Paint paint){
        final int DARK_GREEN = Color.rgb(10,151,16);
        paint.setColor(DARK_GREEN);
        if(playerShip.isEnergyLevelLow()){
            paint.setColor(Color.YELLOW);
        }
        if(playerShip.isEnergyLevelDangerouslyLow()){
            paint.setColor(Color.RED);
        }

    }

}
