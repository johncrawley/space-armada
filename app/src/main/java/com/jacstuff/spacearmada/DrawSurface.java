package com.jacstuff.spacearmada;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
//import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jacstuff.spacearmada.state.StateManager;

/**
 * Created by John on 29/08/2017.
 * The draw surface object intersects the canvas.
 */

public class DrawSurface extends SurfaceView implements SurfaceHolder.Callback {

    private Context context;
    private AnimationThread animationThread;
    protected SurfaceHolder surfaceHolder;
    private ScheduledExecutorService scheduledExecutorService;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private StateManager stateManager;
    private int width, height;

    public DrawSurface(Context context){
        super(context);
    }

    public DrawSurface(Context context, StateManager stateManager, int width, int height) {
        super(context);
        this.width = width;
        this.height = height;
        this.stateManager = stateManager;
        this.context = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        paint.setStyle(Paint.Style.FILL);
        scheduledExecutorService = Executors.newScheduledThreadPool(4);

    }



    public boolean onTouchEvent(MotionEvent event) {

        List<TouchPoint> touchPoints = new ArrayList<>();

        for (int i = 0; i < event.getPointerCount(); i++) {
            float x = event.getX(i);
            float y = event.getY(i);
            boolean isReleasedTouchPoint = isUpEvent(event) && event.getActionIndex() == i;
            touchPoints.add(new TouchPoint(x,y, isReleasedTouchPoint));
        }
        stateManager.handleTouchPoints(touchPoints);
        return true;
    }




    private boolean isUpEvent(MotionEvent event){
        return event.getActionMasked() == MotionEvent.ACTION_POINTER_UP || event.getAction() == MotionEvent.ACTION_UP ;
    }


    public void surfaceCreated(SurfaceHolder holder) {
        animationThread = new AnimationThread(context);

        scheduledExecutorService = Executors.newScheduledThreadPool(4);
        scheduledExecutorService.scheduleAtFixedRate(animationThread, 0, 16, TimeUnit.MILLISECONDS);
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) 	  {

        animationThread.setSurfaceSize(width, height);
    }


    public void surfaceDestroyed(SurfaceHolder holder) {
        scheduledExecutorService.shutdown();
    }



    class AnimationThread extends Thread{


        private final SurfaceHolder sh = surfaceHolder;
        Context ctx;

        AnimationThread(Context context){
            this.ctx = context;
        }

        public void run() {

                stateManager.update();

                Canvas canvas = null;
                try {
                    canvas = sh.lockCanvas(null);
                    synchronized (sh) {
                        if (canvas == null){
                            return;
                        }
                        paint.setColor(Color.BLACK);
                        canvas.drawRect(0,0,width, height, paint);
                         stateManager.draw(canvas, paint);
                    }
                } finally {
                    if (canvas != null) {
                        sh.unlockCanvasAndPost(canvas);
                    }
                }
        }

        private void setSurfaceSize(int width, int height) {
            synchronized (sh) {

            }
        }

    }


}
