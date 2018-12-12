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
import com.dikai.chenghunjiclient.activity.plan.PlanInfoActivity;
import com.dikai.chenghunjiclient.activity.store.RoomPhotoActivity;
import com.dikai.chenghunjiclient.entity.GetSupOrderBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/8/31.
 */
public class NewAddAdapter extends RecyclerView.Adapter<NewAddAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<GetSupOrderBean> list;

    public NewAddAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_new_plan_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        holder.refuse.setTag(holder);
        holder.refuse.setOnClickListener(this);
        holder.agree.setTag(holder);
        holder.agree.setOnClickListener(this);
        holder.call.setTag(holder);
        holder.call.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GetSupOrderBean bean = list.get(position);
        holder.name.setText(bean.getCorpName());
        holder.phone.setText(bean.getCorpNamePhone());
        holder.time.setText(bean.getWeddingDate());
        holder.hotel.setText(bean.getCorpRummeryName());
        holder.address.setText(bean.getCorpRummeryAddress());
        Glide.with(context).load(bean.getCorpLogo()).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends GetSupOrderBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends GetSupOrderBean> collection){
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
        if(v == holder.agree){
            mCallClickListener.onClick(position, false, true, list.get(position));
        }else if(v == holder.refuse){
            mCallClickListener.onClick(position, false, false, list.get(position));
        }else if(v == holder.call){
            mCallClickListener.onClick(position, true, false, list.get(position));
        }else if(v == holder.mLayout){
            Bundle bundle = new Bundle();
            bundle.putSerializable("bean",list.get(position));
            context.startActivity(new Intent(context, PlanInfoActivity.class).putExtras(bundle));
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView time;
        private TextView hotel;
        private TextView address;
        private TextView refuse;
        private TextView agree;
        private TextView name;
        private TextView phone;
        private ImageView call;
        private ImageView logo;
        private LinearLayout mLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.item_new_add_time);
            hotel = (TextView) itemView.findViewById(R.id.item_new_add_hotel);
            address = (TextView) itemView.findViewById(R.id.item_new_add_address);
            refuse = (TextView) itemView.findViewById(R.id.item_new_add_refuse);
            agree = (TextView) itemView.findViewById(R.id.item_new_add_agree);
            name = (TextView) itemView.findViewById(R.id.item_new_add_name);
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
        void onClick(int position, boolean isCall, boolean isAgree, GetSupOrderBean bean);
    }
}