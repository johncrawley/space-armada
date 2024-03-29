package com.jacstuff.spacearmada.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.jacstuff.spacearmada.MainActivity;
import com.jacstuff.spacearmada.service.sound.MusicPlayer;
import com.jacstuff.spacearmada.service.sound.Sound;
import com.jacstuff.spacearmada.service.sound.SoundPlayer;
import com.jacstuff.spacearmada.view.fragments.game.GameFragment;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class GameService extends Service {
    IBinder binder = new LocalBinder();
    private MainActivity mainActivity;
    private final Game game;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> notifyGameOverFuture;
    private SoundPlayer soundPlayer;
    private MusicPlayer musicPlayer;


    public GameService() {
        super();
        game = new Game();
    }


    public void playSound(Sound sound){
        soundPlayer.playSound(sound);
    }


    public SharedPreferences getScorePrefs(){
        return getSharedPreferences("score_preferences", MODE_PRIVATE);
    }


    public Game getGame(){
        return game;
    }


    public void quitGame(){
        game.quit();
    }


    public void notifyThatGameFinished(){
        notifyGameOverFuture.cancel(false);
    }


    public void startGame(){
        game.start();
    }


    @Override
    public void onCreate() {
        log("entered onCreate()");
        soundPlayer = new SoundPlayer(getApplicationContext());
        musicPlayer = new MusicPlayer();
        game.init(this, musicPlayer, soundPlayer);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY; // service is not restarted when terminated
    }

    private void log(String msg){
        System.out.println("^^^ GameService: " + msg);
    }


    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        log("onUnbind()");
        game.onUnbind();
        return false;
    }


    @Override
    public void onRebind(Intent intent) {
    }


    @Override
    public void onDestroy() {
        mainActivity = null;
        game.quit();
    }


    public boolean isActivityUnbound(){
        return mainActivity == null;
    }


    public void setActivity(MainActivity mainActivity){

        this.mainActivity = mainActivity;
        // game.setGameView(mainActivity.);
    }


    public class LocalBinder extends Binder {
        public GameService getService() {
            return GameService.this;
        }
    }

}