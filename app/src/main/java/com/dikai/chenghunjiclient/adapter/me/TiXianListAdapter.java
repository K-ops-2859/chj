package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.GetAPData;
import com.dikai.chenghunjiclient.entity.UserCouponListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/3/2.
 */

public class TiXianListAdapter extends RecyclerView.Adapter<TiXianListAdapter.TiXianListVH> {

    private Context mContext;
    private final List<GetAPData.DataList> mData;

    public TiXianListAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public TiXianListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_tixian_list, parent, false);
        return new TiXianListVH(view);
    }

    @Override
    public void onBindViewHolder(TiXianListVH holder, int position) {
        GetAPData.DataList dataList = mData.get(position);
        holder.tvMoney.setText(dataList.getMoney() + "元");
        if (dataList.getState() == 0) {
            holder.tvState.setText("待审核");
            holder.tvState.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        } else if (dataList.getState() == 1) {
            holder.tvState.setText("审核通过");
            holder.tvState.setTextColor(ContextCompat.getColor(mContext, R.color.green));
        } else {
            holder.tvState.setText("驳回");
            holder.tvState.setTextColor(ContextCompat.getColor(mContext, R.color.red_deep));
        }
        holder.tvAccount.setText(" (" + dataList.getAccountCode() + ")");
        holder.tvDate.setText(dataList.getCreateTime());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setList(List<GetAPData.DataList> list) {
        mData.clear();
        append(list);
    }

    public void append(List<GetAPData.DataList> list) {
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
    }

    public static class TiXianListVH extends RecyclerView.ViewHolder {

        private final TextView tvMoney;
        private final TextView tvAccount;
        private final TextView tvDate;
        private final TextView tvState;

        public TiXianListVH(View itemView) {
            super(itemView);

            tvMoney = (TextView) itemView.findViewById(R.id.tv_money);
            tvAccount = (TextView) itemView.findViewById(R.id.tv_account);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvState = (TextView) itemView.findViewById(R.id.tv_state);
        }
    }
}
