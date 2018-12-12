package com.dikai.chenghunjiclient.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dikai.chenghunjiclient.R;

/**
 * Created by Lucio on 2016/11/15.
 */
public class MyLoadRecyclerView extends LinearLayout {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout fresh;
    private LinearLayout bottom;
    private TextView noData;

    private Context mContext;
    private RecyclerView.Adapter adapter;
    private OnLoadChangeListener mLoadChangeListener;
    private OnListChangeListener mListChangeListener;
    private OnListScrollListener mListScrollListener;

    private int lastVisibleItem;
    private long totalCount;
    private boolean canLoad = true;
    private boolean useListChange = false;
    private boolean useScroll = false;
    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private boolean isGrid = false;

    public MyLoadRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public MyLoadRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.my_load_recyclerview_layout, null);
        fresh = (SwipeRefreshLayout) view.findViewById(R.id.my_load_recycler_fresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_load_recycler);
        bottom = (LinearLayout) view. findViewById(R.id.my_load_recycler_bottom);
        noData = (TextView) view. findViewById(R.id.my_load_recycler_nodata);
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(260);

        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f);
        mHiddenAction.setDuration(260);

        //设置刷新时动画的颜色，可以设置4个
        fresh.setColorSchemeResources(R.color.main);
        fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fresh.post(new Runnable() {
                    @Override
                    public void run() {
                        fresh.setRefreshing(true);
                    }
                });
                mLoadChangeListener.onRefresh();
            }
        });

        mRecyclerView.setVerticalScrollBarEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        linearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (canLoad && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()
                        && lastVisibleItem != 0 && adapter.getItemCount() < totalCount) {
                    startLoad();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isGrid){
                    lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                }else {
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                }
                if(useListChange){
                    if (dy < -2) {
                        mListChangeListener.onChanged(false);
                    } else if (dy > 2) {
                        mListChangeListener.onChanged(true);
                    }
                }
                if(useScroll){
                    mListScrollListener.onScroll(dy);
                }
//                Log.e("scoll", "-----canLoad:" + canLoad + "-----lastVisibleItem:" + lastVisibleItem + "-----adapter.getItemCount:" + adapter.getItemCount()
//                        + "-----totalCount:" + totalCount);
            }
        });
        this.addView(view);
    }

    public void setAdapter(RecyclerView.Adapter adapter){
        this.adapter = adapter;
        mRecyclerView.setAdapter(adapter);
    }

    public void setLoadChangeListener(OnLoadChangeListener loadChangeListener) {
        mLoadChangeListener = loadChangeListener;
    }

    public void setListChangeListener(OnListChangeListener listChangeListener) {
        useListChange = true;
        mListChangeListener = listChangeListener;
    }

    public void setListScrollListener(OnListScrollListener listScrollListener) {
        useScroll = true;
        mListScrollListener = listScrollListener;
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public void startRefresh(){
        if(!fresh.isRefreshing()){
            fresh.post(new Runnable() {
                @Override
                public void run() {
                    fresh.setRefreshing(true);
                }
            });
        }
    }

    public void setFresh(boolean b) {
        fresh.setRefreshing(b);
    }

    private void startLoad(){
        canLoad = false;
        bottom.clearAnimation();
        bottom.setVisibility(View.VISIBLE);
        bottom.startAnimation(mShowAction);
        mLoadChangeListener.onLoadMore();
    }
    public void stopLoad(){
        canLoad = true;
        if(fresh.isRefreshing()){
            fresh.post(new Runnable() {
                @Override
                public void run() {
                    fresh.setRefreshing(false);
                }
            });
        }
        if(bottom.getVisibility() == View.VISIBLE){
            bottom.clearAnimation();
            bottom.setVisibility(View.GONE);
            bottom.startAnimation(mHiddenAction);
        }
    }

    public void setNoData(String mark){
        noData.setText(mark);
    }

    public void setHasData(boolean hasData){
        if(hasData){
            noData.setVisibility(GONE);
        }else {
            noData.setVisibility(VISIBLE);
        }
    }

    /**
     * LinearLayoutManager
     */
    public void setLinearLayout(LinearLayoutManager linearLayoutManager) {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        isGrid = false;
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * GridLayoutManager
     */

    public void setGridLayout(int spanCount) {
        isGrid = true;
        gridLayoutManager = new GridLayoutManager(mContext, spanCount);
//        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }

    public void setNestedScrolling(boolean b) {
        mRecyclerView.setNestedScrollingEnabled(b);
    }

    /**
     * StaggeredGridLayoutManager
     */

    public void setStaggeredGridLayout(int spanCount) {
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(spanCount, LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    public void setNestedScrollingEnabled(boolean enabled){
        mRecyclerView.setNestedScrollingEnabled(enabled);
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public SwipeRefreshLayout getFresh() {
        return fresh;
    }

    public interface OnLoadChangeListener{
        void onRefresh();
        void onLoadMore();
    }

    public interface OnListChangeListener{
        void onChanged(boolean isDown);
    }

    public interface OnListScrollListener{
        void onScroll(int y);
    }
}
