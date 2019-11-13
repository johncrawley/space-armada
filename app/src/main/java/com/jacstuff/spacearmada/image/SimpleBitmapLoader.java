package com.jacstuff.spacearmada.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.actors.ActorState;

import java.util.ArrayList;
import java.util.List;

public class SimpleBitmapLoader implements BitmapLoader {

    private Context context;
    private BitmapManager bitmapManager;
    private int screenWidth, screenHeight;

    public SimpleBitmapLoader(Context context, BitmapManager bitmapManager, int screenWidth, int screenHeight){
        this.context = context;
        this.bitmapManager = bitmapManager;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }


    @Override
    public void load(){
        //TODO: loads lots of other actor bitmaps as well
        Log.i("SimpleBitmapLoader", "Entered load()");
        loadPlayerShipBitmaps();
        loadBulletBitmaps();
        loadEnemyShipBitmaps();

    }

    //TODO: create a bitmap loader implementation that inputs from a file, so the family string can be added to both the group and the actor object
    private void loadEnemyShipBitmaps(){

        String family = "ENEMY_SHIPS";
        final int ENEMY_SHIP_SCALE = 2;


        registerGroup(family, ActorState.DEFAULT, ENEMY_SHIP_SCALE, R.drawable.ship2);
        registerGroup(family, ActorState.DESTROYING, ENEMY_SHIP_SCALE,
                    R.drawable.ship2e1,
                    R.drawable.ship2e2,
                    R.drawable.ship2e3,
                    R.drawable.ship2e4);
        registerGroup(family, ActorState.DESTROYED, ENEMY_SHIP_SCALE, R.drawable.ship2e5);
    }

    private void loadPlayerShipBitmaps() {

        String family = "PLAYER_SHIP";
        final int scale = 2;
        registerGroup(family, ActorState.DEFAULT, scale, R.drawable.ship1);
        registerGroup(family, ActorState.DESTROYING, scale,
                R.drawable.ship1e1,
                R.drawable.ship1e2,
                R.drawable.ship1e3,
                R.drawable.ship1e4);
        registerGroup(family, ActorState.DESTROYED, scale, R.drawable.ship1e5);
    }

    private void loadBulletBitmaps(){

        String family = "BULLET";
        int scale = 2;
        registerGroup(family, ActorState.DEFAULT, scale, R.drawable.bullet1);

    }

    private void registerGroup(String family, ActorState state, int scale, Integer... ids){

        List<Bitmap> bitmaps = getBitmaps(scale, ids);
        bitmapManager.register(family, state, bitmaps);

    }


    private List<Bitmap> getBitmaps(int scale, Integer...resIds){
        List<Bitmap> bitmaps = new ArrayList<>(resIds.length);
        for(int id : resIds){
            bitmaps.add(getBitmap(id, scale));
        }
        return bitmaps;
    }

    private Bitmap getBitmap(int resId, int scale) {

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = scale;
        return BitmapFactory.decodeResource(context.getResources(), resId, opts);
    }




}
