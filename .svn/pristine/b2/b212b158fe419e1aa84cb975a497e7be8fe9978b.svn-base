package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.WeddingKnowDetails;
import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by cmk03 on 2017/12/21.
 */

public class WeddingKnowDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding_konw_details);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        TextView tvContentTitle = (TextView) findViewById(R.id.tv_content_title);
        TextView tvContent = (TextView) findViewById(R.id.tv_content);
        Intent intent = getIntent();
        WeddingKnowDetails data = (WeddingKnowDetails) intent.getSerializableExtra("key");

        tvTitle.setText(data.getTitle());
        tvContentTitle.setText(data.getContentTitle());
        tvContent.setText(data.getContent());

    }

    private void initData() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
