package com.dikai.chenghunjiclient.fragment.plan;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.plan.FlowAdapter;
import com.dikai.chenghunjiclient.entity.ResultGetFlow;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlowFragment extends Fragment {

    private MyLoadRecyclerView mRecyclerView;
    private FlowAdapter mAdapter;
    private ResultGetFlow result;

    public FlowFragment() {
        // Required empty public constructor
    }

    public static FlowFragment newInstance(ResultGetFlow result) {
        Bundle args = new Bundle();
        args.putSerializable("bean",result);
        FlowFragment fragment = new FlowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flow, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        result = (ResultGetFlow) getArguments().getSerializable("bean");
        mRecyclerView = (MyLoadRecyclerView)view.findViewById(R.id.new_add_recycler);
        mAdapter = new FlowAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    private void refresh() {
        mAdapter.refresh(result.getWeddingProcessData());
        mRecyclerView.stopLoad();
    }

}
