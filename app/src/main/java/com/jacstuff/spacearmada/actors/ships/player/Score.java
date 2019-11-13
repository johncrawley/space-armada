package com.jacstuff.spacearmada.actors.ships.player;

import android.util.Log;

public class Score {

    private int numberOfDigits;
    private int value;
    private int maxScore;

    public Score(int numberOfDigits){
        this.numberOfDigits = numberOfDigits;
        value = 0;
        //calculateMaxScore();
    }


    private void calculateMaxScore(){

        maxScore = 9;
        for(int i=0; i< numberOfDigits; i++){
            maxScore = (maxScore * 10) + 9;
        }

    }

    public int getScoreInt(){
        return this.value;
    }

    public void add(int addition){
       // if(this.value >= maxScore){
       //     this.value = maxScore;
       //     return;
       // }
        //Log.i("Score", "value before addition of "  + addition + ": " + value);
        this.value += addition;
        //Log.i("Score", "value after addition: " + value);
        //.value = this.value + addition;
    }


    public String get(){

        StringBuilder strbldr = new StringBuilder();
        int score = this.value;
        for(int i = 0; i < numberOfDigits; i++){
            String digit = "" + score % 10;
            strbldr.append(digit);
            score /= 10;
        }
        return strbldr.reverse().toString();
    }
}
