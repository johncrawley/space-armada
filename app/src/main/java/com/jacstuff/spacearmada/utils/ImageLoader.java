package com.jacstuff.spacearmada.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

public class ImageLoader {

    private Context context;
    private int screenWidth, screenHeight;

    private Map<Integer, Drawable> drawableMap;

    public ImageLoader(){
        drawableMap = new HashMap<>();
    }

    public ImageLoader(Context context, int screenWidth, int screenHeight){
        this();
        this.context = context;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    //Drawable clone = drawable.getConstantState().newDrawable();
    public Drawable loadDrawable(int resId){

        if(drawableMap.containsKey(resId)){

            return drawableMap.get(resId);
        }
        Drawable drawable = loadDrawable(context,resId, screenWidth,screenHeight);
        drawableMap.put(resId,drawable);
        return drawable;
    }


    // used to load large images efficiently and avoiding out of memory exception
    public static Drawable loadDrawable(Context context, int resId, int screenWidth, int screenHeight){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);

        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;

        options.inSampleSize = calculateInSampleSize(options, imageWidth, imageHeight);
        options.inJustDecodeBounds = false;
        options.inInputShareable = true;
        options.inPurgeable = true;
        //options.inPreferredConfig = Bitmap.Config.ARGB_8888;


        Bitmap outputBitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);

        return new BitmapDrawable(context.getResources(), outputBitmap);
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

       return inSampleSize;
       // return 16;
    }

}
