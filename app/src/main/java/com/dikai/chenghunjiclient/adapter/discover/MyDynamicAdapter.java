package com.dikai.chenghunjiclient.adapter.discover;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnAdapterViewClickListener;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.bean.DiscoverLikeBean;
import com.dikai.chenghunjiclient.bean.RemoveDynamicBean;
import com.dikai.chenghunjiclient.dialog.DiscoverDialog;
import com.dikai.chenghunjiclient.entity.DynamicData;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.DensityUtil;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyImageView;
import com.dikai.chenghunjiclient.view.MyImageView1;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cmk03 on 2018/1/25.
 */

public class MyDynamicAdapter extends RecyclerView.Adapter<MyDynamicAdapter.MyDiscoverVH> implements View.OnClickListener {

    private Context mContext;
    private final List<DynamicData.DataList> mData;
    private boolean isLike = false;
    private OnItemClickListener<DynamicData.DataList> onItemClickListener;
    private OnAdapterViewClickListener<DynamicData.DataList> onAdapterViewClickListener;
    private OnAdapterViewClickListener<DynamicData.DataList> likeClickListener;
    private OnAdapterViewClickListener<DynamicData.DataList> removeClickListener;
    private DiscoverDialog dialog;
    private String userID;
    private final List<Integer> likeCount;
    private AlertDialog.Builder normalDialog;

