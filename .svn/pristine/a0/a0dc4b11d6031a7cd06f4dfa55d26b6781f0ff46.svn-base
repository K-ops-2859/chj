package com.dikai.chenghunjiclient.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.DynamicData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/1/26.
 */

public class MessageDetailsAdapter extends RecyclerView.Adapter<MessageDetailsAdapter.MessageDetailsVH> {

    private Context mContext;
    private final List<String> mData;

    public MessageDetailsAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }
    @Override
    public MessageDetailsVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_message_details, parent, false);
        return new MessageDetailsVH(view);
    }

    @Override
    public void onBindViewHolder(MessageDetailsVH holder, int position) {
        Glide.with(mContext).load(mData.get(position)).error(R.color.white).placeholder(R.color.white).centerCrop()
                .into(holder.ivShow);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setList(List<String> list) {
        mData.clear();
        append(list);
    }

    public void append(List<String> list) {
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

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    static class MessageDetailsVH extends RecyclerView.ViewHolder {

        private final ImageView ivShow;

        public MessageDetailsVH(View itemView) {
            super(itemView);
            ivShow = (ImageView) itemView.findViewById(R.id.iv_show);
        }
    }
}
