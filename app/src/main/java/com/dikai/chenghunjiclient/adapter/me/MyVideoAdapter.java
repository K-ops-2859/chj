package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnAdapterViewClickListener;
import com.dikai.chenghunjiclient.entity.PhotoDetailsData;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Lucio on 2018/3/12.
 */

public class MyVideoAdapter extends RecyclerView.Adapter<MyVideoAdapter.MyDiscoverVH> implements View.OnClickListener {

    private Context mContext;
    private List<PhotoDetailsData.DataList> mData;
    private OnAdapterViewClickListener<PhotoDetailsData.DataList> onDeleteListener;
    private String userID;
    private boolean canRemove= false;
    private Map<Integer,Bitmap> coverImgs;

    private GSYVideoHelper smallVideoHelper;
    private GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder;
    public final static String TAG = "RecyclerBaseAdapter";
    private MediaMetadataRetriever mCoverMedia;

    public void setCanRemove(boolean canRemove) {
        this.canRemove = canRemove;
    }

    public MyVideoAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        coverImgs = new HashMap<>();
    }

    @Override
    public MyDiscoverVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video_layout, parent, false);
        MyDiscoverVH holder = new MyDiscoverVH(view);
        holder.delete.setOnClickListener(this);
        holder.delete.setTag(holder);
        holder.listItemBtn.setOnClickListener(this);
        holder.listItemBtn.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyDiscoverVH holder, final int position) {
        PhotoDetailsData.DataList dataList = mData.get(position);
        if(coverImgs.containsKey(position)){
            holder.coverImg.setImageBitmap(coverImgs.get(position));
        }
        if(dataList.getStatus() == 2){
            holder.unqualified.setVisibility(View.VISIBLE);
        }else {
            holder.unqualified.setVisibility(View.INVISIBLE);
        }
//        setImg(position,dataList.getFileUrl());
        smallVideoHelper.addVideoPlayer(position, holder.coverImg, TAG, holder.listItemContainer, holder.listItemBtn);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View view) {
        MyDiscoverVH holder = (MyDiscoverVH) view.getTag();
        int position = holder.getAdapterPosition();
        if (view == holder.delete) {
            onDeleteListener.onAdapterClick(view, position, mData.get(position));
        } else if (view == holder.listItemBtn) {
            notifyDataSetChanged();
            //listVideoUtil.setLoop(true);
            smallVideoHelper.setPlayPositionAndTag(position, TAG);
            gsySmallVideoHelperBuilder.setVideoTitle("title " + position).setUrl(mData.get(position).getFileUrl());
            smallVideoHelper.startPlay();
            //必须在startPlay之后设置才能生效
            //listVideoUtil.getGsyVideoPlayer().getTitleTextView().setVisibility(View.VISIBLE);
        }
    }

//    public void itemChange(int position, int like) {
//        Log.e("第四步", "=======" + like);
//        mData.get(position).setState(like);
//        if (like == 0) {
//            mData.get(position).setGivethumbCount(likeCount.get(position) - 1);
//        } else {
//            mData.get(position).setGivethumbCount(likeCount.get(position) + 1);
//        }
//        notifyItemChanged(position);
//    }

    public void setOnDeleteListener(OnAdapterViewClickListener<PhotoDetailsData.DataList> OnAdapterViewClickListener) {
        this.onDeleteListener = OnAdapterViewClickListener;
    }

    public void refresh(List<PhotoDetailsData.DataList> list) {
        mData = new ArrayList<>();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(List<PhotoDetailsData.DataList> list) {
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

    class MyDiscoverVH extends RecyclerView.ViewHolder {

        private TextView delete;
        private ImageView coverImg;
        private ImageView listItemBtn;
        private TextView unqualified;
        private FrameLayout listItemContainer;

        public MyDiscoverVH(View itemView) {
            super(itemView);
            coverImg = new ImageView(mContext);
            delete = (TextView) itemView.findViewById(R.id.delete);
            listItemContainer = (FrameLayout) itemView.findViewById(R.id.list_item_container);
            listItemBtn = (ImageView) itemView.findViewById(R.id.list_item_btn);
            unqualified = (TextView) itemView.findViewById(R.id.unqualified_layout);
        }
    }

    public void setVideoHelper(GSYVideoHelper smallVideoHelper, GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder) {
        this.smallVideoHelper = smallVideoHelper;
        this.gsySmallVideoHelperBuilder = gsySmallVideoHelperBuilder;
    }
//
//    private void setImg(final int position, final String url){
//        if(!coverImgs.containsKey(position)){
//            Observable.create(new Observable.OnSubscribe<Bitmap>() {
//                @Override
//                public void call(Subscriber<? super Bitmap> subscriber) {
//                    try {
//                        MediaMetadataRetriever mediaMetadataRetriever = getMediaMetadataRetriever(url);
//                        Bitmap bitmap = mediaMetadataRetriever
//                                .getFrameAtTime(1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
//                        bitmap = ThumbnailUtils.extractThumbnail(bitmap,
//                                640,360, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
//                        subscriber.onNext(bitmap);
//                        subscriber.onCompleted();
//                    }catch (Exception e){
//                        subscriber.onError(e);
//                        subscriber.onCompleted();
//                    }
//                }}).subscribeOn(Schedulers.newThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<Bitmap>() {
//                        @Override
//                        public void onCompleted() {
//                        }
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.e("获取封面图出错",e.toString());
//                        }
//                        @Override
//                        public void onNext(Bitmap bitmap) {
//                            coverImgs.put(position,bitmap);
//                            notifyItemChanged(position);
//                        }
//                    });
//        }
//    }

    private MediaMetadataRetriever getMediaMetadataRetriever(String url) {
        if (mCoverMedia == null) {
            mCoverMedia = new MediaMetadataRetriever();
        }
        mCoverMedia.setDataSource(url, new HashMap<String, String>());
        return mCoverMedia;
    }

}