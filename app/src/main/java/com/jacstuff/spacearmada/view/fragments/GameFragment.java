package com.jacstuff.spacearmada.view.fragments;

import static com.jacstuff.spacearmada.view.fragments.utils.ButtonUtils.setupButton;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jacstuff.spacearmada.MainActivity;
import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.service.Game;
import com.jacstuff.spacearmada.service.GameService;
import com.jacstuff.spacearmada.view.fragments.utils.FragmentUtils;


public class GameFragment extends Fragment {


    private int width, height;
    private ImageView shipView;
    private Game game;

    public enum BundleTag { SHIP_X, SHIP_Y};
    public enum MessageTag {UPDATE_SHIP}

    public GameFragment() {
        // Required empty public constructor
    }


    public static MainMenuFragment newInstance() {
        MainMenuFragment fragment = new MainMenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View parentView = inflater.inflate(R.layout.fragment_game, container, false);
        deriveScreenDimensions();
        setupButtons(parentView);
        shipView = parentView.findViewById(R.id.shipView);
        setupListeners();
        return parentView;
    }


    public void setupButtons(View parentView){
        setupButton(parentView, R.id.moveDownButton, this::moveDown);
        setupButton(parentView, R.id.moveUpButton, this::moveUp);
        setupButton(parentView, R.id.moveLeftButton, this::moveLeft);
        setupButton(parentView, R.id.moveRightButton, this::moveRight);
    }


    private void setupListeners(){
        FragmentUtils.setListener(this, MessageTag.UPDATE_SHIP, this::updateShip);
    }


    private void updateShip(Bundle b){
        int shipX = getBundleInt(b, BundleTag.SHIP_X);
        int shipY = getBundleInt(b, BundleTag.SHIP_Y);
        shipView.setX(shipX);
        shipView.setY(shipY);
    }


    private <E extends Enum<E>> int getBundleInt(Bundle bundle, E tag){
        return bundle.getInt(tag.toString(), 0);
    }


    private void moveDown(){
        log("entered moveDown()");
        Game game = getGame();
        if(game != null){
            log("game is not null, invoking game.moveDown()");
            game.moveDown();
        }
    }


    private void moveUp(){
        Game game = getGame();
        if(game != null){
            game.moveUp();
        }
    }


    private void moveLeft(){
        Game game = getGame();
        if(game != null){
            game.moveLeft();
        }
    }


    private void moveRight(){
        Game game = getGame();
        if(game != null){
            game.moveRight();
        }
    }


    private void log(String msg){
        System.out.println("^^^ Game: " + msg);
    }


    private Game getGame(){
        if(game != null){
            return game;
        }
        MainActivity mainActivity = (MainActivity) getActivity();
        if(mainActivity == null){
            log("getGame() main activity is null!");
            return null;
        }
        GameService gameService = mainActivity.getGameService();
        if(gameService == null){
            log("getGame() game service is null!");
            return null;
        }
        game = gameService.getGame();
        return gameService.getGame();
    }


    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        deriveScreenDimensions();
    }


    private void deriveScreenDimensions(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if(getActivity() == null){
            return;
        }
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }

}