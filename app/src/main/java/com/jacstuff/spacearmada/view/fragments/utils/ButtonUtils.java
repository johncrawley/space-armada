package com.jacstuff.spacearmada.view.fragments.utils;

import android.view.View;
import android.widget.Button;

public class ButtonUtils {

    public static void setupButton(View parentView, int buttonId, Runnable runnable){
        Button button = parentView.findViewById(buttonId);
        button.setOnClickListener((View v)->runnable.run());
    }
}
