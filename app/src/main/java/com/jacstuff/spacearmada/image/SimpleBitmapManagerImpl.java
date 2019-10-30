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


    private Map<String, List<Bitmap>> imageMap;

    public SimpleBitmapManagerImpl(){
        imageMap = new HashMap<>();
    }

    public Bitmap getBitmap(DrawInfo drawInfo){
        String key = getKey(drawInfo);

        if(drawInfo.getState()== ActorState.DESTROYING){
            log("destroying state key: "+  key);
        }
        List<Bitmap> bitmaps = imageMap.get(key);
        if(bitmaps == null || bitmaps.isEmpty()){
            return null;
        }
        return bitmaps.get(drawInfo.getFrame());
    }


    public Bitmap getBitmap(String family, ActorState actorState, int frame){
        return null;
    }

    public void register(String family, ActorState state, List<Bitmap> bitmaps){
        String key = getKey(family, state);
        imageMap.put(key, bitmaps);
        log("bitmap registered with key: "+  key);
    }


    private String getKey(String family, ActorState actorState){
        return family + "_" + actorState.toString();
    }

    private void log(String msg){
        Log.i("SimpleBitmapMngr", msg);
    }

    private int currentLog = 0;
    void logInter(String msg){
        int limit = 100;
        currentLog += 1;
        if(currentLog > limit){
            log(msg);
            currentLog = 0;
        }

    }


    private String getKey(DrawInfo drawInfo){


        String key = getKey(drawInfo.getFamily(), drawInfo.getState());
        //logInter("getKey returns: " + key);
        return key;
    }
}
