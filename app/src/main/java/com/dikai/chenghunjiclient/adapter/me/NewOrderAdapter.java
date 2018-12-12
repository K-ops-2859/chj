package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.WedPrizeInfoActivity;
import com.dikai.chenghunjiclient.entity.NewOrderBean;
import com.dikai.chenghunjiclient.entity.NewOrderItemBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/9/20.
 */

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.MyViewHolder> implements OrderItemAdapter.OnItemClickListener {

    private Context context;
    private List<NewOrderBean> list;

    public NewOrderAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_new_order_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mRecyclerView.setNestedScrollingEnabled(false);
        holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewOrderBean bean = list.get(position);
        holder.date.setText(bean.getCreateTime());
        holder.state.setText(bean.getIsStatus() == 0?"未审核":"已审核");
        holder.source.setText(bean.getActivityCategoryName());
        holder.netMoney.setText("￥"+bean.getQuota());
        OrderItemAdapter itemAdapter = new OrderItemAdapter(context);
        itemAdapter.setOnItemClickListener(this);
        holder.mRecyclerView.setAdapter(itemAdapter);
        itemAdapter.refresh(bean.getCommodityData());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends NewOrderBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends NewOrderBean> collection){
        list = new ArrayList<>();
        list.addAll(collection);
        notifyDataSetChanged();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onClick(NewOrderItemBean bean) {
        Intent intent = new Intent(context, WedPrizeInfoActivity.class);
        intent.putExtra("prizeId",bean.getCommodityId());
        intent.putExtra("type",1);
        context.startActivity(intent);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView date;
        private TextView state;
        private TextView netMoney;
        private TextView source;
        private RecyclerView mRecyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            state = (TextView) itemView.findViewById(R.id.state);
            source = (TextView) itemView.findViewById(R.id.source);
            netMoney = (TextView) itemView.findViewById(R.id.net_money);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler);
        }
    }
    //
//    private OnItemClickListener mOnItemClickListener;
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        mOnItemClickListener = onItemClickListener;
//    }
//
//    public interface OnItemClickListener{
//        void onClick(NewOrderBean bean);
//    }
}