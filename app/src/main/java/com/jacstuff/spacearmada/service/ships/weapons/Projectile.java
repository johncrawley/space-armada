package com.jacstuff.spacearmada.service.ships.weapons;

import com.jacstuff.spacearmada.service.ships.UpdatableItem;
import com.jacstuff.spacearmada.view.fragments.game.ItemType;

public class Projectile extends UpdatableItem {

    public Projectile(long id, ItemType itemType, int speed, float sizeFactor, float heightWidthRatio){
        super(id, itemType, speed, sizeFactor, heightWidthRatio);
    }
}
