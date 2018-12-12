package com.dikai.chenghunjiclient.adapter.plan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.plan.NewsPlanActivity;
import com.dikai.chenghunjiclient.entity.NewsPlanBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/21.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int HEAD = 0;
    private static final int ITEM = 1;
    private Context context;
    private List<Object> list;
    private int type;

    public List<Object> getList() {
        return list;
    }

    public NewsAdapter(Context context) {
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
            View view = LayoutInflater.from(context).inflate(R.layout.item_news_plan_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            holder.mLayout.setTag(holder);
            holder.mLayout.setOnClickListener(this);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeadViewHolder){

        }else {
            NewsPlanBean bean = (NewsPlanBean) list.get(position);
            ((MyViewHolder)holder).name.setText(bean.getCorpName());
            ((MyViewHolder)holder).location.setText(bean.getAddress());
            Glide.with(context).load(bean.getLogo()).into( ((MyViewHolder)holder).img);
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
        NewsPlanBean bean = (NewsPlanBean) list.get(position);
        if(v == holder.mLayout){
            context.startActivity(new Intent(context, NewsPlanActivity.class).putExtra("id",bean.getCustomerInfoID()));
        }
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
        private TextView name;
        private TextView location;
        private ImageView img;
        private LinearLayout mLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.news_plan_name);
            location = (TextView) itemView.findViewById(R.id.news_plan_address);
            img = ((ImageView) itemView.findViewById(R.id.news_plan_logo));
            mLayout = (LinearLayout) itemView.findViewById(R.id.news_plan_layout);
        }
    }

}