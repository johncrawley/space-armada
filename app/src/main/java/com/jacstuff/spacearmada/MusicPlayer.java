package com.jacstuff.spacearmada;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicPlayer {

    private MediaPlayer mediaPlayer;
    private Context context;
    private boolean isReleased = false;
    private int trackPosition = 0;

    public MusicPlayer(Context context){
        this.context = context;
    }

    public void release(){
        mediaPlayer.release();
        this.isReleased = true;
    }

    public void pause(){
        if(this.isReleased){
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

    public void playTrack(int trackResourceId){

        mediaPlayer = MediaPlayer.create(context, trackResourceId);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }
    public void playTrack(int trackResourceId, boolean loopTrack){

        mediaPlayer = MediaPlayer.create(context, trackResourceId);
        mediaPlayer.setLooping(loopTrack);
        mediaPlayer.start();
    }
}
