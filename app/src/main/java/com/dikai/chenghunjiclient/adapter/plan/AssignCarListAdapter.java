package com.dikai.chenghunjiclient.adapter.plan;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.AssignCarBean;
import com.dikai.chenghunjiclient.entity.AssignCarBean;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/22.
 */

public class AssignCarListAdapter   extends RecyclerView.Adapter<AssignCarListAdapter.MyViewHolder> implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Context context;
    private List<AssignCarBean> list;
    private List<String> ids;

    public AssignCarListAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        ids = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_assign_driver_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        holder.call.setTag(holder);
        holder.call.setOnClickListener(this);
        holder.mCheckBox.setTag(holder);
        holder.mCheckBox.setOnCheckedChangeListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AssignCarBean bean = list.get(position);
        holder.name.setText(bean.getDriverName());
        holder.phone.setText(bean.getDriverPhone());
        Glide.with(context).load(bean.getDriverImg()).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void remove(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends AssignCarBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends AssignCarBean> collection){
        list = new ArrayList<>();
        list.addAll(collection);
        notifyDataSetChanged();
    }
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        MyViewHolder holder = (MyViewHolder) buttonView.getTag();
        int position = holder.getAdapterPosition();
        if(isChecked){
            ids.add(list.get(position).getDriverID());
        }else {
            ids.remove(list.get(position).getDriverID());
        }
        mChangeListener.onChecked(ids.size());
    }

    @Override
    public void onClick(View v) {
        MyViewHolder holder = (MyViewHolder) v.getTag();
        int position = holder.getAdapterPosition();
        if(v == holder.mLayout){

        }else if(v == holder.call){
            mMoreClickListener.onClick(holder.call, position, list.get(position));
        }
    }

    public List<String> getIds() {
        return ids;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView phone;
        private ImageView call;
        private AppCompatCheckBox mCheckBox;
        private SelectableRoundedImageView logo;
        private LinearLayout mLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            mCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.item_driver_check);
            name = (TextView) itemView.findViewById(R.id.item_driver_name);
            phone = (TextView) itemView.findViewById(R.id.item_driver_phone);
            call = (ImageView) itemView.findViewById(R.id.item_driver_call);
            logo = ((SelectableRoundedImageView) itemView.findViewById(R.id.item_driver_logo));
            mLayout = (LinearLayout) itemView.findViewById(R.id.item_driver_layout);
        }
    }

    private OnMoreClickListener mMoreClickListener;

    public void setMoreClickListener(OnMoreClickListener moreClickListener) {
        mMoreClickListener = moreClickListener;
    }

    public interface OnMoreClickListener{
        void onClick(View view, int position, AssignCarBean bean);
    }

    private OnCheckedChangeListener mChangeListener;

    public void setChangeListener(OnCheckedChangeListener changeListener) {
        mChangeListener = changeListener;
    }

    public interface OnCheckedChangeListener{
        void onChecked(int size);
    }
}
