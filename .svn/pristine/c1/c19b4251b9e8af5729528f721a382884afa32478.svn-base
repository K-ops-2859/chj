package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.RoomPhotoActivity;
import com.dikai.chenghunjiclient.entity.RoomBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/16.
 */

public class EditRoomAdapter extends RecyclerView.Adapter<EditRoomAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<RoomBean> list;

    public EditRoomAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_edit_room_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        holder.more.setTag(holder);
        holder.more.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RoomBean bean = list.get(position);
        holder.name.setText(bean.getBanquetHallName());
        holder.num.setText(bean.getMaxTableCount()+"桌");
//        holder.height.setText("最低价位：" + bean.getFloorPrice());
        Glide.with(context).load(bean.getHotelLogo()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends RoomBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void remove(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void refresh(Collection<? extends RoomBean> collection){
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
            context.startActivity(new Intent(context, RoomPhotoActivity.class)
                    .putExtra("id",list.get(position).getBanquetID()));
        }else if(v == holder.more){
            mMoreClickListener.onClick(holder.mark, position, list.get(position));
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView height;
        private TextView num;
        private ImageView img;
        private ImageView more;
        private LinearLayout mLayout;
        private View mark;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_hotel_room_name);
            num = (TextView) itemView.findViewById(R.id.item_hotel_room_num);
            height = (TextView) itemView.findViewById(R.id.item_hotel_room_height);
            img = ((ImageView) itemView.findViewById(R.id.item_hotel_room_img));
            more = ((ImageView) itemView.findViewById(R.id.item_hotel_room_more));
            mLayout = (LinearLayout) itemView.findViewById(R.id.item_hotel_room_layout);
            mark = itemView.findViewById(R.id.item_hotel_room_mark);
        }
    }
    private  OnMoreClickListener mMoreClickListener;

    public void setMoreClickListener(OnMoreClickListener moreClickListener) {
        mMoreClickListener = moreClickListener;
    }

    public interface OnMoreClickListener{
        void onClick(View view, int position, RoomBean bean);
    }
}