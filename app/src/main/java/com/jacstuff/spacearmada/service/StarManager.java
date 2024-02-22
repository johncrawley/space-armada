package com.jacstuff.spacearmada.service;

import android.graphics.Point;
import android.graphics.RectF;

import com.jacstuff.spacearmada.view.fragments.game.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StarManager {

    private List<Point> slowStars;
    private final Random random;
    private RectF screenBounds;


    public StarManager(){
        random = new Random();
        slowStars = new ArrayList<>();
    }


    public void setBoundsAndGenerateStars(RectF screenBounds){
        this.screenBounds = screenBounds;
        generateStars();
    }


    public void generateStars(){
        int numberOfStars = 20;
        slowStars = new ArrayList<>(numberOfStars);
        for(int i=0; i<numberOfStars; i++){
            slowStars.add(createStarAtRandomCoordinate());
        }
    }


    public List<Point> updateAndGetStars(){
        for(Point star : slowStars){
            updateStar(star);
        }
        return slowStars;
    }



    private void updateStar(Point star){
        int starMovement = 2;
        star.y = star.y + starMovement;
        resetStarIfBeyondBounds(star);
    }


    private void resetStarIfBeyondBounds(Point star){
        if(star.y > screenBounds.bottom){
            star.y = (int)screenBounds.top - random.nextInt(20);
            star.x = getRandomXInBounds();
        }
    }


    private Point createStarAtRandomCoordinate(){
        int starX = getRandomXInBounds();
        int starY = getRandomNumberBetween((int)screenBounds.top, (int)screenBounds.bottom);
        return new Point(starX, starY);
    }


    private int getRandomXInBounds(){
        return getRandomNumberBetween((int)screenBounds.left, (int) screenBounds.right);
    }


    private int getRandomNumberBetween(int a, int b){
        return a + random.nextInt(b - a);
    }


}
