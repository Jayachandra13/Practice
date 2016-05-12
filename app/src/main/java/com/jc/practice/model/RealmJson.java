package com.jc.practice.model;

import org.json.JSONObject;

import io.realm.RealmObject;

/**
 * Created by FOTL on 4/18/2016.
 */
public class RealmJson extends RealmObject{
   private String jsonString;


    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}
