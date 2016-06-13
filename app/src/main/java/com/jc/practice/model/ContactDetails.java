package com.jc.practice.model;

import android.net.Uri;
import java.util.ArrayList;
public class ContactDetails {
    String name;
    ArrayList<String> no;
    ArrayList<String> email;
    Uri uri;

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

    public ArrayList<String> getEmail() {
        return email;
    }

    public void setEmail(ArrayList<String> email) {
        this.email = email;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
