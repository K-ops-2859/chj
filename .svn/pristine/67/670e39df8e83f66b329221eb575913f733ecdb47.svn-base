package com.dikai.chenghunjiclient.adapter.discover;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.DynamicData;
import com.dikai.chenghunjiclient.entity.LikePersonData;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cmk03 on 2018/1/8.
 */

public class DynamicLikeAdapter extends RecyclerView.Adapter<DynamicLikeAdapter.DynamicDetailsVH> {

    private Context mContext;
    private List<LikePersonData.DataList> mData;

    public DynamicLikeAdapter(Context context) {
        this.mContext = context;
        //  this.mData = data;
        mData = new ArrayList<>();
    }

    @Override
    public DynamicDetailsVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.adapter_dynamic_like, parent, false);
        return new DynamicDetailsVH(view);
    }

    @Override
    public void onBindViewHolder(DynamicDetailsVH holder, int position) {
        Glide.with(mContext).load(mData.get(position).getGivethumbHeadportrait())
                .error(R.color.gray_background)
                .into(holder.civLogo);
    }

    @Override
    public int getItemCount() {
        return mData.size() < 5 ? mData.size() : 5;
    }

    public void setList(List<LikePersonData.DataList> list) {
        mData.clear();
        append(list);
    }

    public void append(List<LikePersonData.DataList> list) {
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

    static class DynamicDetailsVH extends RecyclerView.ViewHolder {

        private final CircleImageView civLogo;

        public DynamicDetailsVH(View itemView) {
            super(itemView);
            civLogo = (CircleImageView) itemView.findViewById(R.id.civ_logo);
        }
    }

    static class FooterVH extends RecyclerView.ViewHolder {

        public FooterVH(View itemView) {
            super(itemView);
        }
    }
}
