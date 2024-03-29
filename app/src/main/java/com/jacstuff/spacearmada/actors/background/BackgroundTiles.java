package com.jacstuff.spacearmada.actors.background;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.view.SimpleDrawItem;
import com.jacstuff.spacearmada.view.TransparentView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BackgroundTiles {

    private final List<SimpleDrawItem> tiles;
    private final int scrollSpeed;
    private final int tileHeight;
    private final Context context;
    private final int gameScreenBottom;
    private final int tilesToDraw;
    private final int bottomDrawnTileIndex; // this is all assuming that we are vertical scrolling, with the drawables moving downwards
    private final int initialTileTopY;
    private int nextInitialY;
    private int updateCounter;
    private final TransparentView view;


    public BackgroundTiles(Context context, TransparentView view, int gameScreenTop, int gameScreenBottom){
        tiles = new ArrayList<>();
        this.context = context;
        this.scrollSpeed = context.getResources().getInteger(R.integer.backgroundScrollSpeed);
        this.gameScreenBottom = gameScreenBottom;
        this.tilesToDraw = context.getResources().getInteger(R.integer.backgroundScrollSpeed);
        this.bottomDrawnTileIndex = tilesToDraw - 1;
        tileHeight = calculateTileHeight(gameScreenBottom, gameScreenTop);
        initialTileTopY = gameScreenTop - tileHeight;
        nextInitialY = initialTileTopY;
        this.view = view;
        addBackgroundTiles();
        update();
    }


    private int calculateTileHeight(int gameScreenBottom, int gameScreenTop){
        int divisor = tilesToDraw <=1 ? 1 : tilesToDraw -1;
        return (gameScreenBottom - gameScreenTop) / divisor;
    }


    public void addBackgroundTiles(){
        addTiles(
                R.drawable.level1_bg_1,
                R.drawable.level1_bg_2,
                R.drawable.level1_bg_3,
                R.drawable.level1_bg_4,
                R.drawable.level1_bg_5,
                R.drawable.level1_bg_6,
                R.drawable.level1_bg_7,
                R.drawable.level1_bg_8,
                R.drawable.level1_bg_9,
                R.drawable.level1_bg_10,
                R.drawable.level1_bg_11,
                R.drawable.level1_bg_12);
        view.setDrawItems(tiles);
    }


    public void addTiles(int ... resIds){
        for(int resId : resIds) {
            tiles.add(new Tile(BitmapFactory.decodeResource(context.getResources(), resId), nextInitialY, initialTileTopY));
            nextInitialY += tileHeight;
        }
    }


    public void update() {
        offsetDrawnTiles();
        rotateTilesIfBottomTileOutOfBounds();
        view.invalidate();
    }


    private void rotateTilesIfBottomTileOutOfBounds(){
        if(tiles.get(bottomDrawnTileIndex).getY() > gameScreenBottom) {
            Collections.rotate(tiles,1);
            Tile tile = (Tile)tiles.get(0);
            tile.resetY();
        }
    }


    private void offsetDrawnTiles(){
        if(shouldUpdate()) {
            for (int i = 0; i < tilesToDraw; i++) {
                offsetTile(i);
            }
        }
    }


    private void offsetTile(int index){
        Tile tile = (Tile)tiles.get(index);
        tile.offsetY(scrollSpeed);
    }


    private boolean shouldUpdate(){
        updateCounter++;
        int updateCounterMax = 3;
        if(updateCounter > updateCounterMax){
            updateCounter = 0;
            return true;
        }
        return false;
    }
}
