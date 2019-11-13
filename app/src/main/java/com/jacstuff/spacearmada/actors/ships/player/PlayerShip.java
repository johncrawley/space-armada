package com.jacstuff.spacearmada.actors.ships.player;

import android.content.Context;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.AnimationInfoService;
import com.jacstuff.spacearmada.actors.CollidableActor;
import com.jacstuff.spacearmada.actors.projectiles.ProjectileManager;
import com.jacstuff.spacearmada.actors.ships.ControllableShip;
import com.jacstuff.spacearmada.utils.ImageLoader;

/**
 * Created by John on 29/08/2017.
 *
 * Represents the ship that the user controls
 */

public class PlayerShip extends CollidableActor implements ControllableShip {

        // String mode;
        private Direction direction;

        private int updateCount = 0;
        private int fireDelay = 5;
        private int currentFireIteration = 0;
        private Score score;
        private boolean isFiring = false;
        private Rect tempRect; // used for seeing if the ships rect stays within the game screen bounds
        private ProjectileManager projectileManager;
        private boolean canFireWeapons = true;
        private boolean canMove = true;
        private MediaPlayer mediaPlayer; // TODO: MediaPlayer reference shouldn't be in the actor class, need a separate class to handle sounds
        private boolean isMediaPlayerActive = true;
        private Rect gameScreenBounds; // the space that the ship is allowed to move in.
        //private WeaponsManager weaponsManager;


        PlayerShip(Context context, float initialX, float initialY, int shield, int speed, AnimationInfoService animationInfoService, ProjectileManager projectileManager, ImageLoader imageLoader, int defaultResourceId){
            super( animationInfoService,
                    imageLoader,
                    (int)initialX,
                    (int)initialY,
                    55,
                    96,
                    300,
                    defaultResourceId);

            this.gameScreenBounds = new Rect(0,0,400,640);
            direction = Direction.NONE;
            this.energy = new Energy(shield, shield /3, shield / 4);
            this.speed = speed;
            this.score = new Score(8);
            tempRect = new Rect(0,0,0,0);
            this.projectileManager = projectileManager;
            mediaPlayer = MediaPlayer.create(context, R.raw.bloop1_short );
        }

        public boolean isDead(){
            return getState() == ActorState.DESTROYED;
        }

        public void setGameScreenBounds(Rect bounds){
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
            playSound();
        }

        public void releaseFire(){
            isFiring = false;
            if(isMediaPlayerActive && mediaPlayer != null){
                mediaPlayer.setLooping(false);
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


    private void playSound(){

        if(isMediaPlayerActive){
            if(!mediaPlayer.isPlaying()){
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        }

    }

        public void update(){
            updateDirection();
            if(isFiring){
                currentFireIteration++;
                if(currentFireIteration >= fireDelay){
                    currentFireIteration = 0;
                    fireBullet();
                }
            }
            if(this.getEnergy().isDepleted() && getState() != ActorState.DESTROYED){
                setActorState(ActorState.DESTROYING);
                canFireWeapons = false;
                canMove = false;
                mediaPlayer.setLooping(false);
                isMediaPlayerActive = false;
            }
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


        private String directionChangedLog = "";
        private void recordDirectionChange(Direction newDirection){
            if(!newDirection.equals(this.direction)){
                directionChangedLog += ", " + newDirection.toString();
            }
        }


        public void setDirection(Direction direction){
            recordDirectionChange(direction);
            this.direction = direction;
            //Log.i("basicActor","Setting direction: " + direction);
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
           // boundingBox.offset(xOffset, yOffset);
        }
       // Log.i("PlayerShip moveDrawable", "updated x,y : " + drawable.getBounds().left + " " + drawable.getBounds().top );
    }

    private void updateTempRect(int xOffset, int yOffset){
        tempRect.set(this.getBounds());
        tempRect.offset(xOffset, yOffset);

    }

        public void stopMoving(){
            direction = Direction.NONE;
        }


    @Override
    public void setActorState(ActorState actorState){

            super.setActorState(actorState);
            Log.i("PlayerShip", " setActorState() @Override : changing player ship state to: " + actorState);
    }

}
