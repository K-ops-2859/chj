package com.dikai.chenghunjiclient.adapter.discover;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.entity.GetWeddingData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/1/10.
 */

public class MessageScreenAdapter extends RecyclerView.Adapter<MessageScreenAdapter.MessageScreenVH> {

    private Context mContext;
    private OnItemClickListener<GetWeddingData.DataList> onItemClickListener;
    private List<GetWeddingData.DataList> mData;
    private CallBack callBack;
    private int prev = 0;
    private List<Integer> clickPosition = new ArrayList<>();
    private final GetWeddingData.DataList list;

    public MessageScreenAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        list = new GetWeddingData.DataList();
    }

    @Override
    public MessageScreenVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_message_screen, parent, false);
        return new MessageScreenVH(view, onItemClickListener, mData);
    }

    @Override
    public void onBindViewHolder(final MessageScreenVH holder, final int position) {

        final GetWeddingData.DataList datas = mData.get(position);
        if (datas.getChecked()) {
            holder.tvScreen.setBackgroundResource(R.drawable.bg_btn_red_3);
            holder.tvScreen.setTextColor(Color.WHITE);
        } else {
            holder.tvScreen.setBackgroundResource(R.drawable.bg_btn_red_4);
            holder.tvScreen.setTextColor(Color.BLACK);
        }
        holder.setCallBack(new CallBack() {
            @Override
            public void onBack(int holderPosition) {
                if (!datas.getChecked()) {
                    datas.setChecked(true);
                    for (int i=0; i<mData.size();i++) {
                        if (mData.get(position) == datas) {
                            for (int j=0; j<position;j++) {
                                mData.get(j).setChecked(false);
                            }
                            for (int k=position + 1;k<mData.size();k++) {
                                mData.get(k).setChecked(false);
                            }
                        }
                    }
                    refershButton(position);
                } else {
                    datas.setChecked(false);
                    refershButton(position);
                }
            }
        });
        holder.tvScreen.setText(datas.getTitle());
        System.out.println("筛选=====" + datas.getTitle());
    }

    private void refershButton(int position) {
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener<GetWeddingData.DataList> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void setList(List<GetWeddingData.DataList> list) {
        mData.clear();
        append(list);
    }

    public void append(List<GetWeddingData.DataList> list) {
        int positionStart = mData.size();
        int itemCount = list.size();
        mData.addAll(list);
        if (positionStart > 0 && itemCount > 0) {
            notifyItemRangeInserted(positionStart, itemCount);
        } else {
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    static class MessageScreenVH extends RecyclerView.ViewHolder {

        private final TextView tvScreen;
        private int prev = -1;
        private CallBack callBack;

        public MessageScreenVH(View itemView, final OnItemClickListener<GetWeddingData.DataList> onItemClickListener, final List<GetWeddingData.DataList> data) {
            super(itemView);
            tvScreen = (TextView) itemView.findViewById(R.id.tv_screen);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onItemClick(view, position, data.get(position));
                    callBack.onBack(position);
                }
            });
        }

        public void setCallBack(CallBack callBack) {
            this.callBack = callBack;
        }
    }

    public interface CallBack {
        void onBack(int holderPosition);
    }
}
