package com.jacstuff.spacearmada.view.fragments.game;

import static com.jacstuff.spacearmada.view.fragments.game.GameViewUtils.updateViewFrom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
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
    private ViewGroup controlPanel, energyLayout, topPane;
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

    private int energyLayoutWidth = 100;
    private int energyLayoutHeight = 50;
    private int topPaneHeight = 100;


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
        addStarViewsTo(20);
        setupListeners();
        setupEnergyLayout();
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


    private void setupListeners(){
        FragmentUtils.setListener(this, Message.CONNECT_TO_GAME, this::connectViewToGame);
    }


    private void assignViews(View parentView){
        controlPanel = parentView.findViewById(R.id.controlPanel);
        energyLayout = parentView.findViewById(R.id.energyLayout);
        gamePane = parentView.findViewById(R.id.gamePane);
        shipView = parentView.findViewById(R.id.shipView);
        dpadView = parentView.findViewById(R.id.dpadView);
        fireButtonView = parentView.findViewById(R.id.fireButtonView);
        topPane = parentView.findViewById(R.id.topPane);
    }


    private void setupEnergyLayout(){
        Game game = getGame();
        if(game == null){
            return;
        }
        energyLayout.removeAllViews();
        int numberOfHealthBars = game.getPlayerInitialHealth() / 12;
        int healthBarWidth = numberOfHealthBars / energyLayoutWidth;
        LinearLayout.LayoutParams healthBarLayoutParams = new LinearLayout.LayoutParams(healthBarWidth, energyLayoutHeight);
        healthBarLayoutParams.setMargins(5, 10, 5, 10);
        for(int i = 0; i<numberOfHealthBars; i++){
            View healthBar = new View(getContext());
            healthBar.setBackgroundColor(Color.GREEN);
            healthBar.setLayoutParams(healthBarLayoutParams);
            energyLayout.addView(healthBar);
        }
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
        topPaneHeight = containerHeight / 10;
        int minDpadHeight = 500;
        gamePaneHeight = Math.min(containerHeight - (minDpadHeight + topPaneHeight), (int)(containerWidth * gamePaneDimensionRatio));
        controlPanelWidth = containerWidth;
        controlPanelHeight = containerHeight - gamePaneHeight;
        dPadViewHeight = controlPanelHeight;
        fireButtonViewHeight = controlPanelHeight;

        dPadViewWidth = (controlPanelWidth / 3) * 2;
        fireButtonViewWidth = controlPanelWidth - dPadViewWidth;

        energyLayoutWidth = containerWidth / 2;

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
        dpadView.setLayoutParams(new LinearLayout.LayoutParams(dPadViewWidth, dPadViewHeight));
        fireButtonView.setLayoutParams(new LinearLayout.LayoutParams(fireButtonViewWidth, fireButtonViewHeight));
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


    private void updateStar(View starView, Point p){
        starView.setX(p.x);
        starView.setY(p.y);
    }


    @Override
    public void updateItems(List<DrawInfo> drawInfoList) {
        updateViewsFrom(drawInfoList, itemsMap, this::removeEnemyShip);
    }


    @Override
    public void updateProjectiles(List<DrawInfo> drawInfoList) {
        updateViewsFrom(drawInfoList, projectilesMap, this::removeProjectileViewIfOutOfBounds);
    }


    private void updateViewsFrom(List<DrawInfo> drawInfoList, Map<Long, ImageView> viewMap, BiConsumer<DrawInfo, ImageView> removalConsumer){
        runOnUiThread(()-> {
            for (DrawInfo drawInfo : drawInfoList) {
                updateViewFrom(drawInfo, viewMap, removalConsumer, getContext(), gamePane, itemTypeMap);
            }
        });
    }


    private void removeEnemyShip(DrawInfo drawInfo, ImageView view){
        long id = drawInfo.getId();
        removeIfOutOfBounds(view, id);
        removeIfDestroyed(drawInfo, view, id);
    }


    private void removeIfOutOfBounds(ImageView view, long id){
        if(view.getY() >= gamePane.getBottom()){
            gamePane.removeView(view);
            itemsMap.remove(id);
        }
    }


    private void removeIfDestroyed(DrawInfo drawInfo, ImageView view, long id){
        if(!drawInfo.isDestroyed()) {
            return;
        }
        view.setImageResource(R.drawable.enemy_ship_1_destruction);
        AnimationDrawable frameAnimation = (AnimationDrawable) view.getDrawable();
        frameAnimation.setOneShot(true);
        frameAnimation.start();
        new Handler().postDelayed(() -> removeImageview(view, id), 1100);
    }


    private void removeImageview(ImageView view, long id) {
        gamePane.removeView(view);
        itemsMap.remove(id);
    }


    private void removeProjectileViewIfOutOfBounds(DrawInfo drawInfo, ImageView view){
        if(drawInfo.isScheduledForRemoval() || drawInfo.isDestroyed()){
            gamePane.removeView(view);
            itemsMap.remove(drawInfo.getId());
        }
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