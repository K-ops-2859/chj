package com.dikai.chenghunjiclient.adapter.discover;

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
import com.dikai.chenghunjiclient.entity.MessageData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/1/10.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageVH> {

    private Context mContext;
    private final List<MessageData.DataList> mData;
    private OnItemClickListener<MessageData.DataList> onItemClickListener;

    public MessageAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public MessageVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_message, parent, false);
        return new MessageVH(view, onItemClickListener, mData);
    }

    @Override
    public void onBindViewHolder(MessageVH holder, int position) {
        MessageData.DataList dataList = mData.get(position);
        holder.tvTitle.setText(dataList.getTitle());
        holder.tvGroupTitle.setText(dataList.getWeddingInformationTitle());
        Glide.with(mContext).load(dataList.getShowImg()).centerCrop()
                .placeholder(R.color.gray_background).error(R.color.gray_background).into(holder.ivShow);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener<MessageData.DataList> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setList(List<MessageData.DataList> list) {
        mData.clear();
        append(list);
    }

    public void append(List<MessageData.DataList> list) {
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

    static class MessageVH extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final ImageView ivShow;
        private final TextView tvGroupTitle;

        public MessageVH(View itemView, final OnItemClickListener<MessageData.DataList> listener, final List<MessageData.DataList> data) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvGroupTitle = (TextView) itemView.findViewById(R.id.tv_group_title);
            ivShow = (ImageView) itemView.findViewById(R.id.tv_show);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onItemClick(view, position, data.get(position));
                }
            });
        }
    }
}
