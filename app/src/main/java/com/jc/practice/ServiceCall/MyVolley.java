package com.jc.practice.ServiceCall;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jc.practice.listener.MyVolleyCallBack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by jcskh on 27-04-2016.
 */
public class MyVolley {
    Context context;
    MyVolleyCallBack myVolleyCallBack;

    public MyVolley(Context context, MyVolleyCallBack myVolleyCallBack) {
        this.context = context;
        this.myVolleyCallBack=myVolleyCallBack;
    }

    public void getData(String url){
        Log.v("getData",""+url);
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("response",""+response);
                myVolleyCallBack.onSuccessCallBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error",""+error);
                myVolleyCallBack.onErrorCallBack(error.getMessage());
            }
        });
        requestQueue.add(stringRequest);
    }
    public void uploadImage(String url, final String filePath){
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return super.getBody();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
    }
    public void postData(String url, final Objects objects){
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                myVolleyCallBack.onSuccessCallBack(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                myVolleyCallBack.onErrorCallBack(error.getMessage());
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {

                return new Gson().toJson(objects).getBytes();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("fName","Jayachandra");
                params.put("lName","Jc");
                params.put("age","23");
                return super.getParams();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
