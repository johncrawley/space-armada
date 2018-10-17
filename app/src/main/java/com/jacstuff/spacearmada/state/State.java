package com.jacstuff.spacearmada.state;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.List;

import com.jacstuff.spacearmada.TouchPoint;

/**
 * Created by john on 11/04/18.
 * Represents a state that can be updated
 */

public interface State {

    void update();
    void draw(Canvas canvas, Paint paint);
    void handleTouchPoints(List<TouchPoint> touchPoints);
    void destroy();
    void onPause();
    void onResume();
}
