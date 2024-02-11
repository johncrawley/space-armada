package com.jacstuff.spacearmada.view.fragments;

import static com.jacstuff.spacearmada.view.fragments.utils.ButtonUtils.setupButton;
import static com.jacstuff.spacearmada.view.fragments.utils.FragmentUtils.loadFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacstuff.spacearmada.R;
import com.jacstuff.spacearmada.view.fragments.game.GameFragment;
import com.jacstuff.spacearmada.view.fragments.utils.FragmentTag;


public class MainMenuFragment extends Fragment {


    public MainMenuFragment() {
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
        View parentView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        setupButtons(parentView);
        return parentView;
    }

    private void setupButtons(View parentView){
        setupButton(parentView, R.id.startGameButton, this::startGame);
    }


    private void startGame(){
        loadFragment(this, new GameFragment(), FragmentTag.GAME);
    }
}