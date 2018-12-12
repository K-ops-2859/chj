package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.bean.BeanEditNewsQuestion;
import com.dikai.chenghunjiclient.entity.NewsProBean;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class ProQuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private NewsProBean mBean;
    private TextView title;
    private LinearLayout selectLayout;
    private TextView selectText;
    private EditText editAnswer;
    private String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_question);
        EventBus.getDefault().register(this);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        mBean = (NewsProBean) getIntent().getSerializableExtra("bean");
        title = (TextView) findViewById(R.id.title);
        selectLayout = (LinearLayout) findViewById(R.id.ll_select);
        selectText = (TextView) findViewById(R.id.tv_select);
        editAnswer = (EditText) findViewById(R.id.edit_answer);
        selectLayout.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);
        title.setText(mBean.getQuestionClassification());
        editAnswer.setHint(mBean.getQuestionDescribe());
        if(mBean.getTypeQuestion() == 0){
            selectLayout.setVisibility(View.GONE);
            if(mBean.getAnswer() != null && !"".equals(mBean.getAnswer())){
                editAnswer.setText(mBean.getAnswer());
            }
        }else {
            selectLayout.setVisibility(View.VISIBLE);
            selectText.setText("请选择"+mBean.getQuestionClassification());
            if(mBean.getOptionAnswer() != null && !"".equals(mBean.getOptionAnswer())){
                selectText.setText(mBean.getOptionAnswer());
            }
            if(mBean.getAnswer() != null && !"".equals(mBean.getAnswer())){
                editAnswer.setText(mBean.getAnswer());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.save:
                String answer = "";
                if(mBean.getTypeQuestion() == 1){
                    if(selected == null ||"".equals(selected)){
                        Toast.makeText(this, "请选择至少一项！", Toast.LENGTH_SHORT).show();
                    }else {
                        if(editAnswer.getText()!=null && !"".equals(editAnswer.getText().toString().trim())){
                            answer = editAnswer.getText().toString().trim();
                        }
                        edit(selected,answer);
                    }
                }else {
                    if(editAnswer.getText()!=null && !"".equals(editAnswer.getText().toString().trim())){
                        answer = editAnswer.getText().toString().trim();
                    }
                    edit("",answer);
                }
                break;
            case R.id.ll_select:
                startActivity(new Intent(this,QuestionSelectActivity.class)
                        .putExtra("title",mBean.getQuestionClassification())
                        .putExtra("list",mBean.getTypeContent()));
                break;
        }
    }

    private void edit(String selected,String answer) {
        NetWorkUtil.setCallback("HQOAApi/UpNewPeopleQuestion",
                new BeanEditNewsQuestion(mBean.getQuestionID(),selected,answer),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultMessage result = new Gson().fromJson(respose, ResultMessage.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                EventBus.getDefault().post(new EventBusBean(Constants.NEWS_INFO_CHANGED));
                                finish();
                            } else {
                                Toast.makeText(ProQuestionActivity.this, "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        Log.e("网络出错",e.toString());
                    }
                });
    }


    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.QUESTION_SLELECED){
                    selected = bean.getData();
                    selectText.setText(selected);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
