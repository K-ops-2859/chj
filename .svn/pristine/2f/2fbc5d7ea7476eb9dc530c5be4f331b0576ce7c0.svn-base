package com.dikai.chenghunjiclient.activity.me;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanAddDiscount;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import dmax.dialog.SpotsDialog;

public class AddDiscountsActivity extends AppCompatActivity implements View.OnClickListener {

    private SpotsDialog mDialog;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discounts);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);
        content = (EditText) findViewById(R.id.content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.save:
                if(content.getText() == null || "".equals(content.getText().toString().trim())){
                    Toast.makeText(this, "请添加优惠内容", Toast.LENGTH_SHORT).show();
                }else {
                    addDiscounts(content.getText().toString());
                }
                break;
        }
    }


    /**
     * 添加优惠
     */
    private void addDiscounts(String content){
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/CreateFacilitatorBasicPreferences",
                new BeanAddDiscount(UserManager.getInstance(this).getNewUserInfo().getFacilitatorId(),content),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                Toast.makeText(AddDiscountsActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_DISCOUNT));
                                finish();
                            } else {
                                Toast.makeText(AddDiscountsActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Toast.makeText(AddDiscountsActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
