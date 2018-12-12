package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.ComboVideoBean;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucio on 2018/6/14.
 */

public class ComboVideoAdapter extends RecyclerView.Adapter<ComboVideoAdapter.MyViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<ComboVideoBean> mData;
    private GSYVideoHelper smallVideoHelper;
    private GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder;
    public final static String TAG = "RecyclerBaseAdapter";
    
    public ComboVideoAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_combo_video_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.listItemBtn.setOnClickListener(this);
        holder.listItemBtn.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ComboVideoBean bean = mData.get(position);
        ImageView coverImg = new ImageView(mContext);
        coverImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(mContext).load(bean.getCoverMap()).into(coverImg);
        smallVideoHelper.addVideoPlayer(position, coverImg, TAG, holder.listItemContainer, holder.listItemBtn);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View view) {
        MyViewHolder holder = (MyViewHolder) view.getTag();
        int position = holder.getAdapterPosition();
        if (view == holder.listItemBtn) {
            notifyDataSetChanged();
            //listVideoUtil.setLoop(true);
            smallVideoHelper.setPlayPositionAndTag(position, TAG);
            gsySmallVideoHelperBuilder.setVideoTitle("title " + position).setUrl(mData.get(position).getVideoId());
            smallVideoHelper.startPlay();
            //必须在startPlay之后设置才能生效
            //listVideoUtil.getGsyVideoPlayer().getTitleTextView().setVisibility(View.VISIBLE);
        }
    }

    public void refresh(List<ComboVideoBean> list) {
        mData = new ArrayList<>();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(List<ComboVideoBean> list) {
        int size = mData.size();
        mData.addAll(list);
        notifyItemRangeInserted(size, list.size());
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView listItemBtn;
        private FrameLayout listItemContainer;

        public MyViewHolder(View itemView) {
            super(itemView);
            listItemContainer = (FrameLayout) itemView.findViewById(R.id.list_item_container);
            listItemBtn = (ImageView) itemView.findViewById(R.id.list_item_btn);
        }
    }

    public void setVideoHelper(GSYVideoHelper smallVideoHelper, GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder) {
        this.smallVideoHelper = smallVideoHelper;
        this.gsySmallVideoHelperBuilder = gsySmallVideoHelperBuilder;
    }
}
