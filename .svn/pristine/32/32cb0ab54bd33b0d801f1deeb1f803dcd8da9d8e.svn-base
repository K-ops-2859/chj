package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.PipelineBean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/9/17.
 */

public class PresentAdapter extends RecyclerView.Adapter<PresentAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<PipelineBean> list;

    public PresentAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotel_pay_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.delete.setTag(holder);
        holder.delete.setOnClickListener(this);
        holder.reject.setTag(holder);
        holder.reject.setOnClickListener(this);
        holder.pay.setTag(holder);
        holder.pay.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PipelineBean bean = list.get(position);
        holder.name.setText(bean.getFacilitatorName());
        holder.delete.setVisibility(View.GONE);
        holder.reject.setVisibility(View.GONE);
        holder.pay.setVisibility(View.GONE);
        if(bean.getMakePayment() == 0){
            holder.state.setText("待付款");
            holder.reject.setVisibility(View.VISIBLE);
            holder.pay.setVisibility(View.VISIBLE);
        }else if(bean.getMakePayment() == 1){
            holder.state.setText("已付款");
            holder.delete.setVisibility(View.VISIBLE);
        }else {
            holder.state.setText("已拒单");
        }

        if(bean.getType() == 0){
            holder.shenhe.setText("待审核");
        }else if(bean.getType() == 1){
            holder.shenhe.setText("已审核");
        }else {
            holder.shenhe.setText("已驳回");
        }

        if(bean.getOrderType() == 0){//伴手礼
            holder.typeInfo.setText("    返至伴手礼额度");
        }else if(bean.getOrderType() == 1){//婚礼返还
            holder.typeInfo.setText("    返至婚礼返还额度");
        }else if(bean.getOrderType() == 2){//代收
            holder.typeInfo.setText("    返至代收额度");
        }

        holder.allMoney.setText("￥"+bean.getMoney());
        holder.netMoney.setText("￥"+bean.getMoney());
        holder.returnMoney.setText("￥"+bean.getDistributionLine());
        holder.date.setText(bean.getCreateTime());
        holder.payType.setText(bean.getPaymentType() == 0?"线上支付":"线下支付");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends PipelineBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends PipelineBean> collection){
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
        if(v == holder.reject){
            mOnItemClickListener.onClick(list.get(position),position,0);
        }else if(v == holder.pay){
            mOnItemClickListener.onClick(list.get(position),position,1);
        }else if(v == holder.delete){
            mOnItemClickListener.onClick(list.get(position),position,2);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView state;
        private TextView allMoney;
        private TextView returnMoney;
        private TextView date;
        private TextView payType;
        private TextView netMoney;
        private TextView reject;
        private TextView pay;
        private TextView delete;
        private TextView shenhe;
        private TextView typeInfo;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            state = (TextView) itemView.findViewById(R.id.state);
            allMoney = (TextView) itemView.findViewById(R.id.all_money);
            returnMoney = (TextView) itemView.findViewById(R.id.return_money);
            date = (TextView) itemView.findViewById(R.id.date);
            payType = (TextView) itemView.findViewById(R.id.pay_type);
            netMoney = (TextView) itemView.findViewById(R.id.net_money);
            reject = (TextView) itemView.findViewById(R.id.reject);
            pay = (TextView) itemView.findViewById(R.id.pay);
            delete = (TextView) itemView.findViewById(R.id.delete);
            shenhe = (TextView) itemView.findViewById(R.id.shenhe_type);
            typeInfo = (TextView) itemView.findViewById(R.id.type_info);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(PipelineBean bean,int position, int type);
    }
}