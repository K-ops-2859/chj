package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.AccountBean;
import com.dikai.chenghunjiclient.util.UserManager;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/11/13.
 */

public class AccountAdpater  extends RecyclerView.Adapter<AccountAdpater.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<AccountBean> list;

    public AccountAdpater(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_switch_ids_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AccountBean bean = list.get(position);
        if(bean.isADD()){
            holder.addLayout.setVisibility(View.VISIBLE);
            holder.nowLayout.setVisibility(View.GONE);
        }else {
            holder.nowLayout.setVisibility(View.VISIBLE);
            holder.addLayout.setVisibility(View.GONE);
            holder.name.setText(bean.getUserName());
            holder.phone.setText(bean.getUserPhone());
            if(bean.getUserId().equals(UserManager.getInstance(context).getNewUserInfo().getUserId())){
                holder.state.setText("当前登录账号");
                holder.state.setTextColor(ContextCompat.getColor(context,R.color.red_new));
            }else {
                holder.state.setText("点击切换账号");
                holder.state.setTextColor(ContextCompat.getColor(context,R.color.gray_text_1));
            }
            Glide.with(context).load(bean.getUserLogo()).into(holder.logo);
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

    public void addAll(Collection<? extends AccountBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends AccountBean> collection){
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
        private TextView name;
        private TextView phone;
        private TextView state;
        private SelectableRoundedImageView logo;
        private CardView mLayout;
        private LinearLayout nowLayout;
        private LinearLayout addLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            state = (TextView) itemView.findViewById(R.id.state);
            logo = (SelectableRoundedImageView) itemView.findViewById(R.id.logo);
            mLayout = (CardView) itemView.findViewById(R.id.bottom_layout);
            nowLayout = (LinearLayout) itemView.findViewById(R.id.now_layout);
            addLayout = (LinearLayout) itemView.findViewById(R.id.add_layout);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(AccountBean bean);
    }
}