package com.jacstuff.spacearmada.service.star;

import android.graphics.Point;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StarManager {

    private List<Point> slowStars;
    private List<Point> fastStars;
    private final Random random;
    private RectF screenBounds;


    public StarManager(){
        random = new Random();
        slowStars = new ArrayList<>();
        fastStars = new ArrayList<>();
    }


    public void setBoundsAndGenerateStars(RectF screenBounds){
        this.screenBounds = screenBounds;
        generateStars();
    }


    public void generateStars(){
        slowStars = createStars();
        fastStars = createStars();
    }


    public List<Point> createStars(){
        int numberOfStars = 20;
        var stars = new ArrayList<Point>(numberOfStars);
        for(int i = 0; i < numberOfStars; i++){
            stars.add(createStarAtRandomCoordinate());
        }
        return stars;
    }


    private void log(String msg){
        System.out.println("^^^ StarManager: " + msg);
    }


    public List<Point> updateAndGetStars(){
        for(var star : slowStars){
            updateStar(star, 1);
        }
        for(var star : fastStars){
            updateStar(star, 3);
        }
        var allStars = new ArrayList<Point>();
        allStars.addAll(slowStars);
        allStars.addAll(fastStars);

        return allStars;
    }


    private void updateStar(Point star, int movementOffset){
        star.y = star.y + movementOffset;
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
