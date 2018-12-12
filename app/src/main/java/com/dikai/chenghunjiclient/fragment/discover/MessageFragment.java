package com.dikai.chenghunjiclient.fragment.discover;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.discover.DynamicActivity;
import com.dikai.chenghunjiclient.activity.discover.MessageActivity;
import com.dikai.chenghunjiclient.activity.store.NewArticleActivity;
import com.dikai.chenghunjiclient.activity.store.NewsActivity;
import com.dikai.chenghunjiclient.adapter.OnItemClickListener;
import com.dikai.chenghunjiclient.adapter.discover.MessageAdapter;
import com.dikai.chenghunjiclient.adapter.discover.MessageScreenAdapter;
import com.dikai.chenghunjiclient.bean.BeanPager;
import com.dikai.chenghunjiclient.bean.MessageBean;
import com.dikai.chenghunjiclient.entity.GetWeddingData;
import com.dikai.chenghunjiclient.entity.MessageData;
import com.dikai.chenghunjiclient.fragment.BaseLazyFragment;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.view.MyLoadRecyclerView;
import com.dikai.chenghunjiclient.view.ViewWrapper;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by cmk03 on 2018/1/9.
 */

public class MessageFragment extends BaseLazyFragment {

    private int isExpand = 0;
    private PopupWindow popupWindow;
    private Context mContext;
    private FrameLayout flBg;
    private MessageAdapter mAdapter;
    private int pageIndex = 1;
    private int pageCount = 20;
    private List<GetWeddingData.DataList> weddingDataList;
    private MessageScreenAdapter filterAdapter;
    private long weddingInformationID = 0;
    private TextView tvCodition;
    private ImageView ivArrow;
    private String title = "";
    private EditText etSearch;
    private MyLoadRecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        mContext = getContext();
        final FrameLayout flScreen = (FrameLayout) view.findViewById(R.id.fl_screen);
        tvCodition = (TextView) view.findViewById(R.id.tv_condition);
        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        etSearch = (EditText) view.findViewById(R.id.et_search);
        flBg = (FrameLayout) view.findViewById(R.id.fl_bg);
        mRecyclerView = (MyLoadRecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new MessageAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);

