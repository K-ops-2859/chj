package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.entity.CustomerInfoBySupplierData;
import com.dikai.chenghunjiclient.entity.CustomerInfoData;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cmk03 on 2018/3/26.
 */

public class WeddingPhotoAdapter extends BasePhotoAdapter {

    private int HEADER = 0;
    private int YONGHU = 1;
    private int GONGYINGSHANG = 2;
    private Context mContext;
    private String identity;

    public WeddingPhotoAdapter(Context context, String identity) {
        this.mContext = context;
        this.identity = identity;
        System.out.println("身份======" + identity);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            if (identity.equals("SF_12001000")) { //新人身份  选择供应商
                return YONGHU;
            } else {
                return GONGYINGSHANG; // 供应商身份  选择新人
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == HEADER) {
            View view = inflater.inflate(R.layout.adapter_wedding_header, parent, false);
            viewHolder = new HeaderVH(view);
        } else if (viewType == GONGYINGSHANG) {
            View view = inflater.inflate(R.layout.adapter_photo_supplier, parent, false);
            viewHolder = new SupplierVH(view, onItemClickListener, mData);
        } else if (viewType == YONGHU) {
            View view = inflater.inflate(R.layout.adapter_photo_user, parent, false);
            viewHolder = new UserVH(view, onItemClickListener, mData);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderVH) {
            HeaderVH vh = (HeaderVH) holder;
            if (identity.equals("SF_12001000")) {
                vh.tvHeader.setText("请从以下绑定您的婚庆公司中选择最终合作的");
            } else {
                vh.tvHeader.setText("已绑定新人  " + mData.size());
            }
        } else if (holder instanceof UserVH) {
            CustomerInfoData.DataList dataList = (CustomerInfoData.DataList) mData.get(position - 1);
            UserVH vh = (UserVH) holder;
            vh.tvSupplierName.setText(dataList.getCorpName());
            Glide.with(mContext).load(dataList.getLogo()).error(R.color.gray_background).into(vh.civLogo);
        } else if (holder instanceof SupplierVH) {
            CustomerInfoBySupplierData.DataList dataList = (CustomerInfoBySupplierData.DataList) mData.get(position - 1);
            SupplierVH vh = (SupplierVH) holder;
            vh.tvName1.setText(dataList.getGroomName());
            vh.tvPhone1.setText(dataList.getGroomPhoneNo());
            vh.tvName2.setText(dataList.getBrideName());
            vh.tvPhone2.setText(dataList.getBridePhoneNo());
        }
    }

    @Override
    public int getItemCount() {
        System.out.println("大小==========“" + mData.size());
        return mData.size() + 1;
    }


    static class HeaderVH extends RecyclerView.ViewHolder {

        private final TextView tvHeader;

        public HeaderVH(View itemView) {
            super(itemView);
            tvHeader = (TextView) itemView.findViewById(R.id.tv_header);
        }
    }

    static class SupplierVH extends RecyclerView.ViewHolder {

        private final TextView tvName1;
        private final TextView tvPhone1;
        private final TextView tvName2;
        private final TextView tvPhone2;

        public SupplierVH(View itemView, final OnItemClickListener<CustomerInfoBySupplierData.DataList> listener, final List<CustomerInfoBySupplierData.DataList> dataList) {
            super(itemView);
            tvName1 = (TextView) itemView.findViewById(R.id.tv_name1);
            tvPhone1 = (TextView) itemView.findViewById(R.id.tv_phone1);
            tvName2 = (TextView) itemView.findViewById(R.id.tv_name2);
            tvPhone2 = (TextView) itemView.findViewById(R.id.tv_phone2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition() - 1;
                    listener.onItemClick(view, position, dataList.get(position));
                }
            });
        }
    }

    static class UserVH extends RecyclerView.ViewHolder {


        private final CircleImageView civLogo;
        private final TextView tvSupplierName;


        public UserVH(View itemView, final OnItemClickListener<CustomerInfoData.DataList> listener, final List<CustomerInfoData.DataList> data) {
            super(itemView);
            civLogo = (CircleImageView) itemView.findViewById(R.id.civ_logo);
            tvSupplierName = (TextView) itemView.findViewById(R.id.tv_supplier_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition() - 1;
                    listener.onItemClick(view, position, data.get(position));
                }
            });
        }
    }
}
