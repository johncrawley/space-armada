package com.jacstuff.spacearmada.actors.ships.player;

import android.content.Context;
import android.graphics.Rect;
import android.media.Image;

import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.projectiles.ProjectileManager;
import com.jacstuff.spacearmada.utils.ImageLoader;

public class PlayerShipFactory {


    public static PlayerShip createPlayerShip(Context context, Rect gameScreenBounds, ProjectileManager projectileManager, ImageLoader imageLoader){
        float startingX = gameScreenBounds.centerX();
        float startingY = gameScreenBounds.centerY();
        int startingShield = 300;
        int startingSpeed = 5;
        PlayerShip playerShip = new PlayerShip(context, startingX, startingY, startingShield, startingSpeed, projectileManager, imageLoader, R.drawable.ship1);
        playerShip.setGameScreenBounds(gameScreenBounds);
        playerShip.addAnimation(ActorState.DESTROYING,
                R.drawable.ship1e1,
                R.drawable.ship1e2,
                R.drawable.ship1e3,
                R.drawable.ship1e4,
                R.drawable.ship1e5);
        playerShip.addAnimation(ActorState.DESTROYED, R.drawable.ship1e5);

        return playerShip;

    }

}
