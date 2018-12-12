package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.entity.GetAPData;
import com.dikai.chenghunjiclient.entity.UserCouponListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/3/1.
 */

public class YouHuiAdapter extends RecyclerView.Adapter<YouHuiAdapter.YouHuiAdapterVH> {

    private Context mContext;
    private List<UserCouponListData.DataList> mData;
    private OnItemClickListener<UserCouponListData.DataList> onItemClickListener;

    public YouHuiAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public YouHuiAdapterVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_youhuit, parent, false);
        return  new YouHuiAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(YouHuiAdapterVH holder, int position) {
        UserCouponListData.DataList dataList = mData.get(position);
        holder.tvMoney.setText(dataList.getMoney());
        holder.tvCondition.setText("满"+ dataList.getConditionMoney() + "元");
        holder.tvDate.setText("有效期至" + dataList.getExpirTtime());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public void setList(List<UserCouponListData.DataList> list) {
        mData.clear();
        append(list);
    }

    public void append(List<UserCouponListData.DataList> list) {
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    static class YouHuiAdapterVH extends RecyclerView.ViewHolder {

        private final TextView tvMoney;
        private final TextView tvCondition;
        private final TextView tvDate;

        public YouHuiAdapterVH(View itemView) {
            super(itemView);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_money);
            tvCondition = (TextView) itemView.findViewById(R.id.tv_condition);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
