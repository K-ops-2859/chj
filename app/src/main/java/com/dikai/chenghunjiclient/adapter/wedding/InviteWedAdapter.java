package com.dikai.chenghunjiclient.adapter.wedding;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.PrizeData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/2/7.
 */

public class InviteWedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<Object> mData;

    public InviteWedAdapter(Context context) {
        this.context = context;
        mData = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.item_invite_wed_layout, parent, false);
            HeadViewHolder holder = new HeadViewHolder(view);
            holder.apply.setTag(holder);
            holder.apply.setOnClickListener(this);
            holder.share.setTag(holder);
            holder.share.setOnClickListener(this);
            holder.record.setTag(holder);
            holder.record.setOnClickListener(this);
            holder.rule.setTag(holder);
            holder.rule.setOnClickListener(this);
            holder.qrcode.setTag(holder);
            holder.qrcode.setOnClickListener(this);
            return holder;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_invite_prize, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            holder.mLayout.setTag(holder);
            holder.mLayout.setOnClickListener(this);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            PrizeData.PrizeDataList prizeDataList = (PrizeData.PrizeDataList) mData.get(position);
            Glide.with(context).load(prizeDataList.getShowImg()).centerCrop().error(R.color.gray_background)
                    .into(((MyViewHolder)holder).ivCommodity);
            ((MyViewHolder)holder).tvPrizeName.setText(prizeDataList.getCommodityName());
            ((MyViewHolder)holder).tvPrizeDesc.setText(prizeDataList.getCountry());
            ((MyViewHolder)holder).tvPrice.setText(prizeDataList.getMarketPrice() + "");
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addAll(Collection<? extends Object> collection){
        int size = mData.size();
        mData.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends Object> collection){
        mData = new ArrayList<>();
        mData.addAll(collection);
        notifyDataSetChanged();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {
        RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
        if(holder instanceof HeadViewHolder){
            if(v == ((HeadViewHolder) holder).apply){
                mClickListener.onClick(0,null);
            }else if(v == ((HeadViewHolder) holder).record){
                mClickListener.onClick(1,null);
            }else if(v == ((HeadViewHolder) holder).rule){
                mClickListener.onClick(2,null);
            }else if(v == ((HeadViewHolder) holder).share){
                mClickListener.onClick(3,null);
            }else if(v == ((HeadViewHolder) holder).qrcode){
                mClickListener.onClick(4,null);
            }
        }else {
            if(v == ((MyViewHolder) holder).mLayout){
                mClickListener.onClick(5, (PrizeData.PrizeDataList) mData.get(holder.getAdapterPosition()));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0? 0:1;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvPrice;
        private TextView tvPrizeDesc;
        private TextView tvPrizeName;
        private ImageView ivCommodity;
        private LinearLayout mLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivCommodity = (ImageView) itemView.findViewById(R.id.iv_commodity);
            tvPrizeName = (TextView) itemView.findViewById(R.id.tv_prize_name);
            tvPrizeDesc = (TextView) itemView.findViewById(R.id.tv_prize_desc);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            mLayout = (LinearLayout) itemView.findViewById(R.id.lv_parent);
        }
    }

    public static class HeadViewHolder extends RecyclerView.ViewHolder{

        private TextView apply;
        private TextView share;
        private LinearLayout record;
        private LinearLayout rule;
        private LinearLayout qrcode;

        public HeadViewHolder(View itemView) {
            super(itemView);
            apply = (TextView) itemView.findViewById(R.id.apply);
            share = (TextView) itemView.findViewById(R.id.share);
            record = (LinearLayout) itemView.findViewById(R.id.record);
            rule = (LinearLayout) itemView.findViewById(R.id.rule);
            qrcode = (LinearLayout) itemView.findViewById(R.id.qr_code);
        }
    }

    private OnItemClickListener mClickListener;

    public void setClickListener(OnItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface OnItemClickListener{
        void onClick(int Type, PrizeData.PrizeDataList bean);
    }

}

