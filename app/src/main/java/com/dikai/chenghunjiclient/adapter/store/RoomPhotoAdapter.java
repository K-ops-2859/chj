package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.dikai.chenghunjiclient.view.MyImageView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/8/28.
 */

public class RoomPhotoAdapter extends RecyclerView.Adapter<RoomPhotoAdapter.StorePicsViewHolder> implements View.OnClickListener {
    private Context context;
    private ArrayList<String> pics;

    public RoomPhotoAdapter(Context context) {
        this.context = context;
        this.pics = new ArrayList<>();
    }

    @Override
    public StorePicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room_photo_layout, parent, false);
        StorePicsViewHolder holder = new StorePicsViewHolder(view);
        holder.pic.setTag(R.id.myImageView,holder);
        holder.pic.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(StorePicsViewHolder holder, int position) {
        Glide.with(context).load(pics.get(position)).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return pics.size();
    }

    public void addAll(Collection<? extends String> collection){
        int size = pics.size();
        pics.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends String> collection){
        pics = new ArrayList<>();
        pics.addAll(collection);
        notifyDataSetChanged();
    }
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {
        StorePicsViewHolder holder = (StorePicsViewHolder) v.getTag(R.id.myImageView);
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra("now", holder.getAdapterPosition());
        intent.putStringArrayListExtra("imgs", pics);
        context.startActivity(intent);
    }

    public static class StorePicsViewHolder extends RecyclerView.ViewHolder{
        private MyImageView pic;
        public StorePicsViewHolder(View itemView) {
            super(itemView);
            pic = (MyImageView) itemView.findViewById(R.id.myImageView);
        }
    }
}

