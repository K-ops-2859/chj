package com.dikai.chenghunjiclient.activity.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnImageClickListener;

import java.util.ArrayList;
import java.util.List;

public class CaigoujieInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private String info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caigoujie_info);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        findViewById(R.id.back).setOnClickListener(this);
        info = getIntent().getStringExtra("info");
        textView = (TextView) findViewById(R.id.article);
        RichText.fromHtml(info)
                .clickable(true) //是否可点击，默认只有设置了点击监听才可点击
                .imageClick(new OnImageClickListener() {
                    @Override
                    public void imageClicked(List<String> imageUrls, int position) {
                        Intent intent = new Intent(CaigoujieInfoActivity.this, PhotoActivity.class);
                        intent.putExtra("now", position);
                        intent.putStringArrayListExtra("imgs", new ArrayList<>(imageUrls));
                        startActivity(intent);
                    }
                }) // 设置图片点击回调
                .into(textView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
    }
}
