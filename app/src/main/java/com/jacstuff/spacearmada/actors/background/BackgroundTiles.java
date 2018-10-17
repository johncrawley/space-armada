package com.jacstuff.spacearmada.actors.background;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BackgroundTiles {

    private List<Tile> tiles;

    private int scrollSpeed, screenWidth;
    private int tileHeight;
    private Context context;
    private int gameScreenTop;
    private int gameScreenBottom;
    private int tilesToDraw;
    private int bottomDrawnTileIndex; // this is all assuming that we are vertical scrolling, with the drawables moving downwards


    public BackgroundTiles(Context context, int tilesToDraw, int scrollSpeed, int screenWidth, int gameScreenTop, int gameScreenBottom){
        tiles = new ArrayList<>();
        this.context = context;
        this.scrollSpeed = scrollSpeed;
        this.screenWidth = screenWidth;
        this.gameScreenBottom = gameScreenBottom;
        this.gameScreenTop = gameScreenTop;
        this.tilesToDraw = tilesToDraw;
        this.bottomDrawnTileIndex = tilesToDraw - 1;

        tileHeight = (gameScreenBottom - gameScreenTop) / (tilesToDraw - 1);

        initialTileTopY = gameScreenTop - tileHeight;
        nextInitialY = initialTileTopY;
    }

    private int initialTileTopY;
    private int nextInitialY;

    public void addTiles(int ... resIds){
        for(int resId : resIds) {
            tiles.add(new Tile(BitmapFactory.decodeResource(context.getResources(), resId), nextInitialY, initialTileTopY));
            nextInitialY += tileHeight;
        }
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
        for(int i = 0; i < tilesToDraw; i++){
            tiles.get(i).offsetY(scrollSpeed);
        }

    }
}
