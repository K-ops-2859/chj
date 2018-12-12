package com.dikai.chenghunjiclient.adapter.invitation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.entity.InviteFRData;
import com.dikai.chenghunjiclient.util.DensityUtil;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/10/15.
 */

public class ComInviteRecordAdapter extends RecyclerView.Adapter<ComInviteRecordAdapter.ComInviteRecordVH> {

    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private final List<InviteFRData.DataList> mData;

    public ComInviteRecordAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public ComInviteRecordVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_cominvite_record, parent, false);
        return new ComInviteRecordVH(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(ComInviteRecordVH holder, int position) {
        InviteFRData.DataList list = mData.get(position);
        holder.tvName.setText(list.getName());
        holder.tvPhone.setText(list.getPhone());
        if (list.getIsMakeMoney() == 0) {
            holder.tvType.setText("未打款");
        } else {
            holder.tvType.setText("已打款");
        }
        if (list.getMoney() == 0) {
            holder.llMoneyRoot.setVisibility(View.GONE);
        } else {
            holder.llMoneyRoot.setVisibility(View.VISIBLE);
            holder.tvMoney.setText(list.getMoney() + "");
        }
        if (list.getAuditStatus() == 0) {
            holder.tvState.setText("未审核");
        } else if (list.getAuditStatus() == 1) {
            holder.tvState.setText("已审核");
        } else {
            holder.tvState.setText("审核未通过");
        }
        if (list.getRanking() == 1) {
            holder.ivRank.setImageResource(R.mipmap.ic_sort_1);
            holder.ivRank.setVisibility(View.VISIBLE);
            holder.tvRank.setVisibility(View.GONE);
        } else if (list.getRanking() == 2) {
            holder.ivRank.setVisibility(View.VISIBLE);
            holder.tvRank.setVisibility(View.GONE);
            holder.ivRank.setImageResource(R.mipmap.ic_sort_2);
        } else if (list.getRanking() == 3) {
            holder.ivRank.setVisibility(View.VISIBLE);
            holder.tvRank.setVisibility(View.GONE);
            holder.ivRank.setImageResource(R.mipmap.ic_sort_3);
        } else {
            holder.ivRank.setVisibility(View.GONE);
            holder.tvRank.setVisibility(View.VISIBLE);
            holder.tvRank.setText(list.getRanking() + "");
        }

        Log.e("排名====", list.getRanking() + "");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setList(List<InviteFRData.DataList> list) {
        mData.clear();
        append(list);
    }

    public void append(List<InviteFRData.DataList> list) {
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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class ComInviteRecordVH extends RecyclerView.ViewHolder {

        private final TextView tvState;
        private final TextView tvPhone;
        private final ImageView ivRank;
        private final TextView tvType;
        private final TextView tvMoney;
        private final TextView tvName;
        private final TextView tvRank;
        private final LinearLayout llMoneyRoot;

        public ComInviteRecordVH(final View itemView, final OnItemClickListener listener) {
            super(itemView);
            final LinearLayout llRoot = (LinearLayout) itemView.findViewById(R.id.ll_root);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_money);
            tvType = (TextView) itemView.findViewById(R.id.tv_type);
            ivRank = (ImageView) itemView.findViewById(R.id.iv_rank);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvState = (TextView) itemView.findViewById(R.id.tv_state);
            tvRank = (TextView) itemView.findViewById(R.id.tv_rank);
            llMoneyRoot = (LinearLayout) itemView.findViewById(R.id.ll_money_root);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onItemClick(view, getAdapterPosition(), null);
//                }
//            });
        }
    }
}
