package com.dikai.chenghunjiclient.fragment.discover;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.discover.DynamicActivity;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.discover.AttentionAdapter;
import com.dikai.chenghunjiclient.fragment.BaseLazyFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cmk03 on 2018/1/4.
 */

public class AttentionFragment extends BaseLazyFragment {

    private AttentionAdapter mAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_attention;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        final Context mContext = getContext();
        XRecyclerView mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new AttentionAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);

        List<String> list = new ArrayList<>();
        for (int i=0;i<10;i++) {
            list.add(i + "");
        }
        mAdapter.setList(list);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object o) {
                startActivity(new Intent(mContext, DynamicActivity.class));
            }
        });
    }

    @Override
    protected void DetoryViewAndEvents() {

    }

    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }
}
