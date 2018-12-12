package com.dikai.chenghunjiclient.adapter.ad;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.ad.NewADInfoActivity;
import com.dikai.chenghunjiclient.entity.NewAdHomeList;
import com.dikai.chenghunjiclient.entity.NewAdItemBean;
import com.dikai.chenghunjiclient.view.MyRelativeLayout;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/8/29.
 */

public class NewAdListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, ADItemAdapter.OnItemClickListener {

    private Context context;
    private List<Object> list;

    public NewAdListAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.item_new_ad_head, parent, false);
            HeadViewHolder holder = new HeadViewHolder(view);
            holder.mLayout.setTag(holder);
            holder.mLayout.setOnClickListener(this);
            return holder;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_new_ad_home, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            holder.toAll.setTag(holder);
            holder.toAll.setOnClickListener(this);
            holder.supLayout.setTag(holder);
            holder.supLayout.setOnClickListener(this);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.mRecyclerView.setLayoutManager(mLayoutManager);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            NewAdHomeList bean = (NewAdHomeList) list.get(position);
            ((MyViewHolder)holder).name.setText(bean.getFacilitatorName());
            ADItemAdapter itemAdapter = new ADItemAdapter(context);
            itemAdapter.setOnItemClickListener(this);
            ((MyViewHolder)holder).mRecyclerView.setAdapter(itemAdapter);
            itemAdapter.refresh(bean.getData());
            Glide.with(context).load(bean.getHeadImg()).into(((MyViewHolder)holder).logo);
        }else {
            Glide.with(context).load(((String)list.get(position))).into(((HeadViewHolder)holder).bg);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) instanceof String? 0:1;
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
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

        RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
        int position = holder.getAdapterPosition();
        if(holder instanceof HeadViewHolder){
            if(v == ((HeadViewHolder) holder).mLayout){//banner
                mOnItemClickListener.onClick(0,null);
            }
        }else if(holder instanceof MyViewHolder){
            if(v == ((MyViewHolder) holder).supLayout){//服务商名字
                mOnItemClickListener.onClick(1, (NewAdHomeList) list.get(position));
            }else if(v == ((MyViewHolder) holder).toAll){//查看全部
                mOnItemClickListener.onClick(2, (NewAdHomeList) list.get(position));
            }
        }
    }

    @Override
    public void onClick(NewAdItemBean bean) {
        context.startActivity(new Intent(context, NewADInfoActivity.class).putExtra("id",bean.getId()));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private SelectableRoundedImageView logo;
        private TextView name;
        private TextView toAll;
        private RecyclerView mRecyclerView;
        private LinearLayout supLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            logo = (SelectableRoundedImageView) itemView.findViewById(R.id.logo);
            name = (TextView) itemView.findViewById(R.id.name);
            toAll = (TextView) itemView.findViewById(R.id.all);
            supLayout = (LinearLayout) itemView.findViewById(R.id.sup_layout);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_recycler);
        }
    }

    public static class HeadViewHolder extends RecyclerView.ViewHolder{
        private SelectableRoundedImageView bg;
        private MyRelativeLayout mLayout;

        public HeadViewHolder(View itemView) {
            super(itemView);
            bg = (SelectableRoundedImageView) itemView.findViewById(R.id.img);
            mLayout = (MyRelativeLayout) itemView.findViewById(R.id.bottom_layout);
        }
    }

    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int type, NewAdHomeList bean);
    }
}