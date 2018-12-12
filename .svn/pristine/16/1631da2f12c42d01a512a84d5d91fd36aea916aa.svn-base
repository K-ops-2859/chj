package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.entity.PhotoDetailsData;
import com.dikai.chenghunjiclient.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cmk03 on 2018/3/20.
 */

public class FailAdapter extends RecyclerView.Adapter<FailAdapter.FailVH> {

    private Context mContext;
    private final List<PhotoDetailsData.DataList> mData;
    private OnItemClickListener<PhotoDetailsData.DataList> onItemClickListener;
    private OnAdapterClickListener<PhotoDetailsData.DataList, FailVH> onAdapterClickListener;

    public FailAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public FailVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_fail, parent, false);
        FailVH holder = new FailVH(view, mContext, mData, onItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final FailVH holder, final int position) {
        PhotoDetailsData.DataList dataList = mData.get(position);
        Glide.with(mContext).load(dataList.getFileUrl())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .thumbnail(0.1f)
                .error(R.color.blue).into(holder.ivShow);
        holder.tvName.setText(dataList.getId() + "");
        if (dataList.getStatus() == 2) {
            holder.ivDis.setVisibility(View.VISIBLE);
        } else {
            holder.ivDis.setVisibility(View.GONE);
        }
//        holder.ivShow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onAdapterClickListener.onClick(view,holder, mData.get(position));
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setList(List<PhotoDetailsData.DataList> list) {
        mData.clear();
        append(list);
    }

    public void append(List<PhotoDetailsData.DataList> list) {
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

    public ArrayList<PhotoDetailsData.DataList> getData() {
        ArrayList<PhotoDetailsData.DataList> lists = new ArrayList<>(mData);
        return lists;
    }

    public void setOnItemClickListener(OnItemClickListener<PhotoDetailsData.DataList> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public  static class FailVH extends RecyclerView.ViewHolder {

        public final ImageView ivShow;
        private final TextView tvName;
        private final ImageView ivDis;

        public FailVH(View itemView, Context context, final List<PhotoDetailsData.DataList> data, final OnItemClickListener<PhotoDetailsData.DataList> listener) {
            super(itemView);
            int screenWidth = DensityUtil.getScreenWidth(context);
            int width = (screenWidth) / 3;
            ivShow = (ImageView) itemView.findViewById(R.id.iv_show);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            ivDis = (ImageView) itemView.findViewById(R.id.iv_dis);
            ViewGroup.LayoutParams layoutParams = ivShow.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = width;
            ivShow.setLayoutParams(layoutParams);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onItemClick(view, position, data.get(position));
                }
            });
        }
    }

    public interface OnAdapterClickListener<T, H> {
        void onClick(View view, H holder, T t);
    }
}
