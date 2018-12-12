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
import com.dikai.chenghunjiclient.activity.store.RoomPhotoActivity;
import com.dikai.chenghunjiclient.entity.ManPlanBean;
import com.dikai.chenghunjiclient.entity.PlanCarBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/19.
 */

public class ManPlanAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int HEAD = 1;
    private static final int ITEM = 2;
    private Context context;
    private List<ManPlanBean> list;
    private List<PlanCarBean> mCarList;
    private PlanCarAdapter mCarAdapter;

    public void setCarList(List<PlanCarBean> carList) {
        mCarList = carList;
    }

    public ManPlanAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HEAD){
            View view = LayoutInflater.from(context).inflate(R.layout.item_man_plan_car, parent, false);
            CarViewHolder holder = new CarViewHolder(view);
            holder.call.setTag(holder);
            holder.call.setOnClickListener(this);
            if(mCarAdapter == null){
                mCarAdapter = new PlanCarAdapter(context);
                holder.mRecyclerView.setAdapter(mCarAdapter);
            }
            return holder;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_man_plan_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            holder.call.setTag(holder);
            holder.call.setOnClickListener(this);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ManPlanBean bean = list.get(position);
        if(holder instanceof CarViewHolder){
            ((CarViewHolder) holder).ident.setText(bean.getProfessionName());
            ((CarViewHolder) holder).phone.setText(bean.getTrueName() + "  " + bean.getPhone());
            if(bean.getSuppCorpName() == null || "".equals(bean.getSuppCorpName())){
                ((CarViewHolder) holder).name.setVisibility(View.GONE);
            }else {
                ((CarViewHolder) holder).name.setVisibility(View.VISIBLE);
                ((CarViewHolder) holder).name.setText(bean.getSuppCorpName());
            }
            mCarAdapter.refresh(mCarList);
            Glide.with(context).load(bean.getLogo()).into( ((CarViewHolder) holder).logo);
        }else {
            ((MyViewHolder) holder).ident.setText(bean.getProfessionName());
            ((MyViewHolder) holder).phone.setText(bean.getTrueName() + "  " + bean.getPhone());
            if(bean.getSuppCorpName() == null || "".equals(bean.getSuppCorpName())){
                ((MyViewHolder) holder).name.setVisibility(View.GONE);
            }else {
                ((MyViewHolder) holder).name.setVisibility(View.VISIBLE);
                ((MyViewHolder) holder).name.setText(bean.getSuppCorpName());
            }
            Glide.with(context).load(bean.getLogo()).into(((MyViewHolder) holder).logo);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends ManPlanBean> collection) {
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends ManPlanBean> collection) {
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
        if (holder instanceof CarViewHolder){
            if(v == ((CarViewHolder) holder).call){
                mCallListener.onClick(list.get(position));
            }
        }else {
            if(v == ((MyViewHolder) holder).call){
                mCallListener.onClick(list.get(position));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if("SF_2001000".equals(list.get(position).getProfession())){
            return HEAD;
        }else {
            return ITEM;
        }
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        private TextView ident;
        private TextView name;
        private TextView phone;
        private ImageView call;
        private ImageView logo;
        private LinearLayout mLayout;
        private RecyclerView mRecyclerView;

        public CarViewHolder(View itemView) {
            super(itemView);
            ident = (TextView) itemView.findViewById(R.id.item_plan_identity);
            name = (TextView) itemView.findViewById(R.id.item_plan_name);
            phone = (TextView) itemView.findViewById(R.id.item_plan_phone);
            logo = ((ImageView) itemView.findViewById(R.id.item_plan_logo));
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_plan_recycler);
            call = ((ImageView) itemView.findViewById(R.id.item_plan_call));
            mLayout = (LinearLayout) itemView.findViewById(R.id.item_plan_layout);
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView ident;
        private TextView name;
        private TextView phone;
        private ImageView call;
        private ImageView logo;
        private LinearLayout mLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            ident = (TextView) itemView.findViewById(R.id.item_plan_identity);
            name = (TextView) itemView.findViewById(R.id.item_plan_name);
            phone = (TextView) itemView.findViewById(R.id.item_plan_phone);
            logo = ((ImageView) itemView.findViewById(R.id.item_plan_logo));
            call = ((ImageView) itemView.findViewById(R.id.item_plan_call));
            mLayout = (LinearLayout) itemView.findViewById(R.id.item_plan_layout);
        }
    }

    private OnCallListener mCallListener;

    public void setCallListener(OnCallListener callListener) {
        mCallListener = callListener;
    }

    public interface OnCallListener{
        void onClick(ManPlanBean bean);
    }
}