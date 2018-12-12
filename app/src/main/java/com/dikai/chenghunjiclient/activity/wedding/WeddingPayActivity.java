package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2017/12/18.
 */

public class WeddingPayActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox[] checkBoxes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding_pay);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        ImageView mBack = (ImageView) findViewById(R.id.activity_add_car_back);
        cb1 = (CheckBox) findViewById(R.id.cb_1);
        cb2 = (CheckBox) findViewById(R.id.cb_2);
        cb3 = (CheckBox) findViewById(R.id.cb_3);
        TextView tvNeedPay = (TextView) findViewById(R.id.tv_need_pay);
        TextView tvSubmit = (TextView) findViewById(R.id.tv_submit);


        checkBoxes = new CheckBox[3];
        checkBoxes[0] = cb1;
        checkBoxes[1] = cb2;
        checkBoxes[2] = cb3;
        cb1.setOnCheckedChangeListener(this);
        cb2.setOnCheckedChangeListener(this);
        cb3.setOnCheckedChangeListener(this);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeddingPayActivity.this, WeddingAssureActivity.class));
            }
        });
    }

    private void select(CheckBox checkBox) {
        for (int i=0;i<checkBoxes.length;i++) {
            if (checkBox == checkBoxes[i] && checkBox.isChecked()){
                for (int j=0;j<i;j++) {
                    checkBoxes[j].setChecked(false);
                }

                for (int k=i+1;k<checkBoxes.length;k++) {
                    checkBoxes[k].setChecked(false);
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_1:
                select(cb1);
                break;
            case R.id.cb_2:
                select(cb2);
                break;
            case R.id.cb_3:
                select(cb3);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}

