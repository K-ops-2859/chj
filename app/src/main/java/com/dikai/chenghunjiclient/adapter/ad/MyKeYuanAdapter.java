package com.dikai.chenghunjiclient.adapter.ad;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.KeYuanBean;
import com.dikai.chenghunjiclient.entity.SupKeYuanBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/11/1.
 */

public class MyKeYuanAdapter extends RecyclerView.Adapter<MyKeYuanAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<SupKeYuanBean> list;

    public MyKeYuanAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyKeYuanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_keyuan_me_layout, parent, false);
        MyKeYuanAdapter.MyViewHolder holder = new MyKeYuanAdapter.MyViewHolder(view);
        holder.remarkLayout.setTag(holder);
        holder.remarkLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyKeYuanAdapter.MyViewHolder holder, int position) {
        SupKeYuanBean bean = list.get(position);
        holder.name.setText(bean.getName());
        holder.phone.setText(bean.getPhone());
        holder.date.setText(bean.getWeddingTime());
        holder.identity.setText("["+bean.getIdentity()+"]");
        holder.remark.setText(bean.getMeno().equals("")?"无备注":bean.getMeno());
        if (bean.isRemarkOpen()){
            holder.remarkImg.setImageResource(R.mipmap.ic_app_up_white);
            holder.remark.setVisibility(View.VISIBLE);
            holder.line.setVisibility(View.GONE);
        }else {
            holder.remarkImg.setImageResource(R.mipmap.ic_app_down_white);
            holder.remark.setVisibility(View.GONE);
            holder.line.setVisibility(View.VISIBLE);
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

    public void addAll(Collection<? extends SupKeYuanBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends SupKeYuanBean> collection){
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
        MyKeYuanAdapter.MyViewHolder holder = (MyKeYuanAdapter.MyViewHolder) v.getTag();
        int position = holder.getAdapterPosition();
        list.get(position).setRemarkOpen(!list.get(position).isRemarkOpen());
        notifyItemChanged(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView date;
        private TextView identity;
        private TextView phone;
        private TextView remark;
        private LinearLayout remarkLayout;
        private ImageView remarkImg;
        private View line;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            identity = (TextView) itemView.findViewById(R.id.identity);
            phone = (TextView) itemView.findViewById(R.id.phone);
            remark = (TextView) itemView.findViewById(R.id.remark);
            remarkLayout = (LinearLayout) itemView.findViewById(R.id.remark_layout);
            remarkImg = (ImageView) itemView.findViewById(R.id.remark_img);
            line = itemView.findViewById(R.id.line);
        }
    }
//    //
//    private OnItemClickListener mOnItemClickListener;
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        mOnItemClickListener = onItemClickListener;
//    }
//
//    public interface OnItemClickListener{
//        void onClick(KeYuanBean bean);
//    }
}
