package com.dikai.chenghunjiclient.adapter.wedding;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.entity.CusTaocanPhoto;
import com.dikai.chenghunjiclient.view.MyImageView;
import com.dikai.chenghunjiclient.view.RoundImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/6/14.
 */

public class TaocanPhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private int HEADER_VIEW = 0;
    private int BIG_VIEW = 1;
    private int SMALL_VIEW = 2;
    private RecyclerView mRecyclerView;
    public OnItemClickListener<List<String>> onItemClickListener;
    public List<CusTaocanPhoto> mData = new ArrayList<>();

    public TaocanPhotoAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).isTitle() && mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            return HEADER_VIEW;
        } else if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
            return BIG_VIEW;
        } else {
            return SMALL_VIEW;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == HEADER_VIEW) {
            View view = inflater.inflate(R.layout.adapter_taocan_photo_title, parent, false);
            return new TitleVH(view);
        } else if (viewType == SMALL_VIEW) {
            View view = inflater.inflate(R.layout.adapter_taocan_photo_big, parent, false);
            return new BigVH(view, onItemClickListener, mData);
        } else {
            View view = inflater.inflate(R.layout.adapter_taocan_photo_content, parent, false);
            return new SmallVH(view, onItemClickListener, mData);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CusTaocanPhoto dataList = mData.get(position);
        if (holder instanceof TitleVH) {
            TitleVH titleVH = (TitleVH) holder;
            titleVH.tvTitle.setText(dataList.getAreaName());
        } else if (holder instanceof BigVH) {
            BigVH bigVH = (BigVH) holder;
            Glide.with(mContext).load(dataList.getImage()).centerCrop().into(bigVH.image);
        } else if (holder instanceof SmallVH) {
            SmallVH smallVH = (SmallVH) holder;
            if (dataList.isTitle()) {
                smallVH.tvTitle.setVisibility(View.VISIBLE);
                smallVH.image.setVisibility(View.GONE);
                smallVH.tvTitle.setText(dataList.getAreaName());
            } else {
                smallVH.tvTitle.setVisibility(View.GONE);
                Glide.with(mContext).load(dataList.getImage()).into(smallVH.image);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            //ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setList(List<CusTaocanPhoto> list) {
        mData.clear();
        append(list);
    }

    public void append(List<CusTaocanPhoto> lists) {
        int positionStart = mData.size();
        int itemCount = lists.size();
        mData.addAll(lists);
        if (positionStart > 0 && itemCount > 0) {
            notifyItemRangeInserted(positionStart, itemCount);
        } else {
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<List<String>> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class TitleVH extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        public TitleVH(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    static class BigVH extends RecyclerView.ViewHolder {

        private final MyImageView image;
        private List<String> images = new ArrayList<>();

        public BigVH(View itemView, final OnItemClickListener<List<String>> listener, final List<CusTaocanPhoto> dataList) {
            super(itemView);
            image = (MyImageView) itemView.findViewById(R.id.image);

            for (CusTaocanPhoto data : dataList) {
                images.add(data.getImage());
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    System.out.println("=========" + images);
                    if (listener != null) {
                        listener.onItemClick(v, position, images);
                    }
                }
            });
        }
    }

    static class SmallVH extends RecyclerView.ViewHolder {

        private final RoundImage image;
        private final TextView tvTitle;
        private final LinearLayout llRoot;
        //  private int position;
        private List<String> images = new ArrayList<>();

        public SmallVH(View itemView, final OnItemClickListener<List<String>> listener, final List<CusTaocanPhoto> dataList) {
            super(itemView);
            image = (RoundImage) itemView.findViewById(R.id.image);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            llRoot = (LinearLayout) itemView.findViewById(R.id.ll_root);
            for (CusTaocanPhoto data : dataList) {
                images.add(data.getImage());
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    System.out.println("=========" + images);
                    if (listener != null) {
                        listener.onItemClick(v, position, images);
                    }
                }
            });
        }

//        public void bindPosition(int position) {
//            this.position =position;
//        }
    }
}
