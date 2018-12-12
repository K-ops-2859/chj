package com.dikai.chenghunjiclient.adapter.plan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.plan.AssignCarActivity;
import com.dikai.chenghunjiclient.entity.TeamCarBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/22.
 */

public class TeamCarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int HEAD = 0;
    private static final int ITEM = 1;
    private Context context;
    private List<Object> list;
    private String date;
    private int type;
    private String orderID;

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Object> getList() {
        return list;
    }

    public TeamCarAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HEAD){
            View view = LayoutInflater.from(context).inflate(R.layout.item_news_plan_head, parent, false);
            HeadViewHolder holder = new HeadViewHolder(view);
            return holder;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_team_car_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            holder.mLayout.setTag(holder);
            holder.mLayout.setOnClickListener(this);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeadViewHolder){
            ((HeadViewHolder) holder).mark.setText(date + "  这些车型有空");
        }else {
            TeamCarBean bean = (TeamCarBean) list.get(position);
            ((MyViewHolder)holder).brand.setText(bean.getBrandName());
            ((MyViewHolder)holder).type.setText(bean.getCarName());
            ((MyViewHolder)holder).color.setText(bean.getCarColor());
            Glide.with(context).load(bean.getCarImg()).into( ((MyViewHolder)holder).img);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends Object> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends Object> collection){
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
        TeamCarBean bean = (TeamCarBean) list.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", bean);
        bundle.putString("date",date);
        bundle.putString("order",orderID);
        context.startActivity(new Intent(context, AssignCarActivity.class)
                .putExtras(bundle));
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return HEAD;
        }else {
            return ITEM;
        }
    }

    public static class HeadViewHolder extends RecyclerView.ViewHolder{
        private TextView mark;
        public HeadViewHolder(View itemView) {
            super(itemView);
            mark = (TextView) itemView.findViewById(R.id.news_plan_mark);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView brand;
        private TextView type;
        private TextView color;
        private ImageView img;
        private LinearLayout mLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            brand = (TextView) itemView.findViewById(R.id.news_plan_name);
            color = (TextView) itemView.findViewById(R.id.news_plan_address);
            type = (TextView) itemView.findViewById(R.id.news_plan_type);
            img = ((ImageView) itemView.findViewById(R.id.news_plan_logo));
            mLayout = (LinearLayout) itemView.findViewById(R.id.news_plan_layout);
        }
    }

}