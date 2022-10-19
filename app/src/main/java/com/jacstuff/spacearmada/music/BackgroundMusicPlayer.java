package com.jacstuff.spacearmada.music;

import android.content.Context;

public class BackgroundMusicPlayer extends MusicPlayer {


    public BackgroundMusicPlayer(Context context){

        super(context);
    }

    @Override
    public void playTrack(int resId){
        // do nothing
    }

    public void release(){
        //do nothing
    }

}
