package com.jacstuff.spacearmada;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jacstuff.spacearmada.state.StateManager;

public class MainActivity extends Activity {


    private DrawSurface drawSurface;
    private StateManager stateManager;
    private int width,height;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deriveScreenDimensions();
        stateManager = new StateManager(this, width, height);
        drawSurface = new DrawSurface(this, stateManager, width, height);

        setContentView(drawSurface);
    }

    protected void onDestroy(){
        stateManager.destroy();
        super.onDestroy();

    }


    protected void onPause(){
        super.onPause();
        stateManager.onPause();
    }

    protected void onResume(){
        super.onResume();
        stateManager.onResume();
    }

    private void deriveScreenDimensions(){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        Log.i("MainActivity", "derived dimensions : width : " + width + " height: " + height);
    }







    class CanvasThread extends Thread{
        private SurfaceHolder surfaceHolder;
        boolean isRunning = false;
        // private Panel panel;
        SurfaceView panel; //= (SurfaceView)findViewById(R.id.surfaceView);
        public CanvasThread(SurfaceHolder surfaceHolder, SurfaceView surfaceView){
            this.surfaceHolder = surfaceHolder;
            this.panel = surfaceView;

        }
        public void run(){
            Canvas canvas;
            while(isRunning){

                canvas = null;
                // try{
                canvas = surfaceHolder.lockCanvas(null);
                synchronized(surfaceHolder){
                    // panel.onDraw(canvas);
                }
                // }catch()

            }

        }
    }
}
