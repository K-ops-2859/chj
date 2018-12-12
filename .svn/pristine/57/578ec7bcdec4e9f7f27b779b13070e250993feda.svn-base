package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.FansBean;
import com.dikai.chenghunjiclient.entity.FansBean;
import com.dikai.chenghunjiclient.util.TextLUtil;
import com.dikai.chenghunjiclient.util.UserManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lucio on 2018/5/28.
 */

public class FocusAdapter extends RecyclerView.Adapter<FocusAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<FansBean> list;
    private boolean isFans = false;

    public void setFans(boolean fans) {
        isFans = fans;
    }

    public FocusAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_focus_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        holder.state.setTag(holder);
        holder.state.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FansBean bean = list.get(position);
        String profession = UserManager.getInstance(context).getProfession(bean.getProfession());
        if(isFans){
            holder.state.setVisibility(View.GONE);
            TextLUtil.setLength(context,holder.name,holder.profession,bean.getName(),profession,82,6);
        }else {
            holder.state.setVisibility(View.VISIBLE);
            TextLUtil.setLength(context,holder.name,holder.profession,bean.getName(),profession,154,6);
        }
        Glide.with(context).load(bean.getHeadportrait()).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends FansBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends FansBean> collection){
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
        if(v == holder.mLayout){
            mOnItemClickListener.onClick(0,position,list.get(position));
        }else if(v == holder.state){
            mOnItemClickListener.onClick(1,position,list.get(position));
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView profession;
        private TextView state;
        private CircleImageView logo;
        private LinearLayout mLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            profession = (TextView) itemView.findViewById(R.id.profession);
            state = (TextView) itemView.findViewById(R.id.state);
            logo = (CircleImageView) itemView.findViewById(R.id.logo);
            mLayout = (LinearLayout) itemView.findViewById(R.id.bottom_layout);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int type, int position, FansBean bean);
    }
}