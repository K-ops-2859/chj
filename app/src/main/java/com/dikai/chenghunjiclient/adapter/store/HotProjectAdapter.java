package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.WebProActivity;
import com.dikai.chenghunjiclient.activity.store.WebProListActivity;
import com.dikai.chenghunjiclient.entity.GetColorProjectData;
import com.dikai.chenghunjiclient.entity.GetColorProjectData.DataList;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/1/12.
 */

public class HotProjectAdapter extends RecyclerView.Adapter<HotProjectAdapter.StorePicsViewHolder> implements View.OnClickListener {
    private Context context;
    private List<GetColorProjectData.DataList> list;

    public HotProjectAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @Override
    public HotProjectAdapter.StorePicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hot_project_layout, parent, false);
        HotProjectAdapter.StorePicsViewHolder holder = new HotProjectAdapter.StorePicsViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(HotProjectAdapter.StorePicsViewHolder holder, int position) {
        GetColorProjectData.DataList bean = list.get(position);
        holder.name.setText(bean.getPlanTitle());
        holder.info.setText(bean.getColor() + "系");
        Glide.with(context).load(list.get(position).getShowImg()).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends GetColorProjectData.DataList> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends GetColorProjectData.DataList> collection){
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
        StorePicsViewHolder holder = (StorePicsViewHolder) v.getTag();
        context.startActivity(new Intent(context,WebProActivity.class)
                .putExtra("id",list.get(holder.getAdapterPosition()).getPlanID()+""));
    }

    public static class StorePicsViewHolder extends RecyclerView.ViewHolder{
        private SelectableRoundedImageView pic;
        private TextView name;
        private TextView info;
        private LinearLayout mLayout;
        public StorePicsViewHolder(View itemView) {
            super(itemView);
            pic = (SelectableRoundedImageView) itemView.findViewById(R.id.hot_project_img);
            name = (TextView) itemView.findViewById(R.id.hot_project_name);
            info = (TextView) itemView.findViewById(R.id.hot_project_info);
            mLayout = (LinearLayout) itemView.findViewById(R.id.hot_project_layout);
        }
    }
}