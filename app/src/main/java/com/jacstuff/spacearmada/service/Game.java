package com.jacstuff.spacearmada.service;

import android.graphics.Rect;

import com.jacstuff.spacearmada.service.PlayerShip;

public class Game {

        private GameService gameService;
        private PlayerShip playerShip;


        public Game(){
                Rect screenBounds = new Rect(0,0,1000,1000);
                playerShip = new PlayerShip(50,50,screenBounds );
        }


        public void start(){

        }


        public void quit(){

        }


        public void init(GameService gameService){
            this.gameService = gameService;
        }


        public void moveUp(){
                playerShip.moveUp();
                updatePlayerShip();
        }

        private void log(String msg){
                System.out.println("^^^ Game: " + msg);
        }

        public void moveDown(){
                log("Entered moveDown()");
                playerShip.moveDown();
                updatePlayerShip();
        }


        public void moveLeft(){
                playerShip.moveLeft();
                updatePlayerShip();
        }

        public void moveRight(){
                playerShip.moveRight();
                updatePlayerShip();
        }

        public void updatePlayerShip(){
                gameService.updatePlayerShip(playerShip.getX(), playerShip.getY());
        }
}
