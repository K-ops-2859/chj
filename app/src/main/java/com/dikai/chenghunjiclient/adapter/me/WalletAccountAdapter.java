package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.WalletAccountBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/9/27.
 */

public class WalletAccountAdapter extends RecyclerView.Adapter<WalletAccountAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<WalletAccountBean> list;
    private int selectPosition = -1;

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public WalletAccountAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wallet_withdraw_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WalletAccountBean bean = list.get(position);
        if(position == selectPosition){
            holder.select.setImageResource(R.mipmap.ic_app_wallet_selected);
        }else {
            holder.select.setImageResource(R.mipmap.ic_app_wallet_unselected);
        }

        if(bean.getSourceType() == 1){//支付宝
            holder.img.setImageResource(R.mipmap.ic_app_wallet_alipay);
            holder.name.setText("支付宝");
            if(bean.getIsValid() == 0){
                holder.info.setText(bean.getNumber());
            }else {
                holder.info.setText("添加支付宝账户");
            }
        }else {//银行卡
            if(bean.getIsValid() == 0){
                holder.name.setText(bean.getAffiliatedBank());
                holder.info.setText(bean.getNumber());
                holder.img.setImageResource(R.mipmap.ic_app_wallet_card);
            }else {
                holder.name.setText("新增银行卡");
                holder.info.setText("添加新的银行卡");
                holder.img.setImageResource(R.mipmap.ic_app_add_card);
            }
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

    public void addAll(Collection<? extends WalletAccountBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends WalletAccountBean> collection){
        selectPosition = -1;
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
        mOnItemClickListener.onClick(position,list.get(position));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView name;
        private TextView info;
        private ImageView select;
        private LinearLayout mLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            info = (TextView) itemView.findViewById(R.id.info);
            img = (ImageView) itemView.findViewById(R.id.img);
            select = (ImageView) itemView.findViewById(R.id.select);
            mLayout = (LinearLayout) itemView.findViewById(R.id.bottom_layout);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int position,WalletAccountBean bean);
    }
}