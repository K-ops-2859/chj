package com.dikai.chenghunjiclient.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/1/9.
 */

public class DynamicReplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private final List<String> mData;
    private int CONTENT_VIEW = 0;
    private int FOOTER_VIEW = 1;


    public DynamicReplyAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        mData.add("11111111111");
        mData.add("2222222222222");
        mData.add("333333333333");
        mData.add("44444444444444444");
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 3) {
            return FOOTER_VIEW;
        } else {
            return CONTENT_VIEW;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == CONTENT_VIEW) {
            View view = inflater.inflate(R.layout.adapter_dynamic_reply, parent, false);
            return new DynamicReplyVH(view);
        } else {
            View view = inflater.inflate(R.layout.adapter_dynamic_reply_footer, parent, false);
            return new DyanmicReplyFooter(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size() > 3 ? 4 : mData.size();
    }

    static class DynamicReplyVH extends RecyclerView.ViewHolder {

        private final TextView tvNameContent;

        public DynamicReplyVH(View itemView) {
            super(itemView);
            tvNameContent = (TextView) itemView.findViewById(R.id.tv_name_content);
        }
    }

    static class DyanmicReplyFooter extends RecyclerView.ViewHolder {

        private final TextView tvLook;

        public DyanmicReplyFooter(View itemView) {
            super(itemView);
            tvLook = (TextView) itemView.findViewById(R.id.tv_look);
        }
    }
}
