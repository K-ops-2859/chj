package com.dikai.chenghunjiclient.adapter.store;

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
import com.dikai.chenghunjiclient.entity.CarsBean;
import com.dikai.chenghunjiclient.entity.CarsBean;
import com.dikai.chenghunjiclient.entity.CasesBean;
import com.dikai.chenghunjiclient.tongxunlu.CarBean;
import com.dikai.chenghunjiclient.view.MyImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/13.
 */

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private List<CarsBean> list;

    public CarsAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_list_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CarsBean bean = list.get(position);
        holder.name.setText(bean.getParent());
        holder.num.setText("数量：" + bean.getNumBer());
        Glide.with(context).load(bean.getImg()).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends CarsBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends CarsBean> collection){
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

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView pic;
        private LinearLayout mLayout;
        private TextView name;
        private TextView num;
        public MyViewHolder(View itemView) {
            super(itemView);
            pic = (ImageView) itemView.findViewById(R.id.item_car_img);
            name = (TextView) itemView.findViewById(R.id.item_car_name);
            num = (TextView) itemView.findViewById(R.id.item_car_num);
            mLayout = (LinearLayout) itemView.findViewById(R.id.item_car_layout);
        }
    }

}

