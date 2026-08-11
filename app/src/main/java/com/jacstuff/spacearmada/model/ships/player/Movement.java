package com.jacstuff.spacearmada.model.ships.player;

public enum Movement {
    UP(-1), DOWN(1), LEFT(-1), RIGHT(1);
    final int value;

    Movement(int value){
        this.value = value;
    }

    int getValue(){
        return value;
    }

    boolean isIncreasing(){
        return value > 0;
    }
}