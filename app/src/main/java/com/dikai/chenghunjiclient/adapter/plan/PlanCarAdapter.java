package com.dikai.chenghunjiclient.adapter.plan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.PlanCarBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/23.
 */

public class PlanCarAdapter  extends RecyclerView.Adapter<PlanCarAdapter.MyViewHolder> implements View.OnClickListener {
    
    private Context context;
    private List<PlanCarBean> list;
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public PlanCarAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_plan_car_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PlanCarBean bean = list.get(position);
        holder.name.setText(bean.getName());
        holder.color.setText(bean.getColor());
        holder.num.setText("× "+bean.getCt());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends PlanCarBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends PlanCarBean> collection){
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
       
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView color;
        private TextView num;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_plan_car_name);
            color = (TextView) itemView.findViewById(R.id.item_plan_car_color);
            num = (TextView) itemView.findViewById(R.id.item_plan_car_num);
        }
    }

}
