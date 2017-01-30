package com.example.rinko.myepidroid;

import android.graphics.Bitmap;

/**
 * Created by Rinko on 31/01/2015.
 */
public class Trombi {

    private Bitmap Photo;
    private String Name;

    public Trombi(Bitmap photo, String name) {
        Photo = photo;
        Name = name;
    }

    public Bitmap getPhoto() {
        return Photo;
    }

    public void setPhoto(Bitmap photo) {
        Photo = photo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}

