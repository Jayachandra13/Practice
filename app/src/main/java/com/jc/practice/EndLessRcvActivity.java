package com.jc.practice;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.jc.practice.adapter.RcvAdapter;
import com.jc.practice.listener.OnLoadMoreListener;
import com.jc.practice.model.User;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EndLessRcvActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rcv)
    RecyclerView rcv;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayoutManager linearLayoutManager;
    ArrayList<User> mUsers;
    RcvAdapter rcvAdapter;
    int item_index = 0;
    int increment_count = 10;

    //    String URL = "http://api.androidhive.info/json/imdb_top_250.php?offset=0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_less_rcv);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        rcv.hasFixedSize();
        linearLayoutManager = new LinearLayoutManager(EndLessRcvActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv.setLayoutManager(linearLayoutManager);
        mUsers = new ArrayList<>();
        rcvAdapter = new RcvAdapter(EndLessRcvActivity.this, mUsers, rcv);
        rcv.setAdapter(rcvAdapter);
        loadData(item_index, increment_count);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                mUsers.clear();
                item_index = 0;
                loadData(item_index, increment_count);

            }
        });
        rcvAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mUsers.add(null);
                rcvAdapter.notifyItemInserted(mUsers.size()-1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mUsers.remove(mUsers.size()-1);
                        rcvAdapter.notifyItemRemoved(mUsers.size());
                    }
                },2000);
                loadData(mUsers.size(), (mUsers.size() + increment_count));
            }
        });
    }

    public void loadData(Integer start, Integer end) {
        Log.v("LoadData",start+"---"+end);
        for (int i = start; i < end; i++) {
            User user = new User();
            user.setName("Name " + (i + 1));
            user.setEmail("alibaba" + (i + 1) + "@gmail.com");
            mUsers.add(user);
        }
        rcvAdapter.notifyDataSetChanged();
        rcvAdapter.setLoading();
    }
}
