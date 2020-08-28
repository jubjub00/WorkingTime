package com.example.workingtimewfh.img_slide;

import android.app.slice.SliceItem;
import android.graphics.Bitmap;

public class SliderItem {

    //private int image;
    private Bitmap image;
    /*public SliderItem(int image){
        this.image = image;
    }*/

    public SliderItem(Bitmap image){
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }
}
