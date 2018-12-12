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
import com.dikai.chenghunjiclient.entity.CombosBean;
import com.dikai.chenghunjiclient.view.MyImageView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

/**
 * Created by Lucio on 2018/6/14.
 */

public class CombosAdapter extends RecyclerView.Adapter<CombosAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<CombosBean> list;

    public CombosAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_taocan, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CombosBean bean = list.get(position);
        holder.name.setText(bean.getName());
        if(bean.getLabel() != null){
            List<String> label = Arrays.asList(bean.getLabel().split(","));
            holder.tags.setTags(label);
        }else {
            List<String> label = Arrays.asList("".split(","));
            holder.tags.setTags(label);
        }
        Glide.with(context).load(bean.getCoverMap()).into(holder.pic);
        holder.price.setText("¥" + bean.getPresentPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends CombosBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends CombosBean> collection){
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
        private TextView name;
        private TextView price;
        private ImageView pic;
        private TagContainerLayout tags;
        private LinearLayout mLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_desc);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            pic = (ImageView) itemView.findViewById(R.id.img);
            tags = (TagContainerLayout) itemView.findViewById(R.id.item_project_tag);
            mLayout = (LinearLayout) itemView.findViewById(R.id.bottom_layout);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(CombosBean bean);
    }
}