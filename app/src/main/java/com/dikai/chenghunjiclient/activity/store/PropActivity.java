package com.dikai.chenghunjiclient.activity.store;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnImageClickListener;

import java.util.ArrayList;
import java.util.List;

public class PropActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prop);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        init();
    }

    private void init() {
        try {
            findViewById(R.id.back).setOnClickListener(this);
            TextView textView = (TextView) findViewById(R.id.article);
            RichText.fromHtml(getIntent().getStringExtra("info"))
                    .clickable(true) //是否可点击，默认只有设置了点击监听才可点击
                    .imageClick(new OnImageClickListener() {
                        @Override
                        public void imageClicked(List<String> imageUrls, int position) {
                            Intent intent = new Intent(PropActivity.this, PhotoActivity.class);
                            intent.putExtra("now", position);
                            intent.putStringArrayListExtra("imgs", new ArrayList<>(imageUrls));
                            startActivity(intent);
                        }
                    }) // 设置图片点击回调
                    .into(textView);
        }catch (Exception e){
            Log.e("",e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
