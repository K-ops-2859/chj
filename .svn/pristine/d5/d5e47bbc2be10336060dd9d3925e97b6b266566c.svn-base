package com.dikai.chenghunjiclient.adapter.invitation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by cmk03 on 2018/10/17.
 */

public class VipInviteBusinessAdapter extends RecyclerView.Adapter<VipInviteBusinessAdapter.ContentVH> {

    private Context mContext;
    private int HEADER_VIEW = 0;
    private int CONTENT_VIEW = 1;
    private OnItemClickListener<ImageItem> onItemClickListener;
    private final ArrayList<ImageItem> mData;
    private ArrayList<ImageItem> temp;


    public VipInviteBusinessAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            mData.add(new ImageItem());
        }
    }

    @Override
    public ContentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adapter_vipinvite_business_c, parent, false);
        return new ContentVH(view, onItemClickListener, mData);
    }

    @Override
    public void onBindViewHolder(ContentVH holder, int position) {

//        if (mData.size() == 0) {
//            holder.ivUpLoad.setImageResource(R.mipmap.ic_vipinvite_bus);
//        } else {
        ImageItem item = mData.get(position);
        if (TextUtils.isEmpty(item.path)) {
            holder.ivUpLoad.setImageResource(R.mipmap.ic_vipinvite_bus);
        } else {
            Glide.with(mContext).load(item.path).centerCrop().into(holder.ivUpLoad);
        }

    }

    @Override
    public int getItemCount() {
        if (mData.size() < 3) {
            for (int i = 0; i < 3 - mData.size(); i++) {
                mData.add(new ImageItem());
            }
            return mData.size();
        } else {
            return 3;
        }
    }

    public List<String> getImagePath() {
        List<String> list = new ArrayList<>();
        for (ImageItem item : mData) {
            if (!TextUtils.isEmpty(item.path)) {
                list.add(item.path);
            }
        }
        return list;
    }

    public ArrayList<ImageItem> getData() {

        return mData;
    }

    public boolean isEmpty() {
        boolean b = true;
        for (ImageItem data : mData) {
            if (TextUtils.isEmpty(data.path)) {
                b = true;
            } else {
                b = false;
                break;
            }
        }
        return b;
    }

    public void setList(ArrayList<ImageItem> list) {
        mData.clear();
        append(list);
    }

    public void append1(ArrayList<ImageItem> list) {
        for (int i = 0; i < mData.size(); i++) {

            if (TextUtils.isEmpty(mData.get(i).path)) {
                mData.remove(i);
            }
        }
        if (mData.size() > 3) {
            for (int i = 0; i < mData.size(); i++) {
                if (i > 3) {
                    mData.remove(i);
                }
            }
        }
        int positionStart = mData.size();
        int itemCount = list.size();
        mData.addAll(list);
        notifyDataSetChanged();
//        if (positionStart > 0 && itemCount > 0) {
//            notifyItemRangeInserted(positionStart, itemCount);
//        } else {
//            notifyDataSetChanged();
//        }
    }

    public void append(ArrayList<ImageItem> list) {
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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class ContentVH extends RecyclerView.ViewHolder {

        private final ImageView ivUpLoad;

        public ContentVH(View itemView, final OnItemClickListener listener, final ArrayList<ImageItem> data) {
            super(itemView);
            ivUpLoad = (ImageView) itemView.findViewById(R.id.iv_upload);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int adapterPosition = getAdapterPosition();
                    Log.e("点击位置===", adapterPosition + "");
                    listener.onItemClick(view, adapterPosition, data);

                }
            });
        }
    }
}
