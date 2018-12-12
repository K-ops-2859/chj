package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.CarInfoActivity;
import com.dikai.chenghunjiclient.activity.store.CorpInfoActivity;
import com.dikai.chenghunjiclient.activity.store.HotelInfoActivity;
import com.dikai.chenghunjiclient.entity.SupHolderBean;
import com.dikai.chenghunjiclient.util.UserManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/5/22.
 */

public class SupHolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Object> list;
    private String title;
    private String code;

    public void setTitle(String title,String code) {
        this.title = title;
        this.code = code;
    }

    public SupHolderAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.item_sup_holder_head, parent, false);
            HeadViewHolder holder = new HeadViewHolder(view);
            return holder;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_sup_holder_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            holder.mLayout.setTag(holder);
            holder.mLayout.setOnClickListener(this);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeadViewHolder){
            ((HeadViewHolder) holder).title.setText("成婚纪推荐"+title);
        }else {
            SupHolderBean bean = (SupHolderBean) list.get(position);
            ((MyViewHolder) holder).name.setText(bean.getName());
            if("6000000000000001".equals(bean.getSupplierID())){
                ((MyViewHolder) holder).area.setText("全国");
            }else {
                ((MyViewHolder) holder).area.setText(bean.getRegionName());
            }
            ((MyViewHolder) holder).info.setText("案例 "+bean.getTotalCount()+"        动态 "+bean.getStateCount());
            Glide.with(context).load(bean.getHeadportrait()).into(((MyViewHolder) holder).pic);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0? 0:1;
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends Object> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends Object> collection){
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
        if(UserManager.getInstance(context).isLogin()){
            SupHolderBean bean = (SupHolderBean) list.get(position);
            if("SF_1001000".equals(bean.getProfessionID())){//酒店
                context.startActivity(new Intent(context, HotelInfoActivity.class)
                        .putExtra("id", bean.getSupplierID()).putExtra("userid",bean.getUserID()));
            }else if("SF_2001000".equals(bean.getProfessionID())){//婚车
                context.startActivity(new Intent(context, CarInfoActivity.class)
                        .putExtra("id", bean.getSupplierID()).putExtra("userid",bean.getUserID()));
            }else if("SF_14001000".equals(bean.getProfessionID())){//婚庆
                context.startActivity(new Intent(context, CorpInfoActivity.class)
                        .putExtra("id", bean.getSupplierID())
                        .putExtra("type",1).putExtra("userid",bean.getUserID()));
            }else {//其他
                context.startActivity(new Intent(context, CorpInfoActivity.class)
                        .putExtra("id", bean.getSupplierID())
                        .putExtra("type",0)
                        .putExtra("userid",bean.getUserID()));
            }
        }else {
            Toast.makeText(context, "请先登陆！", Toast.LENGTH_SHORT).show();
        }
    }

    public static class HeadViewHolder extends RecyclerView.ViewHolder{
        private TextView title;

        public HeadViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView info;
        private TextView area;
        private ImageView pic;
        private CardView mLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            area = (TextView) itemView.findViewById(R.id.area);
            info = (TextView) itemView.findViewById(R.id.info);
            pic = (ImageView) itemView.findViewById(R.id.img);
            mLayout = (CardView) itemView.findViewById(R.id.bottom_layout);
        }
    }
    //
    private OnItemClickListener mOnCarClickListener;

    public void setOnItemClickListener(OnItemClickListener onCarClickListener) {
        mOnCarClickListener = onCarClickListener;
    }

    public interface OnItemClickListener{
        void onClick(SupHolderBean bean);
    }
}