package com.dikai.chenghunjiclient.adapter.me;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.ServiceModule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lucio on 2018/8/31.
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private SparseArray<ServiceModule> allType;
    private SparseArray<ServiceModule> tempType;
    private List<ServiceModule> showList;

    /**
     * ServiceModule-type:
     * 0:我的案例
     * 1:结算记录
     * 2:邀请结婚
     * 3:宴会厅管理
     * 4:车辆管理
     * 5:我的车队
     * 6:婚礼照片
     * 7:婚礼视频
     * 8:分享APP
     * 9:下载婚庆版
     * 10:领取礼品
     * 11:婚礼支付
     * 12:客源分配
     */
    public ServiceAdapter(Context context) {
        this.context = context;
        showList = new ArrayList<>();
        Collections.addAll(showList,new ServiceModule(0,R.mipmap.ic_app_gold_case,"我的案例"),
                new ServiceModule(1,R.mipmap.ic_app_gold_order,"结算记录"),
                new ServiceModule(2,R.mipmap.ic_app_gold_invite,"邀请新人"),
                new ServiceModule(3,R.mipmap.ic_app_gold_room,"宴会厅管理"),
                new ServiceModule(4,R.mipmap.ic_app_ce_car,"车辆管理"),
                new ServiceModule(5,R.mipmap.ic_app_ce_team,"我的车队"),
                new ServiceModule(6,R.mipmap.ic_app_ce_photo,"婚礼照片"),
                new ServiceModule(7,R.mipmap.ic_app_ce_video,"婚礼视频"),
                new ServiceModule(8,R.mipmap.ic_app_gold_share,"分享APP"),
                new ServiceModule(9,R.mipmap.ic_app_gold_download,"下载婚庆版"),
                new ServiceModule(10,R.mipmap.ic_app_ce_getprize,"领取礼品"),
                new ServiceModule(11,R.mipmap.ic_app_gold_pay,"婚礼支付"),
                new ServiceModule(12,R.mipmap.ic_app_gold_keyuan,"客源分配"));
        allType = new SparseArray<>();
        tempType = new SparseArray<>();
        for (ServiceModule bean: showList) {
            allType.put(bean.getType(),bean);
            tempType.put(bean.getType(),bean);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service_list_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ServiceModule bean = showList.get(position);
        holder.name.setText(bean.getName());
        Glide.with(context).load(bean.getIcon()).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

//    public void delete(int position){
//        list.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    public void refresh(Collection<? extends ServiceModule> collection){
//        showList = new ArrayList<>();
//        showList.addAll(collection);
//        notifyDataSetChanged();
//    }

    public void refresh(){
        try {
            showList = new ArrayList<>();
            for (int i = 0; i < tempType.size(); i++) {
                showList.add(tempType.get(tempType.keyAt(i)));
                Log.e("type",tempType.get(tempType.keyAt(i)).getType() + " name：" + tempType.get(tempType.keyAt(i)).getName());
            }
            Log.e("showList",showList.size() + "  " + showList.toString());
        }catch (Exception e){
            Log.e("Exception",e.toString());
        }
        notifyDataSetChanged();
    }

    public void setHide(int type) {
        tempType.delete(type);
    }

    public void setShow(int type) {
        tempType.put(type,allType.get(type));
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {
        MyViewHolder holder = (MyViewHolder) v.getTag();
        int position = holder.getAdapterPosition();
        mOnItemClickListener.onClick(showList.get(position));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private ImageView pic;
        private LinearLayout mLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            pic = (ImageView) itemView.findViewById(R.id.icon);
            mLayout = (LinearLayout) itemView.findViewById(R.id.bottom_layout);
        }
    }
    //
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(ServiceModule bean);
    }
}