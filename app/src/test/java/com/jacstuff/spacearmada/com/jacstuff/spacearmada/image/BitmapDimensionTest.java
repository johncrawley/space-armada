package com.jacstuff.spacearmada.com.jacstuff.spacearmada.image;

import com.jacstuff.spacearmada.image.BitmapDimension;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class BitmapDimensionTest {

    @Test
    public void dimensionsCanBeStoredAndRetrieved(){
        int width = 100;
        int height = 200;
        BitmapDimension bitmapDimension = new BitmapDimension(width, height);
        assertEquals(width, bitmapDimension.getWidth());
        assertEquals(height, bitmapDimension.getHeight());

    }
}
