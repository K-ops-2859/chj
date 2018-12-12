package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.GetInviteBean;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lucio on 2017/9/16.
 */

public class MyInviteAdapter  extends RecyclerView.Adapter<MyInviteAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<GetInviteBean> list;

    public MyInviteAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_invite_list_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.refuse.setTag(holder);
        holder.refuse.setOnClickListener(this);
        holder.agree.setTag(holder);
        holder.agree.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GetInviteBean bean = list.get(position);
        holder.man.setText(bean.getUserNmae());
        holder.time.setText(bean.getCreateTime());
        holder.team.setText(bean.getName());
        Glide.with(context).load(bean.getLogo()).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends GetInviteBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends GetInviteBean> collection){
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
        if(v == holder.agree){
            mOnCarClickListener.onAgree(list.get(position));
        }else if(v == holder.refuse){
            mOnCarClickListener.onRefuse(list.get(position));
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView time;
        private CircleImageView logo;
        private TextView man;
        private TextView team;
        private TextView refuse;
        private TextView agree;

        public MyViewHolder(View itemView) {
            super(itemView);
            logo = (CircleImageView) itemView.findViewById(R.id.item_invite_logo);
            time = (TextView) itemView.findViewById(R.id.item_invite_time);
            man = (TextView) itemView.findViewById(R.id.item_invite_man);
            team = (TextView) itemView.findViewById(R.id.item_invite_team);
            refuse = (TextView) itemView.findViewById(R.id.item_invite_refuse);
            agree = (TextView) itemView.findViewById(R.id.item_invite_agree);
        }
    }

    private OnCarClickListener mOnCarClickListener;

    public void setOnCarClickListener(OnCarClickListener onCarClickListener) {
        mOnCarClickListener = onCarClickListener;
    }

    public interface OnCarClickListener{
        void onAgree(GetInviteBean bean);
        void onRefuse(GetInviteBean bean);
    }
}
