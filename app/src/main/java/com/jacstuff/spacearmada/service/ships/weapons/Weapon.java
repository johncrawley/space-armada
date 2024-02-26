package com.jacstuff.spacearmada.service.ships.weapons;

import static com.jacstuff.spacearmada.service.ships.Utils.setSmallestDimension;

import android.graphics.PointF;
import android.graphics.RectF;

import com.jacstuff.spacearmada.service.ships.AbstractItem;
import com.jacstuff.spacearmada.service.ships.navigation.LinearNavigationPath;
import com.jacstuff.spacearmada.view.fragments.game.ItemType;

import java.util.ArrayList;
import java.util.List;

public class Weapon {

    private final ItemType projectileType;
    private final int speed;
    private boolean isFiring;
    private final int rate;
    private final float sizeFactor;
    private int reloadCounter;
    private final List<PointF> barrelOffsets;
    private final ProjectileManager projectileManager;
    private final float heightWidthRatio;
    private final AbstractItem owner;
    private int pixelsTravelledPerFrame;
    private int smallestScreenDimension;


    private Weapon(Builder builder){
        this.projectileType = builder.projectileType;
        this.speed = builder.speed;
        this.sizeFactor = builder.sizeFactor;
        this.rate = builder.rate;
        this.projectileManager = builder.projectileManager;
        this.owner = builder.owner;
        this.heightWidthRatio = builder.heightWidthRatio;
        setBounds(builder.screenBounds);
        this.barrelOffsets = new ArrayList<>();
    }

    public void addBarrel(int xOffsetPercentage, int yOffsetPercentage){
        float offsetX = owner.getWidth() / 200f * xOffsetPercentage;
        float offsetY = owner.getHeight() / 200f * yOffsetPercentage;
        barrelOffsets.add(new PointF(offsetX, offsetY));
    }


    public void update(){
        if(!isFiring){
            return;
        }
        reloadCounter++;
        if(reloadCounter >= rate){
            reloadCounter = 0;
            deployProjectiles();
        }
    }


    public void setBounds(RectF bounds){
        smallestScreenDimension = setSmallestDimension(bounds);
        setPixelsTravelledPerFrame();
    }


    public void startFiring(){
        reloadCounter = rate - 1;
        isFiring = true;
    }


    private void setPixelsTravelledPerFrame(){
        pixelsTravelledPerFrame = (smallestScreenDimension / 1000 ) * speed;
    }


    public void stopFiring(){
        isFiring = false;
    }


    private void deployProjectiles(){
        for(PointF offset : barrelOffsets){
            Projectile projectile = new Projectile(createId(), projectileType, speed, sizeFactor, heightWidthRatio );
            projectile.setX(owner.getX() + offset.x);
            projectile.setY(owner.getY() + offset.y);
            projectile.setSizeBasedOn(smallestScreenDimension);
            projectile.setPath(new LinearNavigationPath(owner.getX(), owner.getY(), pixelsTravelledPerFrame, true));
            projectileManager.add(projectile);
        }
    }


    private long createId(){
        return System.nanoTime();
    }


    public static class Builder {

        private ItemType projectileType;
        private int speed;
        private float sizeFactor;
        private int rate;
        private ProjectileManager projectileManager;
        private AbstractItem owner;
        private float heightWidthRatio;
        private RectF screenBounds;


        public static Builder newInstance() {
            return new Builder();
        }


        private Builder() {}


        public Builder setProjectileType(ItemType itemType) {
            this.projectileType = itemType;
            return this;
        }


        public Builder setSpeed(int speed) {
            this.speed = speed;
            return this;
        }



        public Builder setBounds(RectF bounds) {
            this.screenBounds = bounds;
            return this;
        }


        public Builder setSizeFactor(float sizeFactor) {
            this.sizeFactor = sizeFactor;
            return this;
        }


        public Builder setProjectileManager(ProjectileManager pm) {
            this.projectileManager = pm;
            return this;
        }


        public Builder setRate(int rate){
            this.rate = rate;
            return this;
        }


        public Builder setHeightWidthRatio(float heightWidthRatio){
            this.heightWidthRatio = heightWidthRatio;
            return this;
        }


        public Builder setOwner(AbstractItem owner){
            this.owner = owner;
            return this;
        }


        public Weapon build() {
            return new Weapon(this);
        }
    }

}
