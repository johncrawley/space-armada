package com.jacstuff.spacearmada.actors.ships.player;

import android.content.Context;
import android.graphics.Rect;

import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.AnimationDefinitionGroup;
import com.jacstuff.spacearmada.actors.projectiles.ProjectileManager;
import com.jacstuff.spacearmada.utils.ImageLoader;

public class PlayerShipFactory {


    public static PlayerShip createPlayerShip(Context context, Rect gameScreenBounds, ProjectileManager projectileManager, ImageLoader imageLoader){
        float startingX = gameScreenBounds.centerX();
        float startingY = gameScreenBounds.centerY();
        int startingShield = 300;
        int startingSpeed = 5;





        AnimationDefinitionGroup animationInfoService = getAnimationInfoService();

        PlayerShip playerShip = new PlayerShip(context, startingX, startingY, startingShield, startingSpeed, animationInfoService, projectileManager, imageLoader, R.drawable.ship1);
        playerShip.setGameScreenBounds(gameScreenBounds);

        return playerShip;

    }

    private static AnimationDefinitionGroup getAnimationInfoService(){
        AnimationDefinitionGroup animationInfoService = new AnimationDefinitionGroup("PLAYER_SHIP");
        animationInfoService.registerState(ActorState.DEFAULT, 1, false);
        animationInfoService.registerState(ActorState.DESTROYING, 4, ActorState.DESTROYED);
        animationInfoService.registerState(ActorState.DESTROYED, 1, false);
        return animationInfoService;
    }

}
