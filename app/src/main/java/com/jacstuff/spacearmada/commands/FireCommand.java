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

    public FireCommand(ControllableShip spaceship){
        this.spaceship = spaceship;
    }

    public void invoke(){
        spaceship.fire();
    }
    public void release() {
        System.out.println("^^^ FireCommand: entered release()");
        spaceship.releaseFire();
    }



}
