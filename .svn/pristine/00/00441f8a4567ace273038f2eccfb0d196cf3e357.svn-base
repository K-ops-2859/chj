package com.dikai.chenghunjiclient.adapter.discover;

import android.content.Context;
import android.media.audiofx.AudioEffect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.entity.DynamicData;
import com.dikai.chenghunjiclient.entity.DynamicDetailsData;
import com.dikai.chenghunjiclient.entity.LikePersonData;
import com.dikai.chenghunjiclient.util.DensityUtil;
import com.dikai.chenghunjiclient.util.UserManager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cmk03 on 2018/1/26.
 */

public class LikePersonAdapter extends RecyclerView.Adapter<LikePersonAdapter.LikePersonVH> {

    private Context mContext;
    private final List<LikePersonData.DataList> mData;
    private OnItemClickListener<LikePersonData.DataList> onItemClickListener;
    private  int screenWidth;
    private final int margin;
    private final int logoWidth;


    public LikePersonAdapter(Context context) {
        this.mContext = context;
        mData = new ArrayList<>();
        screenWidth = DensityUtil.getScreenWidth(mContext);
        margin = DensityUtil.dip2px(mContext, 12);
        logoWidth = DensityUtil.dip2px(mContext, 40);
        screenWidth = DensityUtil.getScreenWidth(mContext);
    }

    @Override
    public LikePersonVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_like_person, parent, false);
        return new LikePersonVH(view, onItemClickListener, mData);
    }

    @Override
    public void onBindViewHolder(LikePersonVH holder, int position) {
        LikePersonData.DataList dataList = mData.get(position);
        Glide.with(mContext).load(dataList.getGivethumbHeadportrait())
                .error(R.color.gray_background).into(holder.civLogo);

        String identity = UserManager.getInstance(mContext).getIdentity(dataList.getOccupationCode());

        System.out.println("dkfjas===========” " + identity);
//        float aaaaa = getTextViewLength(holder.tvUserName, "的方式客服就爱上了代课老师放假啦深刻搭街坊拉萨扩大解放快递放假啊算了卡拉圣诞节发");
//        float bbbb = getTextViewLength(holder.tvType, "的发顺丰的");
        float nameLength = getTextViewLength(holder.tvUserName, dataList.getGivethumbName());
        float identityLength = getTextViewLength(holder.tvType, " · " + identity);

        if (nameLength + identityLength > screenWidth - holder.width - logoWidth - margin * 2) {
            holder.tvUserName.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        }
        holder.tvUserName.setText(dataList.getGivethumbName());
        holder.tvType.setText(" · " + identity);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setList(List<LikePersonData.DataList> list) {
        mData.clear();
        append(list);
    }

    public void append(List<LikePersonData.DataList> list) {
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

    public static float getTextViewLength(TextView textView, String text) {
        float textLength = 0;
        if (text != null) {
            TextPaint paint = textView.getPaint();
// 得到使用该paint写上text的时候,像素为多少
            textLength = paint.measureText(text);
        }
        return textLength;
    }


    public void setOnItemClickListener(OnItemClickListener<LikePersonData.DataList> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class LikePersonVH extends RecyclerView.ViewHolder {

        private final TextView tvType;
        private final TextView tvUserName;
        private final CircleImageView civLogo;
        private final int width;

        public LikePersonVH(View itemView, final OnItemClickListener<LikePersonData.DataList> listener, final List<LikePersonData.DataList> data) {
            super(itemView);
            civLogo = (CircleImageView) itemView.findViewById(R.id.civ_logo);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            tvType = (TextView) itemView.findViewById(R.id.tv_type);
            width = civLogo.getLayoutParams().width;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    listener.onItemClick(view, position, data.get(position));
                }
            });
        }
    }
}
