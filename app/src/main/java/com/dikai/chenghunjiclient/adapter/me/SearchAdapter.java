package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.SearchCarBean;
import com.dikai.chenghunjiclient.entity.SearchCarBean;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/20.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<SearchCarBean> list;

    public SearchAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_car_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        holder.state.setTag(holder);
        holder.state.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SearchCarBean bean = list.get(position);
        holder.name.setText(bean.getTrueName());
        holder.car.setText(" - " + bean.getParent());
        holder.phone.setText(bean.getPhone());
        Glide.with(context).load(bean.getHeadportrait()).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends SearchCarBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends SearchCarBean> collection){
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
        if(v == holder.mLayout){

        }else if(v == holder.state){
            mMoreClickListener.onClick(position, list.get(position));
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView state;
        private TextView car;
        private TextView name;
        private TextView phone;
        private SelectableRoundedImageView logo;
        private LinearLayout mLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            state = (TextView) itemView.findViewById(R.id.item_driver_state);
            car = (TextView) itemView.findViewById(R.id.item_driver_car);
            name = (TextView) itemView.findViewById(R.id.item_driver_name);
            phone = (TextView) itemView.findViewById(R.id.item_driver_phone);
            logo = ((SelectableRoundedImageView) itemView.findViewById(R.id.item_driver_logo));
            mLayout = (LinearLayout) itemView.findViewById(R.id.item_driver_layout);
        }
    }

    private OnMoreClickListener mMoreClickListener;

    public void setMoreClickListener(OnMoreClickListener moreClickListener) {
        mMoreClickListener = moreClickListener;
    }

    public interface OnMoreClickListener{
        void onClick(int position, SearchCarBean bean);
    }
}