package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.WalletInfoBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/9/28.
 */

public class WithdrawRecordAdapter extends RecyclerView.Adapter<WithdrawRecordAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<WalletInfoBean> list;

    public WithdrawRecordAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_withdraw_record_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WalletInfoBean bean = list.get(position);
        holder.money.setText("￥ " + bean.getMoney());
        holder.source.setText(bean.getAccountNumber());
        holder.date.setText(bean.getCreateTime());
        switch (bean.getSourceType()){
            case 0:
                holder.type.setText("商家充值");
                break;
            case 1:
                holder.type.setText("伴手礼");
                break;
            case 2:
                holder.type.setText("婚礼返还");
                break;
            case 3:
                holder.type.setText("代收");
                break;
            case 4:
                holder.type.setText("提现 - " + (bean.getAccountType() == 0?"银行卡":"支付宝"));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends WalletInfoBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends WalletInfoBean> collection){
        list = new ArrayList<>();
        list.addAll(collection);
        notifyDataSetChanged();
    }
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {
        MyViewHolder holder = (MyViewHolder) v.getTag();
        int position = holder.getAdapterPosition();
        mOnItemClickListener.onClick(list.get(position));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView type;
        private TextView money;
        private TextView source;
        private TextView date;

        public MyViewHolder(View itemView) {
            super(itemView);
            type = (TextView) itemView.findViewById(R.id.type);
            money = (TextView) itemView.findViewById(R.id.money);
            source = (TextView) itemView.findViewById(R.id.source);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(WalletInfoBean bean);
    }
}