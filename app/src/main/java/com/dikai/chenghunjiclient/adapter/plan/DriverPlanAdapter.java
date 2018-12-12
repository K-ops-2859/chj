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
import com.dikai.chenghunjiclient.activity.plan.AddPlanActivity;
import com.dikai.chenghunjiclient.entity.DriverPlanBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/26.
 */

public class DriverPlanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int HEAD = 0;
    private static final int ITEM = 1;
    private Context context;
    private List<DriverPlanBean> list;

    public List<DriverPlanBean> getList() {
        return list;
    }

    public DriverPlanAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HEAD){
            View view = LayoutInflater.from(context).inflate(R.layout.item_calendar_plan_two, parent, false);
            HeadViewHolder holder = new HeadViewHolder(view);
            holder.mLayout.setTag(holder);
            holder.mLayout.setOnClickListener(this);
            holder.edit.setTag(holder);
            holder.edit.setOnClickListener(this);
            holder.delete.setTag(holder);
            holder.delete.setOnClickListener(this);
            return holder;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_calendar_plan_one, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            holder.call.setTag(holder);
            holder.call.setOnClickListener(this);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DriverPlanBean bean = list.get(position);
        if(holder instanceof HeadViewHolder){
            ((HeadViewHolder)holder).name.setText(bean.getTitle());
            ((HeadViewHolder)holder).phone.setText(bean.getLogContent());
        }else {
            ((MyViewHolder)holder).name.setText(bean.getCaptainName());
            ((MyViewHolder)holder).phone.setText(bean.getCaptainPhone());
            ((MyViewHolder)holder).hotel.setText(bean.getRummeryName());
            ((MyViewHolder)holder).address.setText(bean.getRummeryAddress());
            ((MyViewHolder) holder).remarklayout.setVisibility(View.GONE);
            Glide.with(context).load(bean.getCaptainImg()).into( ((MyViewHolder)holder).logo);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void remove(int position){
        list.remove(position);
        notifyItemChanged(position);
    }

    public void addAll(Collection<? extends DriverPlanBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends DriverPlanBean> collection){
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
            if(v == ((HeadViewHolder) holder).edit){
                mCallClickListener.onClick(position, false, false, list.get(position));
            }else if(v == ((HeadViewHolder) holder).delete){
                mCallClickListener.onClick(position, false, true, list.get(position));
            }else if(v == ((HeadViewHolder) holder).mLayout){
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean",list.get(position));
                bundle.putInt("type",3);
                context.startActivity(new Intent(context, AddPlanActivity.class).putExtras(bundle));
            }
        }else {
            if(v == ((MyViewHolder) holder).call){
                mCallClickListener.onClick(position, true, false, list.get(position));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getScheduleType() == 1){
            return HEAD;
        }else {
            return ITEM;
        }
    }

    public static class HeadViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView edit;
        private TextView delete;
        private TextView phone;
        private LinearLayout mLayout;
        public HeadViewHolder(View itemView) {
            super(itemView);
            delete = (TextView) itemView.findViewById(R.id.item_new_add_refuse);
            edit = (TextView) itemView.findViewById(R.id.item_new_add_agree);
            name = (TextView) itemView.findViewById(R.id.item_new_add_name);
            phone = (TextView) itemView.findViewById(R.id.item_new_add_phone);
            mLayout = (LinearLayout) itemView.findViewById(R.id.item_new_add_layout);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView hotel;
        private TextView address;
        private TextView name;
        private TextView phone;
        private TextView remark;
        private LinearLayout remarklayout;
        private ImageView call;
        private ImageView logo;
        private LinearLayout mLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            hotel = (TextView) itemView.findViewById(R.id.item_new_add_hotel);
            address = (TextView) itemView.findViewById(R.id.item_new_add_address);
            name = (TextView) itemView.findViewById(R.id.item_new_add_name);
            remark = (TextView) itemView.findViewById(R.id.item_new_add_remark);
            remarklayout = (LinearLayout) itemView.findViewById(R.id.item_new_add_remark_layout);
            phone = (TextView) itemView.findViewById(R.id.item_new_add_phone);
            logo = ((ImageView) itemView.findViewById(R.id.item_new_add_logo));
            call = ((ImageView) itemView.findViewById(R.id.item_new_add_call));
            mLayout = (LinearLayout) itemView.findViewById(R.id.item_new_add_layout);
        }
    }

    private OnCallClickListener mCallClickListener;

    public void setCallClickListener(OnCallClickListener callClickListener) {
        mCallClickListener = callClickListener;
    }

    public interface OnCallClickListener{
        void onClick(int position, boolean isCall,boolean isDelete, DriverPlanBean bean);
    }

}