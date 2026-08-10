package com.jacstuff.spacearmada.view.fragments.game;

import static com.jacstuff.spacearmada.view.fragments.game.GameViewUtils.updateViewFrom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jacstuff.spacearmada.MainViewModel;
import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.service.Game;
import com.jacstuff.spacearmada.view.TransparentView;
import com.jacstuff.spacearmada.view.fragments.game.controls.DpadControlView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;


public class GameFragment extends Fragment implements GameView {

    private ImageView shipView;
    private ViewGroup gamePane;
    private Game game;
    private ViewGroup controlPanel, energyLayout, topPane;
    private DpadControlView dpadControlView;
    private final List<View> starViews = new ArrayList<>();
    private TransparentView dpadView;
    private int dPadViewWidth, dPadViewHeight;
    private int containerWidth, containerHeight, smallestContainerDimension;
    private Map<Long, ImageView> itemsMap;
    private Map<Long, ImageView> projectilesMap;
    private Map<ItemType, Integer> itemTypeMap;
    private int gamePaneWidth, gamePaneHeight;
    private int controlPanelWidth, controlPanelHeight;
    private final float gamePaneDimensionRatio = 1.5f;

    private int energyLayoutWidth = 100;
    private int topPaneHeight = 100;
    private List<View> healthBarViews;
    private boolean shouldStarsBeUpdated = false;


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
        setupViewModelAndGame();
        assignViews(parentView);
        assignViewDimensions();
        initControls(parentView);
        setupDpadView(parentView);
        addStarViewsTo();
        setupEnergyLayout();
        return parentView;
    }


    private void setupViewModelAndGame(){
        MainViewModel viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        game = viewModel.game;
        game.setGameView(this);
    }


    @Override
    public void onViewCreated(@NonNull View parentView, Bundle savedInstanceState) {
        initControls(parentView);
    }


    private void getContainerDimensions(ViewGroup container){
        if(container != null){
            containerWidth = container.getMeasuredWidth();
            containerHeight = container.getMeasuredHeight();
            smallestContainerDimension = Math.min(containerWidth, containerHeight);
        }
    }


    @Override
    public void onGameOver(){
        destroySpaceShip();
        showGameOverText();
        enableClickToHighScoreScreen();
        moveToHighScoreScreenAfterPause();
    }


    private void destroySpaceShip(){


    }


    private void showGameOverText(){


    }


    private void enableClickToHighScoreScreen(){


    }


    private void moveToHighScoreScreenAfterPause(){


    }



    private void assignViews(View parentView){
        controlPanel = parentView.findViewById(R.id.controlPanel);
        energyLayout = parentView.findViewById(R.id.energyLayout);
        gamePane = parentView.findViewById(R.id.gamePane);
        shipView = parentView.findViewById(R.id.shipView);
        topPane = parentView.findViewById(R.id.topPane);
    }


    private void setupEnergyLayout(){
        energyLayout.removeAllViews();
        int numberOfHealthBars = getNumberOfHealthBarsFor(game.getPlayerInitialHealth());
        healthBarViews = new ArrayList<>(numberOfHealthBars);
        int healthBarMargin = 2;
        int healthBarWidth = (energyLayoutWidth - (numberOfHealthBars * 2 * healthBarMargin)) / numberOfHealthBars;
        int energyLayoutHeight = 50;
        var layoutParams = new LinearLayout.LayoutParams(healthBarWidth, energyLayoutHeight);
        layoutParams.setMargins(healthBarMargin, 10, healthBarMargin, 10);
        for(int i = 0; i<numberOfHealthBars; i++){
            addHealthBar(layoutParams);
        }

    }


    private void addHealthBar(LinearLayout.LayoutParams healthBarLayoutParams){
        var healthBar = new View(getContext());
        healthBar.setBackgroundColor(Color.GREEN);
        healthBar.setLayoutParams(healthBarLayoutParams);
        energyLayout.addView(healthBar);
        healthBarViews.add(healthBar);
    }


    @Override
    public void updateShipHealth(int remainingHealth){
        int numberOfRemainingHealthBars = getNumberOfHealthBarsFor(remainingHealth);
        for(int i = 0; i < healthBarViews.size(); i++){
            int visibility = i < numberOfRemainingHealthBars ? View.VISIBLE : View.INVISIBLE;
            healthBarViews.get(i).setVisibility(visibility);
        }
    }


    private int getNumberOfHealthBarsFor(int remainingHealth){
        return remainingHealth / 20;
    }


    private void assignViewDimensions(){
        if(containerHeight > containerWidth){
            setupDimensionVariablesForPortrait();
        }else{
          setupDimensionVariablesForLandscape();
        }
        assignLayoutParamsToViews();
        setGameBounds();
    }


    private void setupDimensionVariablesForPortrait(){
        gamePaneWidth = containerWidth;
        topPaneHeight = containerHeight / 15;
        int minDpadHeight = 500;
        gamePaneHeight = Math.min(containerHeight - (minDpadHeight + topPaneHeight), (int)(containerWidth * gamePaneDimensionRatio));
        controlPanelWidth = containerWidth;
        controlPanelHeight = containerHeight - (gamePaneHeight + topPaneHeight);
        dPadViewHeight = controlPanelHeight;
        dPadViewWidth = (controlPanelWidth / 3) * 2;

        energyLayoutWidth = containerWidth / 2;
    }


    private void setupDpadView(View parent){
        dpadView = parent.findViewById(R.id.dpadView);
        dpadView.setLayoutParams(new LinearLayout.LayoutParams(dPadViewWidth, dPadViewHeight));
        if(dpadControlView == null) {
            dpadControlView = new DpadControlView(getContext(), dpadView, game);
        }
        dpadControlView.initControls(dPadViewWidth, dPadViewHeight);
    }


    private void setupDimensionVariablesForLandscape(){
        gamePaneHeight = containerHeight;
        int minLandscapeControlViewWidth = 300;
        int maxGamePaneWidth = containerWidth - (minLandscapeControlViewWidth * 2);
        gamePaneWidth = Math.min(maxGamePaneWidth, (int)(gamePaneHeight / gamePaneDimensionRatio));
        controlPanelWidth = 300;
        controlPanelHeight = containerHeight;
    }


    private void assignLayoutParamsToViews(){
        gamePane.setLayoutParams(new LinearLayout.LayoutParams(Math.max(300, gamePaneWidth), Math.max(300, gamePaneHeight)));
        controlPanel.setLayoutParams(new LinearLayout.LayoutParams(controlPanelWidth, controlPanelHeight));
        topPane.setLayoutParams(new LinearLayout.LayoutParams(gamePaneWidth, topPaneHeight));
        energyLayout.setLayoutParams(new LinearLayout.LayoutParams(energyLayoutWidth, topPaneHeight));
    }


    private void setGameBounds(){
        if(game != null) {
            game.setBounds(getGamePaneRect(), smallestContainerDimension);
        }
    }


    private Rect getGamePaneRect(){
        Rect gamePaneRect = new Rect();
        gamePaneRect.left = (int)gamePane.getX();
        gamePaneRect.top = (int)gamePane.getY();
        gamePaneRect.right = (int)gamePane.getX() + gamePaneWidth;
        gamePaneRect.bottom = (int)gamePane.getY() + gamePaneHeight;
        return gamePaneRect;
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initControls(View parent){

        Button fireButton = parent.findViewById(R.id.fireButtonView);
        fireButton.setOnTouchListener((view, motionEvent) -> {
            var action = motionEvent.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                game.fire();
            } else if (action == MotionEvent.ACTION_UP) {
                game.releaseFire();
            }
            return false;
        });
    }


    private void addStarViewsTo(){
        for(int i = 0; i < 60; i++){
            addStarViewTo();
        }
    }


    private void addStarViewTo(){
        var starView = new View(getContext());
        starView.setLayoutParams(new ViewGroup.LayoutParams(2,2));
        gamePane.addView(starView);
        starView.setBackgroundColor(Color.LTGRAY);
        starViews.add(starView);
    }


    @Override
    public void updateStars(List<Point> starCoordinates){
        shouldStarsBeUpdated = !shouldStarsBeUpdated;
        if(starCoordinates == null
                || starViews.size() != starCoordinates.size()
                || !shouldStarsBeUpdated){
            return;
        }
        runOnUiThread(()->{
            for(int i = 0; i < starCoordinates.size(); i++){
                if(i < starViews.size()){
                    updateStar(starViews.get(i), starCoordinates.get(i));
                }
            }
        });
    }


    private void updateStar(View starView, Point p){
        starView.setX(p.x);
        starView.setY(p.y);
    }


    @Override
    public void updateItems(List<DrawInfoOLD> drawInfoList) {
        updateViewsFrom(drawInfoList, itemsMap, this::removeEnemyShip);
    }


    @Override
    public void updateProjectiles(List<DrawInfoOLD> drawInfoList) {
        updateViewsFrom(drawInfoList, projectilesMap, this::removeProjectileViewIfOutOfBounds);
    }


    private void updateViewsFrom(List<DrawInfoOLD> drawInfoList, Map<Long, ImageView> viewMap, BiConsumer<DrawInfoOLD, ImageView> removalConsumer){
        runOnUiThread(()-> {
            for (DrawInfoOLD drawInfo : drawInfoList) {
                updateViewFrom(drawInfo, viewMap, removalConsumer, getContext(), gamePane, itemTypeMap);
            }
        });
    }


    private void removeEnemyShip(DrawInfoOLD drawInfo, ImageView view){
        long id = drawInfo.getId();
        removeIfOutOfBounds(view, drawInfo, id);
        removeIfDestroyed(drawInfo, view, id);
    }


    private void removeIfOutOfBounds(ImageView view, DrawInfoOLD drawInfo, long id){
        if(drawInfo.isOutOfBounds()){
            gamePane.removeView(view);
            itemsMap.remove(id);
        }
    }


    private void removeIfDestroyed(DrawInfoOLD drawInfo, ImageView view, long id){
        if(!drawInfo.isDestroyed()) {
            return;
        }
        view.setImageResource(R.drawable.enemy_ship_1_destruction);
        var frameAnimation = (AnimationDrawable) view.getDrawable();
        frameAnimation.setOneShot(true);
        frameAnimation.start();
        new Handler(Looper.getMainLooper()).postDelayed(() -> removeImageview(view, id), 1100);
    }


    private void removeImageview(ImageView view, long id) {
        gamePane.removeView(view);
        itemsMap.remove(id);
    }


    private void removeProjectileViewIfOutOfBounds(DrawInfoOLD drawInfo, ImageView view){
        if(drawInfo.isScheduledForRemoval() || drawInfo.isDestroyed()){
            gamePane.removeView(view);
            itemsMap.remove(drawInfo.getId());
        }
    }


    private void log(String msg){
        System.out.println("^^^ GameFragment: " + msg);
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