package com.dikai.chenghunjiclient.adapter.wedding;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnAdapterViewClickListener;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2017/11/16.
 */

public class GetMyPrizeAdapter extends RecyclerView.Adapter<GetMyPrizeAdapter.ContentVH> {

    private final int ITEM_HEADER = 0;
    private final int ITEM_CONTENT = 1;
    private final int ITEM_FOOTER = 2;
    private View mHeaderView;
    private View mFooterView;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private final ArrayList<String> mData;
    private int[] grades = {};
    private String mEndTime;
    private String mCount;
    private int inviteNumber;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private OnAdapterViewClickListener onAdapterViewClickListener;

    public GetMyPrizeAdapter(Context context, String endTime, String count, int inviteNumber) {
        this.mContext = context;
        this.mEndTime = endTime;
        this.mCount = count;
        this.inviteNumber = inviteNumber;
        mData = new ArrayList<>();
    }

    @Override
    public ContentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentVH(getLayout(R.layout.adapter_get_my_prize, parent));
    }

    @Override
    public void onBindViewHolder(final ContentVH holder, final int position) {

        holder.tvStandards.setText("达标 " + mData.get(position));
        if (Integer.valueOf(mData.get(position)) < inviteNumber ||
                Integer.valueOf(mData.get(position)) == inviteNumber) {
            holder.tvGet.setBackgroundResource(R.drawable.bg_btn_orange);
            holder.tvGet.setText("点击领取");
            holder.tvGet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onAdapterViewClickListener.onAdapterClick(view, position, null);
                }
            });
        } else {
            holder.tvGet.setBackgroundResource(R.drawable.bg_btn_gray_solid);
            holder.tvGet.setText("不可领取");
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


    private View getLayout(int layoutId, ViewGroup parent) {
        if (inflater == null) {
            inflater = LayoutInflater.from(mContext);
        }
        return inflater.inflate(layoutId, parent, false);
    }

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            mHeaderView = headerView;
            ifGridLayoutManager();
            notifyItemInserted(0);
        }

    }

    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            mFooterView = footerView;
            ifGridLayoutManager();
            notifyItemInserted(getItemCount() - 1);
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup =
                    ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() :
                            1;
                }
            });
        }
    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }

    private boolean haveHeaderView() {
        return mHeaderView != null;
    }

    public boolean haveFooterView() {
        return mFooterView != null;
    }


    public void setList(List<String> images) {
        mData.clear();
        append(images);
    }

    public void append(List<String> images) {
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

     static class ContentVH extends RecyclerView.ViewHolder {

        private final ImageView ivGiftLogo;
        private final TextView tvStandards;
        private final TextView tvDesc;
        private final TextView tvGet;

        ContentVH(View itemView) {
            super(itemView);
            ivGiftLogo = (ImageView) itemView.findViewById(R.id.iv_gift_logo);
            tvStandards = (TextView) itemView.findViewById(R.id.tv_standards);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            tvGet = (TextView) itemView.findViewById(R.id.tv_get);

        }
    }

    private static class FooterVH extends RecyclerView.ViewHolder {
        FooterVH(View itemView) {
            super(itemView);
        }
    }

    private static class HeaderVH extends RecyclerView.ViewHolder {

        private final TextView tvDate;
        private final TextView tvNumber;

        HeaderVH(View itemView) {
            super(itemView);
            tvNumber = (TextView) itemView.findViewById(R.id.tv_number);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
