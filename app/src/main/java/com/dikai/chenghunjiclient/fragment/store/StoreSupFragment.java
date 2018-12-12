package com.dikai.chenghunjiclient.fragment.store;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.adapter.store.StoreSupAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreSupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreSupFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String profession;
    private RecyclerView mRecyclerView;
    private StoreSupAdapter mAdapter;

    public StoreSupFragment() {
        // Required empty public constructor
    }

    public static StoreSupFragment newInstance(String profession) {
        StoreSupFragment fragment = new StoreSupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, profession);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            profession = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_store_sup, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.store_sup_recycler);
        GridLayoutManager layoutManage = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(layoutManage);
        mAdapter = new StoreSupAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
//        List<String> list = new ArrayList<>();
//        Collections.addAll(list,"","","","","","");
//        mAdapter.refresh(list);
    }
}
