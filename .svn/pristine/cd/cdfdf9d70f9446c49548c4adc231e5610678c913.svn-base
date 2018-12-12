package com.dikai.chenghunjiclient.adapter.wedding;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.TaocanData;
import com.dikai.chenghunjiclient.entity.TaocanPhotoData;
import com.dikai.chenghunjiclient.util.TextLUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

/**
 * Created by cmk03 on 2018/6/8.
 */

public class AdapterTaocan extends RecyclerView.Adapter<AdapterTaocan.TaocanVH> {

    private Context mContext;
    private final List<TaocanData.DataList> mData;

    public AdapterTaocan(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public TaocanVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_taocan, parent, false);
        return new TaocanVH(view);
    }

    @Override
    public void onBindViewHolder(TaocanVH holder, int position) {
        TaocanData.DataList dataList = mData.get(position);
        List<String> label = Arrays.asList(dataList.getLabel().split(","));
        holder.tag.setTags(label);
        Glide.with(mContext).load(dataList.getCoverMap()).into(holder.image);
        holder.tvDesc.setText(dataList.getName());
        holder.tvPrice.setText("¥" + dataList.getPresentPrice());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setList(List<TaocanData.DataList> list) {
        mData.clear();
        append(list);
    }

    public void append(List<TaocanData.DataList> lists) {
        int positionStart = mData.size();
        int itemCount = lists.size();
        mData.addAll(lists);
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

    static class TaocanVH extends RecyclerView.ViewHolder {

        private final TextView tvDesc;
        private final TextView tvPrice;
        private final ImageView image;
        private final TagContainerLayout tag;

        public TaocanVH(View itemView) {
            super(itemView);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            tag = (TagContainerLayout) itemView.findViewById(R.id.item_project_tag);
            image = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
