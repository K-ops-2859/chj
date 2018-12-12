package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.PicEndApdapter;
import com.dikai.chenghunjiclient.adapter.PicsAdapter;
import com.dikai.chenghunjiclient.entity.ActivityHotelBean;
import com.dikai.chenghunjiclient.entity.HotelAdEndImg;
import com.dikai.chenghunjiclient.entity.HotelAdImg;
import com.dikai.chenghunjiclient.entity.ActivityHotelBean;
import com.dikai.chenghunjiclient.util.UserManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/6/1.
 */

public class HotelADAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<Object> list;
    private String location;
    private String headImg;
    private List<HotelAdImg> StartImgData;
    private List<HotelAdEndImg> EndImgListData;

    public void setStartImgData(List<HotelAdImg> startImgData) {
        StartImgData = startImgData;
    }

    public void setEndImgListData(List<HotelAdEndImg> endImgListData) {
        EndImgListData = endImgListData;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public HotelADAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.item_hotel_ad_head, parent, false);
            HeadViewHolder holder = new HeadViewHolder(view);
            holder.mRightLayout.setTag(holder);
            holder.mRightLayout.setOnClickListener(this);
            holder.rule.setTag(holder);
            holder.rule.setOnClickListener(this);
            holder.headPics.setLayoutManager(new LinearLayoutManager(context));
            holder.headPics.setNestedScrollingEnabled(false);
            return holder;
        }else if(viewType == 1){
            View view = LayoutInflater.from(context).inflate(R.layout.item_hotel_ad_layout, parent, false);
            StorePicsViewHolder holder = new StorePicsViewHolder(view);
            holder.mLayout.setTag(holder);
            holder.mLayout.setOnClickListener(this);
            return holder;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_hotel_ad_end, parent, false);
            EndViewHolder holder = new EndViewHolder(view);
            holder.endPics.setNestedScrollingEnabled(false);
            holder.endPics.setLayoutManager(new LinearLayoutManager(context));
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof StorePicsViewHolder){
            ActivityHotelBean bean = (ActivityHotelBean) list.get(position);
            if(!bean.isNoData()){
                ((StorePicsViewHolder) holder).place.setVisibility(View.GONE);
                ((StorePicsViewHolder) holder).mLayout.setVisibility(View.VISIBLE);
                if(bean.getXfyl() == 0){
                    ((StorePicsViewHolder)holder).gift.setVisibility(View.GONE);
                }else {
                    ((StorePicsViewHolder)holder).gift.setVisibility(View.VISIBLE);
                }
                if(bean.getHldb() == 0){
                    ((StorePicsViewHolder)holder).vImg.setVisibility(View.GONE);
                }else {
                    ((StorePicsViewHolder)holder).vImg.setVisibility(View.VISIBLE);
                }
                ((StorePicsViewHolder)holder).name.setText(bean.getName());
                ((StorePicsViewHolder)holder).ad.setText(bean.getDescribe());
                ((StorePicsViewHolder)holder).info.setText("案例 " + bean.getAnliCount()+"     状态 "+bean.getStateCount());
                Glide.with(context).load(bean.getLogo()).into(((StorePicsViewHolder) holder).pic);
            }else {
                ((StorePicsViewHolder) holder).place.setVisibility(View.VISIBLE);
                ((StorePicsViewHolder) holder).mLayout.setVisibility(View.GONE);
            }
        }else if(holder instanceof HeadViewHolder){
            ((HeadViewHolder)holder).area.setText(location);
            PicsAdapter mPicAdapter = new PicsAdapter(context);
            ((HeadViewHolder) holder).headPics.setAdapter(mPicAdapter);
            mPicAdapter.refresh(StartImgData);
            Glide.with(context).load(headImg).into(((HeadViewHolder)holder).img);
        }else if(holder instanceof EndViewHolder){
            PicEndApdapter endApdapter = new PicEndApdapter(context);
            ((EndViewHolder) holder).endPics.setAdapter(endApdapter);
            endApdapter.refresh(EndImgListData);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends Object> collection){
        int size = list.size();
        list.remove(list.size() - 1);
        list.addAll(collection);
        list.add("");
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends Object> collection){
        list = new ArrayList<>();
        list.addAll(collection);
        notifyDataSetChanged();
    }

    public void refreshItem(int position){
        notifyItemChanged(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 0;
        }else if(position == list.size() - 1){
            return 2;
        }else {
            return 1;
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {
        RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
        int position = holder.getAdapterPosition();
        if(UserManager.getInstance(context).isLogin()){
            if(holder instanceof HeadViewHolder){
                if(v == ((HeadViewHolder) holder).mRightLayout){
                    mOnItemClickListener.onClick(0,position,null);
                }else if(v == ((HeadViewHolder) holder).rule){
                    mOnItemClickListener.onClick(1,position,null);
                }
            }else if(holder instanceof StorePicsViewHolder){
                ActivityHotelBean bean = (ActivityHotelBean) list.get(holder.getAdapterPosition());
                mOnItemClickListener.onClick(2,position,bean);
            }
        }else {
            Toast.makeText(context, "请先登陆！", Toast.LENGTH_SHORT).show();
        }
    }

    public static class HeadViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView rule;
        private TextView area;
        private RecyclerView headPics;
        private LinearLayout mRightLayout;
        public HeadViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            rule = (TextView) itemView.findViewById(R.id.rule);
            area = (TextView) itemView.findViewById(R.id.area);
            headPics = (RecyclerView) itemView.findViewById(R.id.head_pic);
            mRightLayout = (LinearLayout) itemView.findViewById(R.id.area_layout);
        }
    }

    public static class EndViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView endPics;
        public EndViewHolder(View itemView) {
            super(itemView);

            endPics = (RecyclerView) itemView.findViewById(R.id.end_pic);
        }
    }

    public static class StorePicsViewHolder extends RecyclerView.ViewHolder{
        private ImageView pic;
        private ImageView vImg;
        private ImageView gift;
        private TextView name;
        private TextView info;
        private CardView mLayout;
        private TextView place;
        private TextView ad;

        public StorePicsViewHolder(View itemView) {
            super(itemView);
            pic = (ImageView) itemView.findViewById(R.id.new_store_sup_img);
            vImg = (ImageView) itemView.findViewById(R.id.new_store_sup_v);
            gift = (ImageView) itemView.findViewById(R.id.new_store_sup_gift);
            name = (TextView) itemView.findViewById(R.id.new_store_sup_name);
            info = (TextView) itemView.findViewById(R.id.new_store_sup_info);
            place = (TextView) itemView.findViewById(R.id.place);
            ad = (TextView) itemView.findViewById(R.id.new_store_sup_ad);
            mLayout = (CardView) itemView.findViewById(R.id.new_store_sup_layout);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int type,int position,ActivityHotelBean bean);
    }
}