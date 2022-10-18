package com.jacstuff.spacearmada.game;

import android.graphics.Bitmap;
import android.view.View;

import com.jacstuff.spacearmada.actors.ships.enemies.EnemyShipManager;
import com.jacstuff.spacearmada.image.BitmapLoader;
import com.jacstuff.spacearmada.view.TransparentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnemyLayer {


    private final TransparentView transparentView;
    private final BitmapLoader bitmapLoader;
    private final EnemyShipManager enemyShipManager;


    public EnemyLayer(View view, BitmapLoader bitmapLoader, EnemyShipManager enemyShipManager){
        transparentView = (TransparentView)view;
        this.bitmapLoader = bitmapLoader;
        this.enemyShipManager = enemyShipManager;
        //linkBitmapsToColorsAndAssignWidths();
    }


    public void setDrawItems(){
        transparentView.clearDrawableItems();
       // transparentView.addDrawableItems(enemyShipManager.getEnemyShips());
        //List<DrawableItem> gems = new ArrayList<>(gemGroup.getGems());
       // transparentView.addDrawableItems(gems);
        //transparentView.setTranslateY((int)gemGroup.getY());
    }


   // @Override
    public void drawIfUpdated(){
        /*
        if(gemGroup == null){
            return;
        }
        if(gemGroup.wasUpdated()) {
            transparentView.setTranslateY((int)gemGroup.getY());
            transparentView.setTranslateX((int)gemGroup.getX());
            transparentView.invalidate();
            gemGroup.setUpdated(false);
        }

         */
    }

/*
    public void wipe(){
        if(gemGroup != null) {
            gemGroup.setGemsInvisible();
        }
        transparentView.clearDrawableItems();
        transparentView.invalidate();
    }


    private void setBitmapReferences(){
        for(Gem gem: gemGroup.getGems()){
            gem.setBitmap(gemColorMap.get(gem.getColor()));
        }
    }


    private void linkBitmapsToColorsAndAssignWidths(){
        gemColorMap = new HashMap<>();
        link(Gem.Color.BLUE);
        link(Gem.Color.YELLOW);
        link(Gem.Color.GREEN);
        link(Gem.Color.RED);
        link(Gem.Color.PURPLE);
    }


    private void link(Gem.Color color){
        gemColorMap.put(color, bitmapLoader.get(color.resourceId));
    }
*/

}
