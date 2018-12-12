package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.HomeFuliBean;
import com.dikai.chenghunjiclient.view.MyImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/3/2.
 */

public class HomeFuliAdapter  extends RecyclerView.Adapter<HomeFuliAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<HomeFuliBean> list;

    public HomeFuliAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_fuli_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.pic.setTag(R.id.item_fuli_img,holder);
        holder.pic.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HomeFuliBean bean = list.get(position);
        Glide.with(context).load(bean.getImg()).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends HomeFuliBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends HomeFuliBean> collection){
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
        MyViewHolder holder = (MyViewHolder) v.getTag(R.id.item_fuli_img);
        int position = holder.getAdapterPosition();
        mOnFuliClickListener.onClick(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private MyImageView pic;

        public MyViewHolder(View itemView) {
            super(itemView);
            pic = (MyImageView) itemView.findViewById(R.id.item_fuli_img);
        }
    }
    //
    private OnItemClickListener mOnFuliClickListener;

    public void setOnItemClickListener(OnItemClickListener onCarClickListener) {
        mOnFuliClickListener = onCarClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }
}