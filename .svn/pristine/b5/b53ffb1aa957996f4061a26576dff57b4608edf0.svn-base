package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.SupPipelineBean;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/10/8.
 */

public class SupPipelineAdapter  extends RecyclerView.Adapter<SupPipelineAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<SupPipelineBean> list;

    public SupPipelineAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sup_pipeline_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.delete.setTag(holder);
        holder.delete.setOnClickListener(this);
        holder.mFoldingCell.setTag(holder);
        holder.mFoldingCell.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SupPipelineBean bean = list.get(position);
        holder.date.setText(bean.getCreateTime());
        holder.date2.setText(bean.getCreateTime());
        holder.name.setText(bean.getUserName());
        holder.name2.setText(bean.getUserName());
        holder.phone.setText(bean.getPhone());
        holder.phone2.setText(bean.getPhone());
        holder.rephone.setText("".equals(bean.getSinglePersonPhone())?"无接单人手机号":bean.getSinglePersonPhone());
        holder.wedDate.setText("".equals(bean.getWeddingDate())?"无日期":bean.getWeddingDate());
        holder.delete.setVisibility(View.GONE);
        if(bean.getActivityType() == 0){//伴手礼
            holder.supName.setText("成婚纪平台");
            holder.source.setText("伴手礼 - " + (bean.getPaymentType() == 0?"线上支付":"线下支付"));
            if(bean.getMakePayment() == 0){
                holder.state.setText("待支付");
                holder.state2.setText("待支付");
            }else if(bean.getMakePayment() == 1){
                holder.state.setText("已支付");
                holder.state2.setText("已支付");
            }
        }else if (bean.getActivityType() == 1){//代收
            holder.supName.setText(bean.getDistributionFacilitatorName());
            holder.source.setText("代收 - " + (bean.getPaymentType() == 0?"线上支付":"线下支付"));
            if(bean.getMakeMoney() == 0){
                if(bean.getMakePayment() == 0){
                    holder.state.setText("待支付");
                    holder.state2.setText("待支付");
                }else if(bean.getMakePayment() == 1){
                    holder.state.setText("已支付");
                    holder.state2.setText("已支付");
                }
            }else {
                holder.state.setText("已提现");
                holder.state2.setText("已提现");
            }
        }else if (bean.getActivityType() == 2){//婚礼返还
            holder.supName.setText("成婚纪平台");
            holder.source.setText("婚礼返还 - " + (bean.getPaymentType() == 0?"线上支付":"线下支付"));
            if(bean.getMakePayment() == 0){
                holder.state.setText("待支付");
                holder.state2.setText("待支付");
            }else if(bean.getMakePayment() == 1){
                holder.state.setText("已支付");
                holder.state2.setText("已支付");
            }
        }
        if(bean.getType() == 0){
            holder.shenhe.setText("未审核");
            holder.shenhe2.setText("未审核");
        }else if(bean.getType() == 1){
            holder.shenhe.setText("已审核");
            holder.shenhe2.setText("已审核");
        }else {
            holder.delete.setVisibility(View.VISIBLE);
            holder.shenhe.setText("已驳回");
            holder.shenhe2.setText("已驳回");
        }
        holder.allMoney.setText(bean.getMoney() + " 元");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends SupPipelineBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends SupPipelineBean> collection){
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
        if(v == holder.delete){
            mOnItemClickListener.onClick(list.get(position),position);
        }else if(v == holder.mFoldingCell){
            holder.mFoldingCell.toggle(false);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private FoldingCell mFoldingCell;
        private TextView date;
        private TextView state;
        private TextView name;
        private TextView phone;
        private TextView shenhe;
        private TextView date2;
        private TextView state2;
        private TextView name2;
        private TextView phone2;
        private TextView shenhe2;
        private TextView allMoney;
        private TextView source;
        private TextView supName;
        private TextView delete;
        private TextView rephone;
        private TextView wedDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            mFoldingCell = (FoldingCell) itemView.findViewById(R.id.folding_cell);
            date = (TextView) itemView.findViewById(R.id.date);
            name = (TextView) itemView.findViewById(R.id.name);
            state = (TextView) itemView.findViewById(R.id.state);
            phone = (TextView) itemView.findViewById(R.id.phone);
            shenhe = (TextView) itemView.findViewById(R.id.shenhe_type);
            date2 = (TextView) itemView.findViewById(R.id.date2);
            name2 = (TextView) itemView.findViewById(R.id.name2);
            state2 = (TextView) itemView.findViewById(R.id.state2);
            phone2 = (TextView) itemView.findViewById(R.id.phone2);
            shenhe2 = (TextView) itemView.findViewById(R.id.shenhe_type2);
            allMoney = (TextView) itemView.findViewById(R.id.all_money);
            source = (TextView) itemView.findViewById(R.id.source);
            supName = (TextView) itemView.findViewById(R.id.supname);
            delete = (TextView) itemView.findViewById(R.id.delete);
            rephone = (TextView) itemView.findViewById(R.id.rephone);
            wedDate = (TextView) itemView.findViewById(R.id.wedding_date);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(SupPipelineBean bean,int position);
    }
}