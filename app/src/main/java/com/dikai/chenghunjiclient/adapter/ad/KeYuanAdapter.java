package com.dikai.chenghunjiclient.adapter.ad;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.KeYuanBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/10/29.
 */

public class KeYuanAdapter extends RecyclerView.Adapter<KeYuanAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<KeYuanBean> list;

    public KeYuanAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_keyuan_list_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        KeYuanBean bean = list.get(position);
        holder.name.setText(bean.getName());
        holder.phone.setText(bean.getPhone());
        holder.date.setText(bean.getWeddingTime());
        holder.identity.setText(bean.getIdentity());
        holder.table.setText(bean.getTablesNumber());
        holder.price.setText(bean.getMealMark());
        holder.time.setText(bean.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends KeYuanBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends KeYuanBean> collection){
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
//        mOnItemClickListener.onClick(list.get(position));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView date;
        private TextView identity;
        private TextView phone;
        private TextView table;
        private TextView price;
        private TextView time;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            identity = (TextView) itemView.findViewById(R.id.identity);
            phone = (TextView) itemView.findViewById(R.id.phone);
            table = (TextView) itemView.findViewById(R.id.table);
            price = (TextView) itemView.findViewById(R.id.price);
            time = (TextView) itemView.findViewById(R.id.time);
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