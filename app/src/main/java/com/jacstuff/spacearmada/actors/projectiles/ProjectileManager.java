package com.jacstuff.spacearmada.actors.projectiles;

import android.content.Context;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.DrawableItem;
import com.jacstuff.spacearmada.DrawableItemGroup;
import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.actors.ships.ArmedShip;
import com.jacstuff.spacearmada.utils.ImageLoader;

/**
 * Created by John on 30/08/2017.
 *
 * Handles the firing and storage of projectiles for all enemy and player ships
 */

public class ProjectileManager implements DrawableItemGroup {

    final private List<Projectile> projectiles;

    private int currentLogNum = 0;
    private int logInterval = 400;
    private Context context;
    private int screenTop, screenBottom;
    private MediaPlayer mediaPlayer;
    private ImageLoader imageLoader;

    public ProjectileManager(Context context, Rect gameScreenBounds, ImageLoader imageLoader){
        projectiles = new ArrayList<>();
        this.imageLoader = imageLoader;
        this.context = context;
        this.screenTop = gameScreenBounds.top;
        this.screenBottom = gameScreenBounds.bottom;
    }


    public List<DrawableItem> getDrawableItems(){

        return new ArrayList<DrawableItem>(this.projectiles);
    }

    private void log(String msg){
        Log.i("ProjectileManager", msg);
    }
    public void createProjectile(float x, float y, int energy, ArmedShip ownerShip){
        synchronized(projectiles){
            projectiles.add(new Bullet((int)x, (int)y, 3, 7, energy, Direction.UP, imageLoader, ownerShip, R.drawable.bullet1));
        }
    }

    public List<Projectile> getProjectiles(){
        return new ArrayList<>(this.projectiles);
    }

    public void update(){
        synchronized (projectiles){
            updateProjectiles(projectiles);
        }


    }

    private void updateProjectiles( List<Projectile> projectiles){
            List<Projectile> projectileList = new ArrayList<>(projectiles);
            //logI("projectileList size = " + projectiles.size());
            for (Projectile projectile : projectileList) {
                projectile.update();
                //logI("updated projectile");


                if(isOusideBounds(projectile) || projectile.getEnergy() < 1){
                     projectiles.remove(projectile);
                }
            }
    }

    private boolean isOusideBounds(Projectile projectile){
        Rect bounds = projectile.getBounds();
        return (bounds != null) && (bounds.bottom < screenTop || bounds.top > screenBottom);
    }

    private void logI(String msg){
        currentLogNum++;
        if(currentLogNum % logInterval == 0){
            Log.i("ProjectileManager", msg);
        }
    }
}
