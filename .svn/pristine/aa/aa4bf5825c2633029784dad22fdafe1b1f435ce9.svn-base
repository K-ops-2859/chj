package com.dikai.chenghunjiclient.adapter.wedding;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.ActivityRuleBean;

import java.util.List;

/**
 * Created by cmk03 on 2017/12/15.
 */

public class ExpandRuleAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<ActivityRuleBean.GradeDataList> mData;

    public ExpandRuleAdapter(Context context, List<ActivityRuleBean.GradeDataList> dataLists) {
        this.mContext = context;
        this.mData = dataLists;
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mData.get(i).getRule().length;
    }

    @Override
    public Object getGroup(int i) {
        return mData.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        View view ;
        GroupHolder holder;
        if (convertView != null) {
            view = convertView;
            holder = (GroupHolder) view.getTag();
        } else {
            holder = new GroupHolder();
            view = View.inflate(mContext, R.layout.adapter_rule_parent, null);
            holder.llContainer = (LinearLayout) view.findViewById(R.id.container);
            holder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            holder.ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
            view.setTag(holder);
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
        View view;
        ChildHolder holder;
        if (convertView != null) {
            view = convertView;
            holder = (ChildHolder) view.getTag();
        } else {
            view = View.inflate(mContext, R.layout.adapter_rule_child, null);
            holder = new ChildHolder();
            holder.tvContent = (TextView) view.findViewById(R.id.tv_rule_content);

            view.setTag(holder);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    private static class GroupHolder {
        LinearLayout llContainer;
        TextView tvTitle;
        ImageView ivArrow;
    }

    private static class ChildHolder {
        TextView tvContent;
    }
}
