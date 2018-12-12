package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.CarInfoActivity;
import com.dikai.chenghunjiclient.activity.store.CorpInfoActivity;
import com.dikai.chenghunjiclient.activity.store.HotelInfoActivity;
import com.dikai.chenghunjiclient.entity.NewSupsBean;
import com.dikai.chenghunjiclient.util.UserManager;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/9/23.
 */

public class SearchSupAdapter extends RecyclerView.Adapter<SearchSupAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<NewSupsBean> list;
    private boolean isSelect = false;

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public SearchSupAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_sup_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewSupsBean bean = list.get(position);
        holder.tag1Layout.setVisibility(View.GONE);
        holder.tag_2.setVisibility(View.GONE);
        holder.tag_3.setVisibility(View.GONE);
        holder.name.setText(bean.getName());
        String info = bean.getStateCount() +" 动态   |   " + bean.getAnliCount() + " 案例";
        holder.info.setText(info);
        if(bean.getXfyl() == 1){
            holder.tag_2.setVisibility(View.VISIBLE);
        }
        if(bean.getHldb() == 1){
            holder.tag_3.setVisibility(View.VISIBLE);
        }
        if("99C06C5A-DDB8-46A1-B860-CD1227B4DB68".equalsIgnoreCase(bean.getSupplierIdentity()) && bean.getTag() != null && !"".equals(bean.getTag())){
            holder.tag1Layout.setVisibility(View.VISIBLE);
            holder.tag_1.setText(bean.getTag());
        }
        Glide.with(context).load(bean.getLogo()).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends NewSupsBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends NewSupsBean> collection){
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
        NewSupsBean bean = list.get(position);
        if(v == holder.mLayout){
            if(isSelect){
                mSelectedClickListener.onClick(position,bean);
            }else {
                if(UserManager.getInstance(context).isLogin()){
                    if("99C06C5A-DDB8-46A1-B860-CD1227B4DB68".equalsIgnoreCase(bean.getSupplierIdentity())){//酒店
                        context.startActivity(new Intent(context, HotelInfoActivity.class)
                                .putExtra("id", bean.getId()).putExtra("userid",bean.getUserId()));
//                }else if("2526D327-B0AE-4D88-922E-1F7A91722422".equalsIgnoreCase(bean.getSupplierIdentity())){//婚车
//                    context.startActivity(new Intent(context, CarInfoActivity.class)
//                            .putExtra("id", bean.getId()).putExtra("userid",bean.getUserId()));
                    }else if("7DC8EDF8-A068-400F-AFD0-417B19DB3C7C".equalsIgnoreCase(bean.getSupplierIdentity())){//婚庆
                        context.startActivity(new Intent(context, CorpInfoActivity.class)
                                .putExtra("id", bean.getId())
                                .putExtra("type",1).putExtra("userid",bean.getUserId()));
                    }else {//其他
                        context.startActivity(new Intent(context, CorpInfoActivity.class)
                                .putExtra("id", bean.getId())
                                .putExtra("type",0)
                                .putExtra("userid",bean.getUserId()));
                    }
                }else {
                    Toast.makeText(context, "请先登陆！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private SelectableRoundedImageView pic;
        private ImageView vImg;
        private ImageView gift;
        private TextView name;
        private TextView info;
        private TextView tag_1;
        private TextView tag_2;
        private TextView tag_3;
        private LinearLayout mLayout;
        private LinearLayout tag1Layout;
        public MyViewHolder(View itemView) {
            super(itemView);
            pic = (SelectableRoundedImageView) itemView.findViewById(R.id.new_store_sup_img);
            vImg = (ImageView) itemView.findViewById(R.id.new_store_sup_v);
            gift = (ImageView) itemView.findViewById(R.id.new_store_sup_gift);
            name = (TextView) itemView.findViewById(R.id.new_store_sup_name);
            info = (TextView) itemView.findViewById(R.id.new_store_sup_info);
            tag_1 = (TextView) itemView.findViewById(R.id.tag_1);
            tag_2 = (TextView) itemView.findViewById(R.id.tag_2);
            tag_3 = (TextView) itemView.findViewById(R.id.tag_3);
            mLayout = (LinearLayout) itemView.findViewById(R.id.new_store_sup_layout);
            tag1Layout = (LinearLayout) itemView.findViewById(R.id.tag_1_layout);
        }
    }

    private OnSelectedClickListener mSelectedClickListener;

    public void setSelectedClickListener(OnSelectedClickListener selectedClickListener) {
        mSelectedClickListener = selectedClickListener;
    }

    public interface OnSelectedClickListener{
        void onClick(int position, NewSupsBean bean);
    }
}