package com.dikai.chenghunjiclient.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;


/**
 * Created by cmk03 on 2018/3/22.
 */

public class PhotoDialog extends Dialog {

    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private EditText etDesc;
    private Context context;

    public PhotoDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public PhotoDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.photoDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用

        lp.width = (int) (d.widthPixels * 0.95); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);

        ImageView ivCancle = (ImageView) findViewById(R.id.iv_cancle);
        etDesc = (EditText) findViewById(R.id.et_desc);
        TextView tvOK = (TextView) findViewById(R.id.tv_ok);

        ivCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noOnclickListener.onNoClick();
            }
        });

        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(etDesc.getText().toString())) {
                    yesOnclickListener.onYesClick(etDesc.getText().toString());
                }
            }
        });
    }

    public void setNoOnclickListener(onNoOnclickListener onNoOnclickListener) {
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     */
    public void setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick(String desc);
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }
}
