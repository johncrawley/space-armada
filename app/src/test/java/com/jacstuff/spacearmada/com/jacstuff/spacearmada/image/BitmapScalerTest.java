package com.jacstuff.spacearmada.com.jacstuff.spacearmada.image;

import android.content.Context;
import android.graphics.Bitmap;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

//@RunWith(MockitoJUnitRunner.class)
public class BitmapScalerTest {

   // private BitmapScaler bitmapScaler;
    private final int SCREEN_WIDTH = 400;
    private final int SCREEN_HEIGHT = 800;
    private Context context;

   // @Before
    public void setup(){

      //  InstrumentationRegistery instrumentationRegistery = new InstrumentationRegistery();


      //  bitmapScaler = new BitmapScaler(SCREEN_WIDTH, SCREEN_HEIGHT);

    }
    @Test
    public void blah(){
        assertTrue(true);
    }


   // @Test
    public void createdBitmapScalesToScreen(){
        int initialBmWidth = 100;
        int initialBmHeight = 200;

        Bitmap.Config conf = Bitmap.Config.ARGB_8888;

        Bitmap originalBitmap = Bitmap.createBitmap(initialBmWidth, initialBmHeight, conf);



        assertEquals(initialBmWidth, originalBitmap.getWidth());


    }



}
