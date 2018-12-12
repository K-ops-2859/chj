package com.dikai.chenghunjiclient.fragment.plan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.entity.UserInfo;
import com.dikai.chenghunjiclient.util.UserManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanFragment extends Fragment {
    private LinearLayout placeHolder;
    private LinearLayout hunqingPlace;
    private TextView placeText;
    private FrameLayout mFrame;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    public PlanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        placeHolder = (LinearLayout) view.findViewById(R.id.fragment_add_place);
        hunqingPlace = (LinearLayout) view.findViewById(R.id.fragment_hunqing_place);
        mFrame = (FrameLayout) view.findViewById(R.id.fragment_add_frame);
        placeText = (TextView) view.findViewById(R.id.fragment_add_place_text);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setType();
    }

    public void setType(){
        placeHolder.setVisibility(View.GONE);
        hunqingPlace.setVisibility(View.GONE);
        mFrame.setVisibility(View.GONE);
        if(UserManager.getInstance(getContext()).isLogin()){
            UserInfo info = UserManager.getInstance(getContext()).getUserInfo();
            String code = info.getProfession();
            switch (code){
                case "SF_1001000"://酒店
                    mFrame.setVisibility(View.VISIBLE);
                    manager = getActivity().getSupportFragmentManager();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_add_frame, new HotelFragment());
                    transaction. commit();
                    break;
                case "SF_2001000"://婚车
                    mFrame.setVisibility(View.VISIBLE);
                    manager = getActivity().getSupportFragmentManager();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_add_frame, new HunCheFragment());
                    transaction.commitAllowingStateLoss();
                    break;
                case "SF_13001000"://车手
                    mFrame.setVisibility(View.VISIBLE);
                    manager = getActivity().getSupportFragmentManager();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_add_frame, new DriverPlanFragment());
                    transaction.commitAllowingStateLoss();
                    break;
                case "SF_14001000"://婚庆
                    hunqingPlace.setVisibility(View.VISIBLE);
                    break;
                case "SF_12001000"://新人
                    mFrame.setVisibility(View.VISIBLE);
                    manager = getActivity().getSupportFragmentManager();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_add_frame, new NewsPlanFragment());
                    transaction.commit();
                    break;
                default://其他
                    mFrame.setVisibility(View.VISIBLE);
                    manager = getActivity().getSupportFragmentManager();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.fragment_add_frame, new OtherFragment());
                    transaction.commit();
                    break;
            }
        }else {
            placeHolder.setVisibility(View.VISIBLE);
        }
    }
}
