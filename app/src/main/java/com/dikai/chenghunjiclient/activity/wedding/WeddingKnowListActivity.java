package com.dikai.chenghunjiclient.activity.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.wedding.WeddingKonwListAdapter;
import com.dikai.chenghunjiclient.entity.WeddingKnowData;
import com.gyf.barlibrary.ImmersionBar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cmk03 on 2017/12/21.
 */

public class WeddingKnowListActivity extends AppCompatActivity {

    private final String base = "https://view.officeapps.live.com/op/view.aspx?src=";
    private TextView tvTitle;
    private WeddingKonwListAdapter mAdapter;
    private WeddingKnowData data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding_konw_list);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();

        ImageView mBack = (ImageView) findViewById(R.id.activity_add_car_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new WeddingKonwListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onItemClick(View view, int position, String doc) {
                try {
                    String title = data.getTitle();
                    if("婚礼风俗".equals(title)){
                        title = "婚礼风俗风俗";
                    }
                    String file = title + "/" + doc;
                    String url = "http://www.chenghunji.com/download/" + URLEncoder.encode(file, "utf-8");
                    Intent intent = new Intent(WeddingKnowListActivity.this, WedDocActivity.class);
                    intent.putExtra("url",base + url);
                    intent.putExtra("title",doc);
                    startActivity(intent);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initData();
    }

    private void initData() {
        data = (WeddingKnowData) getIntent().getSerializableExtra("key");
        tvTitle.setText(data.getTitle());
        List<String> list = Arrays.asList(data.getDocs());
        mAdapter.setList(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
