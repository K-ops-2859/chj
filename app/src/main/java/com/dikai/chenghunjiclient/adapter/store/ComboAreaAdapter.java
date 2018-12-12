package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.ComboAreaBean;
import com.dikai.chenghunjiclient.entity.ComboAreaBean;
import com.dikai.chenghunjiclient.view.MyImageView;
import com.dikai.chenghunjiclient.view.MyRelativeLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/6/14.
 */

public class ComboAreaAdapter extends RecyclerView.Adapter<ComboAreaAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<ComboAreaBean> list;

    public ComboAreaAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_combo_area_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.all.setTag(R.id.img_1,holder);
        holder.all.setOnClickListener(this);
        holder.pic1.setTag(R.id.img_1,holder);
        holder.pic1.setOnClickListener(this);
        holder.pic2.setTag(R.id.img_1,holder);
        holder.pic2.setOnClickListener(this);
        holder.pic3.setTag(R.id.img_1,holder);
        holder.pic3.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ComboAreaBean bean = list.get(position);
        holder.pic1.setVisibility(View.GONE);
        holder.pic2.setVisibility(View.GONE);
        holder.pic3.setVisibility(View.GONE);
        ImageView[] pics = {holder.pic1,holder.pic2,holder.pic3};
        for (int i = 0; i < bean.getImageData().size() && i < 3; i++) {
            pics[i].setVisibility(View.VISIBLE);
            Glide.with(context).load(bean.getImageData().get(i).getImage()).into(pics[i]);
        }
        holder.name.setText(bean.getAreaName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends ComboAreaBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends ComboAreaBean> collection){
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
        MyViewHolder holder = (MyViewHolder) v.getTag(R.id.img_1);
        int position = holder.getAdapterPosition();
        if(v == holder.all){
            mOnItemClickListener.onClick(3,position,list.get(position));
        }else if(v == holder.pic1){
            mOnItemClickListener.onClick(0,position,list.get(position));
        }else if(v == holder.pic2){
            mOnItemClickListener.onClick(1,position,list.get(position));
        }else if(v == holder.pic3){
            mOnItemClickListener.onClick(2,position,list.get(position));
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView all;
        private ImageView pic1;
        private ImageView pic2;
        private ImageView pic3;
//        private MyRelativeLayout mLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            all = (TextView) itemView.findViewById(R.id.all);
            pic1 = (ImageView) itemView.findViewById(R.id.img_1);
            pic2 = (ImageView) itemView.findViewById(R.id.img_2);
            pic3 = (ImageView) itemView.findViewById(R.id.img_3);
//            mLayout = (MyRelativeLayout) itemView.findViewById(R.id.item_case_layout);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int type, int position, ComboAreaBean bean);
    }
}