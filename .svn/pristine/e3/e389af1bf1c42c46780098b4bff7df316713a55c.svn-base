package com.dikai.chenghunjiclient.activity.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanAddComboComment;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import dmax.dialog.SpotsDialog;

public class ComboCommentActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;
    private SpotsDialog mDialog;
    private String comboID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_comment);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        comboID = getIntent().getStringExtra("id");
        mDialog = new SpotsDialog(this,R.style.DialogCustom);
        mEditText = (EditText) findViewById(R.id.edit);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.publish).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.publish:
                if (mEditText.getText() == null || "".equals(mEditText.getText().toString().trim())){
                    Toast.makeText(this, "评价内容不能为空哦", Toast.LENGTH_SHORT).show();
                }else {
                    addCommet(mEditText.getText().toString().trim(),comboID);
                }
                break;
        }
    }

    private void addCommet(String content,String commentID) {
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/CreateWeddingPackageEvaluate",
                new BeanAddComboComment(UserManager.getInstance(this).getNewUserInfo().getUserId(),content,"",commentID),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            mDialog.dismiss();
                            Log.e("返回值", respose);
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if (result.getMessage().getCode().equals("200")) {
                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_COMBO_COMMENT));
                                finish();
                            }else {
                                Toast.makeText(ComboCommentActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("", e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                    }
                });
    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }
}
