package com.jacstuff.spacearmada.music;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayer {

    private MediaPlayer mediaPlayer;
    private Context context;
    private boolean isReleased = true;
    private int trackPosition = 0;

    public MusicPlayer(Context context){
        this.context = context;
    }

    public void release(){
        if(mediaPlayer == null || isReleased){
            return;
        }

        if(mediaPlayer.isPlaying()){
            mediaPlayer.release();
            isReleased = true;
        }
    }

    public void pause(){
        if(isReleased){
            return;
        }
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            trackPosition = mediaPlayer.getCurrentPosition();
        }
    }

    public void resume(){
        if(isReleased){
            return;
        }
        mediaPlayer.seekTo(trackPosition);
        mediaPlayer.start();
    }


    public void playSoundNoLoop(int trackResourceId){
        playTrack(trackResourceId, false);
    }

    public void playTrack(int trackResourceId){
        playTrack(trackResourceId, true);
    }
    public void playTrack(int trackResourceId, boolean loopTrack){

        mediaPlayer = MediaPlayer.create(context, trackResourceId);
        mediaPlayer.setLooping(loopTrack);
        mediaPlayer.start();
        isReleased = false;
    }

    public void load(int trackResourceId){
        mediaPlayer = MediaPlayer.create(context, trackResourceId);

    }


    public void playLoopingSound(int trackResourceId){

        if(isAlreadyPlaying()){
            return;
        }

        if(isReleased){
            mediaPlayer = MediaPlayer.create(context, trackResourceId);
            mediaPlayer.setLooping(true);
            isReleased = false;
        }
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

    }

    private boolean isAlreadyPlaying(){
        return !isReleased && mediaPlayer.isPlaying();
    }


    public void stopLoopingSound(){

        if(mediaPlayer == null || isReleased) {
            return;
        }

        mediaPlayer.setLooping(false);

        //mediaPlayer.reset();

      //  mediaPlayer.pause();
       // mediaPlayer.seekTo(0);
    }

}
