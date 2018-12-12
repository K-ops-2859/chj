package com.dikai.chenghunjiclient.adapter.invitation;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.InvitationBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/10/22.
 */

public class VipInviteAdapter extends RecyclerView.Adapter<VipInviteAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<InvitationBean> list;

    public VipInviteAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vip_invite_record, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        holder.mRecyclerView.setLayoutManager(mLayoutManager);
        holder.prizeLayout.setTag(holder);
        holder.prizeLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InvitationBean bean = list.get(position);
        holder.name.setText(bean.getName());
        holder.phone.setText(bean.getPhone());
        holder.prize.setText(bean.getMoney());
        if(bean.getAuditStatus() == 1){
            holder.state.setTextColor(ContextCompat.getColor(context,R.color.orange2));
            holder.state.setText("审核通过");
        }else {
            holder.state.setTextColor(ContextCompat.getColor(context,R.color.gray_text));
            holder.state.setText(bean.getAuditStatus() == 0?"审核中":"审核未通过");
        }
        if(bean.isCanSee()){
            holder.sourceLayout.setVisibility(View.VISIBLE);
            holder.arrow.setImageResource(R.mipmap.ic_app_up_arrow);
            PrizeSourceAdapter sourceAdapter = new PrizeSourceAdapter(context);
            holder.mRecyclerView.setAdapter(sourceAdapter);
            sourceAdapter.refresh(bean.getMakeMoneyData());
        }else {
            holder.sourceLayout.setVisibility(View.GONE);
            holder.arrow.setImageResource(R.mipmap.ic_app_down_arrow);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends InvitationBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends InvitationBean> collection){
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
        if(v == holder.prizeLayout){
            list.get(position).setCanSee(!list.get(position).isCanSee());
            notifyItemChanged(position);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView state;
        private TextView phone;
        private TextView prize;
        private ImageView arrow;
        private RecyclerView mRecyclerView;
        private LinearLayout prizeLayout;
        private LinearLayout sourceLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            state = (TextView) itemView.findViewById(R.id.state);
            phone = (TextView) itemView.findViewById(R.id.phone);
            prize = (TextView) itemView.findViewById(R.id.prize);
            arrow = (ImageView) itemView.findViewById(R.id.arrow);
            prizeLayout = (LinearLayout) itemView.findViewById(R.id.prize_layout);
            sourceLayout = (LinearLayout) itemView.findViewById(R.id.source_layout);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.source_recycler);
        }
    }
    //
    private OnItemClickListener mOnCarClickListener;

    public void setOnItemClickListener(OnItemClickListener onCarClickListener) {
        mOnCarClickListener = onCarClickListener;
    }

    public interface OnItemClickListener{
        void onClick(InvitationBean bean);
    }
}