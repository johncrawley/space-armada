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

    public SimpleBitmapLoader(Context context, BitmapManager bitmapManager){
        this.context = context;
        this.bitmapManager = bitmapManager;
    }


    @Override
    public void load(){
        //TODO: loads lots of other actor bitmaps as well
        Log.i("SimpleBitmapLoader", "Entered load()");
        loadEnemyShipBitmaps();

    }

    //TODO: create a bitmap loader implementation that inputs from a file, so the family string can be added to both the group and the actor object
    private void loadEnemyShipBitmaps(){

        String family = "ENEMY_SHIPS";

        registerGroup(family, ActorState.DEFAULT, R.drawable.ship2);
        registerGroup(family, ActorState.DESTROYING,
                    R.drawable.ship2e1,
                    R.drawable.ship2e2,
                    R.drawable.ship2e3,
                    R.drawable.ship2e4);
        registerGroup(family, ActorState.DESTROYED, R.drawable.ship2e5);
    }

    private void registerGroup(String family, ActorState state, Integer... ids){

        List<Bitmap> bitmaps = getBitmaps(ids);
        bitmapManager.register(family, state, bitmaps);

    }


    private List<Bitmap> getBitmaps(Integer...resIds){
        List<Bitmap> bitmaps = new ArrayList<>(resIds.length);
        for(int id : resIds){
            bitmaps.add(getBitmap(id));
        }
        return bitmaps;
    }

    private Bitmap getBitmap(int resId){

       return BitmapFactory.decodeResource(context.getResources(), resId);

    }




}
