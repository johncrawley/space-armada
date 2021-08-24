package com.jacstuff.spacearmada.game;

import android.content.Context;

import com.jacstuff.spacearmada.actors.background.BackgroundTiles;
import com.jacstuff.spacearmada.actors.projectiles.ProjectileManager;
import com.jacstuff.spacearmada.actors.ships.enemies.EnemyShipManager;
import com.jacstuff.spacearmada.actors.ships.player.PlayerShip;
import com.jacstuff.spacearmada.managers.CollisionDetectionManager;
import com.jacstuff.spacearmada.managers.InputControlsManager;
import com.jacstuff.spacearmada.music.MusicPlayer;
import com.jacstuff.spacearmada.state.StateManager;

abstract class AbstractGameStateHandlerImpl implements GameStateHandler{

    InputControlsManager inputControlsManager;
    StateManager stateManager;
    MusicPlayer musicPlayer;
    GameView    gameView;
    BackgroundTiles backgroundTiles;
    EnemyShipManager enemyShipManager;
    PlayerShip playerShip;
    ProjectileManager projectileManager;
    CollisionDetectionManager collisionDetectionManager;
    Context context;
    GameState gameState;


    AbstractGameStateHandlerImpl(Context context, GameState gameState){
        this.gameState = gameState;
        this.context = context;
        this.backgroundTiles = gameState.getBackgroundTiles();
        this.gameView = gameState.getGameView();
        this.enemyShipManager = gameState.getEnemyShipManager();
        this.collisionDetectionManager = gameState.getCollisionDetectionManager();
        this.projectileManager = gameState.getProjectileManager();
        this.playerShip = gameState.getPlayerShip();
        this.inputControlsManager = gameState.getInputControlsManager();
        this.musicPlayer = gameState.getMusicPlayer();
        this.stateManager = gameState.getStateManager();
    }
}

