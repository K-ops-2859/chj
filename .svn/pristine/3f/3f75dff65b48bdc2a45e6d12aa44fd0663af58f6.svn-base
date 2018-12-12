package com.dikai.chenghunjiclient.adapter.wedding;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.entity.PrizeData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2017/12/7.
 */

public class InvitePrizeAdapter extends RecyclerView.Adapter<InvitePrizeAdapter.InvitePrizeVH> {

    private Context mContext;
    private final List<PrizeData.PrizeDataList> mData;
    private OnItemClickListener<PrizeData.PrizeDataList> onItemClickListener;

    public InvitePrizeAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public InvitePrizeVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_invite_prize, parent, false);
        return new InvitePrizeVH(view, onItemClickListener, mData);
    }

    @Override
    public void onBindViewHolder(InvitePrizeVH holder, int position) {
        PrizeData.PrizeDataList prizeDataList = mData.get(position);
        Glide.with(mContext).load(prizeDataList.getShowImg()).centerCrop().error(R.color.gray_background)
                .into(holder.ivCommodity);
        holder.tvPrizeName.setText(prizeDataList.getCommodityName());
        holder.tvPrizeDesc.setText(prizeDataList.getCountry());
        holder.tvPrice.setText(prizeDataList.getMarketPrice() + "");
    }

    @Override
    public int getItemCount() {
        System.out.println("==================大小" + mData.size());
        return mData.size();
    }

    public void setList(List<PrizeData.PrizeDataList> images) {
        mData.clear();
        append(images);
    }

    public void append(List<PrizeData.PrizeDataList> images) {
        int positionStart = mData.size();
        int itemCount = images.size();
        mData.addAll(images);
        if (positionStart > 0 && itemCount > 0) {
            notifyItemRangeInserted(positionStart, itemCount);
        } else {
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<PrizeData.PrizeDataList> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class InvitePrizeVH extends RecyclerView.ViewHolder {

        private final TextView tvPrice;
        private final TextView tvPrizeDesc;
        private final TextView tvPrizeName;
        private final ImageView ivCommodity;

        InvitePrizeVH(View itemView, final OnItemClickListener<PrizeData.PrizeDataList> onItemClickListener, final List<PrizeData.PrizeDataList> dataLists) {
            super(itemView);
            ivCommodity = (ImageView) itemView.findViewById(R.id.iv_commodity);
            tvPrizeName = (TextView) itemView.findViewById(R.id.tv_prize_name);
            tvPrizeDesc = (TextView) itemView.findViewById(R.id.tv_prize_desc);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    onItemClickListener.onItemClick(view, pos, dataLists.get(pos));
                }
            });
        }
    }
}
