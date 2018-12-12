package com.dikai.chenghunjiclient.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cmk03 on 2018/1/9.
 */

public class DiscoverDialog extends Dialog implements View.OnClickListener {

    private DisplayMetrics displayMetrics;
    private View view;


    public DiscoverDialog(Context context) {
        super(context, R.style.DiscoverDialog);

        mContext = context;
        displayMetrics = context.getResources().getDisplayMetrics();
    }

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //设置view 弹出的平移动画，从底部-100% 平移到自身位置
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(300);
        animation.setStartOffset(150);
        setCanceledOnTouchOutside(true);

        //把view冲气冲进来
        view = View.inflate(mContext, R.layout.dialog_discover_dialog, null);
        view.setAnimation(animation);//设置动画

        CircleImageView civLogo = (CircleImageView) view.findViewById(R.id.civ_logo);
        TextView tvStopAttention = (TextView) view.findViewById(R.id.tv_stop_attention);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        tvStopAttention.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_stop_attention:
                if (onResultChangeListener != null) {
                    onResultChangeListener.resultChanged("停止关注");
                }
                break;
            case R.id.tv_cancel://取消
            default:
                break;
        }
        dismiss();
    }

    private OnResultChangeListener onResultChangeListener;

    //对外的接口回调
    public interface OnResultChangeListener {
        void resultChanged(String result);
    }

    public void setOnResultChangeListener(OnResultChangeListener onResultChangeListener) {
        this.onResultChangeListener = onResultChangeListener;
    }

    @Override
    public void show() {
        super.show();
        //设置dialog的宽高是全屏,注意：一定要放在show的后面，否则不是全屏显示
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = displayMetrics.widthPixels;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT ;
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);
        getWindow().setContentView(view);
    }
}
