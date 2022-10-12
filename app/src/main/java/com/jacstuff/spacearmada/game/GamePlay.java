package com.jacstuff.spacearmada.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.jacstuff.spacearmada.controls.TouchPoint;

import java.util.List;

class GamePlay extends AbstractGameStateHandlerImpl{

    GamePlay(Context context, GameState gameState){
        super(context, gameState);
    }

    public void handleTouchPoints(List<TouchPoint> touchPoints){
        if(inputControlsManager == null){
            return;
        }
        inputControlsManager.process(touchPoints);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        gameView.draw(canvas,paint);
    }

    private int gameOverTimer = 150;

    @Override
    public void update() {

        enemyShipManager.update();
        playerShip.update();
        projectileManager.update();
        collisionDetectionManager.detect();
        playerShip.updateAnimation();
        updateBackgroundTiles();

        if(gameOverTimer <= 0){
            gameState.setCurrentGameStateHandler( new GameEnding(context, gameState));
        }
    }


    private void updateBackgroundTiles(){
        if(playerShip.isDead()){
            gameOverTimer--;
        }
        else{
            backgroundTiles.update();
        }
    }

}