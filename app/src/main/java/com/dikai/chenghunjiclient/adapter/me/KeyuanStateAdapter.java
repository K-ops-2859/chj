package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/11/27.
 */

public class KeyuanStateAdapter extends RecyclerView.Adapter<KeyuanStateAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<String> list;
    private int nowPosition;

    public KeyuanStateAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_keyuan_state_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String bean = list.get(position);
        holder.state.setText(bean);
        if(position == nowPosition){
            holder.state.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            holder.pic.setVisibility(View.VISIBLE);
        }else {
            holder.state.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            holder.pic.setVisibility(View.INVISIBLE);
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

    public void addAll(Collection<? extends String> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends String> collection){
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
        nowPosition = position;
        notifyDataSetChanged();
        mOnItemClickListener.onClick(position,list.get(position));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView state;
        private ImageView pic;
        private LinearLayout mLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            state = (TextView) itemView.findViewById(R.id.state);
            pic = (ImageView) itemView.findViewById(R.id.img);
            mLayout = (LinearLayout) itemView.findViewById(R.id.bottom_layout);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int position, String bean);
    }
}