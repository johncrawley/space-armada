package com.jacstuff.spacearmada.service;

import android.graphics.Point;
import android.graphics.Rect;

import com.jacstuff.spacearmada.view.fragments.game.GameFragment;
import com.jacstuff.spacearmada.view.fragments.game.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StarManager {

    private GameView gameView;
    private List<Point> slowStars;
    private final Random random;
    private final Rect screenBounds;


    public StarManager(Rect screenBounds){
        this.screenBounds = screenBounds;
        random = new Random();
        generateStars();
    }


    public void setGameView(GameView gameView){
        this.gameView = gameView;
    }


    public void updateStarsOnView(){
        updateStars();
        gameView.updateStars(slowStars);
    }


    public void generateStars(){
        int numberOfStars = 20;
        slowStars = new ArrayList<>(numberOfStars);
        for(int i=0; i<numberOfStars; i++){
            slowStars.add(createStarAtRandomCoordinate());
        }
    }


    private void updateStars(){
        for(Point star : slowStars){
            updateStar(star);
        }
    }


    private void updateStar(Point star){
        int starMovement = 2;
        star.y = star.y + starMovement;
        resetStarIfBeyondBounds(star);
    }


    private void resetStarIfBeyondBounds(Point star){
        if(star.y > screenBounds.bottom){
            star.y = screenBounds.top - random.nextInt(20);
            star.x = getRandomXInBounds();
        }
    }


    private Point createStarAtRandomCoordinate(){
        int starX = getRandomXInBounds();
        int starY = getRandomNumberBetween(screenBounds.top, screenBounds.bottom);
        return new Point(starX, starY);
    }


    private int getRandomXInBounds(){
        return getRandomNumberBetween(screenBounds.left, screenBounds.right);
    }


    private int getRandomNumberBetween(int a, int b){
        return a + random.nextInt(b - a);
    }


}
