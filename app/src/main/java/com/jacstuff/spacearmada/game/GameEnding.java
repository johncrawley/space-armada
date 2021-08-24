package com.jacstuff.spacearmada.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.controls.TouchPoint;
import com.jacstuff.spacearmada.state.StateManager;

import java.util.List;

/*
    player ship has been destroyed
        controls are locked
        control panel disappears
        main music stops
        game-over music plays
        game-over message appears x seconds after ship has been destroyed
 */

class GameEnding extends AbstractGameStateHandlerImpl{

    GameEnding(Context context, GameState gameState){
        super(context, gameState);
        init();
    }

    private void init(){

        musicPlayer.release();
        musicPlayer.playTrack(R.raw.game_over_1, false);
    }

    @Override
    public void handleTouchPoints(List<TouchPoint> touchPoints) {
        if(nextStateCountdown <= 0){
            musicPlayer.release();
            gameState.destroy();
            Log.i("GameEnding", "gameState.destroy() called.");
            stateManager.setState(StateManager.StateCode.TITLE);

        }
    }

    private int nextStateCountdown = 20;

    @Override
    public void update() {
        nextStateCountdown--;
        enemyShipManager.update();
        projectileManager.update();
        //if(nextStateCountdown <= 0){
        // gameState.setCurrentGameStateHandler(new GameOverScreen(context, gameState));
        //}
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        gameView.drawGameOver(canvas, paint);
    }
}