package com.jacstuff.spacearmada.service.ships;

import android.graphics.RectF;

import com.jacstuff.spacearmada.view.fragments.game.DrawInfo;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {


    public static <E extends AbstractItem>  List<DrawInfo> getChangedDrawInfoList(List<E> items){
        return items.stream()
                .filter(AbstractItem::hasChanged)
                .map(e -> {
                    e.resetChangedStatus();
                    return e.getDrawInfo();
                })
                .collect(Collectors.toList());
    }


    public static int setSmallestDimension(RectF screenBounds){
        return (int)Math.min(screenBounds.bottom - screenBounds.top, screenBounds.right - screenBounds.left);
    }
}
