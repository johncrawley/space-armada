package com.jacstuff.spacearmada.actor.player;

import com.jacstuff.spacearmada.actors.ships.player.Score;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ScoreTest {

    Score score;

    @Before
    public void setup(){

        score = new Score(8);

    }


    @Test
    public void canAddToScore(){
        String expectedScore = "00000000";
        assertEquals("Score should be 0 at the start of this test", expectedScore, score.get());

        assertScoreAddition( 350,"00000350", 350);
        assertScoreAddition(100,"00000450", 450);
    }


    private void assertScoreAddition(int addition, String expected, int expectedInt){
        score.add(addition);
        String actual = score.get();

        assertEquals("score incorrect", expectedInt, score.getScoreInt());
        assertEquals("Score is incorrect after addition", expected, actual);
    }


}
