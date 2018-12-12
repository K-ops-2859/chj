package com.dikai.chenghunjiclient.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnAdapterViewClickListener;
import com.dikai.chenghunjiclient.entity.DynamicDetailsData;
import com.dikai.chenghunjiclient.util.TextLUtil;
import com.dikai.chenghunjiclient.util.UserManager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cmk03 on 2018/1/9.
 */

public class DynamicCommentAdapter extends RecyclerView.Adapter<DynamicCommentAdapter.DynamicCommentVH> implements View.OnClickListener{

    private Context mContext;
    private final List<DynamicDetailsData.DataList> mData;
    private OnAdapterViewClickListener<DynamicDetailsData.DataList> onAdapterViewClickListener;
    private final String userID;

    public DynamicCommentAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        userID = UserManager.getInstance(context).getNewUserInfo().getUserId();
    }

    @Override
    public DynamicCommentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.adapter_dynamic_comment, parent, false);
        DynamicCommentVH holder = new DynamicCommentVH(view);
        holder.tvRemveCom.setOnClickListener(this);
        holder.tvRemveCom.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(DynamicCommentVH holder, int position) {
//        holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        DynamicReplyAdapter adapter = new DynamicReplyAdapter(mContext);
//        holder.mRecyclerView.setAdapter(adapter);
//        holder.tvUserName.setText(dataList.getCommentserName());
        DynamicDetailsData.DataList dataList = mData.get(position);
        holder.tvComment.setText(dataList.getCommentsContent());
        if (dataList.getCommentsPeopleId().equals(userID)) {
            holder.tvRemveCom.setVisibility(View.VISIBLE);
        } else {
            holder.tvRemveCom.setVisibility(View.GONE);
        }
        TextLUtil.setLength(mContext,holder.tvUserName,holder.tvProfe,dataList.getCommentserName(),
                UserManager.getInstance(mContext).getProfession(dataList.getProfession()),126,6);
        holder.tvTime.setText(dataList.getCommentsCreateTime());
        Glide.with(mContext).load(dataList.getCommentsHeadportrait())
                .error(R.color.gray_background)
                .into(holder.civLogo);
    }

    public void setOnAdapterViewClickListener(OnAdapterViewClickListener<DynamicDetailsData.DataList> onAdapterViewClickListener) {
        this.onAdapterViewClickListener = onAdapterViewClickListener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setList(List<DynamicDetailsData.DataList> list) {
        mData.clear();
        append(list);
    }

    public void append(List<DynamicDetailsData.DataList> list) {
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

    @Override
    public void onClick(View v) {
        DynamicCommentVH holder = (DynamicCommentVH) v.getTag();
        if (v == holder.tvRemveCom) {
            int position = holder.getAdapterPosition() - 1;
            Log.d("position===============" , ""+position);
            onAdapterViewClickListener.onAdapterClick(v, position, mData.get(position));
        }
    }

    static class DynamicCommentVH extends RecyclerView.ViewHolder {

        private final CircleImageView civLogo;
        private final TextView tvUserName;
        private final TextView tvComment;
        private final TextView tvTime;
        private final ImageView tvRemveCom;
        private final TextView tvProfe;
        // private final TextView tvLikeNumber;
        // private final TextView tvReply;
        // private final ImageView ivLike;
        // private final RecyclerView mRecyclerView;

        public DynamicCommentVH(View itemView) {
            super(itemView);
            civLogo = (CircleImageView) itemView.findViewById(R.id.civ_logo);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            tvComment = (TextView) itemView.findViewById(R.id.tv_comment);
            tvProfe = (TextView) itemView.findViewById(R.id.tv_identity);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvRemveCom = (ImageView) itemView.findViewById(R.id.tv_remove_com);
            // tvLikeNumber = (TextView) itemView.findViewById(R.id.tv_like_number);
            // tvReply = (TextView) itemView.findViewById(R.id.tv_reply);
            // ivLike = (ImageView) itemView.findViewById(R.id.iv_like);
            // mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        }
    }
}
