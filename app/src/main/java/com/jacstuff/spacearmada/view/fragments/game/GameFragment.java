package com.jacstuff.spacearmada.view.fragments.game;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jacstuff.spacearmada.MainActivity;
import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.service.Game;
import com.jacstuff.spacearmada.service.GameService;
import com.jacstuff.spacearmada.view.TransparentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameFragment extends Fragment implements GameView {


    private int width, height;
    private ImageView shipView, enemyShip;
    private TextView textView;
    private Game game;
    private DpadControlView dpadControlView;
    private final List<View> starViews = new ArrayList<>();
    private TransparentView dpadView;
    private int containerWidth, containerHeight, smallestContainerDimension;
    private Map<Long, View> itemsMap;

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        log("Entered onAttach()");
        deriveScreenDimensions();
        Game game = getGame();
        if(game != null){
            game.setGameView(this);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(container != null){
            log("Entered onCreateView() container width: " +
                    container.getMeasuredWidth());
            containerWidth = container.getMeasuredWidth();
            containerHeight = container.getMeasuredHeight();
            smallestContainerDimension = Math.min(containerWidth, containerHeight);
        }
        View parentView = inflater.inflate(R.layout.fragment_game, container, false);
        deriveScreenDimensions();
        itemsMap = new HashMap<>();
        shipView = parentView.findViewById(R.id.shipView);
        enemyShip = parentView.findViewById(R.id.enemyShipView);
        dpadView = parentView.findViewById(R.id.dpadView);
        textView = parentView.findViewById(R.id.tempTextView);
        registerShipDimensions();
        addStarViewsTo((ViewGroup)parentView, 20);
        //initControls();
        return parentView;
    }


    private void registerShipDimensions(){
        game.adjustSizesBasedOn(smallestContainerDimension);
    }


    @Override
    public void onViewCreated(@NonNull View parentView, Bundle savedInstanceState) {
        initControls();
    }


    private void initControls(){
        if(dpadControlView == null) {
            dpadControlView = new DpadControlView(getContext(), dpadView, textView);
        }
        dpadControlView.initControls(game);
    }


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


    @Override
    public void updateStars(List<Point> starCoordinates){
        if(starCoordinates == null || starViews.size() != starCoordinates.size()){
            return;
        }
        runOnUiThread(()->{
            for(int i=0; i<starCoordinates.size(); i++){
                if(i >= starViews.size()){
                    return;
                };
                updateStar(starViews.get(i), starCoordinates.get(i));
            }
        });
    }


    @Override
    public void createItem(DrawInfo drawInfo) {
        View itemView = new View(getContext());
        itemView.setLayoutParams(new ViewGroup.LayoutParams(drawInfo.getWidth(),drawInfo.getHeight()));
        ViewGroup parentView = (ViewGroup) getView();
        if(parentView == null){
            return;
        }
        parentView.addView(itemView);
        itemView.setBackgroundColor(Color.WHITE);
        itemView.setX(drawInfo.getX());
        itemView.setY(drawInfo.getY());
        itemsMap.put(drawInfo.getId(), itemView);

    }


    private void updateStar(View starView, Point p){
        starView.setX(p.x);
        starView.setY(p.y);
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
    public void updateShipPosition(float x, float y){
       runOnUiThread(()->{
            shipView.setX(x);
            shipView.setY(y);
        } );
    }


    @Override
    public void setShipSize(int width, int height){
        runOnUiThread(()->{
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
            shipView.setLayoutParams(layoutParams);
        } );
    }


    private void runOnUiThread(Runnable runnable){
        if(getActivity() == null){
            return;
        }
        getActivity().runOnUiThread(runnable);
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