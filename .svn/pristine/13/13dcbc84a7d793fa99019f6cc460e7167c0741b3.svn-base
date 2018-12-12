package com.dikai.chenghunjiclient.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.DynamicData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lucio on 2018/5/25.
 */

public class TrendsAdapter extends RecyclerView.Adapter<TrendsAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<DynamicData.DataList> list;

    public TrendsAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_dynamic_item_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        holder.ivLike.setTag(holder);
        holder.ivLike.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DynamicData.DataList bean = list.get(position);
        holder.tvTime.setText(bean.getCreateTime());
        holder.tvUserName.setText(bean.getDynamicerName());
        if (bean.getState() == 0) {
            holder.ivLike.setImageResource(R.mipmap.ic_app_new_like_1);
        } else {
            holder.ivLike.setImageResource(R.mipmap.ic_app_new_like_2);
        }
        holder.tvDesc.setText(bean.getContent());
        holder.tvComment.setText(bean.getGivethumbCount()+"");

        if(bean.getFileType() == 2){
            Glide.with(context).load(bean.getCoverImg()).into(holder.coverPic);
            holder.videoLogo.setVisibility(View.VISIBLE);
        }else {
            String[] images = bean.getFileId().split(",");
            holder.videoLogo.setVisibility(View.GONE);
            if(images.length > 0){
                Glide.with(context).load(images[0]).into(holder.coverPic);
            }else {
                Glide.with(context).load(R.drawable.ic_app_place_img).into(holder.coverPic);
            }
        }
        Glide.with(context).load(bean.getDynamicerHeadportrait()).into(holder.civLogo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void itemChange(int position, int state, int num){
        list.get(position).setState(state);
        list.get(position).setGivethumbCount(num);
        notifyItemChanged(position);
    }

    public void addAll(Collection<? extends DynamicData.DataList> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends DynamicData.DataList> collection){
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
            mOnItemClickListener.onClick(position,0, list.get(position));
        }else if(v == holder.ivLike){
            mOnItemClickListener.onClick(position,1, list.get(position));
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView civLogo;
        private TextView tvUserName;
        private TextView tvTime;
        private ImageView coverPic;
        private TextView tvDesc;
        private TextView tvComment;
        private ImageView ivLike;
        //        private TextView tvIdentity;
        private LinearLayout mLayout;
        private ImageView videoLogo;

        public MyViewHolder(View itemView) {
            super(itemView);
            civLogo = (CircleImageView) itemView.findViewById(R.id.civ_logo);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
//            tvIdentity = (TextView) itemView.findViewById(R.id.tv_identity);
            coverPic = (ImageView) itemView.findViewById(R.id.iv_show_photo);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            tvComment = (TextView) itemView.findViewById(R.id.tv_comment);
            ivLike = (ImageView) itemView.findViewById(R.id.iv_like);
            videoLogo = (ImageView) itemView.findViewById(R.id.iv_video_logo);
            mLayout = (LinearLayout) itemView.findViewById(R.id.bottom_layout);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onCarClickListener) {
        mOnItemClickListener = onCarClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int position, int type, DynamicData.DataList bean);
    }
}