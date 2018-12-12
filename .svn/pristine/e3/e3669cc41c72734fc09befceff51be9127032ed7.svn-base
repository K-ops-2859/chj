package com.dikai.chenghunjiclient.adapter.wedding;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2017/12/21.
 */

public class WeddingKonwListAdapter extends RecyclerView.Adapter<WeddingKonwListAdapter.WeddingKonwListVH> {

    private Context mContext;
    private List<String> mData;
    private OnItemClickListener<String> onItemClickListener;

    public WeddingKonwListAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }
    @Override
    public WeddingKonwListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_wedding_konw_layout, parent, false);
        return new WeddingKonwListVH(view, onItemClickListener, mData);
    }

    @Override
    public void onBindViewHolder(WeddingKonwListVH holder, int position) {
        holder.tvName.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public void setList(List<String> list) {
        mData = list;
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<String> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class WeddingKonwListVH extends RecyclerView.ViewHolder {

        private final TextView tvName;

        public WeddingKonwListVH(View itemView, final OnItemClickListener<String> listener, final List<String> data) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(view, pos, data.get(pos));
                }
            });
        }
    }
}
