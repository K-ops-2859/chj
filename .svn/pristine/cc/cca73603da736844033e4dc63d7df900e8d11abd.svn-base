package com.dikai.chenghunjiclient.adapter.me;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dikai.chenghunjiclient.adapter.OnItemClickListener;

/**
 * Created by cmk03 on 2018/3/27.
 */

public class BasePhotoViewHolder<T> extends RecyclerView.ViewHolder {

    public OnItemClickListener<T> listener;
    public T t;

    public BasePhotoViewHolder(View itemView) {
        super(itemView);
        this.listener = listener;
        this.t = t;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                listener.onItemClick(view, position, t);
            }
        });
    }


}
