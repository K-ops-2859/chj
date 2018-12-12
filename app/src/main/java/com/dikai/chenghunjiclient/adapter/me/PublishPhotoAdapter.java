package com.dikai.chenghunjiclient.adapter.me;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.util.Constants;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2016/8/17.
 */
public class PublishPhotoAdapter extends RecyclerView.Adapter<PublishPhotoAdapter.PublishViewHolder> implements View.OnClickListener {
    private int maxPhotoNum;
    private Context context;
    private List<ImageItem> result;
    private CloseClickListener mCloseClickListener;
    private ImagePicker imagePicker;

    public void setImagePicker(ImagePicker imagePicker) {
        this.imagePicker = imagePicker;
    }

    public PublishPhotoAdapter(Context context, List<ImageItem> result , int maxPhotoNum) {
        this.context = context;
        this.result = result;
        this.maxPhotoNum = maxPhotoNum;
        mCloseClickListener = new CloseClickListener();
    }

    @Override
    public PublishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_publish_circle_list, parent, false);
        PublishViewHolder holder = new PublishViewHolder(view);
        holder.mImg.setTag(R.id.pick_photo_tag, holder);
        holder.mImg.setOnClickListener(this);

        holder.mClose.setTag(holder);
        holder.mClose.setOnClickListener(mCloseClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(PublishViewHolder holder, int position) {

        if(result.size() == maxPhotoNum) {
            holder.mClose.setVisibility(View.VISIBLE);
            Glide.with(context).load(result.get(position).path).error(R.mipmap.ic_upload).override(100,100).into(holder.mImg);
//            setImgWeight(holder.mImg, Uri.parse("file://" + result.get(position)));
//            setImgWeight(holder.mImg, Uri.fromFile(new File(result.get(position))));
        }else {
            if(position < result.size()){
                holder.mClose.setVisibility(View.VISIBLE);
                Glide.with(context).load(result.get(position).path).error(R.mipmap.ic_upload).override(100,100).into(holder.mImg);
            }else {
                Glide.with(context).load(R.mipmap.ic_upload).error(R.mipmap.ic_upload).override(100,100).into(holder.mImg);
                holder.mClose.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(result.size() < maxPhotoNum){
            return result.size()+1;
        }else {
            return result.size();
        }
    }

    public int getMaxPhotoNum() {
        return maxPhotoNum - result.size();
    }

    @Override
    public void onClick(View v) {
        PublishViewHolder holder = (PublishViewHolder) v.getTag(R.id.pick_photo_tag);
        int position = holder.getAdapterPosition();
        if(result.size() == maxPhotoNum) {
//            Toast.makeText(context, "点击了" + position, Toast.LENGTH_SHORT).show();
        }else {
            if(position < result.size()){
//                Toast.makeText(context, "点击了" + position, Toast.LENGTH_SHORT).show();
            }else {
                mAddClickListener.onClick();
            }
        }
    }

    private class CloseClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            PublishViewHolder holder = (PublishViewHolder) v.getTag();
            int position = holder.getAdapterPosition();
            result.remove(position);
            notifyItemRemoved(position);
        }
    }
    public void addAll(Collection<? extends ImageItem> collection){
        int size = result.size();
        result.addAll(collection);
//        notifyItemRangeInserted(size, collection.size());
        notifyDataSetChanged();
    }

    public void refresh(List<ImageItem> list){
        result = list;
        notifyDataSetChanged();
    }

    class PublishViewHolder extends RecyclerView.ViewHolder {
        ImageView mClose;
        ImageView mImg;
        PublishViewHolder(View itemView) {
            super(itemView);
            mClose = (ImageView) itemView.findViewById(R.id.item_publish_circle_close);
            mImg = (ImageView) itemView.findViewById(R.id.item_publish_circle_img);
        }
    }

    public int getCheckedNum(){
        return result.size();
    }

    public List<String> getResult() {
        List<String> list = new ArrayList<>();
        for (ImageItem item : result) {
            list.add(item.path);
        }
        return list;
    }

    private OnAddClickListener mAddClickListener;

    public void setAddClickListener(OnAddClickListener addClickListener) {
        mAddClickListener = addClickListener;
    }

    public interface OnAddClickListener{
        void onClick();
    }
}
