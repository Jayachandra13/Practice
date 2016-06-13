package com.jc.practice.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by jcskh on 08-06-2016.
 */
public class MyContacts {
    private String name;
    private ArrayList<String> no;
    private String email;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getNo() {
        return no;
    }

    public void setNo(ArrayList<String> no) {
        this.no = no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
