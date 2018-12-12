package com.dikai.chenghunjiclient.activity.wedding;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.wedding.WeddingKonwAdapter;
import com.dikai.chenghunjiclient.entity.WeddingKnowData;
import com.dikai.chenghunjiclient.entity.WeddingKnowDetails;
import com.dikai.chenghunjiclient.util.DownloadUtil;
import com.gyf.barlibrary.ImmersionBar;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by cmk03 on 2017/12/19.
 */

public class WeddingKnowActivity extends AppCompatActivity {

    private String[] docUrl1 = {"婚礼致辞稿","婚礼风俗风俗","婚礼必备"};
    private String[] docUrl11 = {"领导讲话.doc","新郎父亲致辞.doc","新郎致辞.doc","新娘父亲演讲稿.doc","证婚人致辞.doc","主婚人讲话稿.doc"};
    private String[] docUrl12 = {"青岛当地婚礼流程.doc","青岛风俗习惯.doc","新人当天礼仪指导.doc"};
    private String[] docUrl13 = {"婚礼操办总管职责.doc","新郎家或新娘准备的东西.doc"};

    private WeddingKonwAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding_know);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        ImageView mBack = (ImageView) findViewById(R.id.activity_add_car_back);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new WeddingKonwAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener<WeddingKnowData>() {
            @Override
            public void onItemClick(View view, int position, WeddingKnowData weddingKonwData) {
                Intent intent = new Intent(WeddingKnowActivity.this, WeddingKnowListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("key", weddingKonwData);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        initData();
    }

    private void initData() {
        List<WeddingKnowData> dataList = new ArrayList<>();
        dataList.add(new WeddingKnowData(R.mipmap.ic_wedding_lecture, "婚礼致辞稿", docUrl11));
        dataList.add(new WeddingKnowData(R.mipmap.ic_wedding_mores, "婚礼风俗", docUrl12));
        dataList.add(new WeddingKnowData(R.mipmap.ic_wedding_know, "婚礼必备",docUrl13));
        mAdapter.setList(dataList);
    }

//    private void download(){
//        try {
//            String file = "婚礼致辞稿/新郎致辞.doc";
//            String url = "http://www.chenghunji.com/download/" + URLEncoder.encode(file, "utf-8");
//            String fileFolder = Environment.getExternalStorageDirectory() + "/ChengHunJi";
//            DownloadUtil.setCallback(url, fileFolder, new DownloadUtil.CallBackListener() {
//                @Override
//                public void onFinish(String respose) {
//                    Log.e("mingcheng:",respose);
////                    getWordFileIntent(respose);
//                }
//
//                @Override
//                public void onError(String e) {
//
//                }
//            });
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }

    //android获取一个用于打开Word文件的intent
    public void getWordFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri,"application/msword");
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
