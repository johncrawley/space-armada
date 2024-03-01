package com.jacstuff.spacearmada.service.ships.weapons;

import static com.jacstuff.spacearmada.service.ships.Utils.getChangedDrawInfoList;

import android.graphics.RectF;

import com.jacstuff.spacearmada.view.fragments.game.DrawInfo;

import java.util.ArrayList;
import java.util.List;

public class ProjectileManager {

    private final RectF bounds = new RectF();
    private final List<Projectile> projectiles;

    public ProjectileManager(){
        projectiles = new ArrayList<>(200);
    }


    public void setBounds(RectF screenBounds){
        bounds.left = screenBounds.left;
        bounds.top = screenBounds.top;
        bounds.right = screenBounds.right;
        bounds.bottom = screenBounds.bottom;
    }


    public void add(Projectile projectile){
        this.projectiles.add(projectile);
    }


    public List<Projectile> getProjectiles(){
        return projectiles;
    }


    public List<DrawInfo> update(){
        projectiles.forEach(Projectile::update);
        projectiles.removeIf(this::isScheduledForRemoval);
        projectiles.forEach(this::scheduleForRemovalIfOutOfBounds);
        return getChangedDrawInfoList(projectiles);
    }


    private boolean isScheduledForRemoval(Projectile projectile){
        return projectile.getDrawInfo().isScheduledForRemoval();
    }


    private void scheduleForRemovalIfOutOfBounds(Projectile projectile){
        if(hasProjectileExceededBounds(projectile)){
            projectile.getDrawInfo().scheduleForRemoval();
        }
    }


    public void removeProjectilesIfDestroyed(){
       projectiles.removeIf(p -> p.getDrawInfo().isDestroyed());
    }


    private boolean hasProjectileExceededBounds(Projectile projectile){
        float right = projectile.getX() + projectile.getWidth();
        float bottom = projectile.getY() + projectile.getHeight();
        return right < bounds.left
                || bottom < bounds.top
                || projectile.getX() > bounds.right
                || projectile.getY() > bounds.bottom;
    }

}
