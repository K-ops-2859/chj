package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.ImgBean;
import com.dikai.chenghunjiclient.view.MyRelativeLayout;
import com.joooonho.SelectableRoundedImageView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/11/20.
 */

public class RoomPicsAdapter extends RecyclerView.Adapter<RoomPicsAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<ImgBean> list;

    public RoomPicsAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_new_room_pics, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImgBean bean = list.get(position);
        Glide.with(context).load(bean.getImgUrl()).override(160,160).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends ImgBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends ImgBean> collection){
        list = new ArrayList<>();
        list.addAll(collection);
        notifyDataSetChanged();
    }

    public ArrayList<String> getAllPhoto(){
        ArrayList<String> temp = new ArrayList<>();
        for (ImgBean bean : list) {
            temp.add(bean.getImgUrl());
        }
        return temp;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {
        MyViewHolder holder = (MyViewHolder) v.getTag();
        int position = holder.getAdapterPosition();
        mOnItemClickListener.onClick(position,list.get(position));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private SelectableRoundedImageView pic;
        private MyRelativeLayout mLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            pic = (SelectableRoundedImageView) itemView.findViewById(R.id.img);
            mLayout = (MyRelativeLayout) itemView.findViewById(R.id.bottom_layout);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int position, ImgBean bean);
    }
}