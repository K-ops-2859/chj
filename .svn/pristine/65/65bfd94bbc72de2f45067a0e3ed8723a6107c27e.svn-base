package com.dikai.chenghunjiclient.adapter.wedding;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.entity.WeddingKnowData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2017/12/21.
 */

public class WeddingKonwAdapter extends RecyclerView.Adapter<WeddingKonwAdapter.WeddingKonwVH> {

    private Context mContext;
    private final List<WeddingKnowData> mData;
    private OnItemClickListener<WeddingKnowData> onItemClickListener;

    public WeddingKonwAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();

    }
    @Override
    public WeddingKonwAdapter.WeddingKonwVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_wedding_konw, parent, false);
        return new WeddingKonwVH(view, onItemClickListener, mData);
    }

    @Override
    public void onBindViewHolder(WeddingKonwAdapter.WeddingKonwVH holder, int position) {
        WeddingKnowData data = mData.get(position);
        holder.ivShow.setImageResource(data.getImage());
        holder.tvTitle.setText(data.getTitle());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setList(List<WeddingKnowData> list) {
        mData.clear();
        append(list);
    }

    public void append(List<WeddingKnowData> list) {
        int startPosition = mData.size();
        int itemCount = list.size();
        mData.addAll(list);
        if (startPosition > 0 && itemCount >0) {
            notifyItemRangeInserted(startPosition, itemCount);
        } else {
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        notifyItemRemoved(0);
    }

    public void setOnItemClickListener(OnItemClickListener<WeddingKnowData> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class WeddingKonwVH extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final ImageView ivShow;

        public WeddingKonwVH(View itemView, final OnItemClickListener<WeddingKnowData> listener, final List<WeddingKnowData> datas) {
            super(itemView);
            ivShow = (ImageView) itemView.findViewById(R.id.iv_show);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onItemClick(view, position, datas.get(position));
                }
            });
        }
    }
}
