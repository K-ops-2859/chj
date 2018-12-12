package com.dikai.chenghunjiclient.adapter.wedding;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.entity.PersonAddressData;
import com.dikai.chenghunjiclient.entity.WeddingKnowData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/5/2.
 */

public class ShippingAddressAdapter extends RecyclerView.Adapter<ShippingAddressAdapter.ShippintAddressVH> implements View.OnClickListener {

    private Context mContext;
    private  List<PersonAddressData.DataList> mData;

    public ShippingAddressAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
    }

    @Override
    public ShippintAddressVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_shippingaddress, parent, false);
        ShippintAddressVH holder = new ShippintAddressVH(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        holder.edit.setTag(holder);
        holder.edit.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ShippintAddressVH holder, int position) {
        PersonAddressData.DataList dataList = mData.get(position);
        holder.tvName.setText(dataList.getConsignee());
        holder.tvAddress.setText(dataList.getDetailedAddress());
        holder.tvPhone.setText(dataList.getConsigneePhone());
        if (dataList.getDefaultAddress()==0) {
            holder.tvDefault.setVisibility(View.GONE);
        } else {
            holder.tvDefault.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setList(List<PersonAddressData.DataList> list) {
        mData.clear();
        append(list);
    }

    public void append(List<PersonAddressData.DataList> list) {
        int startPosition = mData.size();
        int itemCount = list.size();
        mData.addAll(list);
        if (startPosition > 0 && itemCount >0) {
            notifyItemRangeInserted(startPosition, itemCount);
        } else {
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        notifyItemRemoved(0);
    }

    @Override
    public void onClick(View v) {
        ShippintAddressVH holder = (ShippintAddressVH) v.getTag();
        int position = holder.getAdapterPosition();
        if(v == holder.mLayout){
            mOnCarClickListener.onClick(mData.get(position),position,0);
        }else if(v == holder.edit){
            mOnCarClickListener.onClick(mData.get(position),position,1);
        }
    }

    class ShippintAddressVH extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvPhone;
        private TextView tvAddress;
        private TextView tvDefault;
        private ImageView edit;
        private LinearLayout mLayout;
        ShippintAddressVH(View itemView) {
            super(itemView);
            edit = (ImageView) itemView.findViewById(R.id.edit);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvDefault = (TextView) itemView.findViewById(R.id.tv_default);
            mLayout = (LinearLayout) itemView.findViewById(R.id.bottom_layout);
        }
    }

    private OnItemClickListener mOnCarClickListener;

    public void setOnItemClickListener(OnItemClickListener onCarClickListener) {
        mOnCarClickListener = onCarClickListener;
    }

    public interface OnItemClickListener{
        void onClick(PersonAddressData.DataList bean,int position, int type);
    }
}
