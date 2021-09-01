package com.jacstuff.spacearmada.image;

import android.graphics.Bitmap;
import android.util.Log;

import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.DrawInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*

 Stores the bitmaps in a map containing a list at every key
 The key corresponds to a bitmap 'family' (e.g. EnemyShip, Bullet etc) appended to


 */

public class SimpleBitmapManagerImpl implements BitmapManager {


    private final Map<String, Bitmap> bitmapMap;
    private int currentLog = 0;

    public SimpleBitmapManagerImpl(){
        bitmapMap = new HashMap<>();
    }


    public Bitmap getBitmap(DrawInfo drawInfo){
        String key = getKey(drawInfo);
        Bitmap bitmap = bitmapMap.get(key);
        if(bitmap == null){
            logInter("Bitmap is null for drawInfo: " + drawInfo);
        }
        return bitmap;
    }


    public void register(String family, ActorState state, List<Bitmap> bitmaps){

        for(int i=0; i< bitmaps.size(); i++){
            String key = getKey(family, state, i);
            bitmapMap.put(key, bitmaps.get(i));

        }
    }


    private void log(String msg){
        Log.i("SimpleBitmapMngr", msg);
    }


    void logInter(String msg){
        int limit = 100;
        currentLog += 1;
        if(currentLog > limit){
            log(msg);
            currentLog = 0;
        }
    }


    private String getKey(String family, ActorState state, int frame){
        return family + "_" + state + "_" + frame;
    }


    private String getKey(DrawInfo drawInfo){
        return getKey(drawInfo.getFamily(), drawInfo.getState(), drawInfo.getFrame());
    }

}
