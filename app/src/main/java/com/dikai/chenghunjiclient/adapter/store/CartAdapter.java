package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.CartBean;
import com.dikai.chenghunjiclient.entity.CartBean;
import com.dikai.chenghunjiclient.view.MyImageView;
import com.dikai.chenghunjiclient.view.MyRelativeLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/4/28.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Context context;
    private List<CartBean> list;
    private List<CartBean> selectedList;
    private boolean isEdit = false;

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyDataSetChanged();
        if(!isEdit){
            getTotalPrice();
        }
    }

    public void setAllSelect(boolean allSelect) {
        for (CartBean bean : list) {
            bean.setSelected(allSelect);
        }
        notifyDataSetChanged();
    }

    public List<CartBean> getSelectedList() {
        return selectedList;
    }

    public CartAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        selectedList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart_list_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mCardView.setTag(holder);
        holder.mCardView.setOnClickListener(this);
        holder.add.setTag(holder);
        holder.add.setOnClickListener(this);
        holder.subtract.setTag(holder);
        holder.subtract.setOnClickListener(this);
        holder.mCheckBox.setTag(holder);
        holder.mCheckBox.setOnCheckedChangeListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CartBean bean = list.get(position);
        if(isEdit){
            holder.mCheckBox.setVisibility(View.VISIBLE);
        }else {
            holder.mCheckBox.setVisibility(View.GONE);
        }
        holder.mCheckBox.setChecked(bean.isSelected());
        holder.name.setText(bean.getCommodityName());
        holder.type.setText(bean.getPlaceOriginName());
        holder.price.setText("￥"+bean.getQuota());
        holder.num.setText(""+bean.getCount());
        Glide.with(context).load(bean.getBriefIntroduction()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends CartBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends CartBean> collection){
        list = new ArrayList<>();
        list.addAll(collection);
        selectedList = new ArrayList<>();
        selectedList.addAll(collection);
        notifyDataSetChanged();
        getTotalPrice();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {
        MyViewHolder holder = (MyViewHolder) v.getTag();
        int position = holder.getAdapterPosition();
        if(v == holder.mCardView){
            mOnItemClickListener.onClick(list.get(position),position,0);
        }else if(v == holder.add){
            mOnItemClickListener.onClick(list.get(position),position,1);
        }else if(v == holder.subtract){
            mOnItemClickListener.onClick(list.get(position),position,2);
        }
    }

    private void getTotalPrice(){
        BigDecimal price = new BigDecimal(0);
        try {
            if(isEdit){
                for (CartBean bean : selectedList) {
                    price = price.add(new BigDecimal(bean.getQuota()).multiply(new BigDecimal(bean.getCount())));
                }
            }else {
                for (CartBean bean : list) {
                    price = price.add(new BigDecimal(bean.getQuota()).multiply(new BigDecimal(bean.getCount())));
                }
            }
        }catch (Exception e){
            Log.e("",e.toString());
        }
        mOnPriceChangeListener.onChanged(price);
    }

//    public BigDecimal getCaryPrice(){
//        BigDecimal price = new BigDecimal(0);
//        try {
//            for (CartBean bean : list) {
//                price = price.add(new BigDecimal(bean.getQuota()).multiply(new BigDecimal(bean.getCount())));
//            }
//        }catch (Exception e){
//            Log.e("",e.toString());
//        }
//        return price;
//    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.e("onCheckedChanged",isChecked + " ===== 执行");
        MyViewHolder holder = (MyViewHolder) buttonView.getTag();
        try {
            int position = holder.getAdapterPosition();
            list.get(position).setSelected(isChecked);
            if(isChecked){
                if(!selectedList.contains(list.get(position))){
                    selectedList.add(list.get(position));
                }
            }else {
                if(selectedList.contains(list.get(position))){
                    selectedList.remove(list.get(position));
                }
            }
        }catch (Exception e){
            Log.e("",e.toString());
        }
        getTotalPrice();
    }

    public void itemChange(int position, int num){
        list.get(position).setCount(num);
        notifyItemChanged(position);
        getTotalPrice();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private AppCompatCheckBox mCheckBox;
        private CardView mCardView;
        private TextView name;
        private TextView type;
        private TextView price;
        private ImageView img;
        private ImageView subtract;
        private ImageView add;
        private TextView num;

        public MyViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.bottom_layout);
            mCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.check_box);
            name = (TextView) itemView.findViewById(R.id.name);
            type = (TextView) itemView.findViewById(R.id.type);
            price = (TextView) itemView.findViewById(R.id.price);
            img = (ImageView) itemView.findViewById(R.id.img);
            subtract = (ImageView) itemView.findViewById(R.id.subtract);
            add = (ImageView) itemView.findViewById(R.id.add);
            num = (TextView) itemView.findViewById(R.id.num);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onitemClickListener) {
        mOnItemClickListener = onitemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(CartBean bean, int position, int type);
    }

    private OnPriceChangeListener mOnPriceChangeListener;

    public void setOnPriceChangeListener(OnPriceChangeListener onPriceChangeListener) {
        mOnPriceChangeListener = onPriceChangeListener;
    }

    public interface OnPriceChangeListener{
        void onChanged(BigDecimal totalPrice);
    }
}