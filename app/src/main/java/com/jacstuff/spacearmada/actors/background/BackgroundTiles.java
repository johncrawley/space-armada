package com.jacstuff.spacearmada.actors.background;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.jacstuff.spacearmada.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BackgroundTiles {

    private final List<Tile> tiles;

    private final int scrollSpeed;
    private final int tileHeight;
    private final Context context;
    private final int gameScreenTop;
    private final int gameScreenBottom;
    private final int tilesToDraw;
    private final int bottomDrawnTileIndex; // this is all assuming that we are vertical scrolling, with the drawables moving downwards
    private final int initialTileTopY;
    private int nextInitialY;
    private final int updateCounterMax = 3;
    private int updateCounter;

    public BackgroundTiles(Context context, int tilesToDraw, int scrollSpeed, int gameScreenTop, int gameScreenBottom){
        tiles = new ArrayList<>();
        this.context = context;
        this.scrollSpeed = scrollSpeed;
        this.gameScreenBottom = gameScreenBottom;
        this.gameScreenTop = gameScreenTop;
        this.tilesToDraw = tilesToDraw;
        this.bottomDrawnTileIndex = tilesToDraw - 1;

        tileHeight = (gameScreenBottom - gameScreenTop) / (tilesToDraw - 1);

        initialTileTopY = gameScreenTop - tileHeight;
        nextInitialY = initialTileTopY;
        addBackgroundTiles();
    }


    public void addTiles(int ... resIds){
        for(int resId : resIds) {
            tiles.add(new Tile(BitmapFactory.decodeResource(context.getResources(), resId), nextInitialY, initialTileTopY));
            nextInitialY += tileHeight;
        }
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
    }

    public List<Tile> getTiles(){
        return this.tiles;
    }

    public void update() {
        offsetDrawnTiles();
        rotateTilesIfBottomTileOutOfBounds();
    }


    private void rotateTilesIfBottomTileOutOfBounds(){
        if(tiles.get(bottomDrawnTileIndex).getY() > gameScreenBottom) {
            Collections.rotate(tiles,1);
            tiles.get(0).resetY();
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
        tiles.get(index).offsetY(scrollSpeed);
    }

    private boolean shouldUpdate(){
        updateCounter++;
        if(updateCounter > updateCounterMax){
            updateCounter = 0;
            return true;
        }
        return false;
    }
}
