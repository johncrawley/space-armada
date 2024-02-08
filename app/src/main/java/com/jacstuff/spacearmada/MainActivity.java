package com.jacstuff.spacearmada;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.jacstuff.spacearmada.service.GameService;
import com.jacstuff.spacearmada.view.fragments.MainMenuFragment;

public class MainActivity extends AppCompatActivity {


  //  private DrawSurface drawSurface;
    private GameService gameService;
  //  private StateManager stateManager;
    private int width,height;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            log("Entered onServiceConnected()");
            GameService.LocalBinder binder = (GameService.LocalBinder) service;

            gameService = binder.getService();
            gameService.setActivity(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            log("Entered onServiceDisconnected()");
        }
    };


    private void log(String msg){
        System.out.println("^^^ MainActivity: " + msg);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deriveScreenDimensions();
        setupFragments();
        setupGameService();
    }


    public GameService getGameService(){
        return gameService;
    }


    private void setupFragments() {
        FragmentContainerView fragmentContainerView = findViewById(R.id.fragment_container);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new MainMenuFragment())
                .commit();
    }


    private void setupGameService(){
        Intent intent = new Intent(getApplicationContext(), GameService.class);
        getApplicationContext().startService(intent);
        getApplicationContext().bindService(intent, connection, 0);
    }


    protected void onDestroy(){
        //stateManager.destroy();
        super.onDestroy();
    }


    protected void onPause(){
        super.onPause();
        //stateManager.onPause();
    }


    protected void onResume(){
        super.onResume();
        //stateManager.onResume();
    }


    private void deriveScreenDimensions(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }


}
