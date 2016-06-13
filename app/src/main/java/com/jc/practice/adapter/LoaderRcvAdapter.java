package com.jc.practice.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jc.practice.R;
import com.jc.practice.model.ContactDetails;
import com.jc.practice.model.MyContacts;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jcskh on 08-06-2016.
 */
public class LoaderRcvAdapter extends RecyclerView.Adapter<LoaderRcvAdapter.LoaderHolder> {
    Context context;
    ArrayList<ContactDetails> list;

    public LoaderRcvAdapter(Context context, ArrayList<ContactDetails> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public LoaderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LoaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_li, parent, false));
    }

    @Override
    public void onBindViewHolder(LoaderHolder holder, int position) {
        String name = "NA";
        if (list.get(position).getName() != null) {
            name = list.get(position).getName();
        }
        holder.tvName.setText(name);
        String no = "NA";
        if (list.get(position).getNo() != null && list.get(position).getNo().size() != 0) {
            no = "";
            for (int i = 0; i < list.get(position).getNo().size(); i++) {
                no = no + list.get(position).getNo().get(i);
                if(i<list.get(position).getNo().size()-1){
                    no = no+"\n";
                }
            }
        }
        holder.tvNo.setText(no);
        String email = "NA";
        if (list.get(position).getEmail() != null) {
            email = "";
            for (int i = 0; i < list.get(position).getEmail().size(); i++) {
                email = email + list.get(position).getEmail().get(i);
                if(i<list.get(position).getEmail().size()-1){
                    email = email+"\n";
                }
            }
        }
        holder.tvEmail.setText(email);
        if (list.get(position).getUri() != null) {
            //holder.imageViewContact.setImageURI(Uri.parse(list.get(position).getImage()));
            DrawableRequestBuilder<String> thumbnailRequest = Glide
                    .with(context)
                    .load(list.get(position).getUri().toString());
            Glide.with(context)
                    .load(list.get(position).getUri().toString())
                    .placeholder(R.drawable.launcher)
                    .error(R.drawable.ic_up_arrow)
                    .override(100, 100)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .thumbnail(thumbnailRequest)
                    .into(holder.imageViewContact);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LoaderHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.tvNo)
        TextView tvNo;
        @Bind(R.id.tvEmail)
        TextView tvEmail;
        @Bind(R.id.imageViewContact)
        ImageView imageViewContact;

        public LoaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
