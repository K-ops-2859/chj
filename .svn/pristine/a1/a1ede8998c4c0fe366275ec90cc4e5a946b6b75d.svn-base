package com.dikai.chenghunjiclient.adapter.invitation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.JianglijinBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/10/22.
 */

public class PrizeSourceAdapter extends RecyclerView.Adapter<PrizeSourceAdapter.MyViewHolder>{

    private Context context;
    private List<JianglijinBean> list;

    public PrizeSourceAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jianglijin_source_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        JianglijinBean bean = list.get(position);
        holder.type.setText(bean.getMeno());
        holder.money.setText(bean.getMonry());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends JianglijinBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends JianglijinBean> collection){
        list = new ArrayList<>();
        list.addAll(collection);
        notifyDataSetChanged();
    }
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

//    @Override
//    public void onClick(View v) {
//        MyViewHolder holder = (MyViewHolder) v.getTag();
//        int position = holder.getAdapterPosition();
//        mOnItemClickListener.onClick(list.get(position));
//    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView type;
        private TextView money;

        public MyViewHolder(View itemView) {
            super(itemView);
            type = (TextView) itemView.findViewById(R.id.type);
            money = (TextView) itemView.findViewById(R.id.money);
        }
    }
}