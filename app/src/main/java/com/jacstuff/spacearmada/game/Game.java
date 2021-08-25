package com.jacstuff.spacearmada.game;

import com.jacstuff.spacearmada.view.TransparentView;

public class Game {
/*

    private final GemGroupFactory gemGroupFactory;
    private GemGroupView gemGroupView;
    private final int height;
    private final int width;
    private final int floorY;
    private GemGridView gemGridView;
    private ClickHandler clickHandler;
    private GemControls gemControls;
    private final int gemWidth;
    private Evaluator evaluator;
    private ActionMediator actionMediator;
    private GemCountTracker gemCountTracker;
    private ScoreView scoreView;


    public Game(int screenWidth, int screenHeight, int gemWidth){
        this.width = screenWidth;
        this.height = screenHeight;
        this.gemWidth = gemWidth;
        int initialY = this.gemWidth * -2;
        floorY = height - (height /10);
    }


    void click(int x, int y){
        clickHandler.click(x,y);
        actionMediator.createAndDropGems();
    }


    void initGemGridView(TransparentView v){
        GemGrid gemGrid = new GemGrid(7, 12);
        gemGrid.setDropIncrement(gemWidth / 5);
        gemGridView = new GemGridView(v, gemGrid, gemWidth, floorY);
        evaluator = new Evaluator(gemGrid, 3);
        gemControls = new GemControls(gemGrid);
        clickHandler = new ClickHandler(gemControls, width, height);
        gemCountTracker = new GemCountTracker(gemGrid);
    }


    void initGemGroupView(TransparentView v, BitmapLoader bitmapLoader){
        gemGroupView = new GemGroupView(v, bitmapLoader, gemGroupFactory.createGemGroup());
    }


    void initScoreView(TransparentView v, BitmapLoader bitmapLoader){
        Score score = new Score(100);
        scoreView = new ScoreView(v, score, bitmapLoader, height);
        scoreView.draw();
    }


    void init(){
        SoundPlayer soundPlayer = new SoundPlayer();
        actionMediator = new ActionMediator.Builder()
                .evaluator(evaluator)
                .gemControls(gemControls)
                .gemGroupView(gemGroupView)
                .gridView(gemGridView)
                .gemGroupFactory(gemGroupFactory)
                .scoreView(scoreView)
                .gemCountTracker(gemCountTracker)
                .soundPlayer(soundPlayer)
                .build();
    }

 */
}


/*

    State
        -


 */
