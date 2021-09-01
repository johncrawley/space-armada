package com.jacstuff.spacearmada.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.animation.AnimationDefinition;
import com.jacstuff.spacearmada.actors.animation.AnimationDefinitionGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScaledBitmapLoader implements BitmapLoader {

    private final Context context;
    private final BitmapManager bitmapManager;
    private final Map<String, AnimationDefinitionGroup> animationDefinitionGroups;
    private final int scale;
    private final int width, height;


    public ScaledBitmapLoader(Context context, BitmapManager bitmapManager, int width, int height){
        this.context = context;
        this.width = width;
        this.height = height;
        this.bitmapManager = bitmapManager;
        this.scale = 2;
        animationDefinitionGroups = new HashMap<>();
        load();
    }


    @Override
    public void load(){
        loadPlayerShipBitmaps();
        loadBulletBitmaps();
        loadEnemyShipBitmaps();
    }


    public AnimationDefinitionGroup getAnimationDefinitionGroup(String family){
        return animationDefinitionGroups.get(family);
    }


    //TODO: create a bitmap loader implementation that inputs from a file, so the family string can be added to both the group and the actor object
    private void loadEnemyShipBitmaps(){
        String groupName = "ENEMY_SHIPS";
        AnimationDefinitionGroup animationDefinitionGroup = new AnimationDefinitionGroup(groupName);
        registerGroup(ActorState.DEFAULT, animationDefinitionGroup, R.drawable.ship2);
        registerGroup(ActorState.DESTROYING, ActorState.DESTROYED, animationDefinitionGroup,
                R.drawable.ship2e1,
                R.drawable.ship2e2,
                R.drawable.ship2e3,
                R.drawable.ship2e4);
        registerGroup(ActorState.DESTROYED, animationDefinitionGroup, R.drawable.ship2e5);
        animationDefinitionGroups.put(groupName, animationDefinitionGroup);
    }


    private void loadPlayerShipBitmaps() {
        String groupName = "PLAYER_SHIP";
        AnimationDefinitionGroup animationDefinitionGroup = new AnimationDefinitionGroup(groupName);
        registerGroup(ActorState.DEFAULT, animationDefinitionGroup, R.drawable.ship1);
        registerGroup(ActorState.DESTROYING, ActorState.DESTROYED, animationDefinitionGroup,
                R.drawable.ship1e1,
                R.drawable.ship1e2,
                R.drawable.ship1e3,
                R.drawable.ship1e4);
        registerGroup(ActorState.DESTROYED, animationDefinitionGroup, R.drawable.ship1e5);
        animationDefinitionGroups.put(groupName, animationDefinitionGroup);
    }


    private void loadBulletBitmaps(){
        String groupName = "BULLET";
        AnimationDefinitionGroup animationDefinitionGroup = new AnimationDefinitionGroup(groupName);
        registerGroup(ActorState.DEFAULT, animationDefinitionGroup,  R.drawable.bullet1);
        animationDefinitionGroups.put(groupName, animationDefinitionGroup);
    }


    private void registerGroup(ActorState state, AnimationDefinitionGroup animationDefinitionGroup, Integer... ids) {
        registerGroup(state, false, null, animationDefinitionGroup, ids);
    }


    private void registerGroup(ActorState state, ActorState nextState, AnimationDefinitionGroup animationDefinitionGroup, Integer... ids) {
        registerGroup(state, false, nextState, animationDefinitionGroup, ids);
    }


    private void registerGroup(ActorState state, boolean doesLoop, ActorState nextState, AnimationDefinitionGroup animationDefinitionGroup, Integer... ids){
        List<Bitmap> bitmaps = getBitmaps(ids);
        bitmapManager.register(animationDefinitionGroup.getGroupName(), state, bitmaps);

        BitmapDimension bitmapDimension = getDimensionsOfFirstBitmap(bitmaps);
        AnimationDefinition animationDefinition = new AnimationDefinition(ids.length, doesLoop, bitmapDimension, nextState);
        animationDefinitionGroup.register(state, animationDefinition);
    }


    private BitmapDimension getDimensionsOfFirstBitmap(List<Bitmap> bitmaps){
        Bitmap bm = bitmaps.get(0);
        return new BitmapDimension(bm.getWidth(), bm.getHeight());
    }


    private List<Bitmap> getBitmaps(Integer...resIds){
        List<Bitmap> bitmaps = new ArrayList<>(resIds.length);
        for(int id : resIds){
            bitmaps.add(getBitmap(id));
        }
        return bitmaps;
    }


    private Bitmap getBitmap(int resId) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = scale;
        return BitmapFactory.decodeResource(context.getResources(), resId, opts);
    }


    private Bitmap getBitmap(int resId, int percentageOfCanvasShortSide){

        int shortSide = Math.min(width, height);
        int bmWidth = (shortSide / 100) * percentageOfCanvasShortSide;

        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resId);
        int bmHeight = bm.getHeight() * ( bmWidth / bm.getWidth());
        return Bitmap.createScaledBitmap(bm, bmWidth, bmHeight, true);
    }



}
