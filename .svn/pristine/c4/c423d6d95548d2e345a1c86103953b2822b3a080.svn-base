package com.dikai.chenghunjiclient.adapter.plan;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.AssignDriverBean;
import com.joooonho.SelectableRoundedImageView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/22.
 */

public class AssignDriverAdapter  extends RecyclerView.Adapter<AssignDriverAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<AssignDriverBean> list;

    public AssignDriverAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_assign_car_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        holder.call.setTag(holder);
        holder.call.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AssignDriverBean bean = list.get(position);
        holder.name.setText(bean.getName());
        holder.car.setText(" - " + bean.getModelName());
        holder.phone.setText(bean.getPhoneNo());
        if("0".equals(bean.getAnswerStatus())){
            holder.state.setTextColor(ContextCompat.getColor(context, R.color.gray_text));
            holder.state.setText("待接受");
        }else if("1".equals(bean.getAnswerStatus())){
            holder.state.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.state.setText("已接受");
        }else if("2".equals(bean.getAnswerStatus())){
            holder.state.setTextColor(ContextCompat.getColor(context, R.color.red_money));
            holder.state.setText("已拒绝");
        }
        Glide.with(context).load(bean.getImg()).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void remove(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends AssignDriverBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends AssignDriverBean> collection){
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
        if(v == holder.mLayout){

        }else if(v == holder.call){
            mMoreClickListener.onClick(holder.call, position, list.get(position));
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView state;
        private TextView car;
        private TextView name;
        private TextView phone;
        private ImageView call;
        private SelectableRoundedImageView logo;
        private LinearLayout mLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            state = (TextView) itemView.findViewById(R.id.item_driver_state);
            car = (TextView) itemView.findViewById(R.id.item_driver_car);
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
        void onClick(View view, int position, AssignDriverBean bean);
    }
}
