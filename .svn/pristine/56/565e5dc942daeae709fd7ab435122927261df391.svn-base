package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.store.WebProActivity;
import com.dikai.chenghunjiclient.entity.GetColorProjectData;
import com.dikai.chenghunjiclient.entity.GetProjectBean;
import com.dikai.chenghunjiclient.view.MyImageView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by Lucio on 2017/7/17.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private List<GetColorProjectData.DataList> list;

    public List<GetColorProjectData.DataList> getList() {
        return list;
    }

    public ProjectAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_project_store_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.mLayout.setTag(holder);
        holder.mLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GetColorProjectData.DataList bean = list.get(position);
        holder.title.setText(bean.getPlanTitle());
        holder.grade.setText(bean.getColor());
        holder.keyWord.setText(bean.getPlanKeyWord());
        Glide.with(context).load(bean.getShowImg())
                .error(R.color.gray_background).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends GetColorProjectData.DataList> collection){
        int size = list.size();
        list.addAll(collection);
        notifyItemRangeInserted(size, collection.size());
    }

    public void refresh(Collection<? extends GetColorProjectData.DataList> collection){
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
       // mClickListener.onClick(list.get(position));
        context.startActivity(new Intent(context,WebProActivity.class)
                .putExtra("id",list.get(position).getPlanID()));
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private MyImageView img;
        private TextView title;
        private TextView grade;
        private TextView keyWord;
        private LinearLayout mLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = (MyImageView) itemView.findViewById(R.id.item_project_img);
            title = (TextView) itemView.findViewById(R.id.item_project_title);
            grade = (TextView) itemView.findViewById(R.id.item_project_grade);
            keyWord = (TextView) itemView.findViewById(R.id.item_project_key_word);
            mLayout = (LinearLayout) itemView.findViewById(R.id.item_project_layout);

        }
    }

    private OnClickListener mClickListener;

    public void setAssignListener(OnClickListener onClickListener) {
        mClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onClick(GetColorProjectData.DataList bean);
    }
}
