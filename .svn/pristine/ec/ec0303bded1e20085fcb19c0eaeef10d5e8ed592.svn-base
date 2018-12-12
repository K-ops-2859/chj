package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.MyPrizeBean;
import com.dikai.chenghunjiclient.entity.MyPrizeBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/4/4.
 */

public class MyPrizeAdapter extends RecyclerView.Adapter<MyPrizeAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<MyPrizeBean> list;

    public MyPrizeAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_prize_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.getNow.setTag(holder);
        holder.getNow.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyPrizeBean bean = list.get(position);
        if(bean.getType() == 0){
            holder.state.setVisibility(View.VISIBLE);
            holder.getNow.setVisibility(View.GONE);
            holder.state.setText("已添加至-我的账户");
        }else {
            if(bean.getIsUse() == 0){
                holder.state.setVisibility(View.GONE);
                holder.getNow.setVisibility(View.VISIBLE);
            }else {
                holder.state.setVisibility(View.VISIBLE);
                holder.getNow.setVisibility(View.GONE);
                holder.state.setText("已领取");
            }
        }
        holder.name.setText(bean.getName());
        Glide.with(context).load(bean.getImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void itemChange(int position){
        notifyItemChanged(position);
    }

    public void addAll(Collection<? extends MyPrizeBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends MyPrizeBean> collection){
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
        MyViewHolder holder = (MyViewHolder) v.getTag();
        int position = holder.getAdapterPosition();
        mOnCarClickListener.onClick(position, list.get(position));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private ImageView img;
        private TextView getNow;
        private TextView state;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            getNow = (TextView) itemView.findViewById(R.id.get_now);
            state = (TextView) itemView.findViewById(R.id.state);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    private OnItemClickListener mOnCarClickListener;

    public void setOnItemClickListener(OnItemClickListener onCarClickListener) {
        mOnCarClickListener = onCarClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int position, MyPrizeBean bean);
    }
}