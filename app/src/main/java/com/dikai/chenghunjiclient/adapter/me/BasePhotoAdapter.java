package com.dikai.chenghunjiclient.adapter.me;

import android.support.v7.widget.RecyclerView;

import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.entity.WeddingPhotoData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/3/27.
 */

public abstract class BasePhotoAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{

    protected List<T> mData = new ArrayList<>();
    protected OnItemClickListener<T> onItemClickListener;

    public List<T> getData() {
        return mData;
    }

    public void setList(List<T> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    public void setData(List<T> data) {
        mData.clear();
        append(data);
    }

    public void append(List<T> list) {
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
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
