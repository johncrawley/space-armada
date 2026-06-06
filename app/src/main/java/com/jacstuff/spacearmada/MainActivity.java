package com.jacstuff.spacearmada;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.jacstuff.spacearmada.service.GameService;
import com.jacstuff.spacearmada.view.fragments.MainMenuFragment;
import com.jacstuff.spacearmada.view.fragments.game.GameFragment;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {


    private void log(String msg){
        System.out.println("^^^ MainActivity: " + msg);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);
        setupInsets();
        setupFragments();
    }


    private void setupInsets(){
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    protected void onDestroy(){
        //stateManager.destroy();
        log("Entered onDestroy()");
        super.onDestroy();
    }


    protected void onPause(){
        super.onPause();
        //stateManager.onPause();
    }


    private void sendMessageToAttachFragmentToGame(){
        sendMessage(GameFragment.Message.CONNECT_TO_GAME);
    }


    public GameService getGameService(){
        return null;
    }


    private void setupFragments() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new MainMenuFragment())
                .commit();
    }


    public <E extends Enum<E>> void sendMessage(E operationName) {
        getSupportFragmentManager().setFragmentResult(operationName.toString(), new Bundle());
    }
}
