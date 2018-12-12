package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.dikai.chenghunjiclient.entity.ImgBean;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/11/19.
 */

public class AddImgAdapter extends RecyclerView.Adapter<AddImgAdapter.EditPhotoViewHolder> implements View.OnClickListener {

    private int maxPhotoNum;
    private Context context;
    private CloseClickListener mCloseClickListener;
    private List<ImgBean> mOldPaths;
    private List<String> mNewPaths;

    public AddImgAdapter(Context context, int maxPhotoNum) {
        this.context = context;
        this.maxPhotoNum = maxPhotoNum;
        mOldPaths = new ArrayList<>();
        mNewPaths = new ArrayList<>();
        mCloseClickListener = new CloseClickListener();
    }


    @Override
    public EditPhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_img_select_layout, parent, false);
        EditPhotoViewHolder holder = new EditPhotoViewHolder(view);
        holder.mImg.setTag(R.id.pick_photo_tag, holder);
        holder.mImg.setOnClickListener(this);
        holder.mClose.setTag(holder);
        holder.mClose.setOnClickListener(mCloseClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(EditPhotoViewHolder holder, int position) {
        int size = mOldPaths.size() + mNewPaths.size();
        if(size == maxPhotoNum) {
            holder.mClose.setVisibility(View.VISIBLE);
            if(position < mOldPaths.size()){
                Glide.with(context).load(mOldPaths.get(position).getImgUrl()).error(R.mipmap.ic_app_upload_place).override(160,160).into(holder.mImg);
            }else {
                Glide.with(context).load(mNewPaths.get(position - mOldPaths.size())).error(R.mipmap.ic_app_upload_place).override(160,160).into(holder.mImg);
            }
        }else {
            if(position < size){
                holder.mClose.setVisibility(View.VISIBLE);
                if(position < mOldPaths.size()){
                    Glide.with(context).load(mOldPaths.get(position).getImgUrl()).error(R.mipmap.ic_app_upload_place).override(160,160).into(holder.mImg);
                }else {
                    Glide.with(context).load(mNewPaths.get(position - mOldPaths.size())).error(R.mipmap.ic_app_upload_place).override(160,160).into(holder.mImg);
                }
            }else {
                Glide.with(context).load(R.mipmap.ic_app_upload_place).error(R.mipmap.ic_app_upload_place).override(160,160).into(holder.mImg);
                holder.mClose.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        int size = mOldPaths.size() + mNewPaths.size();
        if(size < maxPhotoNum){
            return size + 1;
        }else {
            return size;
        }
    }

    @Override
    public void onClick(View v) {
        EditPhotoViewHolder holder = (EditPhotoViewHolder) v.getTag(R.id.pick_photo_tag);
        int position = holder.getAdapterPosition();
        int size = mOldPaths.size() + mNewPaths.size();
        if(size == maxPhotoNum) {
//            Toast.makeText(context, "点击了" + position, Toast.LENGTH_SHORT).show();
            photoLook(position);
        }else {
            if(position < size){
//                Toast.makeText(context, "点击了" + position, Toast.LENGTH_SHORT).show();
                photoLook(position);
            }else {
                mAddClickListener.onClick();
            }
        }
    }

    private void photoLook(int position){
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra("now", position);
        intent.putStringArrayListExtra("imgs", getAllPhoto());
        context.startActivity(intent);
    }

    private ArrayList<String> getAllPhoto(){
        ArrayList<String> temp = new ArrayList<>();
        for (ImgBean bean : mOldPaths) {
            temp.add(bean.getImgUrl());
        }
        temp.addAll(mNewPaths);
        return temp;
    }

    private class CloseClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            EditPhotoViewHolder holder = (EditPhotoViewHolder) v.getTag();
            int position = holder.getAdapterPosition();
//            int size = mOldPaths.size() + mNewPaths.size();
            if(position < mOldPaths.size()){
                mOldPaths.remove(position);
            }else {
                mNewPaths.remove(position - mOldPaths.size());
            }
            notifyDataSetChanged();
        }
    }

    public int getMaxPhotoNum() {
        return maxPhotoNum - (mOldPaths.size() + mNewPaths.size());
    }

    public void addNew(Collection<? extends String> collection){
        mNewPaths.addAll(collection);
        notifyDataSetChanged();
    }

    public void addOld(Collection<? extends ImgBean> collection){
        mOldPaths = new ArrayList<>();
        mOldPaths.addAll(collection);
        notifyDataSetChanged();
    }

    class EditPhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView mClose;
        SelectableRoundedImageView mImg;
        EditPhotoViewHolder(View itemView) {
            super(itemView);
            mClose = (ImageView) itemView.findViewById(R.id.item_publish_circle_close);
            mImg = (SelectableRoundedImageView) itemView.findViewById(R.id.item_publish_circle_img);
        }

    }

    public List<String> getNewPaths() {
        return mNewPaths;
    }

    public List<String> getOldIds() {
        List<String> temp = new ArrayList<>();
        for (ImgBean bean : mOldPaths) {
            temp.add(bean.getImgId());
        }
        return temp;
    }

    private OnAddClickListener mAddClickListener;

    public void setAddClickListener(OnAddClickListener addClickListener) {
        mAddClickListener = addClickListener;
    }

    public interface OnAddClickListener{
        void onClick();
    }
}