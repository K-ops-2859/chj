package com.dikai.chenghunjiclient.activity.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding.widget.RxTextView;

import rx.functions.Action1;

/**
 * Created by cmk03 on 2018/2/28.
 */

public class EditZhiActivity extends AppCompatActivity{

    private TextView tvSave;
    private EditText etNumber;
    private final int RESULT_OK = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_zhi);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        Toolbar mToolBar = (Toolbar) findViewById(R.id.toolbar);
        etNumber = (EditText) findViewById(R.id.et_number);
        tvSave = (TextView) findViewById(R.id.tv_save);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RxTextView.textChanges(etNumber).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                if (charSequence.length()>0) {
                    tvSave.setBackgroundResource(R.drawable.bg_btn_pink_deep);
                    tvSave.setTextColor(Color.WHITE);
                } else {
                    tvSave.setBackgroundResource(R.drawable.bg_btn_gray_deep);
                    tvSave.setTextColor(ContextCompat.getColor(EditZhiActivity.this, R.color.gray_text));
                }
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("result", etNumber.getText().toString());
                EditZhiActivity.this.setResult(RESULT_OK, intent);
                EditZhiActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
