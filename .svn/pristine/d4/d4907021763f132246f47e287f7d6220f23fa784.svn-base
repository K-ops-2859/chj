package com.dikai.chenghunjiclient.adapter.plan;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.IdentityBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/5.
 */

public class SelectIdentityAdapter extends RecyclerView.Adapter<SelectIdentityAdapter.MyViewHolder> implements View.OnClickListener {

    private int selected = -1;
    private Context context;
    private List<IdentityBean> list;

    public SelectIdentityAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_identity_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        IdentityBean bean = list.get(position);
        holder.name.setText(bean.getOccupationName());
        if(position == selected){
            holder.mLayout.setBackgroundResource(R.drawable.text_identity_bg_selected);
            holder.name.setTextColor(ContextCompat.getColor(context,R.color.white));
        }else {
            holder.mLayout.setBackgroundResource(R.drawable.text_identity_bg_unselect);
            holder.name.setTextColor(ContextCompat.getColor(context,R.color.gray_text));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends IdentityBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends IdentityBean> collection){
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
        selected = holder.getAdapterPosition();
        notifyDataSetChanged();
        mItemClickListener.onClick(list.get(selected));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private RelativeLayout mLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_identity_name);
            mLayout = (RelativeLayout) itemView.findViewById(R.id.item_identity_layout);
        }
    }

    private OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(IdentityBean bean);
    }
}