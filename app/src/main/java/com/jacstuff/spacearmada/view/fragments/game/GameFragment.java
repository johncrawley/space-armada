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
    private ImageView shipView;
    private TextView textView;
    private View gamePane;
    private Game game;
    private DpadControlView dpadControlView;
    private final List<View> starViews = new ArrayList<>();
    private TransparentView dpadView;
    private int containerWidth, containerHeight, smallestContainerDimension;
    private Map<Long, ImageView> itemsMap;
    private Map<ItemType, Integer> itemTypeMap;

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

    ViewGroup parentView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        itemTypeMap = new HashMap<>();
        itemTypeMap.put(ItemType.ENEMY_SHIP_1, R.drawable.ship2);
        gamePane = parentView.findViewById(R.id.gamePane);
        shipView = parentView.findViewById(R.id.shipView);
        dpadView = parentView.findViewById(R.id.dpadView);
        textView = parentView.findViewById(R.id.tempTextView);
        registerShipDimensions();
        addStarViewsTo((ViewGroup)parentView, 20);
        this.parentView = (ViewGroup)parentView;
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
        for(int i = 0; i < numberOfStars; i++){
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
            removeOutOfBoundsItems(id, view);
        }
    }


    private void removeOutOfBoundsItems(long id, ImageView view){
        if(view.getY() > gamePane.getBottom()){
            parentView.removeView(view);
            itemsMap.remove(id);
        }
    }


    @Override
    public ImageView createItem(DrawInfo drawInfo) {
        ImageView itemView = createImageViewWithDimensionsFrom(drawInfo);
        if(parentView == null){
            return itemView;
        }
        parentView.addView(itemView);
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