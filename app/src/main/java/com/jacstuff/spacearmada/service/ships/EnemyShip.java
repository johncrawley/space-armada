package com.jacstuff.spacearmada.service.ships;

import com.jacstuff.spacearmada.view.fragments.game.ItemType;

public class EnemyShip extends AbstractItem{


    private final int points;


    private EnemyShip(Builder builder){
        super(builder.id, ItemType.ENEMY_SHIP_1, builder.speed, builder.sizeFactor, builder.heightToWidthRatio);
        this.x = builder.initialX;
        this.y = builder.initialY;
        drawInfo.setDimensions((int)width, (int)height);
        this.points = builder.points;
    }


    public void update(){
        setY(y + speed);
    }


    public int getPoints(){
        return points;
    }


    public static class Builder{
        float initialX, initialY, sizeFactor, heightToWidthRatio;
        long id;
        int speed, points;


        public static Builder newInstance(){
            return new Builder();
        }


        private Builder(){}


        public Builder initialX(float initialX){
            this.initialX = initialX;
            return this;
        }


        public Builder initialY(float initialY){
            this.initialY = initialY;
            return this;
        }


        public Builder sizeFactor(float sizeFactor){
            this.sizeFactor = sizeFactor;
            return this;
        }


        public Builder heightToWidthRatio(float heightToWidthRatio){
            this.heightToWidthRatio = heightToWidthRatio;
            return this;
        }


        public Builder id(long id){
            this.id = id;
            return this;
        }


        public Builder speed(int speed){
            this.speed = speed;
            return this;
        }


        public Builder points(int points){
            this.points = points;
            return this;
        }



        public EnemyShip build(){
            return new EnemyShip(this);
        }

    }

}
