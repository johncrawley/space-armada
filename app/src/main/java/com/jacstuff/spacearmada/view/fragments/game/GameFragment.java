package com.jacstuff.spacearmada.view.fragments.game;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
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
import com.jacstuff.spacearmada.view.fragments.utils.FragmentUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;


public class GameFragment extends Fragment implements GameView {

    private ImageView shipView;
    private ViewGroup gamePane;
    private Game game;
    private ViewGroup controlPanel;
    private DpadControlView dpadControlView;
    private FireButtonControlView fireButtonControlView;
    private final List<View> starViews = new ArrayList<>();
    private TransparentView dpadView, fireButtonView;
    private int containerWidth, containerHeight, smallestContainerDimension;
    private Map<Long, ImageView> itemsMap;
    private Map<Long, ImageView> projectilesMap;
    private Map<ItemType, Integer> itemTypeMap;
    private int gamePaneWidth, gamePaneHeight;
    private int controlPanelWidth, controlPanelHeight;
    private final float gamePaneDimensionRatio = 1.5f;
    public enum Message { CONNECT_TO_GAME }
    private int dPadViewWidth, dPadViewHeight, fireButtonViewWidth, fireButtonViewHeight;

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
        connectViewToGame();
    }


    private void connectViewToGame(Bundle bundle){
        connectViewToGame();
    }


    private void connectViewToGame(){
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
        projectilesMap = new HashMap<>();
        itemTypeMap = new HashMap<>();
        itemTypeMap.put(ItemType.ENEMY_SHIP_1, R.drawable.ship2);
        itemTypeMap.put(ItemType.PLAYER_BULLET, R.drawable.bullet1);
        assignViews(parentView);
        assignViewDimensions();
        registerShipDimensions();
        addStarViewsTo(20);
        setupListeners();
        return parentView;
    }


    @Override
    public void onViewCreated(@NonNull View parentView, Bundle savedInstanceState) {
        initControls();
    }


    private void getContainerDimensions(ViewGroup container){
        if(container != null){
            containerWidth = container.getMeasuredWidth();
            containerHeight = container.getMeasuredHeight();
            smallestContainerDimension = Math.min(containerWidth, containerHeight);
        }
    }


    private void setupListeners(){
        FragmentUtils.setListener(this, Message.CONNECT_TO_GAME, this::connectViewToGame);
    }


    private void assignViews(View parentView){
        controlPanel = parentView.findViewById(R.id.controlPanel);
        gamePane = parentView.findViewById(R.id.gamePane);
        shipView = parentView.findViewById(R.id.shipView);
        dpadView = parentView.findViewById(R.id.dpadView);
        fireButtonView = parentView.findViewById(R.id.fireButtonView);
    }


    private void assignViewDimensions(){
        if(containerHeight > containerWidth){
            setupDimensionVariablesForPortrait();
        }else{
          setupDimensionVariablesForLandscape();
        }
        gamePane.setLayoutParams(new LinearLayout.LayoutParams(Math.max(300, gamePaneWidth), Math.max(300, gamePaneHeight)));
        controlPanel.setLayoutParams(new LinearLayout.LayoutParams(controlPanelWidth, controlPanelHeight));
        dpadView.setLayoutParams(new LinearLayout.LayoutParams(dPadViewWidth, dPadViewHeight));
        fireButtonView.setLayoutParams(new LinearLayout.LayoutParams(fireButtonViewWidth, fireButtonViewHeight));
        setGameBounds();
    }


    private void setupDimensionVariablesForPortrait(){
        gamePaneWidth = containerWidth;
        int minDpadHeight = 500;
        gamePaneHeight = Math.min(containerHeight - minDpadHeight, (int)(containerWidth * gamePaneDimensionRatio));
        controlPanelWidth = containerWidth;
        controlPanelHeight = containerHeight - gamePaneHeight;
        dPadViewHeight = controlPanelHeight;
        fireButtonViewHeight = controlPanelHeight;

        dPadViewWidth = (controlPanelWidth / 3) * 2;
        fireButtonViewWidth = controlPanelWidth - dPadViewWidth;

    }


    private void setupDimensionVariablesForLandscape(){
        gamePaneHeight = containerHeight;
        int minLandscapeControlViewWidth = 300;
        int maxGamePaneWidth = containerWidth - (minLandscapeControlViewWidth * 2);
        gamePaneWidth = Math.min(maxGamePaneWidth, (int)(gamePaneHeight / gamePaneDimensionRatio));
        controlPanelWidth = 300;
        controlPanelHeight = containerHeight;
    }


    private void setGameBounds(){
        if(game != null) {
            game.setBounds(gamePane.getX(), gamePane.getY(), gamePaneWidth, gamePaneHeight);
            initGamePaneRect();
        }
    }


    private void initGamePaneRect(){
        Rect gamePaneRect = new Rect();
        gamePaneRect.left = (int)gamePane.getX();
        gamePaneRect.top = (int)gamePane.getY();
        gamePaneRect.right = (int)gamePane.getX() + gamePaneWidth;
        gamePaneRect.bottom = (int)gamePane.getY() + gamePaneHeight;
    }


    private void registerShipDimensions(){
        if(game != null){
            game.adjustSizesBasedOn(smallestContainerDimension);
         }
    }


    private void initControls(){
        if(dpadControlView == null) {
            dpadControlView = new DpadControlView(getContext(), dpadView);
        }
        dpadControlView.initControls(game, dPadViewWidth, dPadViewHeight);
        if(fireButtonControlView == null){
            fireButtonControlView = new FireButtonControlView(getContext(), fireButtonView);
        }
        fireButtonControlView.init(game, fireButtonViewWidth, fireButtonViewHeight);
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
        updateViewsFrom(drawInfoList, itemsMap, this::removeIfOutOfBounds);
    }


    @Override
    public void updateProjectiles(List<DrawInfo> drawInfoList) {
        updateViewsFrom(drawInfoList, projectilesMap, this::removeProjectileViewIfOutOfBounds);
    }


    private void updateViewsFrom(List<DrawInfo> drawInfoList, Map<Long, ImageView> viewMap, BiConsumer<DrawInfo, ImageView> removalConsumer){
        runOnUiThread(()-> {
            for (DrawInfo drawInfo : drawInfoList) {
                updateViewFrom(drawInfo, viewMap, removalConsumer);
            }
        });
    }


    private void updateViewFrom(DrawInfo drawInfo, Map<Long, ImageView> map, BiConsumer<DrawInfo, ImageView> removalConsumer){
        long id = drawInfo.getId();
        if(map == null){
            return;
        }
        if(!map.containsKey(id)){
            ImageView item = createItemAndAddToGamePane(drawInfo);
            map.put(id, item);
        }
        ImageView view = map.get(id);
        if(view != null){
            view.setX(drawInfo.getX());
            view.setY(drawInfo.getY());
            view.setRotation(drawInfo.getRotation());
            drawInfo.incrementRotation(5);
            removalConsumer.accept(drawInfo, view);
        }
    }


    private void removeIfOutOfBounds(DrawInfo drawInfo, ImageView view){
        long id = drawInfo.getId();
        if(view.getY() >= gamePane.getBottom()){
            gamePane.removeView(view);
            itemsMap.remove(id);
        }
    }


    private void removeProjectileViewIfOutOfBounds(DrawInfo drawInfo, ImageView view){
        if(drawInfo.isScheduledForRemoval()){
            gamePane.removeView(view);
            itemsMap.remove(drawInfo.getId());
        }
    }


    public ImageView createItemAndAddToGamePane(DrawInfo drawInfo) {
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
        System.out.println("^^^ GameFragment: " + msg);
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