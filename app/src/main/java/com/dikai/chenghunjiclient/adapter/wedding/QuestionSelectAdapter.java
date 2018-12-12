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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/12/13.
 */

public class QuestionSelectAdapter extends RecyclerView.Adapter<QuestionSelectAdapter.MyViewHolder> implements View.OnClickListener {

    private Context context;
    private List<SelectBean> list;
    
    public QuestionSelectAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_question_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SelectBean bean = list.get(position);
        holder.name.setText(bean.getName());
        if(bean.isSelected()){
            holder.name.getPaint().setFakeBoldText(true);
            holder.img.setVisibility(View.VISIBLE);
        }else {
            holder.name.getPaint().setFakeBoldText(false);
            holder.img.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void remove(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addAll(Collection<? extends SelectBean> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends SelectBean> collection){
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
        if(v == holder.mLayout){
            list.get(position).setSelected(!list.get(position).isSelected());
            notifyItemChanged(position);
        }
    }

    public List<SelectBean> getList() {
        return list;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        private TextView name;
        private LinearLayout mLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.ll_img);
            name = (TextView) itemView.findViewById(R.id.ll_text);
            mLayout = (LinearLayout) itemView.findViewById(R.id.ll_layout);
        }
    }

//    private OnMoreClickListener mMoreClickListener;
//
//    public void setMoreClickListener(OnMoreClickListener moreClickListener) {
//        mMoreClickListener = moreClickListener;
//    }
//
//    public interface OnMoreClickListener{
//        void onClick(View view, int position, SelectBean bean);
//    }
}