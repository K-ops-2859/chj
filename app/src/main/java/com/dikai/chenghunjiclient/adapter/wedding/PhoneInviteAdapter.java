package com.dikai.chenghunjiclient.adapter.wedding;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.InvitePhoneData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2017/12/5.
 */

public class PhoneInviteAdapter extends RecyclerView.Adapter<PhoneInviteAdapter.PhoneInviteVH> {

    private Context mContext;
    private final List<InvitePhoneData.InviteDataList> mData;

    public PhoneInviteAdapter(Context context) {
        this.mContext =context;
        mData = new ArrayList<>();
    }

    @Override
    public PhoneInviteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_phone_invite, parent, false);
        return new PhoneInviteVH(view);
    }

    @Override
    public void onBindViewHolder(PhoneInviteVH holder, int position) {
        holder.tvName.setText(mData.get(position).getBeinvitedName());
        holder.tvPhoneNumber.setText(mData.get(position).getBeinvitedPhone());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setList(List<InvitePhoneData.InviteDataList> list) {
        mData.clear();
        append(list);
    }

    public void append(List<InvitePhoneData.InviteDataList> list) {
        int startPosition = mData.size();
        int itemCount = list.size();
        mData.addAll(list);
        if (startPosition > 0 && itemCount >0) {
            notifyItemRangeInserted(startPosition, itemCount);
        } else {
            notifyDataSetChanged();
        }
    }

    public void remove(int position) {
        notifyItemRemoved(0);
    }

    static class PhoneInviteVH extends RecyclerView.ViewHolder {

        private final TextView tvName;
        private final TextView tvPhoneNumber;

        public PhoneInviteVH(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.tv_phone_number);
        }
    }
}
