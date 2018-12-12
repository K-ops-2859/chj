package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.dikai.chenghunjiclient.view.MyImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/17.
 */

public class CasePicAdapter extends RecyclerView.Adapter<CasePicAdapter.StorePicsViewHolder> implements View.OnClickListener {
    private Context context;
    private List<String> list;

    public CasePicAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @Override
    public StorePicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_project_list_layout, parent, false);
        StorePicsViewHolder holder = new StorePicsViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(StorePicsViewHolder holder, int position) {
        System.out.println(list.get(position));
        Glide.with(context).load(list.get(position)).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends String> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends String> collection){
        list = new ArrayList<>();
        list.addAll(collection);
        notifyDataSetChanged();
    }
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {
        StorePicsViewHolder holder = (StorePicsViewHolder) v.getTag();
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra("now", holder.getAdapterPosition());
        intent.putStringArrayListExtra("imgs", new ArrayList<>(list));
        context.startActivity(intent);
    }

    public static class StorePicsViewHolder extends RecyclerView.ViewHolder{
        private MyImageView pic;
        private LinearLayout mLayout;
        public StorePicsViewHolder(View itemView) {
            super(itemView);
            pic = (MyImageView) itemView.findViewById(R.id.item_project_img);
            mLayout = (LinearLayout) itemView.findViewById(R.id.item_project_layout);
        }
    }
}
