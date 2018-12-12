package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.CaigoujieSupBean;
import com.dikai.chenghunjiclient.entity.CaigoujieSupBean;
import com.dikai.chenghunjiclient.view.MyImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/7/5.
 */

public class CaigoujieSupAdapter extends RecyclerView.Adapter<CaigoujieSupAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<CaigoujieSupBean> list;

    public CaigoujieSupAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.caigoujie_supplier_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.more.setTag(holder);
        holder.more.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CaigoujieSupBean bean = list.get(position);
        holder.name.setText(bean.getExhibitorName());
        holder.type.setText(bean.getWeddingClass());
        Glide.with(context).load(bean.getExhibitorLogo()).into(holder.logo);
//        Glide.with(context).load("http://www.chenghunji.com/Download/User/caigoujie.png").into(holder.bg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends CaigoujieSupBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends CaigoujieSupBean> collection){
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
        private ImageView more;
        private ImageView logo;
        private MyImageView bg;
        private TextView name;
        private TextView type;

        public MyViewHolder(View itemView) {
            super(itemView);
            more = (ImageView) itemView.findViewById(R.id.more_info);
            logo = (ImageView) itemView.findViewById(R.id.logo);
//            bg = (MyImageView) itemView.findViewById(R.id.bg);
            name = (TextView) itemView.findViewById(R.id.name);
            type = (TextView) itemView.findViewById(R.id.type);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(CaigoujieSupBean bean);
    }
}