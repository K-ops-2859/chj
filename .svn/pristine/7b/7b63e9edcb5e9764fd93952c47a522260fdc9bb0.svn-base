package com.dikai.chenghunjiclient.adapter.discover;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnAdapterViewClickListener;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.dialog.DiscoverDialog;
import com.dikai.chenghunjiclient.entity.DynamicData;
import com.dikai.chenghunjiclient.util.UserManager;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lucio on 2018/3/12.
 */

public class DiscoverVideoAdapter extends RecyclerView.Adapter<DiscoverVideoAdapter.MyDiscoverVH> implements View.OnClickListener {

    private Context mContext;
    private List<DynamicData.DataList> mData;
    private boolean isLike = false;
    private OnItemClickListener<DynamicData.DataList> onItemClickListener;
    private OnAdapterViewClickListener<DynamicData.DataList> onAdapterViewClickListener;
    private OnAdapterViewClickListener<DynamicData.DataList> likeClickListener;
    private OnAdapterViewClickListener<DynamicData.DataList> removeClickListener;
    private DiscoverDialog dialog;
    private String userID;
    private final List<Integer> likeCount;
    private AlertDialog.Builder normalDialog;
    private boolean canRemove= false;

    private GSYVideoHelper smallVideoHelper;
    private GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder;
    public final static String TAG = "RecyclerBaseAdapter";

    public void setCanRemove(boolean canRemove) {
        this.canRemove = canRemove;
    }

    public DiscoverVideoAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        likeCount = new ArrayList<>();
        dialog = new DiscoverDialog(mContext);
    }

    @Override
    public MyDiscoverVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_discover_video_layout, parent, false);
        MyDiscoverVH holder = new MyDiscoverVH(view, onItemClickListener, mData);
        holder.ivLike.setOnClickListener(this);
        holder.ivLike.setTag(holder);
        holder.ivShare.setOnClickListener(this);
        holder.ivShare.setTag(holder);
        holder.tvRemove.setOnClickListener(this);
        holder.tvRemove.setTag(holder);
        holder.listItemBtn.setOnClickListener(this);
        holder.listItemBtn.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyDiscoverVH holder, final int position) {
        DynamicData.DataList dataList = mData.get(position);
        if(canRemove){
            holder.tvRemove.setVisibility(View.VISIBLE);
        }
        Glide.with(mContext).load(dataList.getDynamicerHeadportrait()).into(holder.civLogo);
        holder.tvUserName.setText(dataList.getDynamicerName());
        holder.tvTime.setText(dataList.getCreateTime());
        String identity = UserManager.getInstance(mContext).getIdentity(dataList.getOccupationCode());
        holder.tvIdentity.setText(" · " + identity);
        if (dataList.getState() == 0) {
            holder.ivLike.setImageResource(R.mipmap.ic_like_nor);
        } else {
            holder.ivLike.setImageResource(R.mipmap.ic_like_pre);
        }
        holder.tvDesc.setText(dataList.getContent());
        holder.tvComment.setText(dataList.getGivethumbCount() + "人喜欢 · " + dataList.getCommentsCount() + "条评论");
        likeCount.add(position, dataList.getGivethumbCount());
//        Glide.with(mContext).load(dataList.getImgs()).into(holder.coverImg);
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
        if (view == holder.ivLike) {
            Log.e("第一步", "=======" + mData.get(position).getState());
            likeClickListener.onAdapterClick(view, position, mData.get(position));
        } else if (view == holder.ivShare) {
            onAdapterViewClickListener.onAdapterClick(view, position, mData.get(position));
        }  else if (view == holder.tvRemove) {
            removeClickListener.onAdapterClick(view, position, mData.get(position));
        }  else if (view == holder.listItemBtn) {
            notifyDataSetChanged();
            //listVideoUtil.setLoop(true);
            smallVideoHelper.setPlayPositionAndTag(position, TAG);
//            gsySmallVideoHelperBuilder.setVideoTitle("title " + position).setUrl(mData.get(position).getVideos());
            smallVideoHelper.startPlay();
            //必须在startPlay之后设置才能生效
            //listVideoUtil.getGsyVideoPlayer().getTitleTextView().setVisibility(View.VISIBLE);
        }
    }

    public void itemChange(int position, int like) {
        Log.e("第四步", "=======" + like);
        mData.get(position).setState(like);
        if (like == 0) {
            mData.get(position).setGivethumbCount(likeCount.get(position) - 1);
        } else {
            mData.get(position).setGivethumbCount(likeCount.get(position) + 1);
        }
        notifyItemChanged(position);
    }

    public void setOnItemClickListener(OnItemClickListener<DynamicData.DataList> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnAdapterViewClickListener(OnAdapterViewClickListener<DynamicData.DataList> onAdapterViewClickListener) {
        this.onAdapterViewClickListener = onAdapterViewClickListener;
    }

    public void refresh(List<DynamicData.DataList> list) {
        mData = new ArrayList<>();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(List<DynamicData.DataList> list) {
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

    public void setLikeClickListener(OnAdapterViewClickListener<DynamicData.DataList> likeClickListener) {
        this.likeClickListener = likeClickListener;
    }

    public void setRemoveClickListener(OnAdapterViewClickListener<DynamicData.DataList> removeClickListener) {
        this.removeClickListener = removeClickListener;
    }

    class MyDiscoverVH extends RecyclerView.ViewHolder {

        private CircleImageView civLogo;
        private TextView tvUserName;
        private TextView tvTime;
        private TextView tvIdentity;
        private TextView tvDesc;
        private TextView tvComment;
        private ImageView ivLike;
        private ImageView ivMessage;
        private ImageView ivShare;
        private ImageView coverImg;
        private TextView tvRemove;
        private FrameLayout listItemContainer;
        private ImageView listItemBtn;

        public MyDiscoverVH(View itemView, final OnItemClickListener<DynamicData.DataList> listener, final List<DynamicData.DataList> data) {
            super(itemView);
            coverImg = new ImageView(mContext);
            civLogo = (CircleImageView) itemView.findViewById(R.id.civ_logo);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvIdentity = (TextView) itemView.findViewById(R.id.tv_identity);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            tvComment = (TextView) itemView.findViewById(R.id.tv_comment);
            ivLike = (ImageView) itemView.findViewById(R.id.iv_like);
            ivMessage = (ImageView) itemView.findViewById(R.id.iv_message);
            ivShare = (ImageView) itemView.findViewById(R.id.iv_share);
            tvRemove = (TextView) itemView.findViewById(R.id.tv_remove);
            listItemContainer = (FrameLayout) itemView.findViewById(R.id.list_item_container);
            listItemBtn = (ImageView) itemView.findViewById(R.id.list_item_btn);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onItemClick(view, position, data.get(position));
                }
            });

        }
    }

    public void setVideoHelper(GSYVideoHelper smallVideoHelper, GSYVideoHelper.GSYVideoHelperBuilder gsySmallVideoHelperBuilder) {
        this.smallVideoHelper = smallVideoHelper;
        this.gsySmallVideoHelperBuilder = gsySmallVideoHelperBuilder;
    }

    interface CallBackListener {
        void onCall();
    }
}