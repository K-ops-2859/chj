package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.CargoCodeBean;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2017/12/12.
 */

public class ConvertGiftActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convertgift);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final EditText etCode = (EditText) findViewById(R.id.et_code);
        TextView tvOk = (TextView) findViewById(R.id.tv_ok);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData(etCode.getText().toString().trim());
            }
        });
    }

    private void initData(final String code) {
        if (code.equals("")) {
            Toast.makeText(this, "请输入合适的兑换码", Toast.LENGTH_SHORT).show();
            return;
        }
        NetWorkUtil.setCallback("Corp/IsCargoCodeLapse",
                new CargoCodeBean(code), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        Log.e("返回结果",respose);
                        ResultMessage resultMessage = new Gson().fromJson(respose, ResultMessage.class);
                        if ("200".equals(resultMessage.getMessage().getCode())) {
                            Intent intent = new Intent(ConvertGiftActivity.this, ShippingAddressActivity.class);
                            intent.putExtra("type", 2);
                            intent.putExtra("code", code);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ConvertGiftActivity.this, resultMessage.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String e) {

                    }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
