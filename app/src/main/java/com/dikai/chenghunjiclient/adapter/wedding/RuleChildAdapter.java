package com.dikai.chenghunjiclient.adapter.wedding;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2017/12/13.
 */

public class RuleChildAdapter extends RecyclerView.Adapter<RuleChildAdapter.RuleChildVH> {

    private Context mContext;
    private final List<String> mData;

    public RuleChildAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public RuleChildVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_rule_child, parent, false);
        return new RuleChildVH(view);
    }

    @Override
    public void onBindViewHolder(RuleChildVH holder, int position) {
            holder.tvContent.setText(mData.get(position));
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

    static class RuleChildVH extends RecyclerView.ViewHolder {

        private final TextView tvContent;

        public RuleChildVH(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_rule_content);
        }
    }
}
