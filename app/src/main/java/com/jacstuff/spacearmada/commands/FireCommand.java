package com.jacstuff.spacearmada.commands;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.actors.ships.ControllableShip;
import com.jacstuff.spacearmada.actors.ships.Spaceship;

/**
 * Created by John on 29/08/2017.
 */

public class FireCommand extends com.jacstuff.spacearmada.commands.AbstractSpaceshipCommand implements com.jacstuff.spacearmada.commands.Command {
    private Context context;

    public FireCommand(Context context, ControllableShip spaceship){

        this.context = context;
        this.spaceship = spaceship;
    }

    public void invoke(){
        spaceship.fire();
    }
    public void release() {

        spaceship.releaseFire();
    }



}
