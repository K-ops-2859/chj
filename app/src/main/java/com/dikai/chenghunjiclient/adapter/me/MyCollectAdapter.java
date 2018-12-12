package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.CarInfoActivity;
import com.dikai.chenghunjiclient.activity.store.CorpInfoActivity;
import com.dikai.chenghunjiclient.activity.store.HotelInfoActivity;
import com.dikai.chenghunjiclient.entity.CollectionBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/17.
 */

public class MyCollectAdapter extends RecyclerView.Adapter<MyCollectAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<CollectionBean> list;

    public MyCollectAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_collect_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CollectionBean bean = list.get(position);
        holder.name.setText(bean.getCollectionTitle());
        Glide.with(context).load(bean.getCollectionLogo()).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends CollectionBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends CollectionBean> collection){
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
        switch (list.get(position).getProfessionID()){
            case "SF_1001000":
                context.startActivity(new Intent(context, HotelInfoActivity.class)
                        .putExtra("id", list.get(position).getTypeID()));
                break;
            case "SF_2001000":
                context.startActivity(new Intent(context, CarInfoActivity.class)
                        .putExtra("id", list.get(position).getTypeID()));
                break;
            case "SF_14001000":
                context.startActivity(new Intent(context, CorpInfoActivity.class)
                        .putExtra("id", list.get(position).getTypeID()).putExtra("type",1));
                break;
            default:
                context.startActivity(new Intent(context, CorpInfoActivity.class)
                        .putExtra("id", list.get(position).getTypeID()).putExtra("type",0));
                break;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private ImageView logo;
        private LinearLayout mLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_my_collect_name);
            logo = (ImageView) itemView.findViewById(R.id.item_my_collect_img);
            mLayout = (LinearLayout) itemView.findViewById(R.id.item_my_collect_layout);
        }
    }
//
//    private OnCarClickListener mOnCarClickListener;
//
//    public void setOnCarClickListener(OnCarClickListener onCarClickListener) {
//        mOnCarClickListener = onCarClickListener;
//    }
//
//    public interface OnCarClickListener{
//        void onClick(CollectionBean bean);
//    }
}
