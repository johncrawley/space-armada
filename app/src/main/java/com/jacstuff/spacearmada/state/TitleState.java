package com.jacstuff.spacearmada.state;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.jacstuff.spacearmada.music.MusicPlayer;
import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.controls.TouchPoint;
import com.jacstuff.spacearmada.managers.DefaultDrawableLoader;
import com.jacstuff.spacearmada.managers.DrawableLoader;

import java.util.List;

public class TitleState implements  State {

    private int border = 50;
    private int topBorder = 150;
    private StateManager stateManager;
    private MusicPlayer musicPlayer;
    private Context context;
    private Drawable title, background;
    private int noClickLimit = 20; // number of updates that must happen before we allow touch events to be registered, this stops us starting a new game too soon after a "game over".
    private int noClickCounter = 0;

    private Paint musicCreditsPaint;
    private String musicCredits, tapToPlayText;
    private int tapToPlayTextSize = 1;
    private int tapToPlayTextSizeLimit = 52;
    private int tapToPlayTextSizeIncrement = 4;


    public TitleState(Context context, int canvasWidth, int canvasHeight, StateManager stateManager){
        this.context = context;
        this.stateManager = stateManager;
        initMusic();
        initTitleGraphics(canvasWidth, canvasHeight);
    }



    private void initTitleGraphics(int canvasWidth, int canvasHeight){
        DrawableLoader imageLoader = new DefaultDrawableLoader(context);
        Rect bounds = new Rect(0,0,canvasWidth, canvasHeight);
        title = imageLoader.getDrawable(R.drawable.title);
        title.setBounds(border, topBorder, bounds.right - border, 600);
        background = imageLoader.getDrawable(R.drawable.title_bg);
        background.setBounds(0, 0, canvasWidth, canvasHeight);
        musicCredits = context.getString(R.string.music_credit);
        tapToPlayText = context.getString(R.string.tap_to_start);
        initMusicCreditsPaint();
    }

    private void initMusic(){
        musicPlayer = new MusicPlayer(context);
        musicPlayer.playTrack(R.raw.bensound_scifi );
    }


    private final int DARK_RED = Color.rgb(130,22,22);

    private void initMusicCreditsPaint() {
        musicCreditsPaint = new Paint();
        musicCreditsPaint.setColor(DARK_RED);
        musicCreditsPaint.setTextAlign(Paint.Align.CENTER);
        musicCreditsPaint.setTextSize(35);

    }


    private void drawMusicCreditsMessage(Canvas canvas){


        musicCreditsPaint.setTextSize(35);
        musicCreditsPaint.setColor(DARK_RED);
        int textX = canvas.getWidth() / 2;
        int textY = canvas.getHeight() - 100;
        canvas.drawText(musicCredits, textX, textY, musicCreditsPaint);
    }


    private void drawTapToPlayMessage(Canvas canvas){

        int textX = canvas.getWidth() / 2;
        int textY = (int) ((canvas.getHeight() / 2) - ((musicCreditsPaint.descent() + musicCreditsPaint.ascent()) / 2)) ;


        musicCreditsPaint.setColor(Color.BLACK);
        musicCreditsPaint.setTextSize(tapToPlayTextSize);
        canvas.drawText(tapToPlayText, textX, textY, musicCreditsPaint);
        canvas.drawText(tapToPlayText, textX -6, textY-4, musicCreditsPaint);

        musicCreditsPaint.setColor(Color.LTGRAY);
        canvas.drawText(tapToPlayText, textX -3, textY -2, musicCreditsPaint);

    }


    public void onPause(){
        musicPlayer.pause();
    }
    public void onResume(){
        musicPlayer.resume();
    }


    @Override
    public void update() {
        if(tapToPlayTextSize < tapToPlayTextSizeLimit) {
            tapToPlayTextSize += tapToPlayTextSizeIncrement;
        }
        noClickCounter++;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        background.draw(canvas);
        title.draw(canvas);
        drawTapToPlayMessage(canvas);
        drawMusicCreditsMessage(canvas);
    }

    @Override
    public void handleTouchPoints(List<TouchPoint> touchPoints) {
        if(isTooSoonToRegisterClickEvents()){
            return;
        }
        stopMusicAndGoToNextStateOnTouch(touchPoints);
    }


    private void stopMusicAndGoToNextStateOnTouch(List<TouchPoint> touchPoints){

        for (TouchPoint touchPoint : touchPoints) {
            if (!touchPoint.isRelease()) {
                musicPlayer.release();
                stateManager.setState(StateManager.StateCode.GAME);
                break;
            }
        }
    }

    private boolean isTooSoonToRegisterClickEvents(){
        return noClickCounter < noClickLimit;
    }


    @Override
    public void destroy(){

    }
}
