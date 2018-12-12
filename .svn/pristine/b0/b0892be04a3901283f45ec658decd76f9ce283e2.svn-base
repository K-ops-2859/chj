package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dikai.chenghunjiclient.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/11/22.
 */

public class SupcanbiaoAdapter extends RecyclerView.Adapter<SupcanbiaoAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<String> list;

    public SupcanbiaoAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sup_canbiao_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.delete.setTag(holder);
        holder.delete.setOnClickListener(this);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String bean = list.get(position);
        holder.price.setText(bean);
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
    public void itemChange(String value, int position){
        list.remove(position);
        list.add(position,value);
        notifyItemChanged(position);
    }
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public String getAllCanbiao(){
        String canbiao = "";
        for (int i = 0; i < list.size(); i++) {
            if (i < list.size() - 1) {
                canbiao = canbiao + list.get(i) + ",";
            } else {
                canbiao = canbiao + list.get(i);
            }
        }
        return canbiao;
    }

    @Override
    public void onClick(View v) {
        MyViewHolder holder = (MyViewHolder) v.getTag();
        int position = holder.getAdapterPosition();
        if(v == holder.delete){
            mOnItemClickListener.onClick(true,position,list.get(position));
        }else if(v == holder.mLayout){
            mOnItemClickListener.onClick(false,position,list.get(position));
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView price;
        private ImageView delete;
        private LinearLayout mLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.price);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            mLayout = (LinearLayout) itemView.findViewById(R.id.bottom_layout);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(boolean isDelete, int position, String bean);
    }
}