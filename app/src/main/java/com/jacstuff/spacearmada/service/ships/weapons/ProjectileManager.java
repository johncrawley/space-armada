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
        log("Entered add()");
        this.projectiles.add(projectile);
    }


    private void log(String msg){
        System.out.println("^^^ ProjectileManager : " + msg);
    }


    public List<DrawInfo> update(){
        projectiles.forEach(Projectile::update);
        projectiles.removeIf(this::hasProjectileExceededBounds);
        return getChangedDrawInfoList(projectiles);
    }


    private boolean hasProjectileExceededBounds(Projectile projectile){
        return projectile.getX() < bounds.left
                || projectile.getY() + projectile.getHeight() < bounds.top
                || projectile.getX() + projectile.getWidth() > bounds.right
                || projectile.getY() > bounds.bottom;
    }

}
