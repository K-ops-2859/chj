package com.dikai.chenghunjiclient.adapter.store;

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
import com.dikai.chenghunjiclient.entity.ComboCommentBean;
import com.dikai.chenghunjiclient.entity.ComboCommentBean;
import com.dikai.chenghunjiclient.entity.DynamicDetailsData;
import com.dikai.chenghunjiclient.util.TextLUtil;
import com.dikai.chenghunjiclient.util.UserManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lucio on 2018/6/14.
 */

public class ComboCommentAdapter extends RecyclerView.Adapter<ComboCommentAdapter.MyViewHolder> implements View.OnClickListener{

    private Context context;
    private List<ComboCommentBean> list;
    private String userID;


    public ComboCommentAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        userID = UserManager.getInstance(context).getNewUserInfo().getUserId();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_combo_comment_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.tvRemveCom.setOnClickListener(this);
        holder.tvRemveCom.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        DynamicReplyAdapter adapter = new DynamicReplyAdapter(mContext);
//        holder.mRecyclerView.setAdapter(adapter);
//        holder.tvUserName.setText(dataList.getCommentserName());
        ComboCommentBean dataList = list.get(position);
        holder.tvComment.setText(dataList.getContent());
        if (dataList.getPeopleId().equals(userID)) {
            holder.tvRemveCom.setVisibility(View.VISIBLE);
        } else {
            holder.tvRemveCom.setVisibility(View.GONE);
        }
        TextLUtil.setLength(context,holder.tvUserName,holder.tvProfe,dataList.getPeopleName(),dataList.getPeopleOccupation(),126,6);
        holder.tvTime.setText(dataList.getCreateTime());
        Glide.with(context).load(dataList.getPeopleImage())
                .error(R.color.gray_background)
                .into(holder.civLogo);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends ComboCommentBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends ComboCommentBean> collection){
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
        if (v == holder.tvRemveCom) {
            mOnItemClickListener.onClick(position,list.get(position));
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView civLogo;
        private final TextView tvUserName;
        private final TextView tvComment;
        private final TextView tvTime;
        private final ImageView tvRemveCom;
        private final TextView tvProfe;

        public MyViewHolder(View itemView) {
            super(itemView);
            civLogo = (CircleImageView) itemView.findViewById(R.id.civ_logo);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            tvComment = (TextView) itemView.findViewById(R.id.tv_comment);
            tvProfe = (TextView) itemView.findViewById(R.id.tv_identity);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvRemveCom = (ImageView) itemView.findViewById(R.id.tv_remove_com);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int position,ComboCommentBean bean);
    }
}
