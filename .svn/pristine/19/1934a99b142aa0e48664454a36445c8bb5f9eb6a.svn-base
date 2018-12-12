package com.dikai.chenghunjiclient.adapter.plan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.PlanFlowBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/21.
 */

public class PlanFlowAdapter extends RecyclerView.Adapter<PlanFlowAdapter.MyViewHolder> implements View.OnClickListener {

    private int[] colors = new int[]{0xffed5565,0xffffce54,0xff37bc9b,0xff3bafda,0xffd770ad,0xffaab2bd,0xffe9573f,0xff8cc152,
            0xffac92ec,0xff656d78,0xffDC596E,0xff003570,0xffF0DEB0,0xffB05E41,0xffEBD1DA,0xffC9DE23,0xff7BCEA9};
    private Context context;
    private List<PlanFlowBean> list;
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public PlanFlowAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_flow_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PlanFlowBean bean = list.get(position);
        holder.start.setText(bean.getBeginTime());
        holder.end.setText(bean.getEndTime());
        holder.content.setText(bean.getContent());
        holder.color.setBackgroundColor(colors[position % colors.length]);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends PlanFlowBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends PlanFlowBean> collection){
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
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private View color;
        private TextView start;
        private TextView end;
        private TextView content;

        public MyViewHolder(View itemView) {
            super(itemView);
            start = (TextView) itemView.findViewById(R.id.item_flow_start);
            end = (TextView) itemView.findViewById(R.id.item_flow_end);
            content = (TextView) itemView.findViewById(R.id.item_flow_content);
            color = itemView.findViewById(R.id.item_flow_color);
        }
    }

}