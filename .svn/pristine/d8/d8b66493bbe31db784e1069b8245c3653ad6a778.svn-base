package com.dikai.chenghunjiclient.adapter.store;

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
import com.dikai.chenghunjiclient.activity.store.CarInfoActivity;
import com.dikai.chenghunjiclient.activity.store.CorpInfoActivity;
import com.dikai.chenghunjiclient.activity.store.HotelInfoActivity;
import com.dikai.chenghunjiclient.entity.HomeSupBean;
import com.dikai.chenghunjiclient.util.UserManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/1/12.
 */

public class StoreSupAdapter extends RecyclerView.Adapter<StoreSupAdapter.StorePicsViewHolder> implements View.OnClickListener {
    private Context context;
    private List<HomeSupBean> list;

    public StoreSupAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @Override
    public StoreSupAdapter.StorePicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_new_store_sup_layout, parent, false);
        StoreSupAdapter.StorePicsViewHolder holder = new StoreSupAdapter.StorePicsViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(StoreSupAdapter.StorePicsViewHolder holder, int position) {
        HomeSupBean bean = list.get(position);
        holder.gift.setVisibility(View.GONE);
        holder.vImg.setVisibility(View.GONE);
        holder.name.setText(bean.getName());
        holder.info.setText("案例 " + bean.getCaseCount()+"     状态 "+bean.getStateCount());
        if(bean.getIsGifts() == 1){
            holder.gift.setVisibility(View.VISIBLE);
        }
        if(bean.getIsSign() == 1){
            holder.vImg.setVisibility(View.VISIBLE);
        }
        Glide.with(context).load(bean.getImgUrl()).placeholder(R.color.gray_background)
                .error(R.color.gray_background).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends HomeSupBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends HomeSupBean> collection){
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
        if(UserManager.getInstance(context).checkLogin()){
            StorePicsViewHolder holder = (StorePicsViewHolder) v.getTag();
            HomeSupBean bean = list.get(holder.getAdapterPosition());
            if("SF_1001000".equals(bean.getProfessionID())){//酒店
                context.startActivity(new Intent(context, HotelInfoActivity.class)
                        .putExtra("id", bean.getSupplierID()).putExtra("userid",bean.getUserID()));
            }else if("SF_2001000".equals(bean.getProfessionID())){//婚车
                context.startActivity(new Intent(context, CarInfoActivity.class)
                        .putExtra("id", bean.getSupplierID()).putExtra("userid",bean.getUserID()));
            }else if("SF_14001000".equals(bean.getProfessionID())){//婚庆
                context.startActivity(new Intent(context, CorpInfoActivity.class)
                        .putExtra("id", bean.getSupplierID())
                        .putExtra("type",1).putExtra("userid",bean.getUserID()));
            }else {//其他
                context.startActivity(new Intent(context, CorpInfoActivity.class)
                        .putExtra("id", bean.getSupplierID())
                        .putExtra("type",0)
                        .putExtra("userid",bean.getUserID()));
            }
        }
    }

    public static class StorePicsViewHolder extends RecyclerView.ViewHolder{
        private ImageView pic;
        private ImageView vImg;
        private ImageView gift;
        private TextView name;
        private TextView info;
        private LinearLayout mLayout;
        public StorePicsViewHolder(View itemView) {
            super(itemView);
            pic = (ImageView) itemView.findViewById(R.id.new_store_sup_img);
            vImg = (ImageView) itemView.findViewById(R.id.new_store_sup_v);
            gift = (ImageView) itemView.findViewById(R.id.new_store_sup_gift);
            name = (TextView) itemView.findViewById(R.id.new_store_sup_name);
            info = (TextView) itemView.findViewById(R.id.new_store_sup_info);
            mLayout = (LinearLayout) itemView.findViewById(R.id.new_store_sup_layout);
        }
    }
}