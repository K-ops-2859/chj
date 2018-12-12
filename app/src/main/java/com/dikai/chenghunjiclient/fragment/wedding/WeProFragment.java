package com.dikai.chenghunjiclient.fragment.wedding;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.wedding.WeQuwstionActivity;
import com.dikai.chenghunjiclient.activity.wedding.WedHotelActivity;
import com.dikai.chenghunjiclient.adapter.NineGridAdapter;
import com.dikai.chenghunjiclient.bean.BeanGetCustom;
import com.dikai.chenghunjiclient.entity.NewUserInfo;
import com.dikai.chenghunjiclient.entity.ResultGetNewsCustom;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.entity.WeProBean;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.google.gson.Gson;
import com.jaeger.ninegridimageview.NineGridImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeProFragment extends Fragment implements View.OnClickListener {

    private NineGridImageView mNineGridImageView;
    private NineGridAdapter mGridAdapter;
    private LinearLayout budgetLayout1;
    private LinearLayout budgetLayout2;
    private TextView budgetText;
    private LinearLayout dateLayout1;
    private LinearLayout dateLayout2;
    private TextView datetText;
    private LinearLayout hotelLayout1;
    private LinearLayout hotelLayout2;
    private TextView hoteltText;
    private LinearLayout codeLayout1;
    private LinearLayout codeLayout2;
    private TextView codeLtText;
    private LinearLayout ledLayout1;
    private LinearLayout ledLayout2;
    private TextView ledtText;
    private LinearLayout infoLayout1;
    private LinearLayout infoLayout2;
    private TextView infotText;
    private WeProBean mWeProBean;
    private SwipeRefreshLayout fresh;
    private int state;

    public WeProFragment() {
        // Required empty public constructor
    }

    public static WeProFragment newInstance(int state) {
        Bundle args = new Bundle();
        args.putInt("state",state);
        WeProFragment fragment = new WeProFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_we, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        state = getArguments().getInt("state");
        Log.e("WeProFragment---","state: " + state);
        fresh = (SwipeRefreshLayout) view.findViewById(R.id.my_fresh);
        budgetLayout1 = (LinearLayout) view.findViewById(R.id.ll_budget);
        budgetLayout2 = (LinearLayout) view.findViewById(R.id.we_budget_layout);
        budgetText = (TextView) view.findViewById(R.id.we_budget);
        dateLayout1 = (LinearLayout) view.findViewById(R.id.ll_date);
        dateLayout2 = (LinearLayout) view.findViewById(R.id.date_layout);
        datetText = (TextView) view.findViewById(R.id.we_date);
        hotelLayout1 = (LinearLayout) view.findViewById(R.id.ll_hotel);
        hotelLayout2 = (LinearLayout) view.findViewById(R.id.hotel_layout);
        hoteltText = (TextView) view.findViewById(R.id.we_hotel);
        codeLayout1 = (LinearLayout) view.findViewById(R.id.ll_code);
        codeLayout2 = (LinearLayout) view.findViewById(R.id.code_layout);
        codeLtText = (TextView) view.findViewById(R.id.we_code);
        ledLayout1 = (LinearLayout) view.findViewById(R.id.ll_led);
        ledLayout2 = (LinearLayout) view.findViewById(R.id.led_layout);
        ledtText = (TextView) view.findViewById(R.id.we_led);
        infoLayout1 = (LinearLayout) view.findViewById(R.id.ll_info);
        infoLayout2 = (LinearLayout) view.findViewById(R.id.info_layout);
        infotText = (TextView) view.findViewById(R.id.we_info);
        budgetLayout1.setOnClickListener(this);
        dateLayout1.setOnClickListener(this);
        budgetLayout1.setOnClickListener(this);
        hotelLayout1.setOnClickListener(this);
        codeLayout1.setOnClickListener(this);
        ledLayout1.setOnClickListener(this);
        infoLayout1.setOnClickListener(this);
        view.findViewById(R.id.we_edit).setOnClickListener(this);
        view.findViewById(R.id.we_date_edit).setOnClickListener(this);
        view.findViewById(R.id.we_hotel_edit).setOnClickListener(this);
        view.findViewById(R.id.we_code_edit).setOnClickListener(this);
        view.findViewById(R.id.we_led_edit).setOnClickListener(this);
        view.findViewById(R.id.we_info_edit).setOnClickListener(this);
        mNineGridImageView = ((NineGridImageView) view.findViewById(R.id.we_hotel_nine));
        mGridAdapter = new NineGridAdapter();
        mNineGridImageView.setAdapter(mGridAdapter);
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
                getCustomQuestion();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getCustomQuestion();
    }

    @Override
    public void onClick(View v) {
        if(state == 1){
            Toast.makeText(getContext(), "已提交，不可修改", Toast.LENGTH_SHORT).show();
        }else {
            switch (v.getId()){
                case R.id.we_edit:
                case R.id.ll_budget:
                    Bundle bundle0 = new Bundle();
                    bundle0.putSerializable("info",mWeProBean);
                    bundle0.putSerializable("type",5);
                    startActivity(new Intent(getContext(), WeQuwstionActivity.class)
                            .putExtras(bundle0));
                    break;
                case R.id.we_date_edit:
                case R.id.ll_date:
                    Bundle bundle01 = new Bundle();
                    bundle01.putSerializable("info",mWeProBean);
                    bundle01.putSerializable("type",2);
                    startActivity(new Intent(getContext(), WeQuwstionActivity.class)
                            .putExtras(bundle01));
                    break;
                case R.id.we_hotel_edit:
                case R.id.ll_hotel:
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("info",mWeProBean);
                    startActivity(new Intent(getContext(), WedHotelActivity.class)
                            .putExtras(bundle1));
                    break;
                case R.id.we_code_edit:
                case R.id.ll_code:
                    Bundle bundle03 = new Bundle();
                    bundle03.putSerializable("info",mWeProBean);
                    bundle03.putSerializable("type",4);
                    startActivity(new Intent(getContext(), WeQuwstionActivity.class)
                            .putExtras(bundle03));
                    break;
                case R.id.we_led_edit:
                case R.id.ll_led:
                    Bundle bundle04 = new Bundle();
                    bundle04.putSerializable("info",mWeProBean);
                    bundle04.putSerializable("type",3);
                    startActivity(new Intent(getContext(), WeQuwstionActivity.class)
                            .putExtras(bundle04));
                    break;
                case R.id.we_info_edit:
                case R.id.ll_info:
                    Bundle bundle05 = new Bundle();
                    bundle05.putSerializable("info",mWeProBean);
                    bundle05.putSerializable("type",6);
                    startActivity(new Intent(getContext(), WeQuwstionActivity.class)
                            .putExtras(bundle05));
                    break;
            }
        }
    }

    private void getCustomQuestion() {
        NewUserInfo userInfo = UserManager.getInstance(getContext()).getNewUserInfo();
        NetWorkUtil.setCallback("HQOAApi/GetNewPeopleQuestionList",
                new BeanGetCustom(userInfo.getUserId(),"0"),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(final String respose) {
                        stopLoad();
                        Log.e("返回值",respose);
                        try {
                            ResultGetNewsCustom result = new Gson().fromJson(respose, ResultGetNewsCustom.class);
                            if ("200".equals(result.getMessage().getCode())) {
                                if(result.getDataGG().size()>0)
                                    setData(result.getDataGG().get(0));
                            } else {
                                Toast.makeText(getContext(), "" + result.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                            //Toast.makeText(getContext(), "网络请求错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(final String e) {
                        stopLoad();
                        Log.e("网络出错",e.toString());
                    }
                });
    }

    private void setData(WeProBean bean) {
        mWeProBean = bean;
        if(bean.getBudget() == 0){
            budgetLayout1.setVisibility(View.VISIBLE);
            budgetLayout2.setVisibility(View.GONE);
        }else {
            budgetLayout1.setVisibility(View.GONE);
            budgetLayout2.setVisibility(View.VISIBLE);
            budgetText.setText("" + bean.getBudget());
        }
        if(bean.getWeddingDay() == null || "".equals(bean.getWeddingDay())
                || "0001-01-01".equals(bean.getWeddingDay())){
            dateLayout1.setVisibility(View.VISIBLE);
            dateLayout2.setVisibility(View.GONE);
        }else {
            dateLayout1.setVisibility(View.GONE);
            dateLayout2.setVisibility(View.VISIBLE);
            datetText.setText(bean.getWeddingDay());
        }
        if(bean.getInvitationCode() == null || "".equals(bean.getInvitationCode())){
            codeLayout1.setVisibility(View.VISIBLE);
            codeLayout2.setVisibility(View.GONE);
        }else {
            codeLayout1.setVisibility(View.GONE);
            codeLayout2.setVisibility(View.VISIBLE);
            codeLtText.setText(bean.getInvitationCode());
        }
        if(bean.getIsLEDScreen() == 2){
            ledLayout1.setVisibility(View.VISIBLE);
            ledLayout2.setVisibility(View.GONE);
        }else {
            ledLayout1.setVisibility(View.GONE);
            ledLayout2.setVisibility(View.VISIBLE);
            ledtText.setText(bean.getIsLEDScreen()==0?"不需要":"需要");
        }
        if(bean.getSpecialVersion() == null || "".equals(bean.getSpecialVersion())){
            infoLayout1.setVisibility(View.VISIBLE);
            infoLayout2.setVisibility(View.GONE);
        }else {
            infoLayout1.setVisibility(View.GONE);
            infoLayout2.setVisibility(View.VISIBLE);
            infotText.setText(bean.getSpecialVersion());
        }
        if(bean.getHotelName() == null || "".equals(bean.getHotelName())){
            hotelLayout1.setVisibility(View.VISIBLE);
            hotelLayout2.setVisibility(View.GONE);
        }else {
            hotelLayout1.setVisibility(View.GONE);
            hotelLayout2.setVisibility(View.VISIBLE);
            try{
                String[] xl = bean.getRummeryXls().split(",");
                String hotelInfo = "酒店名称：" + bean.getHotelName() + "\n酒店地址：" + bean.getHotelAddress() +
                        "\n宴会厅名："+ bean.getHallName()+"\n桌数："+bean.getTableCount()+"\n宴会厅尺寸：长：" + xl[0] + "m  宽："
                        + xl[1] + "m  高：" + xl[2];
                hoteltText.setText(hotelInfo);
            }catch (Exception e){
                Log.e("酒店信息",e.toString());
            }
            mNineGridImageView.setImagesData(Arrays.asList(bean.getRummeryImg().split(",")));
        }
    }

    public void stopLoad(){
        if(fresh.isRefreshing()){
            fresh.post(new Runnable() {
                @Override
                public void run() {
                    fresh.setRefreshing(false);
                }
            });
        }
    }

    /**
     * 事件总线刷新
     * @param bean
     */
    @Subscribe
    public void onEventMainThread(final EventBusBean bean) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if(bean.getType() == Constants.WE_INFO_CHANGED){
                    getCustomQuestion();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
