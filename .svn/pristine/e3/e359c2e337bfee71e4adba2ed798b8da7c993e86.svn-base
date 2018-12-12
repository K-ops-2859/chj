package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.ProjectInfoActivity;
import com.dikai.chenghunjiclient.entity.CasesBean;
import com.dikai.chenghunjiclient.view.MyImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/11.
 */

public class HotelPicAdapter extends RecyclerView.Adapter<HotelPicAdapter.StorePicsViewHolder> implements View.OnClickListener {
    private Context context;
    private List<CasesBean> list;
    private String supId;

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public HotelPicAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @Override
    public StorePicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_project_list_layout, parent, false);
        StorePicsViewHolder holder = new StorePicsViewHolder(view);
        holder.mLinearLayout.setTag(holder);
        holder.mLinearLayout.setOnClickListener(this);
        return holder;

    }

    @Override
    public void onBindViewHolder(StorePicsViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getCoverMap())
                .centerCrop()
                .error(R.color.gray_background)
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends CasesBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends CasesBean> collection){
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
        int position = holder.getAdapterPosition();
        context.startActivity(new Intent(context, ProjectInfoActivity.class)
                .putExtra("sup",supId).putExtra("case",list.get(position).getCaseID()));
    }

    public static class StorePicsViewHolder extends RecyclerView.ViewHolder{

        private ImageView pic;
        private LinearLayout mLinearLayout;

        public StorePicsViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.item_project_layout);
            pic = (ImageView) itemView.findViewById(R.id.item_project_img);
        }
    }

}


