package com.jacstuff.spacearmada.state;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.jacstuff.spacearmada.image.ScaledBitmapLoader;
import com.jacstuff.spacearmada.music.MusicPlayer;
import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.controls.TouchPoint;
import com.jacstuff.spacearmada.managers.DefaultDrawableLoader;
import com.jacstuff.spacearmada.managers.DrawableLoader;
import com.jacstuff.spacearmada.view.TransparentView;

import java.util.List;

public class TitleState implements  State {


    private final StateManager stateManager;
    private MusicPlayer musicPlayer;
    private final Activity activity;
    private Drawable title, background;
    private int noClickCounter = 0;
    private TransparentView backgroundView, animatedView;
    private DrawableLoader imageLoader;
    private int width, height;

    private Paint musicCreditsPaint;
    private String musicCredits, tapToPlayText;
    private int tapToPlayTextSize = 1;
    private final int DARK_RED = Color.rgb(130,22,22);


    public TitleState(Activity activity, int canvasWidth, int canvasHeight, StateManager stateManager){
        this.activity = activity;
        this.stateManager = stateManager;
        initMusic();
        this.width = canvasWidth;
        this.height = canvasHeight;
        initTitleGraphics();
    }


    private void initTitleGraphics(){

        backgroundView = activity.findViewById(R.id.titleBackgroundView);
        animatedView = activity.findViewById(R.id.titleAnimationsView);

        DrawableLoader imageLoader = new DefaultDrawableLoader(activity);
        ScaledBitmapLoader scaledBitmapLoader = new ScaledBitmapLoader(activity, width, height);

        backgroundView.addDrawableItem(scaledBitmapLoader.getStretchedDrawableBitmap(R.drawable.title_bg, 0,0));


        Rect bounds = new Rect(0,0, width, height);
        background = imageLoader.getDrawable(R.drawable.title_bg);
        background.setBounds(0, 0, width, height);
        musicCredits = activity.getString(R.string.music_credit);
        tapToPlayText = activity.getString(R.string.tap_to_start);
        initMusicCreditsPaint();
    }


    private void setupTitleGraphic(){
        backgroundView.addDrawableItem(imageLoader.getDrawableBitmap(R.drawable.title,0,0));

        //Bitmap bitmap = imageLoader.getDrawableBitmap(R.drawable.title,0,0);
       // title.setBounds(border, topBorder, bounds.right - border, 600);
        int border = width / 5;
        int topBorder = height /10;
    }


    private void initMusic(){
        musicPlayer = new MusicPlayer(activity);
        musicPlayer.playTrack(R.raw.bensound_scifi );
    }


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
        final int tapToPlayTextSizeLimit = 52;
        final int tapToPlayTextSizeIncrement = 4;
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
        int noClickLimit = 20; // number of updates that must happen before we allow touch events to be registered, this stops us starting a new game too soon after a "game over".
        return noClickCounter < noClickLimit;
    }


    @Override
    public void destroy(){

    }
}
