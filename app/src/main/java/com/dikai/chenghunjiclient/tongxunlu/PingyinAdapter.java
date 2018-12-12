package com.dikai.chenghunjiclient.tongxunlu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class PingyinAdapter extends BaseExpandableListAdapter {

    private int type;
    private List<String> empIDs;
    private List<CarBean> empNames;
    private Context context;
    private ExpandableListView listView;
    private PinyinKeyMapList<CarBean> keyMapList;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;
    public PingyinAdapter(ExpandableListView listView, List<CarBean> list) {
        this.context = listView.getContext();
        this.listView = listView;
        mListener = new OnItemClickListener();
        inflater = LayoutInflater.from(context);
        keyMapList = new PinyinKeyMapList<CarBean>(list){
            @Override
            public String getField(CarBean t) {
                return getItemName(t);
            }
        };
    }

    public void refresh(List<CarBean> list){

        keyMapList = new PinyinKeyMapList<CarBean>(list){
            @Override
            public String getField(CarBean t) {
                return getItemName(t);
            }
        };
        notifyDataSetChanged();
    }

    public void expandGroup(){
        listView.setAdapter(this);
        for (int i = 0, length = this.getGroupCount(); i < length; i++) {
            listView.expandGroup(i);
        }
    }


    public String getItemName(CarBean t) {
        if(t.getName() != null || !"".equals(t.getName())){
            return t.getName();
        }else {
            return t.getCarModelID();
        }
    }

    public Object getChild(int group, int child) {
        // TODO Auto-generated method stub
        return keyMapList.getIndexList(group).get(child);
    }

    public long getChildId(int group, int child) {
        return child;
    }


    public int getChildrenCount(int group) {
        // TODO Auto-generated method stub
        return keyMapList.getIndexList(group).size();
    }

    public Object getGroup(int group) {
        return keyMapList.getIndexList(group);
    }

    public int getGroupCount() {
        return keyMapList.getTypes().size();
    }

    public long getGroupId(int group) {
        // TODO Auto-generated method stub
        return group;
    }

    public View getGroupView(int group, boolean arg1, View contentView,
                             ViewGroup arg3) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.group_item, null);
            contentView.setClickable(true);
        }
        TextView textView = (TextView) contentView.findViewById(R.id.tv_index);
        textView.setText(keyMapList.getTypes().get(group));
        return contentView;
    }

    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return true;
    }

    public View getChildView(int group, int child, boolean arg2,
                             View convertView, ViewGroup arg4) {
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_man,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.layout= (LinearLayout) convertView.findViewById(R.id.item_my_friend_layout);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CarBean bean = keyMapList.getIndexList(group).get(child);
        viewHolder.tv_name.setText(bean.getName());
        viewHolder.layout.setTag(R.id.contacts_value_1,viewHolder);
        viewHolder.layout.setTag(R.id.contacts_value_2,group);
        viewHolder.layout.setTag(R.id.contacts_value_3,child);
        viewHolder.layout.setOnClickListener(mListener);
        return convertView;
    }

    public String getEmpIDs(){
        String ids = "";
        for (int i = 0; i < empIDs.size(); i++) {
            if(i < empIDs.size() - 1){
                ids += empIDs.get(i) + ",";
            }else {
                ids += empIDs.get(i);
            }
        }
        return ids;
    }

    public List<CarBean> getEmpNames() {
        return empNames;
    }

    public Context getContext() {
        return context;
    }

    public PingyinAdapter setContext(Context context) {
        this.context = context;
        return this;
    }

    public PinyinKeyMapList<CarBean> getKeyMapList() {
        return keyMapList;
    }

    public PingyinAdapter setKeyMapList(PinyinKeyMapList<CarBean> keyMapList) {
        this.keyMapList = keyMapList;
        return this;
    }

    public class OnItemClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.contacts_value_1);
            CarBean bean = keyMapList.getIndexList((int)v.getTag(R.id.contacts_value_2)).
                    get((int)v.getTag(R.id.contacts_value_3));
            if(v == viewHolder.layout){
                mOnCarClickListener.onClick(bean);
            }
        }
    }

    class ViewHolder{
        TextView tv_name;
        LinearLayout layout;
    }

    public void setType(int type) {
        this.type = type;
    }

    private OnCarClickListener mOnCarClickListener;

    public void setOnCarClickListener(OnCarClickListener onCarClickListener) {
        mOnCarClickListener = onCarClickListener;
    }

    public interface OnCarClickListener{
        void onClick(CarBean bean);
    }
}
