package com.dikai.chenghunjiclient.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnAdapterViewClickListener;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.dialog.DiscoverDialog;
import com.dikai.chenghunjiclient.entity.DynamicData;
import com.dikai.chenghunjiclient.view.MyImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cmk03 on 2018/5/24.
 */

public class NewMyDynamicAdapter extends RecyclerView.Adapter<NewMyDynamicAdapter.NewMyDynamicVH> implements View.OnClickListener{
    private Context mContext;
    private final List<DynamicData.DataList> mData;
    private OnItemClickListener<DynamicData.DataList> onItemClickListener;
    private OnAdapterViewClickListener<DynamicData.DataList> likeClickListener;
    private DiscoverDialog dialog;
    private final List<Integer> likeCount;

    public void setLikeClickListener(OnAdapterViewClickListener<DynamicData.DataList> likeClickListener) {
        this.likeClickListener = likeClickListener;
    }

    public NewMyDynamicAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        likeCount = new ArrayList<>();
        dialog = new DiscoverDialog(mContext);
    }

    @Override
    public NewMyDynamicVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.new_dynamic_item_layout, parent, false);
        NewMyDynamicVH holder = new NewMyDynamicVH(view, onItemClickListener, mData);
        holder.ivLike.setOnClickListener(this);
        holder.ivLike.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NewMyDynamicVH holder, final int position) {
//        DynamicData.DataList dataList = mData.get(position);
//        String[] images = dataList.getImgs().split(",");
//        holder.tvTime.setText(dataList.getCreateTime());
//        holder.tvUserName.setText(dataList.getDynamicerName());
//        if (dataList.getState() == 0) {
//            holder.ivLike.setImageResource(R.mipmap.ic_app_new_like_1);
//        } else {
//            holder.ivLike.setImageResource(R.mipmap.ic_app_new_like_2);
//        }
//        holder.tvDesc.setText(dataList.getContent());
//        holder.tvComment.setText(dataList.getGivethumbCount()+"");
//        likeCount.add(position, dataList.getGivethumbCount());
//        if(images.length > 0){
//            Glide.with(mContext).load(images[0]).into(holder.ivShowPhoto);
//        }else {
//            Glide.with(mContext).load(R.drawable.ic_app_place_img).into(holder.ivShowPhoto);
//        }
//        Glide.with(mContext).load(dataList.getDynamicerHeadportrait()).into(holder.civLogo);
    }

    @Override
    public void onClick(View view) {
        NewMyDynamicVH holder = (NewMyDynamicVH) view.getTag();
        int position = holder.getAdapterPosition();
        if (view == holder.ivLike) {
            Log.e("第一步", "=======" + mData.get(position).getState());
            likeClickListener.onAdapterClick(view, position, mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        System.out.println("====================” +" + mData.size());
        return mData.size();
    }

    private void dialogShow() {
        dialog.setOnResultChangeListener(new DiscoverDialog.OnResultChangeListener() {
            @Override
            public void resultChanged(String result) {
                Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
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

//    public void setOnAdapterViewClickListener(OnAdapterViewClickListener<DynamicData.DataList> onAdapterViewClickListener) {
//        this.onAdapterViewClickListener = onAdapterViewClickListener;
//    }

    public void refresh(List<DynamicData.DataList> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void add(List<DynamicData.DataList> list) {
        int positionStart = mData.size();
        int itemCount = list.size();
        mData.addAll(list);
        if (positionStart > 0 && itemCount > 0) {
            notifyItemRangeInserted(positionStart, itemCount);
        } else {
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    // 计算出该TextView中文字的长度(像素)
    public static float getTextViewLength(TextView textView, String text) {
        TextPaint paint = textView.getPaint();
        // 得到使用该paint写上text的时候,像素为多少
        float textLength = paint.measureText(text);
        return textLength;
    }

    static class NewMyDynamicVH extends RecyclerView.ViewHolder {

        private final CircleImageView civLogo;
        private final TextView tvUserName;
        private final TextView tvTime;
        private final MyImageView ivShowPhoto;
        private final TextView tvDesc;
        private final TextView tvComment;
        private final ImageView ivLike;
        private final TextView tvIdentity;

        public NewMyDynamicVH(View itemView, final OnItemClickListener<DynamicData.DataList> listener, final List<DynamicData.DataList> data) {
            super(itemView);
            civLogo = (CircleImageView) itemView.findViewById(R.id.civ_logo);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvIdentity = (TextView) itemView.findViewById(R.id.tv_identity);
            ivShowPhoto = (MyImageView) itemView.findViewById(R.id.iv_show_photo);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            tvComment = (TextView) itemView.findViewById(R.id.tv_comment);
            ivLike = (ImageView) itemView.findViewById(R.id.iv_like);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onItemClick(view, position, data.get(position));
                }
            });

        }
    }
}
