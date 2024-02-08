package com.jacstuff.spacearmada.actors.ships.player;

import android.content.Context;
import android.graphics.Rect;

import com.jacstuff.spacearmada.actors.animation.AnimationDefinitionGroup;
import com.jacstuff.spacearmada.actors.projectiles.ProjectileManager;
import com.jacstuff.spacearmada.image.BitmapLoader;

public class PlayerShipFactory {


    public static PlayerShip createPlayerShip(Rect gameScreenBounds, ProjectileManager projectileManager, BitmapLoader bitmapLoader){
        float startingX = gameScreenBounds.centerX();
        float startingY = gameScreenBounds.centerY();
        int startingShield = 300;
        int startingSpeed = 5;

        AnimationDefinitionGroup animationDefinitionGroup = bitmapLoader.getAnimationDefinitionGroup("PLAYER_SHIP");

        PlayerShip playerShip = new PlayerShip(startingX, startingY, startingShield, startingSpeed, animationDefinitionGroup, projectileManager);
        playerShip.setGameScreenBounds(gameScreenBounds);

        return playerShip;

    }

}
