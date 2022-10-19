package com.jacstuff.spacearmada.actors.ships.player;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.music.MusicPlayer;
import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.animation.AnimationDefinitionGroup;
import com.jacstuff.spacearmada.actors.CollidableActor;
import com.jacstuff.spacearmada.actors.projectiles.ProjectileManager;
import com.jacstuff.spacearmada.actors.ships.ControllableShip;

/**
 * Created by John on 29/08/2017.
 *
 * Represents the ship that the user controls
 */

public class PlayerShip extends CollidableActor implements ControllableShip {


        private Direction direction;
        private int updateCount = 0;
        private int fireDelay = 5; //TODO: move into a bullet class
        private int currentFireIteration = 0;
        private Score score;
        private boolean isFiring = false;
        private Rect tempRect; // used for seeing if the ships rect stays within the game screen bounds
        private ProjectileManager projectileManager;
        private boolean canFireWeapons = true;
        private boolean canMove = true;
        private MusicPlayer musicPlayer;
        private Rect gameScreenBounds;


        PlayerShip(Context context, float initialX, float initialY, int shield, int speed, AnimationDefinitionGroup animationInfoService, ProjectileManager projectileManager){
            super( animationInfoService,
                    (int)initialX,
                    (int)initialY,
                    shield);

            musicPlayer = new MusicPlayer(context);
            this.gameScreenBounds = new Rect(0,0,400,640);
            direction = Direction.NONE;

            this.speed = speed;
            this.score = new Score(8);
            tempRect = new Rect(0,0,0,0);
            this.projectileManager = projectileManager;
        }

        public boolean isDead(){
            return getState() == ActorState.DESTROYED;
        }

        void setGameScreenBounds(Rect bounds){
            this.gameScreenBounds = bounds;
        }

        public void addToScore(int points){
            this.score.add(points);
        }

        public Score getScore(){
            return this.score;
        }


        public void fire(){
            isFiring = true;
            musicPlayer.playLoopingSound(R.raw.bloop1_short);
        }

        public void releaseFire(){
            isFiring = false;
            musicPlayer.stopLoopingSound();
        }


        public void update(){
            updateDirection();
            releaseBulletIfFiring();
            killShipIfEnergyGone();
        }

        private void releaseBulletIfFiring(){
            if(isFiring){
                fireBulletIfDelayCompleted();
            }
        }


        private void fireBulletIfDelayCompleted(){

            currentFireIteration++;
            if(currentFireIteration >= fireDelay){
                currentFireIteration = 0;
                fireBullet();
            }

        }


        private void fireBullet(){
            if(!canFireWeapons){
                return;
            }
            float x = getBounds().centerX();
            float y = getBounds().top;
            projectileManager.createProjectile(x,y,30, this);
        }



        private void killShipIfEnergyGone(){
            if(isEnergyGoneButStillAlive()){
                setState(ActorState.DESTROYING);
                canFireWeapons = false;
                canMove = false;
                musicPlayer.release();
            }

        }

        private boolean isEnergyGoneButStillAlive(){
            return this.getEnergy().isDepleted() && isAlive();

        }


        private boolean isAlive(){
            return getState() != ActorState.DESTROYING && getState() != ActorState.DESTROYED;
        }

        private void logUpdate(){
            if(updateCount % 500 == 0){
                Log.i("PlayerShip update()", "Updating playership.");
            }
            updateCount++;
        }

        private void updateDirection(){
            if(!canMove){
                return;
            }
            switch(direction){
                case UP:
                    moveUp();break;
                case DOWN:
                    moveDown();break;
                case LEFT:
                    moveLeft();break;
                case RIGHT:
                    moveRight();break;
                case UP_LEFT:
                    moveUp(); moveLeft();break;
                case UP_RIGHT:
                    moveUp(); moveRight();break;
                case DOWN_LEFT:
                    moveDown(); moveLeft();break;
                case DOWN_RIGHT:
                    moveDown(); moveRight();break;

               // default: Log.i("PlayerShip updateDir()", "No direction detected");
            }
        }


        public void setDirection(Direction direction){
            this.direction = direction;
        }


    private void moveDown() {    moveDrawable(0,1);}
    private void moveUp(){       moveDrawable(0,-1);}
    private void moveLeft(){    moveDrawable(-1,0); }
    private void moveRight(){   moveDrawable(1,0); }

    private void moveDrawable(int xOffset, int yOffset){

        xOffset *= speed;
        yOffset *= speed;

        updateTempRect(xOffset, yOffset);
        if(gameScreenBounds.contains(tempRect)){
            offsetBounds(xOffset, yOffset);
        }
    }

    private void updateTempRect(int xOffset, int yOffset){
        tempRect.set(this.getBounds());
        tempRect.offset(xOffset, yOffset);

    }

        public void stopMoving(){
            direction = Direction.NONE;
        }


    @Override
    public void setState(ActorState actorState){

            super.setState(actorState);
            Log.i("PlayerShip", " setState() @Override : changing player ship state to: " + actorState);
    }

}
