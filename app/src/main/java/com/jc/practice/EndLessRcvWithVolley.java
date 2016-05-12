package com.jc.practice;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.practice.ServiceCall.MyVolley;
import com.jc.practice.adapter.MyRcvAdapterVolley;
import com.jc.practice.listener.MyVolleyCallBack;
import com.jc.practice.listener.OnLoadMoreListener;
import com.jc.practice.model.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EndLessRcvWithVolley extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rcvEndLessWithVolley)
    RecyclerView rcvEndLessWithVolley;
    @Bind(R.id.swipeRefreshLayoutVolley)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.progressBarVolley)
    ProgressBar progressBar;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Example> exampleArrayList;
    MyRcvAdapterVolley myRcvAdapterVolley;
    //    String URL = "http://api.androidhive.info/json/imdb_top_250.php?offset=0";
    String URL = "http://api.androidhive.info/json/imdb_top_250.php";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_less_rcv_with_volley);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        progressBar.setVisibility(View.GONE);
        exampleArrayList = new ArrayList<>();
        rcvEndLessWithVolley.hasFixedSize();
        linearLayoutManager = new LinearLayoutManager(EndLessRcvWithVolley.this);
        rcvEndLessWithVolley.setLayoutManager(linearLayoutManager);
        myRcvAdapterVolley = new MyRcvAdapterVolley(EndLessRcvWithVolley.this, exampleArrayList, rcvEndLessWithVolley);
        rcvEndLessWithVolley.setAdapter(myRcvAdapterVolley);
        progressDialog = new ProgressDialog(EndLessRcvWithVolley.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        loadData();
        myRcvAdapterVolley.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadData();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                exampleArrayList.clear();
                loadData();
            }
        });
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW);
    }

    public void loadData() {
        if(exampleArrayList.size()!=0){
            progressBar.setVisibility(View.VISIBLE);
        }
        new MyVolley(EndLessRcvWithVolley.this, myVolleyCallBack).getData(URL + "?offset=" + exampleArrayList.size());
    }

    MyVolleyCallBack myVolleyCallBack = new MyVolleyCallBack() {
        @Override
        public void onSuccessCallBack(String response) {
            myRcvAdapterVolley.setLoaded();
            progressBar.setVisibility(View.GONE);
            ArrayList<Example> exmpl=new Gson().fromJson(response, new TypeToken<List<Example>>() {}.getType());
            exampleArrayList.addAll(exmpl);
           myRcvAdapterVolley.notifyDataSetChanged();
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void onErrorCallBack(String error) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(EndLessRcvWithVolley.this, "Error:" + error, Toast.LENGTH_SHORT).show();
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    };
}
