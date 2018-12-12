package com.dikai.chenghunjiclient.adapter.wedding;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnAdapterViewClickListener;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.entity.WrapperPrizeData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2017/11/16.
 */

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.InviteVH> {

    private Context mContext;
    private final List<WrapperPrizeData> mData;
    private RecyclerView mRecyclerView;
    private OnItemClickListener<WrapperPrizeData> onItemClickListener;
    private OnAdapterViewClickListener onAdapterViewClickListener;


    public InviteAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public InviteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_invite, parent, false);
        return new InviteVH(view, onItemClickListener, mData);
    }

    @Override
    public void onBindViewHolder(InviteVH holder, final int position) {
        WrapperPrizeData wrapperPrizeData = mData.get(position);
        wrapperPrizeData.setSignBill(4);
        if (wrapperPrizeData.isTitle()) {
            holder.tvHeader.setVisibility(View.VISIBLE);
            holder.tvHeader.setText("达标" + wrapperPrizeData.getGrade() + "即可领取好礼");
            holder.llContent.setVisibility(View.GONE);
        } else {
            holder.tvHeader.setVisibility(View.GONE);
            holder.llContent.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(wrapperPrizeData.getShowImg()).centerCrop().into(holder.ivCommodity);
            holder.tvPrizeName.setText(wrapperPrizeData.getCommodityName());
            holder.tvPrizeDesc.setText(wrapperPrizeData.getCountry());
//            NumberFormat ddf1 = NumberFormat.getNumberInstance();
//            ddf1.setMaximumFractionDigits(0);
//            String price = ddf1.format();
            if (wrapperPrizeData.getMarketPrice() == null && wrapperPrizeData.getShowImg() == null) {
                holder.llRoot.setVisibility(View.GONE);
            }
            holder.tvPrice.setText("  ￥" + wrapperPrizeData.getMarketPrice());

            if (wrapperPrizeData.getSignBill() >= wrapperPrizeData.getGrade()) {
                holder.tvGetPrize.setBackgroundResource(R.drawable.bg_btn_pink_deep_rect);
                holder.tvGetPrize.setText("可领取");
                holder.tvGetPrize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onAdapterViewClickListener.onAdapterClick(view, position, null);
                    }
                });
            } else {
                holder.tvGetPrize.setBackgroundResource(R.drawable.bg_btn_gray_solid);
                holder.tvGetPrize.setText("不可领取");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) {
            return;
        }
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
//            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup =
//                    ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return mData.get(position).isTitle() ? 2 : 1;
                }
            });
        }
    }


    public void setList(List<WrapperPrizeData> images) {
        mData.clear();
        append(images);
    }

    public void append(List<WrapperPrizeData> images) {
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnAdapterViewClickListener(OnAdapterViewClickListener listener) {
        this.onAdapterViewClickListener = listener;
    }

    static class InviteVH extends RecyclerView.ViewHolder {

        private final ImageView ivCommodity;
        private final TextView tvPrizeDesc;
        private final TextView tvPrizeName;
        private final TextView tvPrice;
        private final LinearLayout llContent;
        private final TextView tvHeader;
        private final TextView tvGetPrize;
        private final LinearLayout llRoot;

        public InviteVH(View itemView, final OnItemClickListener listener, final List<WrapperPrizeData> data) {
            super(itemView);

            llRoot = (LinearLayout) itemView.findViewById(R.id.ll_root);
            ivCommodity = (ImageView) itemView.findViewById(R.id.iv_commodity);
            tvPrizeName = (TextView) itemView.findViewById(R.id.tv_prize_name);
            tvPrizeDesc = (TextView) itemView.findViewById(R.id.tv_prize_desc);
            tvGetPrize = (TextView) itemView.findViewById(R.id.tv_get_prize);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvHeader = (TextView) itemView.findViewById(R.id.tv_header);
            llContent = (LinearLayout) itemView.findViewById(R.id.ll_content);

            if (listener != null) {

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = InviteVH.this.getAdapterPosition();
                        WrapperPrizeData wrapperPrizeData = data.get(position);
                        listener.onItemClick(view, position, data.get(position));
                    }
                });
            }
        }
    }
}
