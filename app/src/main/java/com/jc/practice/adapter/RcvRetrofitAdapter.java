package com.jc.practice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jc.practice.R;
import com.jc.practice.model.Example;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jcskh on 03-05-2016.
 */
public class RcvRetrofitAdapter extends RecyclerView.Adapter<RcvRetrofitAdapter.Vh> {
    Context context;
    List<Example> exampleList;

    public RcvRetrofitAdapter(Context context, List<Example> exampleList) {
        this.context = context;
        this.exampleList = exampleList;
    }

    @Override
    public Vh onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Vh(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_example_item, parent, false));
    }

    @Override
    public void onBindViewHolder(Vh holder, int position) {
        holder.tvRank.setText(String.valueOf(exampleList.get(position).getRank()));
        holder.tvTitle.setText(exampleList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    public class Vh extends RecyclerView.ViewHolder {
        @Bind(R.id.tvRank)
        TextView tvRank;
        @Bind(R.id.tvTitle)
        TextView tvTitle;

        public Vh(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
