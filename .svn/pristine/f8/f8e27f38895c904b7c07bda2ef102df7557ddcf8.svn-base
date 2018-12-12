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
import com.dikai.chenghunjiclient.entity.GoodsItemBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/4/18.
 */

public class StoreItemAdapter extends RecyclerView.Adapter<StoreItemAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<GoodsItemBean> list;

    public StoreItemAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wedding_store_list_content, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GoodsItemBean bean = list.get(position);
        holder.name.setText(bean.getCommodityName());
        holder.price.setText("￥"+bean.getQuota());
        Glide.with(context).load(bean.getCoverMap()).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends GoodsItemBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends GoodsItemBean> collection){
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
        mOnCarClickListener.onClick(list.get(position));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView price;
        private ImageView pic;
        private LinearLayout mLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            pic = (ImageView) itemView.findViewById(R.id.img);
            price = (TextView) itemView.findViewById(R.id.price);
            mLayout = (LinearLayout) itemView.findViewById(R.id.bottom_layout);
        }
    }
    //
    private OnItemClickListener mOnCarClickListener;

    public void setOnItemClickListener(OnItemClickListener onCarClickListener) {
        mOnCarClickListener = onCarClickListener;
    }

    public interface OnItemClickListener{
        void onClick(Object bean);
    }
}