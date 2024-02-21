package com.jacstuff.spacearmada.view.fragments.game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

    private ImageView shipView;
    private ViewGroup gamePane;
    private Game game;
    private ViewGroup controlPanel;
    private DpadControlView dpadControlView;
    private final List<View> starViews = new ArrayList<>();
    private TransparentView dpadView;
    private int containerWidth, containerHeight, smallestContainerDimension;
    private Map<Long, ImageView> itemsMap;
    private Map<ItemType, Integer> itemTypeMap;
    private int gamePaneWidth, gamePaneHeight;
    private int controlPanelWidth, controlPanelHeight;
    private final int minDpadHeight = 500;
    private final int minGamePaneWidth = 500;
    private final int minLandscapeControlViewWidth = 300;
    private final float gamePaneDimensionRatio = 1.5f;

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
        Game game = getGame();
        if(game != null){
            game.setGameView(this);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getContainerDimensions(container);
        View parentView = inflater.inflate(R.layout.fragment_game, container, false);
        itemsMap = new HashMap<>();
        itemTypeMap = new HashMap<>();
        itemTypeMap.put(ItemType.ENEMY_SHIP_1, R.drawable.ship2);
        assignViews(parentView);
        assignViewDimensions();
        registerShipDimensions();
        addStarViewsTo(20);
        return parentView;
    }


    private void getContainerDimensions(ViewGroup container){
        if(container != null){
            containerWidth = container.getMeasuredWidth();
            containerHeight = container.getMeasuredHeight();
            smallestContainerDimension = Math.min(containerWidth, containerHeight);
        }
    }


    private void assignViews(View parentView){
        controlPanel = parentView.findViewById(R.id.controlPanel);
        gamePane = parentView.findViewById(R.id.gamePane);
        shipView = parentView.findViewById(R.id.shipView);
        dpadView = parentView.findViewById(R.id.dpadView);
    }


    private void assignViewDimensions(){
        if(containerHeight > containerWidth){
            setupDimensionVariablesForPortrait();
        }else{
          setupDimensionVariablesForLandscape();
        }
        gamePane.setLayoutParams(new LinearLayout.LayoutParams(Math.max(300, gamePaneWidth), Math.max(300, gamePaneHeight)));
        controlPanel.setLayoutParams(new LinearLayout.LayoutParams(controlPanelWidth, controlPanelHeight));
        setGameBounds();
    }


    private void setupDimensionVariablesForPortrait(){
        gamePaneWidth = containerWidth;
        gamePaneHeight = Math.min(containerHeight - minDpadHeight, (int)(containerWidth * gamePaneDimensionRatio));
        controlPanelWidth = containerWidth;
        controlPanelHeight = containerHeight - gamePaneHeight;
    }


    private void setupDimensionVariablesForLandscape(){
        gamePaneHeight = containerHeight;
        int maxGamePaneWidth = containerWidth - (minLandscapeControlViewWidth * 2);
        gamePaneWidth = Math.min(maxGamePaneWidth, (int)(gamePaneHeight / gamePaneDimensionRatio));
        controlPanelWidth = 300;
        controlPanelHeight = containerHeight;
    }


    private void setGameBounds(){
        if(game != null) {
            game.setBounds(gamePane.getX(), gamePane.getY(), gamePaneWidth, gamePaneHeight);
        }
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
            dpadControlView = new DpadControlView(getContext(), dpadView);
        }
        dpadControlView.initControls(game);
    }


    private void addStarViewsTo(int numberOfStars){
        for(int i = 0; i < numberOfStars; i++){
            addStarViewTo();
        }
    }


    private void addStarViewTo(){
        View starView = new View(getContext());
        starView.setLayoutParams(new ViewGroup.LayoutParams(2,2));
        gamePane.addView(starView);
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
    public void updateItems(List<DrawInfo> drawInfoList) {
        runOnUiThread(()-> {
            for (DrawInfo drawInfo : drawInfoList) {
                updateViewFrom(drawInfo);
            }
        });
    }


    private void updateViewFrom(DrawInfo drawInfo){
        long id = drawInfo.getId();
        if(!itemsMap.containsKey(id)){
            itemsMap.put(id, createItem(drawInfo));
        }
        ImageView view = itemsMap.get(id);
        if(view != null){
            view.setX(drawInfo.getX());
            view.setY(drawInfo.getY());
            removeIfOutOfBounds(id, view);
        }
    }


    private void removeIfOutOfBounds(long id, ImageView view){
        if(view.getY() >= gamePane.getBottom()){
            gamePane.removeView(view);
            itemsMap.remove(id);
        }
    }


    @Override
    public ImageView createItem(DrawInfo drawInfo) {
        ImageView itemView = createImageViewWithDimensionsFrom(drawInfo);
        gamePane.addView(itemView);
        setImage(itemView, drawInfo);
        setImageViewCoordinates(itemView, drawInfo);
        return itemView;
    }


    private ImageView createImageViewWithDimensionsFrom(DrawInfo drawInfo){
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(drawInfo.getWidth(), drawInfo.getHeight()));
        return imageView;
    }


    private void setImage(ImageView imageView, DrawInfo drawInfo){
        Integer imageResource = itemTypeMap.get(drawInfo.getItemType());
        if(imageResource != null){
            imageView.setImageResource(imageResource);
        }
    }


    private void setImageViewCoordinates(ImageView imageView, DrawInfo drawInfo){
        imageView.setY(drawInfo.getY());
        imageView.setX(drawInfo.getX());
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
            return null;
        }
        GameService gameService = mainActivity.getGameService();
        if(gameService == null){
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

}