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
import com.dikai.chenghunjiclient.entity.MessageData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/5/9.
 */

public class WeddingNoteAdapter extends RecyclerView.Adapter<WeddingNoteAdapter.WeddintNoteVH> {

    private Context mContext;
    private final List<MessageData.DataList> mData;
    private OnItemClickListener<MessageData.DataList> onItemClickListener;

    public WeddingNoteAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public WeddintNoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_wedding_note, parent, false);
        return  new WeddingNoteAdapter.WeddintNoteVH(view, onItemClickListener, mData);
    }

    @Override
    public void onBindViewHolder(WeddintNoteVH holder, int position) {
        MessageData.DataList dataList = mData.get(position);
        holder.tvTitle.setText(dataList.getWeddingInformationTitle());
        holder.tvDesc.setText(dataList.getTitle());
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
        notifyDataSetChanged();
//        if (positionStart > 0 && itemCount > 0) {
//            notifyItemRangeInserted(positionStart, itemCount);
//        } else {
//
//        }
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    static class WeddintNoteVH extends RecyclerView.ViewHolder {

        private final ImageView ivShow;
        private final TextView tvDesc;
        private final TextView tvTitle;

        public WeddintNoteVH(View itemView, final OnItemClickListener<MessageData.DataList> listener, final List<MessageData.DataList> dataLists) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            ivShow = (ImageView) itemView.findViewById(R.id.iv_show);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemClick(v, position - 1, dataLists.get(position-1));
                }
            });
        }
    }
}
