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
import com.dikai.chenghunjiclient.entity.OrderBean;
import com.joooonho.SelectableRoundedImageView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/15.
 */

public class ClearAdapter extends RecyclerView.Adapter<ClearAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<OrderBean> list;
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public ClearAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_clear_list_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.tvRemove.setTag(holder);
        holder.tvRemove.setOnClickListener(this);
        holder.ivCall.setTag(holder);
        holder.ivCall.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrderBean bean = list.get(position);
        if(type == 1){
            holder.tvRemove.setVisibility(View.VISIBLE);
        }else {
            holder.tvRemove.setVisibility(View.GONE);
        }
        holder.tvUserName.setText(bean.getCorpNamePhone());
        holder.tvTitle.setText(bean.getCorpName());
        holder.tvDate.setText(bean.getWeddingDate());
        holder.tvDate2.setText(bean.getCorpRummeryAddress());
        Glide.with(context).load(bean.getCorpLogo()).into(holder.ivUserLogo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends OrderBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends OrderBean> collection){
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
        if(v == holder.tvRemove){
            mOnCarClickListener.onClick(position, list.get(position));
        }else if(v == holder.ivCall){
            mCallClickListener.onClick(list.get(position));
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvRemove;
        private SelectableRoundedImageView ivUserLogo;
        private TextView tvTitle;
        private TextView tvUserName;
        private TextView tvDate;
        private TextView tvDate2;
        private ImageView ivCall;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivUserLogo = (SelectableRoundedImageView) itemView.findViewById(R.id.iv_user_logo);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvDate2 = (TextView) itemView.findViewById(R.id.tv_date2);
            tvRemove = (TextView) itemView.findViewById(R.id.tv_remove);
            ivCall = (ImageView) itemView.findViewById(R.id.iv_call);
        }
    }

    private OnCarClickListener mOnCarClickListener;

    public void setOnCarClickListener(OnCarClickListener onCarClickListener) {
        mOnCarClickListener = onCarClickListener;
    }

    public interface OnCarClickListener{
        void onClick(int position, OrderBean bean);
    }

    private OnCallClickListener mCallClickListener;

    public void setCallClickListener(OnCallClickListener callClickListener) {
        mCallClickListener = callClickListener;
    }

    public interface OnCallClickListener{
        void onClick(OrderBean bean);
    }
}
