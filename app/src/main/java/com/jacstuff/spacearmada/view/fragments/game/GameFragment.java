package com.jacstuff.spacearmada.view.fragments.game;

import static com.jacstuff.spacearmada.view.fragments.utils.ButtonUtils.setupButton;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
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
import com.jacstuff.spacearmada.view.fragments.MainMenuFragment;
import com.jacstuff.spacearmada.view.fragments.utils.FragmentUtils;

import java.util.ArrayList;
import java.util.List;


public class GameFragment extends Fragment implements GameView {


    private int width, height;
    private ImageView shipView, enemyShip;
    private Game game;
    private DpadControlView dpadControlView;

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
    public void updateStars(List<Point> starCoordinates){
        if(getActivity() == null){
            return;
        }
        if(starCoordinates == null){
            log("updateStars() input coordinates are null!");
            return;
        }
        if(starViews == null || starViews.size() != starCoordinates.size()){
            return;
        }
        for(int i=0; i<starCoordinates.size(); i++){
            View starView = starViews.get(i);
            Point p = starCoordinates.get(i);
            getActivity().runOnUiThread(()->{
                starView.setX(p.x);
                starView.setY(p.y);
            } );
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View parentView = inflater.inflate(R.layout.fragment_game, container, false);
        deriveScreenDimensions();
        setupButtons(parentView);
        shipView = parentView.findViewById(R.id.shipView);
        enemyShip = parentView.findViewById(R.id.enemyShipView);
        addStarViewsTo((ViewGroup)parentView, 20);
        setupListeners();
        return parentView;
    }

    private List<View> starViews = new ArrayList<>();


    private void addStarViewsTo(ViewGroup parentView, int numberOfStars){
        for(int i=0; i< numberOfStars;i++){
            addStarViewTo(parentView);
        }
    }


    private void addStarViewTo(ViewGroup parentView){
        View starView = new View(getContext());
        starView.setLayoutParams(new ViewGroup.LayoutParams(2,2));
        parentView.addView(starView);
        starView.setBackgroundColor(Color.LTGRAY);
        starViews.add(starView);
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
        Game game = getGame();
        View parentView = getView();
        if(parentView == null){
            return;
        }
        dpadControlView = new DpadControlView(getContext(), game, parentView, R.id.dpadView);
        if(game != null){
           game.setGameView(this);
        }
    }


    @Override
    public void updateShip(int x, int y){

    }


    @Override
    public void updateEnemyShip(int x, int y){
        if(getActivity() == null){
            return;
        }
        getActivity().runOnUiThread(()->{
            enemyShip.setX(x);
            enemyShip.setY(y);
        } );
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