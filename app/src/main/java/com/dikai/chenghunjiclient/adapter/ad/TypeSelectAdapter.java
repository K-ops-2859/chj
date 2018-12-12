package com.dikai.chenghunjiclient.adapter.ad;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.NewIdentityBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/8/20.
 */

public class TypeSelectAdapter extends RecyclerView.Adapter<TypeSelectAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<NewIdentityBean> list;
    private int selectPosition = 0;

    public TypeSelectAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_type_select_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.name.setTag(holder);
        holder.name.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewIdentityBean bean = list.get(position);
        if(position == selectPosition){
            holder.name.setBackgroundResource(R.drawable.text_bg_red_new);
            holder.name.setTextColor(ContextCompat.getColor(context,R.color.white));
        }else {
            holder.name.setBackgroundResource(R.drawable.text_bg_gray_line2);
            holder.name.setTextColor(ContextCompat.getColor(context,R.color.black));
        }
        holder.name.setText(bean.getOccupationName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends NewIdentityBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends NewIdentityBean> collection){
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
        selectPosition = holder.getAdapterPosition();
        notifyDataSetChanged();
        mOnItemClickListener.onClick(list.get(selectPosition));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.type);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(NewIdentityBean bean);
    }
}