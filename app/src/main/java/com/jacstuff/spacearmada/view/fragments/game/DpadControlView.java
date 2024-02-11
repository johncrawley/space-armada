package com.jacstuff.spacearmada.view.fragments.game;

import android.view.View;

import com.jacstuff.spacearmada.view.TransparentView;

public class DpadControlView{

    public DpadControlView(View parentView, int viewId){
        TransparentView dpadView = parentView.findViewById(viewId);
        int width = dpadView.getMeasuredWidth();
        int height = dpadView.getMeasuredHeight();


    }
}
