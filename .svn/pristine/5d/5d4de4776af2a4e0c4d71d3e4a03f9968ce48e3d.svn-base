package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.BoomRecordBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/4/2.
 */

public class BoomRecordAdapter extends RecyclerView.Adapter<BoomRecordAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<BoomRecordBean> list;

    public BoomRecordAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_boom_location_record, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BoomRecordBean bean = list.get(position);
        holder.prize.setText(bean.getGlass()==1?"小桶爆米花":"大桶爆米花");
        holder.date.setText(bean.getCreateTime());
        holder.location.setText(bean.getShopAdress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends BoomRecordBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends BoomRecordBean> collection){
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
//        mOnCarClickListener.onClick(list.get(position));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView prize;
        private TextView date;
        private TextView location;
        private CardView mLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            prize = (TextView) itemView.findViewById(R.id.prize);
            date = (TextView) itemView.findViewById(R.id.date);
            location = (TextView) itemView.findViewById(R.id.location);
            mLayout = (CardView) itemView.findViewById(R.id.group_layout);
        }
    }
    //
//    private OnItemClickListener mOnCarClickListener;
//
//    public void setOnItemClickListener(OnItemClickListener onCarClickListener) {
//        mOnCarClickListener = onCarClickListener;
//    }
//
//    public interface OnItemClickListener{
//        void onClick(BoomRecordBean bean);
//    }
}