        initPopup();
        flScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!popupWindow.isShowing()) {
                    popupWindow.showAsDropDown(flScreen);
                    flBg.setVisibility(View.VISIBLE);
                    imageStartAnimation(ivArrow);
                } else {
                    popupWindow.dismiss();
                    imageEndAnimation(ivArrow);
                    // flBg.setVisibility(View.GONE);
                }
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etSearch.setCursorVisible(true);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               // Toast.makeText(mContext, editable.toString(), Toast.LENGTH_SHORT).show();
                String title = editable.toString();
                initList(weddingInformationID, title);
            }
        });

        mRecyclerView.setLoadChangeListener(new MyLoadRecyclerView.OnLoadChangeListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                pageCount = 20;
                initList(weddingInformationID, "");
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                pageCount = 20;
                loadMore();
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener<MessageData.DataList>() {
            @Override
            public void onItemClick(View view, int position, MessageData.DataList dataList) {
                Intent intent = new Intent(getContext(), NewArticleActivity.class);
                intent.putExtra("news", String.valueOf(dataList.getInformationArticleID()));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void DetoryViewAndEvents() {

    }

    @Override
    public void onFirstUserVisible() {
            initWeddingId();
            initList(weddingInformationID, this.title);
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    @Subscribe
    public void onEvent(EventBusBean event) {
        if (event.getType() == Constants.USER_INFO_CHANGE) {
            initList(weddingInformationID, this.title);
        }
    }

    private void initList(long weddingInformationID, String likeTitle) {
//        if(UserManager.getInstance(getContext()).isLogin()){
//            NetWorkUtil.setCallback("User/GetInformationArticleList", new MessageBean(pageIndex, pageCount, weddingInformationID, likeTitle), new NetWorkUtil.CallBackListener() {
//                @Override
//                public void onFinish(String respose) {
//                    MessageData messageData = new Gson().fromJson(respose, MessageData.class);
//                    mRecyclerView.stopLoad();
//                    if (messageData.getMessage().getCode().equals("200")) {
//                        //Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
//                        List<MessageData.DataList> data = messageData.getData();
//                        mAdapter.setList(data);
//                    } else {
//                        Toast.makeText(mContext, messageData.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onError(String e) {
//
//                }
//            });
//        }
    }

    private void initWeddingId() {
        NetWorkUtil.setCallback("User/GetWeddingInformationList", new BeanPager(pageIndex + "", "9999"), new NetWorkUtil.CallBackListener() {
            @Override
            public void onFinish(String respose) {
                GetWeddingData weddingData = new Gson().fromJson(respose, GetWeddingData.class);
                if (weddingData.getMessage().getCode().equals("200")) {
                    weddingDataList = weddingData.getData();
                    weddingDataList.add(0, new GetWeddingData.DataList(0, "婚礼", ""));
                    filterAdapter.setList(weddingDataList);
                }
            }

            @Override
            public void onError(String e) {

            }
        });
    }

    private void loadMore() {
//        if(UserManager.getInstance(getContext()).isLogin()){
//            NetWorkUtil.setCallback("User/GetInformationArticleList", new MessageBean(pageIndex, pageCount, weddingInformationID, ""), new NetWorkUtil.CallBackListener() {
//                @Override
//                public void onFinish(String respose) {
//                    MessageData messageData = new Gson().fromJson(respose, MessageData.class);
//                    if (messageData.getMessage().getCode().equals("200")) {
//                        //Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
//                        List<MessageData.DataList> data = messageData.getData();
//                        mAdapter.append(data);
//                    } else {
//                        Toast.makeText(mContext, messageData.getMessage().getInform(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onError(String e) {
//
//                }
//            });
//        }
    }

    private void initPopup() {
        View popupWindowView = LayoutInflater.from(mContext).inflate(R.layout.pop_message_filtrate, null);
        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        RecyclerView mRecyclerView = (RecyclerView) popupWindowView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        filterAdapter = new MessageScreenAdapter(mContext);
        mRecyclerView.setAdapter(filterAdapter);

        filterAdapter.setOnItemClickListener(new OnItemClickListener<GetWeddingData.DataList>() {
            @Override
            public void onItemClick(View view, int position, GetWeddingData.DataList dataList) {
                tvCodition.setText(dataList.getTitle());
                weddingInformationID = dataList.getWeddingInformationID();
                filterAdapter.notifyDataSetChanged();
                initList(weddingInformationID, "");
                dimss();
            }
        });

        //popupWindow.setAnimationStyle(R.style.TopSelectAnimationShow);
        // 菜单背景色。加了一点透明度
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setBackgroundDrawable(dw);

        //TODO 注意：这里的 R.layout.activity_main，不是固定的。你想让这个popupwindow盖在哪个界面上面。就写哪个界面的布局。这里以主界面为例
        // popupWindow.showAsDropDown(view);

        // 设置背景半透明
        // backgroundAlpha(0.7f);

//        adapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position, Object o) {
//                Toast.makeText(mContext, "点击了" + position, Toast.LENGTH_SHORT).show();
//                dimss();
//            }
//        });

        popupWindow.setOnDismissListener(new popupDismissListener());

        popupWindowView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*
                 * if( popupWindow!=null && popupWindow.isShowing()){
                 * popupWindow.dismiss(); popupWindow=null; }
                 */
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });
    }

    private void layoutStartAnimation(View target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(new ViewWrapper(target), "height", 400);
        objectAnimator.setDuration(400);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();

    }

    private void layoutEndAnimation(View target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(new ViewWrapper(target), "height", 0);
        objectAnimator.setDuration(400);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    private void imageStartAnimation(View target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "rotation", 0, 180);
        objectAnimator.setDuration(400).start();
    }

    private void imageEndAnimation(View target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "rotation", 180, 0);
        objectAnimator.setDuration(400).start();
    }

    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            flBg.setVisibility(View.GONE);

            // backgroundAlpha(1f);
        }
    }

    public void dimss() {
        if (popupWindow != null) {
            //flBg.setVisibility(View.GONE);
            imageEndAnimation(ivArrow);
            popupWindow.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
