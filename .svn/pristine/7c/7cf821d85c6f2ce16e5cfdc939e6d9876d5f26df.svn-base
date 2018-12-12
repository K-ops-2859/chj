package com.dikai.chenghunjiclient.adapter.wedding;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.InviteDateBean;
import com.dikai.chenghunjiclient.entity.MyInviteBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2018/2/7.
 */

public class MyInviteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Object> list;
    private boolean isFromCode = false;

    public void setFromCode(boolean fromCode) {
        isFromCode = fromCode;
    }

    public MyInviteAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.item_invite_list_head, parent, false);
            HeadViewHolder holder = new HeadViewHolder(view);
            return holder;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_my_invite_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeadViewHolder){
            InviteDateBean bean = (InviteDateBean) list.get(position);
            ((HeadViewHolder) holder).date.setText(bean.getDate());
            ((HeadViewHolder) holder).number.setText(bean.getNumber()+"人");
        }else {
            if (isFromCode){
                ((MyViewHolder)holder).qiandan.setVisibility(View.GONE);
            }else {
                ((MyViewHolder)holder).qiandan.setVisibility(View.VISIBLE);
            }
            MyInviteBean bean = (MyInviteBean) list.get(position);
            if(isValid(bean.getGroomName())){
//                holder.type.setText(" - 新郎");
                ((MyViewHolder)holder).name.setText(isValid(bean.getGroomName())? bean.getGroomName():"未填写姓名");
                ((MyViewHolder)holder).phone.setText(isValid(bean.getGroomPhone())? bean.getGroomPhone():"未填写手机");
            }else {
//                holder.type.setText(" - 新娘");
                ((MyViewHolder)holder).name.setText(isValid(bean.getBrideName())? bean.getBrideName():"未填写姓名");
                ((MyViewHolder)holder).phone.setText(isValid(bean.getBridePhone())? bean.getBridePhone():"未填写手机");
            }
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

    private boolean isValid(String s){
        if(s == null|| "".equals(s.trim())){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof InviteDateBean){
            return 0;
        }else {
            return 1;
        }
    }

    @Override
    public void onClick(View v) {
//        MyViewHolder holder = (MyViewHolder) v.getTag();
//        int position = holder.getAdapterPosition();
//        mOnCarClickListener.onClick(list.get(position));
    }

    public static class HeadViewHolder extends RecyclerView.ViewHolder{
        private TextView date;
        private TextView number;

        public HeadViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            number = (TextView) itemView.findViewById(R.id.number);

        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView phone;
//        private TextView type;
private TextView qiandan;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            qiandan = (TextView) itemView.findViewById(R.id.qiandan);
//            type = (TextView) itemView.findViewById(R.id.type);

        }
    }

    //
    private OnItemClickListener mOnCarClickListener;

    public void setOnItemClickListener(OnItemClickListener onCarClickListener) {
        mOnCarClickListener = onCarClickListener;
    }

    public interface OnItemClickListener{
        void onClick(Object bean);
    }
}