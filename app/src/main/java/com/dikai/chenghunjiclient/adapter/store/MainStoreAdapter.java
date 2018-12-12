package com.dikai.chenghunjiclient.adapter.store;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.CarInfoActivity;
import com.dikai.chenghunjiclient.activity.store.CorpInfoActivity;
import com.dikai.chenghunjiclient.activity.store.HotelInfoActivity;
import com.dikai.chenghunjiclient.entity.SupplierListBean;
import com.dikai.chenghunjiclient.fragment.store.StoreHeadFragment;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.UserManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/8/22.
 */

public class MainStoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int HEAD = 0;
    private static final int ITEM = 1;
    private Context context;
    private List<Object> list;
    private StoreHeadFragment mHeadFragment;
    private int type;

    public List<Object> getList() {
        return list;
    }

    public MainStoreAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HEAD){
            View view = LayoutInflater.from(context).inflate(R.layout.item_store_main_head_layout, parent, false);
            HeadViewHolder holder = new HeadViewHolder(view);
            return holder;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_store_main_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            holder.mLayout.setTag(holder);
            holder.mLayout.setOnClickListener(this);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeadViewHolder){
            setFragment();
        }else {
            SupplierListBean bean = (SupplierListBean) list.get(position);
            if(type == Constants.HOME_TYPE_HOTEL){
                ((MyViewHolder)holder).money.setVisibility(View.GONE);
//                ((MyViewHolder)holder).money.setText(bean.getBriefinTroduction());
                ((MyViewHolder)holder).location.setText(bean.getAdress());
                ((MyViewHolder)holder).project.setText(bean.getBriefinTroduction());
            }else if(type == Constants.HOME_TYPE_HUNCHE){
                ((MyViewHolder)holder).money.setVisibility(View.GONE);
                ((MyViewHolder)holder).project.setText("");
                ((MyViewHolder)holder).location.setText(bean.getBriefinTroduction());
            }else if(type == Constants.HOME_TYPE_HUNQING){
                ((MyViewHolder)holder).money.setVisibility(View.GONE);
                ((MyViewHolder)holder).location.setText(bean.getAdress());
                ((MyViewHolder)holder).project.setText("案例 " + bean.getTotalCount());
            }else {
                ((MyViewHolder)holder).money.setVisibility(View.GONE);
                ((MyViewHolder)holder).project.setText("案例 " + bean.getTotalCount());
                ((MyViewHolder)holder).location.setText(bean.getBriefinTroduction());
            }
            ((MyViewHolder)holder).name.setText(bean.getName());
            Glide.with(context).load(bean.getHeadportrait()).centerCrop().into( ((MyViewHolder)holder).img);
        }
    }

    private void setFragment(){
        if(mHeadFragment == null){
            mHeadFragment = new StoreHeadFragment();
            FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.item_main_store_head_layout,mHeadFragment);
            transaction.show(mHeadFragment);
            transaction.commit();
        }else {
            FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.show(mHeadFragment);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends Object> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends Object> collection){
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
        SupplierListBean bean = (SupplierListBean) list.get(position);
        if(UserManager.getInstance(context).isLogin()){
            if("SF_1001000".equals(bean.getProfessionID())){//酒店
                context.startActivity(new Intent(context, HotelInfoActivity.class)
                        .putExtra("id", bean.getSupplierID()).putExtra("userid",bean.getUserID()));
            }else if("SF_2001000".equals(bean.getProfessionID())){//婚车
                context.startActivity(new Intent(context, CarInfoActivity.class)
                        .putExtra("id", bean.getSupplierID()).putExtra("userid",bean.getUserID()));
            }else if("SF_14001000".equals(bean.getProfessionID())){//婚庆
                context.startActivity(new Intent(context, CorpInfoActivity.class)
                        .putExtra("id", bean.getSupplierID())
                        .putExtra("type",1).putExtra("userid",bean.getUserID()));
            }else {//其他
                context.startActivity(new Intent(context, CorpInfoActivity.class)
                        .putExtra("id", bean.getSupplierID())
                        .putExtra("type",0)
                        .putExtra("userid",bean.getUserID()));
            }
        }else {
            Toast.makeText(context, "请先登陆！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return HEAD;
        }else {
            return ITEM;
        }
    }

    public static class HeadViewHolder extends RecyclerView.ViewHolder{
        private FrameLayout mFrameLayout;
        public HeadViewHolder(View itemView) {
            super(itemView);
            mFrameLayout = (FrameLayout) itemView.findViewById(R.id.item_main_store_head_layout);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView project;
        private TextView location;
        private TextView money;
        private ImageView img;
        private LinearLayout mLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_main_store_name);
            project = (TextView) itemView.findViewById(R.id.item_main_store_project);
            location = (TextView) itemView.findViewById(R.id.item_main_store_location);
            money = (TextView) itemView.findViewById(R.id.item_main_store_money);
            img = ((ImageView) itemView.findViewById(R.id.item_main_store_img));
            mLayout = (LinearLayout) itemView.findViewById(R.id.item_main_store_layout);
        }
    }


    public void setType(int type) {
        this.type = type;
    }

    public void setIdent(String ident) {
        switch (ident){
            case "SF_1001000":
                type = Constants.HOME_TYPE_HOTEL;
                break;
            case "SF_2001000":
                type = Constants.HOME_TYPE_HUNCHE;
                break;
            case "SF_14001000":
                type = Constants.HOME_TYPE_HUNQING;
                break;
            default:
                type = Constants.HOME_TYPE_USUAL;
                break;
        }
    }

    private OnAssignListener mAssignListener;

    public void setAssignListener(OnAssignListener assignListener) {
        mAssignListener = assignListener;
    }

    public interface OnAssignListener{
        void onClick(int type, Object bean);
    }
}
