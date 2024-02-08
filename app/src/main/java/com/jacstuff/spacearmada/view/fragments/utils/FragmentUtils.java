package com.jacstuff.spacearmada.view.fragments.utils;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jacstuff.spacearmada.R;

import java.util.function.Consumer;

public class FragmentUtils {


    public static void showDialog(Fragment parentFragment, DialogFragment dialogFragment, FragmentTag tag, Bundle bundle){
        FragmentManager fragmentManager = parentFragment.getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        removePreviousFragmentTransaction(fragmentManager, tag.toString(), fragmentTransaction);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fragmentTransaction, tag.toString());
    }


    public static void loadFragment(Fragment parentFragment, Fragment fragment, FragmentTag tag, Bundle bundle){
        FragmentManager fragmentManager = parentFragment.getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        removePreviousFragmentTransaction(fragmentManager, tag.toString(), fragmentTransaction);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.pop_enter, R.anim.pop_exit )
                .replace(R.id.fragment_container, fragment, tag.toString())
                .addToBackStack(null)
                .commit();
    }


    public static void loadFragment(Fragment parentFragment, Fragment fragment, FragmentTag tag){
        loadFragment(parentFragment, fragment, tag, new Bundle());
    }


    public static void onBackButtonPressed(Fragment parentFragment, Runnable action){
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                action.run();
            }
        };
        parentFragment.requireActivity().getOnBackPressedDispatcher().addCallback(parentFragment.getViewLifecycleOwner(), callback);
    }


    public static void doNothingWhenBackButtonPressed(Fragment parentFragment){
        onBackButtonPressed(parentFragment, ()->{});
    }


    private static void removePreviousFragmentTransaction(FragmentManager fragmentManager, String tag, FragmentTransaction fragmentTransaction){
        Fragment prev = fragmentManager.findFragmentByTag(tag);
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        fragmentTransaction.addToBackStack(null);
    }


    public static void setListener(Fragment fragment, String key, Consumer<Bundle> consumer){
        fragment.getParentFragmentManager().setFragmentResultListener(key, fragment, (requestKey, bundle) -> consumer.accept(bundle));
    }

    public static <E extends Enum<E>> void setListener(Fragment fragment, E key, Consumer<Bundle> consumer){
        fragment.getParentFragmentManager().setFragmentResultListener(key.toString(), fragment, (requestKey, bundle) -> consumer.accept(bundle));
    }


    public static void sendMessage(Fragment fragment, String key){
        sendMessage(fragment, key, new Bundle());
    }


    public static void sendMessage(Fragment fragment, String key, Bundle bundle){
        fragment.getParentFragmentManager().setFragmentResult(key, bundle);
    }


    public static int getInt(Bundle bundle, Enum<?> tag){
        return bundle.getInt(tag.toString());
    }


    public static String getStr(Bundle bundle, Enum<?> tag){
        return bundle.getString(tag.toString());
    }


    public static boolean getBoolean(Bundle bundle, Enum<?> tag){
        return  bundle.getBoolean(tag.toString());
    }


}
