package com.dikai.chenghunjiclient.activity.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.dikai.chenghunjiclient.bean.BeanGetNewsInfo;
import com.dikai.chenghunjiclient.entity.ResultGetZixunInfo;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.OldNetWorkUtil;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnImageClickListener;

import java.util.ArrayList;
import java.util.List;

public class NewArticleActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private TextView title;
    private String newsID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_article);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        newsID = getIntent().getStringExtra("news");
        textView = (TextView) findViewById(R.id.article);
        title = (TextView) findViewById(R.id.title);
        getInfo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }


    /**
     * 获取案例
     */
    private void getInfo(){
       NetWorkUtil.setCallback("HQOAApi/GetInformationArticleInfo",
                new BeanGetNewsInfo(newsID,0),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        Log.e("返回值",respose);
                        try {
                            ResultGetZixunInfo result = new Gson().fromJson(respose, ResultGetZixunInfo.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                title.setText(result.getTitle());
                                RichText.fromHtml(result.getContent())
                                        .clickable(true) //是否可点击，默认只有设置了点击监听才可点击
                                        .imageClick(new OnImageClickListener() {
                                            @Override
                                            public void imageClicked(List<String> imageUrls, int position) {
                                                Intent intent = new Intent(NewArticleActivity.this, PhotoActivity.class);
                                                intent.putExtra("now", position);
                                                intent.putStringArrayListExtra("imgs", new ArrayList<>(imageUrls));
                                                startActivity(intent);
                                            }
                                        }) // 设置图片点击回调
                                        .into(textView);
                            } else {
//                                mDialog.dismiss();
                                Toast.makeText(NewArticleActivity.this, result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
//                            mDialog.dismiss();
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
//                        mDialog.dismiss();
                        Toast.makeText(NewArticleActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }
}
