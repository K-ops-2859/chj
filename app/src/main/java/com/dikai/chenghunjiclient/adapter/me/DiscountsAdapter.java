package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.DiscountsBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/11/19.
 */

public class DiscountsAdapter extends RecyclerView.Adapter<DiscountsAdapter.MyViewHolder> implements View.OnClickListener {

    private boolean canDelete;
    private Context context;
    private List<DiscountsBean> list;

    public DiscountsAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
        notifyDataSetChanged();
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_discounts_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.close.setTag(holder);
        holder.close.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DiscountsBean bean = list.get(position);
        holder.title.setText("优惠" + (position + 1));
        holder.content.setText(bean.getDiscount());
        if(canDelete){
            holder.close.setVisibility(View.VISIBLE);
        }else {
            holder.close.setVisibility(View.GONE);
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

    public void addAll(Collection<? extends DiscountsBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends DiscountsBean> collection){
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
        mOnItemClickListener.onClick(position, list.get(position));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView content;
        private ImageView close;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            close = (ImageView) itemView.findViewById(R.id.close);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int position,DiscountsBean bean);
    }
}