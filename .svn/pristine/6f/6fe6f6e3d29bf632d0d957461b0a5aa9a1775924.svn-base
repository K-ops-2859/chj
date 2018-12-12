package com.dikai.chenghunjiclient.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.util.DensityUtil;
import com.dikai.chenghunjiclient.view.MyImageView;
import com.dikai.chenghunjiclient.view.MyImageView1;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cmk03 on 2018/1/4.
 */

public class AttentionAdapter extends RecyclerView.Adapter<AttentionAdapter.AttentionVH>  {

    private Context mContext;
    private final List<String> mData;
    private boolean isLike = false;
    private OnItemClickListener onItemClickListener;
    private int[] images = {R.mipmap.ic_wedding_know};
    private List<String> list = new ArrayList<>();


    public AttentionAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        int i = DensityUtil.px2dip(context, 624);
        System.out.println("----------------大小" + i);
    }

    @Override
    public AttentionVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_discover, parent, false);
        return new AttentionVH(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(final AttentionVH holder, final int position) {
//        holder.tvAttention.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.tvAttention.setText("已关注");
//                holder.tvAttention.setTextColor(ContextCompat.getColor(mContext, R.color.gray_text));
//            }
//        });
        holder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLike) {
                    holder.ivLike.setImageResource(R.mipmap.ic_like_pre);
                    isLike = true;
                } else {
                    holder.ivLike.setImageResource(R.mipmap.ic_like_nor);
                    isLike = false;
                }
            }
        });
        holder.ivMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "点击信息" + position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "点击分享" + position, Toast.LENGTH_SHORT).show();
            }
        });

        if (images.length==1) {
            holder.ivShowPhoto.setRatio(2);
            holder.ivShowPhoto.setVisibility(View.VISIBLE);
            holder.ivShowPhoto2.setVisibility(View.GONE);
            holder.ivSHowPhoto3.setVisibility(View.GONE);
        } else if (images.length == 2) {
            holder.ivShowPhoto.setRatio(1);
            holder.ivShowPhoto2.setRatio(1);
            holder.ivShowPhoto.setVisibility(View.VISIBLE);
            holder.ivShowPhoto2.setVisibility(View.VISIBLE);
            holder.ivSHowPhoto3.setVisibility(View.GONE);
        } else if (images.length == 3) {
            holder.ivShowPhoto.setRatio(1);
            holder.ivShowPhoto2.setRatio(1);
            holder.ivSHowPhoto3.setRatio(1);
            holder.ivShowPhoto.setVisibility(View.VISIBLE);
            holder.ivShowPhoto2.setVisibility(View.VISIBLE);
            holder.ivSHowPhoto3.setVisibility(View.VISIBLE);
        }
//        for (int i = 0; i < images.length; i++) {
//            MyImageView myImageView = (MyImageView) holder.llImage.getChildAt(i);
//            if (images.length == 1) {
//                myImageView.setRatio(2);
//                for (int j=i + 1;j<images.length - 1; j++) {
//                    View childAt = holder.llImage.getChildAt(j);
//                    childAt.setVisibility(View.GONE);
//                }
//            } else if (images.length > 1) {
//                myImageView.setRatio(1);
//                myImageView.setVisibility(View.VISIBLE);
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setList(List<String> list) {
        mData.clear();
        append(list);
    }

    public void append(List<String> list) {
        int positionStart = mData.size();
        int itemCount = list.size();
        mData.addAll(list);
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

    static class AttentionVH extends RecyclerView.ViewHolder {

        private final CircleImageView civLogo;
        private final TextView tvUserName;
        private final TextView tvTime;
        private final TextView tvIdentity;
       // private final TextView tvAttention;
        private final LinearLayout llImage;
        private final MyImageView1 ivShowPhoto;
        private final TextView tvDesc;
        private final TextView tvComment;
        private final ImageView ivLike;
        private final ImageView ivMessage;
        private final ImageView ivShare;
        private final MyImageView1 ivSHowPhoto3;
        private final MyImageView1 ivShowPhoto2;

        public AttentionVH(View itemView, final OnItemClickListener listener) {
            super(itemView);
            civLogo = (CircleImageView) itemView.findViewById(R.id.civ_logo);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvIdentity = (TextView) itemView.findViewById(R.id.tv_identity);
           // tvAttention = (TextView) itemView.findViewById(R.id.tv_attention);
            llImage = (LinearLayout) itemView.findViewById(R.id.ll_image);
            ivShowPhoto = (MyImageView1) itemView.findViewById(R.id.iv_show_photo);
            ivShowPhoto2 = (MyImageView1) itemView.findViewById(R.id.iv_show_photo2);
            ivSHowPhoto3 = (MyImageView1) itemView.findViewById(R.id.iv_show_photo3);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            tvComment = (TextView) itemView.findViewById(R.id.tv_comment);
            ivLike = (ImageView) itemView.findViewById(R.id.iv_like);
            ivMessage = (ImageView) itemView.findViewById(R.id.iv_message);
            ivShare = (ImageView) itemView.findViewById(R.id.iv_share);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(view, getAdapterPosition(), null);
                }
            });
        }
    }
}
