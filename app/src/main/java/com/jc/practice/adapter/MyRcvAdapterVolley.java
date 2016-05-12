package com.jc.practice.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jc.practice.R;
import com.jc.practice.listener.OnLoadMoreListener;
import com.jc.practice.model.Example;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jcskh on 27-04-2016.
 */
public class MyRcvAdapterVolley extends RecyclerView.Adapter<MyRcvAdapterVolley.ExampleHolder> {
    Context context;
    ArrayList<Example> list;
    RecyclerView recyclerView;
    OnLoadMoreListener onLoadMoreListener;
    int visibleThreshold = 2;
    int lastVisibleItem, totalItemCount;
    boolean loading;

    public MyRcvAdapterVolley(Context context, ArrayList<Example> list, RecyclerView recyclerView) {
        this.context = context;
        this.list = list;
        this.recyclerView = recyclerView;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public ExampleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExampleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_example_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ExampleHolder holder, int position) {
        holder.tvRank.setText("Rank   : " + list.get(position).getRank());
        holder.tvTitle.setText("Title : " + list.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    public class ExampleHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvRank)
        TextView tvRank;
        @Bind(R.id.tvTitle)
        TextView tvTitle;

        public ExampleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Rank:" + list.get(getAdapterPosition()).getRank() + "\nTitle:" + list.get(getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
