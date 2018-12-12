package com.dikai.chenghunjiclient.activity.plan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanAddMark;
import com.dikai.chenghunjiclient.entity.DayPlanBean;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import dmax.dialog.SpotsDialog;

public class AddMarkActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mFinish;
    private EditText content;
    private int type;
    private DayPlanBean mPlanBean;
    private SpotsDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mark);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        type = getIntent().getIntExtra("type",0);
        mPlanBean = (DayPlanBean) getIntent().getSerializableExtra("bean");
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        mBack = (ImageView) findViewById(R.id.activity_add_mark_back);
        mFinish = (TextView) findViewById(R.id.activity_add_mark_finish);
        content = (EditText) findViewById(R.id.activity_add_mark_content);
        mBack.setOnClickListener(this);
        mFinish.setOnClickListener(this);
        if(type == 1){
            content.setText(mPlanBean.getLogContent());
        }
//        try {
//        }catch (Exception e){
//            Log.e("AddMarkActivity  ",e.toString());
//        }
    }

    @Override
    public void onClick(View v) {
        if(content.getText() == null || "".equals(content.getText().toString().trim())){
            Toast.makeText(this, "备注内容不能为空！", Toast.LENGTH_SHORT).show();
        }else {
            add();
        }
    }

    /**
     * 添加
     */
    private void add() {
        mDialog.show();
        UserInfo userInfo = UserManager.getInstance(this).getUserInfo();
        NetWorkUtil.setCallback("User/AddScheduleRemark",
                new BeanAddMark(userInfo.getSupplierID(), mPlanBean.getScheduleID(), content.getText().toString().trim()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        mDialog.dismiss();
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(type == 0) {
                                    Toast.makeText(AddMarkActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(AddMarkActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                                }
                                EventBus.getDefault().post(new EventBusBean(Constants.ADD_REMARK_SUCCESS, content.getText().toString().trim()));
                                finish();
                            } else {
                                Toast.makeText(AddMarkActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        mDialog.dismiss();
                        Log.e("网络出错",e.toString());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
