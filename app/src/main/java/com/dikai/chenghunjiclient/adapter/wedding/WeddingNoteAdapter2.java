package com.dikai.chenghunjiclient.adapter.wedding;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.MessageData;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by cmk03 on 2018/5/16.
 */

public class WeddingNoteAdapter2 extends RecyclerArrayAdapter<MessageData.DataList> {
    public WeddingNoteAdapter2(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(parent);
    }



    public class VH extends BaseViewHolder<MessageData.DataList> {
        private final ImageView ivShow;
        private final TextView tvDesc;
        private final TextView tvTitle;

        public VH(ViewGroup parent) {
            super(parent, R.layout.adapter_wedding_note);
            tvTitle = $(R.id.tv_title);
            tvDesc = $(R.id.tv_desc);
            ivShow = $(R.id.iv_show);
        }

        @Override
        public void setData(MessageData.DataList data) {
            super.setData(data);
            tvTitle.setText(data.getWeddingInformationTitle());
            tvDesc.setText(data.getTitle());
            Glide.with(getContext()).load(data.getShowImg()).centerCrop()
                    .placeholder(R.color.gray_background).error(R.color.gray_background).into(ivShow);
        }
    }
}
