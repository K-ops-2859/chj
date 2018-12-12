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
import com.dikai.chenghunjiclient.entity.NewsProBean;
import com.dikai.chenghunjiclient.entity.ResultGetNewsInfo;
import com.joooonho.SelectableRoundedImageView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucio on 2017/12/7.
 */

public class WedProjectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private static final int HEAD = 0;
    private static final int ITEM = 1;
    private Context context;
    private List<Object> list;

    public WedProjectAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == HEAD){
            View view = LayoutInflater.from(context).inflate(R.layout.wedding_project_man_info, parent, false);
            HeadViewHolder holder = new HeadViewHolder(view);
            holder.edit.setTag(holder);
            holder.edit.setOnClickListener(this);
            return holder;
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_man_project_layout, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            holder.layout1.setTag(holder);
            holder.layout1.setOnClickListener(this);
            holder.edit.setTag(holder);
            holder.edit.setOnClickListener(this);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeadViewHolder){
            ResultGetNewsInfo info = (ResultGetNewsInfo) list.get(position);
            ((HeadViewHolder) holder).name.setText("姓名："+info.getName());
            ((HeadViewHolder) holder).phone.setText("手机："+info.getPhone());
            ((HeadViewHolder) holder).place.setText("籍贯："+info.getPlaceOfOrigin());
            ((HeadViewHolder) holder).star.setText("星座："+info.getConstellation());
            ((HeadViewHolder) holder).profession.setText("职业："+info.getOccupation());
            ((HeadViewHolder) holder).qq.setText("QQ："+info.getQQNumber());
            ((HeadViewHolder) holder).wx.setText("微信："+info.getWechatNumber());
            if(info.getDateOfBirth() != null && !"0001-01-01".equals(info.getDateOfBirth())){
                ((HeadViewHolder) holder).birth.setText("出生日期："+info.getDateOfBirth());
            }else {
                ((HeadViewHolder) holder).birth.setText("出生日期：");
            }
        }else {
            NewsProBean bean = (NewsProBean) list.get(position);
            ((MyViewHolder)holder).title.setText(bean.getQuestionClassification());
            if(bean.getTypeQuestion() == 0){
                if(bean.getAnswer() == null || "".equals(bean.getAnswer().trim())){
                    ((MyViewHolder)holder).layout1.setVisibility(View.VISIBLE);
                    ((MyViewHolder)holder).layout2.setVisibility(View.GONE);
                    ((MyViewHolder)holder).question.setText(bean.getQuestionDescribe());
                }else {
                    ((MyViewHolder)holder).layout2.setVisibility(View.VISIBLE);
                    ((MyViewHolder)holder).layout1.setVisibility(View.GONE);
                    ((MyViewHolder)holder).answer.setText(bean.getAnswer());
                }
            }else {
                if(bean.getOptionAnswer() == null || "".equals(bean.getOptionAnswer().trim())){
                    ((MyViewHolder)holder).layout1.setVisibility(View.VISIBLE);
                    ((MyViewHolder)holder).layout2.setVisibility(View.GONE);
                    ((MyViewHolder)holder).question.setText(bean.getQuestionDescribe());
                }else {
                    ((MyViewHolder)holder).layout2.setVisibility(View.VISIBLE);
                    ((MyViewHolder)holder).layout1.setVisibility(View.GONE);
                    ((MyViewHolder)holder).answer.setText(bean.getOptionAnswer());
                }
            }
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
        RecyclerView.ViewHolder  holder = (RecyclerView.ViewHolder) v.getTag();
        int position = holder.getAdapterPosition();
        if(holder instanceof  MyViewHolder){
            if(v == ((MyViewHolder)holder).layout1){
                mOnAddClickListener.onClick(position, (NewsProBean) list.get(position));
            }else if(v == ((MyViewHolder)holder).edit){
                mMoreClickListener.onClick(position, list.get(position));
            }
        }else {
            if(v == ((HeadViewHolder)holder).edit){
                mMoreClickListener.onClick(position, list.get(position));
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof ResultGetNewsInfo){
            return HEAD;
        }else {
            return ITEM;
        }
    }

    public static class HeadViewHolder extends RecyclerView.ViewHolder{
        private TextView edit;
        private TextView name;
        private TextView phone;
        private TextView birth;
        private TextView star;
        private TextView place;
        private TextView profession;
        private TextView qq;
        private TextView wx;
        public HeadViewHolder(View itemView) {
            super(itemView);
            edit = (TextView) itemView.findViewById(R.id.man_info_edit);
            name = (TextView) itemView.findViewById(R.id.man_info_name);
            phone = (TextView) itemView.findViewById(R.id.man_info_phone);
            birth = (TextView) itemView.findViewById(R.id.man_info_birth);
            star = (TextView) itemView.findViewById(R.id.man_info_star);
            place = (TextView) itemView.findViewById(R.id.man_info_place);
            profession = (TextView) itemView.findViewById(R.id.man_info_profession);
            qq = (TextView) itemView.findViewById(R.id.man_info_qq);
            wx = (TextView) itemView.findViewById(R.id.man_info_wx);
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView question;
        private LinearLayout layout1;
        private LinearLayout layout2;
        private TextView answer;
        private TextView edit;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            question = (TextView) itemView.findViewById(R.id.question);
            answer = (TextView) itemView.findViewById(R.id.answer);
            layout1 = (LinearLayout) itemView.findViewById(R.id.layout1);
            layout2 = (LinearLayout) itemView.findViewById(R.id.layout2);
            edit = (TextView) itemView.findViewById(R.id.edit);
        }
    }

    private OnAddClickListener mOnAddClickListener;
    private OnEditClickListener mMoreClickListener;
    public void setEditClickListener(OnEditClickListener editClickListener) {
        mMoreClickListener = editClickListener;
    }

    public void setOnAddClickListener(OnAddClickListener onAddClickListener) {
        mOnAddClickListener = onAddClickListener;
    }

    public interface OnEditClickListener{
        void onClick(int position, Object bean);
    }

    public interface OnAddClickListener{
        void onClick(int position, NewsProBean bean);
    }
}