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
 * Created by cmk03 on 2017/12/13.
 */

public class SignBillAdapter extends RecyclerView.Adapter<SignBillAdapter.SignBillVH> {

    private Context mContext;
    private final List<WrapperPrizeData> mData;
    private RecyclerView mRecyclerView;
    private final int HEADER_ITEM = 0;
    private final int CONTENT_ITEM = 1;
    private final LayoutInflater layoutInflater;
    private OnAdapterViewClickListener onAdapterViewClickListener;
    private OnItemClickListener<WrapperPrizeData> onItemClickListener;
    private int signatureNumber;
    private int budget;

    public SignBillAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        layoutInflater = LayoutInflater.from(mContext);
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return HEADER_ITEM;
//        } else {
//            return CONTENT_ITEM;
//        }
//    }

    @Override
    public SignBillVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_sign_bill, parent, false);
        return new SignBillVH(view, onItemClickListener, mData);
    }

    @Override
    public void onBindViewHolder(SignBillVH holder, final int position) {
        WrapperPrizeData data = mData.get(position);
        if (data.isTitle()) {
            holder.tvHeader.setVisibility(View.VISIBLE);
            holder.tvHeader.setText("签单金额≥" + data.getGrade() + "可领取好礼");
            holder.llContent.setVisibility(View.GONE);
        } else {
            holder.tvHeader.setVisibility(View.GONE);
            holder.llContent.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(data.getShowImg()).centerCrop().into(holder.ivCommodity);
            holder.tvPrizeName.setText(data.getCommodityName());
            holder.tvPrizeDesc.setText(data.getCountry());
//            NumberFormat ddf1 = NumberFormat.getNumberInstance();
//            ddf1.setMaximumFractionDigits(0);
//            String price = ddf1.format();
            if (data.getMarketPrice() == null && data.getShowImg() == null) {
                holder.llRoot.setVisibility(View.GONE);
            }
            holder.tvPrice.setText("  ￥" + data.getMarketPrice());
            if (signatureNumber < 1) {
                holder.tvGetPrize.setBackgroundResource(R.drawable.bg_btn_gray_solid);
                holder.tvGetPrize.setText("不可领取");
            } else {
                if (budget < data.getGrade()) {
                    holder.tvGetPrize.setBackgroundResource(R.drawable.bg_btn_gray_solid);
                    holder.tvGetPrize.setText("不可领取");
                } else {
                    holder.tvGetPrize.setBackgroundResource(R.drawable.bg_btn_pink_deep_rect);
                    holder.tvGetPrize.setText("可领取");
                    holder.tvGetPrize.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onAdapterViewClickListener.onAdapterClick(view, position, null);
                        }
                    });
                }
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

    public void setList(List<WrapperPrizeData> list) {
        mData.clear();
        append(list);
    }

    public void append(List<WrapperPrizeData> list) {
        int positionStart = mData.size();
        int itemCount = list.size();
        mData.addAll(list);
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

    public void setStatusData(int signatureNumber, int budget) {
        this.signatureNumber = signatureNumber;
        this.budget = budget;
    }

    public void setOnAdapterViewClickListener(OnAdapterViewClickListener onAdapterViewClickListener) {
        this.onAdapterViewClickListener = onAdapterViewClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener<WrapperPrizeData> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
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

    static class SignBillVH extends RecyclerView.ViewHolder {

        private final TextView tvGetPrize;
        private final TextView tvPrice;
        private final TextView tvPrizeDesc;
        private final TextView tvPrizeName;
        private final ImageView ivCommodity;
        private final TextView tvHeader;
        private final LinearLayout llContent;
        private final LinearLayout llRoot;

        public SignBillVH(View itemView, final OnItemClickListener<WrapperPrizeData> listener, final List<WrapperPrizeData> data) {
            super(itemView);
            llRoot = (LinearLayout) itemView.findViewById(R.id.ll_root);
            tvHeader = (TextView) itemView.findViewById(R.id.tv_header);
            llContent = (LinearLayout) itemView.findViewById(R.id.ll_content);
            ivCommodity = (ImageView) itemView.findViewById(R.id.iv_commodity);
            tvPrizeName = (TextView) itemView.findViewById(R.id.tv_prize_name);
            tvPrizeDesc = (TextView) itemView.findViewById(R.id.tv_prize_desc);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvGetPrize = (TextView) itemView.findViewById(R.id.tv_get_prize);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onItemClick(view, position, data.get(position));
                }
            });
        }
    }

    static class HeaderVH extends RecyclerView.ViewHolder {
        public HeaderVH(View itemView) {
            super(itemView);
        }
    }
}
