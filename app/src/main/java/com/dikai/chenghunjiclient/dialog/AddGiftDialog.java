package com.dikai.chenghunjiclient.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;

/**
 * Created by cmk03 on 2018/10/26.
 */

public class AddGiftDialog extends Dialog {
    private Context context;
    private TextView tvMessage;
    private TextView tvOk;
    private String message;

    public AddGiftDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public AddGiftDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_gift, null);
        setContentView(view);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.84); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(lp);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setMessage(String msg) {
       this.message = msg;
       mHandler.sendEmptyMessage(0);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            int what = msg.what;
            if (what == 0) {    //update
                tvMessage.setText(message);
            } else {
                dismiss();
            }
        }
    };
}
