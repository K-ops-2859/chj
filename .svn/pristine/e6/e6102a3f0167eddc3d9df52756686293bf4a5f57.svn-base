package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.ZixunTagBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/9/5.
 */

public class ZixunTabAdapter extends RecyclerView.Adapter<ZixunTabAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<ZixunTagBean> list;
    private int selectPosition = 0;

    public ZixunTabAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_zixun_tabs, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.name.setTag(holder);
        holder.name.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ZixunTagBean bean = list.get(position);
        if(position == selectPosition){
            holder.name.setBackgroundResource(R.drawable.text_bg_pink_jianbian);
            holder.name.setTextColor(ContextCompat.getColor(context,R.color.white));
        }else {
            holder.name.setBackgroundResource(R.drawable.text_bg_gray_light);
            holder.name.setTextColor(ContextCompat.getColor(context,R.color.black_three));
        }
        holder.name.setText(bean.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends ZixunTagBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends ZixunTagBean> collection){
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
        selectPosition = holder.getAdapterPosition();
        notifyDataSetChanged();
        mOnItemClickListener.onClick(selectPosition, list.get(selectPosition));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tab);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int position, ZixunTagBean bean);
    }
}