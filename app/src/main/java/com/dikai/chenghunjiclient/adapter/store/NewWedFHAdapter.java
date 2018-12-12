package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.GoodsItemBean;
import com.dikai.chenghunjiclient.entity.GoodsTypeBean;
import com.dikai.chenghunjiclient.entity.ResultGetGoodListHead;
import com.dikai.chenghunjiclient.view.MyImageView;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/8/21.
 */

public class NewWedFHAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int HEAD = 0;
    private static final int GROUP = 1;
    private static final int LIST = 2;
    private Context context;
    private List<Object> list;
    private boolean isFanhuan = true;

    public void setFanhuan(boolean fanhuan) {
        isFanhuan = fanhuan;
    }

    public NewWedFHAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HEAD){
            View view = LayoutInflater.from(context).inflate(R.layout.item_new_fh_head_layout, parent, false);
            HeadViewHolder holder = new HeadViewHolder(view);
            holder.apply.setTag(holder);
            holder.apply.setOnClickListener(this);
            holder.rule.setTag(holder);
            holder.rule.setOnClickListener(this);
            return holder;
        }else if(viewType == GROUP){
            View view = LayoutInflater.from(context).inflate(R.layout.item_wed_fh_group_layout, parent, false);
            GroupViewHolder holder = new GroupViewHolder(view);
            holder.more.setTag(holder);
            holder.more.setOnClickListener(this);
            return holder;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_wed_fh_list_layout, parent, false);
            ListViewHolder holder = new ListViewHolder(view);
            holder.mLayout.setTag(holder);
            holder.mLayout.setOnClickListener(this);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeadViewHolder){
            if(isFanhuan){
                ((HeadViewHolder) holder).apply.setVisibility(View.VISIBLE);
            }else {
                ((HeadViewHolder) holder).apply.setVisibility(View.GONE);
            }
            ResultGetGoodListHead head = (ResultGetGoodListHead) list.get(position);
            ((HeadViewHolder) holder).money.setText(head.getIsUse() == 1? "已使用":head.getQuota());
            Glide.with(context).load(head.getHeadImg()).into(((HeadViewHolder) holder).bg);
        }else if(holder instanceof GroupViewHolder){
            GoodsTypeBean bean = (GoodsTypeBean) list.get(position);
            ((GroupViewHolder) holder).name.setText(bean.getTypeName());
        }else if(holder instanceof ListViewHolder){
            GoodsItemBean bean = (GoodsItemBean) list.get(position);
            Glide.with(context).load(bean.getCoverMap()).into(((ListViewHolder) holder).img);
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

    public void addAll(Collection<? extends Object> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends Object> collection){
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
        RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
        int position = holder.getAdapterPosition();
        if (holder instanceof HeadViewHolder){
            if(v == ((HeadViewHolder) holder).apply){
                mOnItemClickListener.onClick(0,list.get(position));
            }else if(v == ((HeadViewHolder) holder).rule){
                mOnItemClickListener.onClick(1,list.get(position));
            }
        }else if(holder instanceof GroupViewHolder){
            if(v == ((GroupViewHolder) holder).more){
                mOnItemClickListener.onClick(2,list.get(position));
            }
        }else if(holder instanceof ListViewHolder){
            if(v == ((ListViewHolder) holder).mLayout){
                mOnItemClickListener.onClick(3,list.get(position));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof ResultGetGoodListHead){
            return HEAD;
        }else if(list.get(position) instanceof GoodsTypeBean){
            return GROUP;
        }else{
            return LIST;
        }
    }

    public static class HeadViewHolder extends RecyclerView.ViewHolder{
        private TextView money;
        private TextView rule;
        private TextView apply;
        private MyImageView bg;
        public HeadViewHolder(View itemView) {
            super(itemView);
            money = (TextView) itemView.findViewById(R.id.money);
            rule = (TextView) itemView.findViewById(R.id.rule);
            apply = (TextView) itemView.findViewById(R.id.apply);
            bg = (MyImageView) itemView.findViewById(R.id.bg);
        }
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView more;

        public GroupViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.group_name);
            more = (TextView) itemView.findViewById(R.id.group_more);
        }
    }


    public static class ListViewHolder extends RecyclerView.ViewHolder{
        private MyImageView img;
        private RelativeLayout mLayout;

        public ListViewHolder(View itemView) {
            super(itemView);
            img = (MyImageView) itemView.findViewById(R.id.img);
            mLayout = (RelativeLayout) itemView.findViewById(R.id.bottom_layout);
        }
    }


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int type, Object bean);
    }
}
