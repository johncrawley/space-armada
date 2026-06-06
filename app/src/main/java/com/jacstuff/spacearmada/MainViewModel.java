package com.jacstuff.spacearmada;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.jacstuff.spacearmada.service.Game;
import com.jacstuff.spacearmada.service.sound.MusicPlayer;
import com.jacstuff.spacearmada.service.sound.SoundPlayer;

public class MainViewModel extends AndroidViewModel {

    public Game game;
    public MusicPlayer musicPlayer;
    public SoundPlayer soundPlayer;

    public MainViewModel(@NonNull Application application) {
        super(application);
        game = new Game();
        var context = application.getApplicationContext();
        musicPlayer = new MusicPlayer();
        soundPlayer = new SoundPlayer(context);
        game.init(musicPlayer, soundPlayer);
    }
}
