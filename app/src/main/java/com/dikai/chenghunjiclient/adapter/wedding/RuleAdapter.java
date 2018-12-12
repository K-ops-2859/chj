package com.dikai.chenghunjiclient.adapter.wedding;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.entity.ActivityRuleBean;
import com.dikai.chenghunjiclient.util.DensityUtil;
import com.dikai.chenghunjiclient.view.ViewWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cmk03 on 2017/12/7.
 */

public class RuleAdapter extends RecyclerView.Adapter<RuleAdapter.ParentVH> {

    public static final int PARENT_ITEM = 0;
    public static final int CHILD_ITEM = 1;
    private Context mContext;
    private List<ActivityRuleBean.GradeDataList> mData;
    private List<String> childData;
    private LayoutInflater layoutInflater;
    private OnItemClickListener<ActivityRuleBean.GradeDataList> onItemClickListener;
    private int clickPosition = -1;
    private boolean isExpand = false;
    private int layoutHeight;
    private int expandHeight;
    private View target;

    public RuleAdapter() {
    }

    public RuleAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        childData = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
//        mData = new ArrayList<>();
    }

//    @Override
//    public int getItemViewType(int position) {
//        int parentSize = mData.size();
//        if (position <= parentSize || position > clickPosition + childData.size()) {
//            return PARENT_ITEM;
//        } else if (isExpand && childData.size() == position){
//            return CHILD_ITEM;
//        }
//        return 0;
//    }


//    @Override
//    public int getItemViewType(int position) {
//        if (mData.get(position).getTitle() != null) {
//            return PARENT_ITEM;
//        } else {
//
//        }
//        return super.getItemViewType(position);
//    }

    @Override
    public ParentVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_rule_parent, parent, false);
        return new ParentVH(view, onItemClickListener, mContext);
    }

    @Override
    public void onBindViewHolder(final ParentVH holder, final int position) {
        ActivityRuleBean.GradeDataList gradeDataList = mData.get(position);
        holder.tvTitle.setText(gradeDataList.getTitle() + "");
        holder.llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isExpand ) {
                    addChildData(position);
                    expandHeight = DensityUtil.dip2px(mContext, 40) * childData.size();
                    layoutStartAnimation(holder.flChild);
                    holder.ruleChildAdapter.setList(childData);
                    imageStartAnimation(holder.ivArrow);
                    isExpand = !isExpand;
                } else {
                    layoutEndAnimation(holder.flChild);
                    imageEndAnimation(holder.ivArrow);
                    removeChildData();
                    isExpand = !isExpand;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
//        if (!isExpand) {
//            System.out.println("父View 大小==" + mData.size());
//            return mData.size();
//        } else {
//            System.out.println("子VIew大小==" + childData.size());
//            return childData.size();
//        }
        return mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener<ActivityRuleBean.GradeDataList> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setList(List<ActivityRuleBean.GradeDataList> list) {
        mData.clear();
        append(list);
    }

    public void append(List<ActivityRuleBean.GradeDataList> list) {
        int positionStart = mData.size();
        int itemCount = list.size();
        mData.addAll(list);
        if (positionStart > 0 && itemCount > 0) {
            notifyItemRangeInserted(positionStart, itemCount);
        } else {
            notifyDataSetChanged();
        }
    }

    private void addChildData(int position) {
        childData.clear();
        System.out.println("position=============“ " + position);
        List<String> list = Arrays.asList(mData.get(position).getRule());
        childData.addAll(list);
    }

    private void removeChildData() {
        childData.clear();
    }

    public void addChild(int position) {
        List<String> list = Arrays.asList(mData.get(position).getRule());
        notifyItemRangeRemoved(position, mData.size());
        childData.addAll(list);
        notifyItemRangeInserted(position, childData.size());
    }

    private void removeChild(int position) {
        notifyItemRangeRemoved(position, childData.size());
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    private void layoutStartAnimation(View target) {
        layoutHeight = target.getLayoutParams().height;
        System.out.println("layoutHeight====“" + layoutHeight);
        System.out.println("expandHeight====" + expandHeight);
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(new ViewWrapper(target), "height", expandHeight);
        objectAnimator.setDuration(400);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();

    }

    private void layoutEndAnimation(View target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(new ViewWrapper(target), "height", 0);
        objectAnimator.setDuration(400);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    private void imageStartAnimation(View target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "rotation", 0, 180);
        objectAnimator.setDuration(400).start();
    }

    private void imageEndAnimation(View target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "rotation", 180, 0);
        objectAnimator.setDuration(400).start();
    }

    static class ParentVH extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final ImageView ivArrow;
        private final LinearLayout llContainer;
        private final RecyclerView mRecyclerView;
        private final RuleChildAdapter ruleChildAdapter;
        private final FrameLayout flChild;

        ParentVH(final View itemView, final OnItemClickListener<ActivityRuleBean.GradeDataList> onItemClickListener, Context context) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            ivArrow = (ImageView) itemView.findViewById(R.id.iv_arrow);
            llContainer = (LinearLayout) itemView.findViewById(R.id.container);
            flChild = (FrameLayout) itemView.findViewById(R.id.fl_child);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            ruleChildAdapter = new RuleChildAdapter(context);
            mRecyclerView.setAdapter(ruleChildAdapter);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    System.out.println("1111111111111111111111111");
//                    int position = getAdapterPosition();
//
//                }
//            });

        }
    }

    private static class ChildVH extends RecyclerView.ViewHolder {

        private final TextView tvContent;

        ChildVH(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_rule_content);
        }
    }
}
