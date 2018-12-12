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
import com.dikai.chenghunjiclient.activity.store.NewArticleActivity;
import com.dikai.chenghunjiclient.activity.store.NewsActivity;
import com.dikai.chenghunjiclient.entity.ZixunBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/1/15.
 */

public class ZixunAdapter extends RecyclerView.Adapter<ZixunAdapter.StorePicsViewHolder> implements View.OnClickListener {
    private Context context;
    private List<ZixunBean> list;

    public ZixunAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @Override
    public ZixunAdapter.StorePicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_zixun_layout, parent, false);
        ZixunAdapter.StorePicsViewHolder holder = new ZixunAdapter.StorePicsViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ZixunAdapter.StorePicsViewHolder holder, int position) {
        ZixunBean bean = list.get(position);
        holder.name.setText(bean.getTitle());
        holder.content.setText(bean.getTextContent());
        holder.type.setText(bean.getWeddingInformationTitle());
        Glide.with(context).load(list.get(position).getShowImg()).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends ZixunBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends ZixunBean> collection){
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
        Intent intent = new Intent(context, NewArticleActivity.class);
        intent.putExtra("news", list.get(position).getInformationArticleID());
        context.startActivity(intent);
    }

    public static class StorePicsViewHolder extends RecyclerView.ViewHolder{
        private ImageView pic;
        private TextView name;
        private TextView type;
        private TextView content;
        private LinearLayout mLayout;
        public StorePicsViewHolder(View itemView) {
            super(itemView);
            pic = (ImageView) itemView.findViewById(R.id.zixun_img);
            name = (TextView) itemView.findViewById(R.id.zixun_name);
            type = (TextView) itemView.findViewById(R.id.zixun_type);
            content = (TextView) itemView.findViewById(R.id.zixun_content);
            mLayout = (LinearLayout) itemView.findViewById(R.id.zixun_layout);
        }
    }
}