    public MyDynamicAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        likeCount = new ArrayList<>();
        dialog = new DiscoverDialog(mContext);
    }

    @Override
    public MyDiscoverVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_discover, parent, false);
        MyDiscoverVH holder = new MyDiscoverVH(view, onItemClickListener, mData);
        holder.ivLike.setOnClickListener(this);
        holder.ivLike.setTag(holder);
        holder.ivShare.setOnClickListener(this);
        holder.ivShare.setTag(holder);
        holder.tvRemove.setOnClickListener(this);
        holder.tvRemove.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyDiscoverVH holder, final int position) {
        DynamicData.DataList dataList = mData.get(position);
        holder.tvRemove.setVisibility(View.VISIBLE);
//        String[] images = dataList.getImgs().split(",");
        Glide.with(mContext).load(dataList.getDynamicerHeadportrait()).into(holder.civLogo);
        holder.tvUserName.setText(dataList.getDynamicerName());
        holder.tvTime.setText(dataList.getCreateTime());

        String identity = UserManager.getInstance(mContext).getIdentity(dataList.getOccupationCode());
        holder.tvIdentity.setText(" · " + identity);
        if (dataList.getState() == 0) {
            holder.ivLike.setImageResource(R.mipmap.ic_like_nor);
        } else {
            holder.ivLike.setImageResource(R.mipmap.ic_like_pre);
        }
        holder.tvDesc.setText(dataList.getContent());
        holder.tvComment.setText(dataList.getGivethumbCount() + "人喜欢 · " + dataList.getCommentsCount() + "条评论");
        likeCount.add(position, dataList.getGivethumbCount());

//        if (images[0].equals("")) {
//            holder.llImage.setVisibility(View.GONE);
//        }
//        if (images.length == 1 && !images[0].equals("")) {
//            holder.llImage.setVisibility(View.VISIBLE);
//            Glide.with(mContext).load(images[0]).into(holder.ivShowPhoto);
//            holder.ivShowPhoto.setRatio(2);
//            holder.ivShowPhoto.setVisibility(View.VISIBLE);
//            holder.ivShowPhoto2.setVisibility(View.GONE);
//            holder.ivSHowPhoto3.setVisibility(View.GONE);
//        } else if (images.length == 2) {
//            holder.ivShowPhoto.setRatio(1);
//            holder.ivShowPhoto2.setRatio(1);
//            Glide.with(mContext).load(images[0]).into(holder.ivShowPhoto);
//            Glide.with(mContext).load(images[1]).into(holder.ivShowPhoto2);
//            holder.ivShowPhoto.setVisibility(View.VISIBLE);
//            holder.ivShowPhoto2.setVisibility(View.VISIBLE);
//            holder.ivSHowPhoto3.setVisibility(View.GONE);
//        } else if (images.length >= 3) {
//            holder.ivShowPhoto.setRatio(1);
//            holder.ivShowPhoto2.setRatio(1);
//            holder.ivSHowPhoto3.setRatio(1);
//            Glide.with(mContext).load(images[0]).into(holder.ivShowPhoto);
//            Glide.with(mContext).load(images[1]).into(holder.ivShowPhoto2);
//            Glide.with(mContext).load(images[2]).into(holder.ivSHowPhoto3);
//            holder.ivShowPhoto.setVisibility(View.VISIBLE);
//            holder.ivShowPhoto2.setVisibility(View.VISIBLE);
//            holder.ivSHowPhoto3.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View view) {
        MyDiscoverVH holder = (MyDiscoverVH) view.getTag();
        int position = holder.getAdapterPosition();
        if (view == holder.ivLike) {
            Log.e("第一步", "=======" + mData.get(position).getState());
            likeClickListener.onAdapterClick(view, position, mData.get(position));
        } else if (view == holder.ivShare) {
            onAdapterViewClickListener.onAdapterClick(view, position, mData.get(position));
        }  else if (view == holder.tvRemove) {
            removeClickListener.onAdapterClick(view, position, null);
        }
    }

    public void itemChange(int position, int like) {
        Log.e("第四步", "=======" + like);
        mData.get(position).setState(like);
        if (like == 0) {
            mData.get(position).setGivethumbCount(likeCount.get(position) - 1);
        } else {
            mData.get(position).setGivethumbCount(likeCount.get(position) + 1);
        }
        notifyItemChanged(position);
    }

    public void setOnItemClickListener(OnItemClickListener<DynamicData.DataList> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnAdapterViewClickListener(OnAdapterViewClickListener<DynamicData.DataList> onAdapterViewClickListener) {
        this.onAdapterViewClickListener = onAdapterViewClickListener;
    }

    public void refresh(List<DynamicData.DataList> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void add(List<DynamicData.DataList> list) {
        int size = mData.size();
        mData.addAll(list);
        notifyItemRangeInserted(size, list.size());
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void setLikeClickListener(OnAdapterViewClickListener<DynamicData.DataList> likeClickListener) {
        this.likeClickListener = likeClickListener;
    }

    public void setRemoveClickListener(OnAdapterViewClickListener<DynamicData.DataList> removeClickListener) {
        this.removeClickListener = removeClickListener;
    }

    static class MyDiscoverVH extends RecyclerView.ViewHolder {

        private final CircleImageView civLogo;
        private final TextView tvUserName;
        private final TextView tvTime;
        private final TextView tvIdentity;
        //private final TextView tvAttention;
        private final LinearLayout llImage;
        private final MyImageView1 ivShowPhoto;
        private final TextView tvDesc;
        private final TextView tvComment;
        private final ImageView ivLike;
        private final ImageView ivMessage;
        private final ImageView ivShare;
        private final MyImageView1 ivSHowPhoto3;
        private final MyImageView1 ivShowPhoto2;
        private final TextView tvRemove;

        public MyDiscoverVH(View itemView, final OnItemClickListener<DynamicData.DataList> listener, final List<DynamicData.DataList> data) {
            super(itemView);
            civLogo = (CircleImageView) itemView.findViewById(R.id.civ_logo);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvIdentity = (TextView) itemView.findViewById(R.id.tv_identity);
            // tvAttention = (TextView) itemView.findViewById(R.id.tv_attention);
            llImage = (LinearLayout) itemView.findViewById(R.id.ll_image);
            ivShowPhoto = (MyImageView1) itemView.findViewById(R.id.iv_show_photo);
            ivShowPhoto2 = (MyImageView1) itemView.findViewById(R.id.iv_show_photo2);
            ivSHowPhoto3 = (MyImageView1) itemView.findViewById(R.id.iv_show_photo3);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            tvComment = (TextView) itemView.findViewById(R.id.tv_comment);
            ivLike = (ImageView) itemView.findViewById(R.id.iv_like);
            ivMessage = (ImageView) itemView.findViewById(R.id.iv_message);
            ivShare = (ImageView) itemView.findViewById(R.id.iv_share);
            tvRemove = (TextView) itemView.findViewById(R.id.tv_remove);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onItemClick(view, position, data.get(position));
                }
            });
        }
    }

    interface CallBackListener {
        void onCall();
    }
}
