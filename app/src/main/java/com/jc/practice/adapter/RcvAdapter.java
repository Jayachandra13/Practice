package com.jc.practice.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jc.practice.R;
import com.jc.practice.listener.OnLoadMoreListener;
import com.jc.practice.model.User;

import java.util.ArrayList;

/**
 * Created by FOTL on 4/19/2016.
 */
public class RcvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<User> list;
    RecyclerView rcv;
    LinearLayoutManager linearLayoutManager;
    int lastVisibleItem, totalItemCount;
    boolean isLoading;
    int visibleThreshold = 2;
    OnLoadMoreListener onLoadMoreListener;
    int VIEW_TYPE_ITEM = 0;
    int VIEW_TYPE_LOADING = 1;

    public RcvAdapter(Context context, final ArrayList<User> list, RecyclerView rcv) {
        this.context = context;
        this.list = list;
        this.rcv = rcv;
        linearLayoutManager = (LinearLayoutManager) rcv.getLayoutManager();
        rcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_user_item, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            User user = list.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.tvName.setText(user.getName());
            userViewHolder.tvEmailId.setText(user.getEmail());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setLoading() {
        isLoading = false;
    }
}
class UserViewHolder extends RecyclerView.ViewHolder {
    public TextView tvName;
    public TextView tvEmailId;

    public UserViewHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tvName);

        tvEmailId = (TextView) itemView.findViewById(R.id.tvEmailId);
    }
}

class LoadingViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
    }
}