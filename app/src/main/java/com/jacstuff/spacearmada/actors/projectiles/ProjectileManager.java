package com.jacstuff.spacearmada.actors.projectiles;

import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.jacstuff.spacearmada.Direction;
import com.jacstuff.spacearmada.DrawableItem;
import com.jacstuff.spacearmada.DrawableItemGroup;
import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.actors.ActorState;
import com.jacstuff.spacearmada.actors.AnimationDefinitionGroup;
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
    private int screenTop, screenBottom;
    private ImageLoader imageLoader;
    private AnimationDefinitionGroup animationInfoService;

    public ProjectileManager(Rect gameScreenBounds, ImageLoader imageLoader){
        projectiles = new ArrayList<>();
        this.imageLoader = imageLoader;
        this.screenTop = gameScreenBounds.top;
        this.screenBottom = gameScreenBounds.bottom;
         animationInfoService = new AnimationDefinitionGroup("BULLET");
         animationInfoService.registerState(ActorState.DEFAULT, 1, false);
    }


    public List<DrawableItem> getDrawableItems(){
        return new ArrayList<DrawableItem>(this.projectiles);
    }

    public void createProjectile(float x, float y, int energy, ArmedShip ownerShip){
        synchronized(projectiles){
            Bullet bullet = new Bullet((int)x, (int)y, 3, 7, 15, energy, Direction.UP, animationInfoService, ownerShip, R.drawable.bullet1, imageLoader);
            Log.i("ProjectMngr", "createProjectile() new bullet drawInfo: " + bullet.getDrawInfo().toString());
            projectiles.add(bullet);
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
            for (Projectile projectile : new ArrayList<>(projectiles)) {
               projectile.update();
               removeProjectileIfInvalid(projectile);
            }
    }


    private void removeProjectileIfInvalid(Projectile projectile){
        if(isOutsideBounds(projectile) || projectile.getEnergy().isDepleted()){
            projectiles.remove(projectile);
        }
    }


    private boolean isOutsideBounds(Projectile projectile){
        Rect bounds = projectile.getBounds();
        return (bounds != null) && (bounds.bottom < screenTop || bounds.top > screenBottom);
    }


    private void logI(String msg){

        int logInterval = 400;
        currentLogNum++;
        if(currentLogNum == logInterval){
            Log.i("ProjectileManager", msg);
            currentLogNum = 0;
        }
    }
}
