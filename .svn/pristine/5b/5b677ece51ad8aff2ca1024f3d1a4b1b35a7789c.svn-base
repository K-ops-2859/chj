package com.dikai.chenghunjiclient.activity.discover;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.dikai.chenghunjiclient.R;
import com.dikai.chenghunjiclient.activity.PhotoActivity;
import com.dikai.chenghunjiclient.activity.newregister.NewLoginActivity;
import com.dikai.chenghunjiclient.activity.store.CarInfoActivity;
import com.dikai.chenghunjiclient.activity.store.CorpInfoActivity;
import com.dikai.chenghunjiclient.activity.store.HotelInfoActivity;
import com.dikai.chenghunjiclient.adapter.NineGridAdapter;
import com.dikai.chenghunjiclient.adapter.OnAdapterViewClickListener;
import com.dikai.chenghunjiclient.adapter.discover.DynamicCommentAdapter;
import com.dikai.chenghunjiclient.adapter.discover.DynamicLikeAdapter;
import com.dikai.chenghunjiclient.bean.BeanFocus;
import com.dikai.chenghunjiclient.bean.CommentBean;
import com.dikai.chenghunjiclient.bean.DiscoverLikeBean;
import com.dikai.chenghunjiclient.bean.DynamicInfoBean;
import com.dikai.chenghunjiclient.bean.DynamicLikeBean;
import com.dikai.chenghunjiclient.bean.Photo;
import com.dikai.chenghunjiclient.bean.RemoveComBean;
import com.dikai.chenghunjiclient.bean.RemoveDynamicBean;
import com.dikai.chenghunjiclient.entity.DynamicData;
import com.dikai.chenghunjiclient.entity.DynamicDetailsData;
import com.dikai.chenghunjiclient.entity.LikePersonData;
import com.dikai.chenghunjiclient.entity.ResultMessage;
import com.dikai.chenghunjiclient.util.Constants;
import com.dikai.chenghunjiclient.util.DensityUtil;
import com.dikai.chenghunjiclient.util.EventBusBean;
import com.dikai.chenghunjiclient.util.NetWorkUtil;
import com.dikai.chenghunjiclient.util.TextLUtil;
import com.dikai.chenghunjiclient.util.UserManager;
import com.dikai.chenghunjiclient.video.LandLayoutVideo;
import com.dikai.chenghunjiclient.view.CustomLinearLayoutManager;
import com.dikai.chenghunjiclient.view.MyImageView1;
import com.dikai.chenghunjiclient.view.MyRelativeLayout;
import com.dikai.chenghunjiclient.view.SoftKeyBoardListener;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.MaterialDialog;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by cmk03 on 2018/1/8.
 */

public class DynamicActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etComment;
    private ImageView ivLike;
    private ImageView ivShare;
    private TextView tvPublish;
    private XRecyclerView mRecyclerViewCom;
    private DynamicData.DataList data;
    private CircleImageView civLogo;
    private TextView tvUserName;
    private TextView tvIdentity;
    private TextView focus;
    private CircleImageView civLogo1;
    private TextView tvUserName1;
    private TextView tvIdentity1;
    private TextView focus1;
    private TextView tvTime;
    private TextView tvDesc;
    private TextView tvLikeNumber;
    private TextView tvTotalComment;
    private String dynamicID;
    private String userID;
    private DynamicCommentAdapter commentAdapter;
    private InputMethodManager im;
    private DynamicDetailsData LikeData;
    private MyRelativeLayout llImages;
    private String objectID;
    private DynamicDetailsData dynamicDetailsData;
    private RelativeLayout mVideoLayout;
    private int type;
    private boolean isPlay;
    private boolean isPause;
    private OrientationUtils orientationUtils;
    private GSYVideoOptionBuilder gsyVideoOption;
    private LandLayoutVideo detailPlayer;
    private ImageView coverImageView;
    private SpotsDialog mDialog;
    private ConvenientBanner mAdBanner;
    private CBViewHolderCreator mAdView;
    private List<String> pics;
    private boolean isMe = true;
    private int selectState = -1;
    private ActionSheetDialog sheetDialog;
    private int who;
    private boolean isFocused = false;
    private int likeNum;
    private MaterialDialog dialog;
    private MaterialDialog dialog2;
    private NestedScrollView mScrollView;
    private int nowPosition;
    private String comID;
    private String commerID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        ImmersionBar.with(this)
                .statusBarView(R.id.top_view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
        mScrollView = (NestedScrollView) findViewById(R.id.my_scroll);

        civLogo = (CircleImageView) findViewById(R.id.civ_logo);
        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        tvIdentity = (TextView) findViewById(R.id.tv_identity);
        focus = (TextView) findViewById(R.id.focus);

        civLogo1 = (CircleImageView) findViewById(R.id.civ_logo_1);
        tvUserName1 = (TextView) findViewById(R.id.tv_user_name_1);
        tvIdentity1 = (TextView) findViewById(R.id.tv_identity_1);
        focus1 = (TextView) findViewById(R.id.focus_1);

        tvTime = (TextView) findViewById(R.id.tv_time);
        llImages = (MyRelativeLayout) findViewById(R.id.ll_images);
        mRecyclerViewCom = (XRecyclerView) findViewById(R.id.recycler_view_comment);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        tvLikeNumber = (TextView) findViewById(R.id.tv_total_like);
        tvTotalComment = (TextView) findViewById(R.id.tv_total_comment);
        etComment = (EditText) findViewById(R.id.et_comment);
        ivLike = (ImageView) findViewById(R.id.iv_like);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        tvPublish = (TextView) findViewById(R.id.tv_publish);
        mVideoLayout = (RelativeLayout) findViewById(R.id.rl_video);
        detailPlayer = (LandLayoutVideo) findViewById(R.id.video_layout);
        mDialog = new SpotsDialog(this, R.style.DialogCustom);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ivShare.setOnClickListener(this);
        init();
    }

    private void init() {
        if(UserManager.getInstance(this).isLogin()){
            userID = UserManager.getInstance(DynamicActivity.this).getNewUserInfo().getUserId();
        }else {
            userID = "00000000-0000-0000-0000-000000000000";
        }
        data = (DynamicData.DataList) getIntent().getSerializableExtra("data");
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.setScrollEnabled(false);
        tvLikeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dynamicID != null && !"".equals(dynamicID)) {
                    Intent intent = new Intent(DynamicActivity.this, LikePersonActivity.class);
                    intent.putExtra("dynamicID", dynamicID);
                    intent.putExtra("objectID", objectID);
                    intent.putExtra("type", type);
                    startActivity(intent);
                }
            }
        });

        civLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLogoClick();
            }
        });

        civLogo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLogoClick();
            }
        });

        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.e("scrollY", DensityUtil.px2dip(DynamicActivity.this,scrollY)+"    oldScrollY"+oldScrollY);
                setAlpha((float) DensityUtil.px2dip(DynamicActivity.this,scrollY)/200);
            }
        });

        tvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComments();
            }
        });

        focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFocused){
                    dialog.show();
                }else {
                    focus();
                }
            }
        });
        focus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFocused){
                    dialog.show();
                }else {
                    focus();
                }
            }
        });

        dialog = new MaterialDialog(this);
        dialog.isTitleShow(false)//
                .btnNum(2)
                .content("确定要取消关注吗？")//
                .btnText("取消", "确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                        unfocus();
                    }
                });

        dialog2 = new MaterialDialog(this);
        dialog2.isTitleShow(false)//
                .btnNum(2)
                .content("确定要删除评论吗？")//
                .btnText("取消", "确定")
                .setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog2.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog2.dismiss();
                        removeCom(nowPosition, comID, commerID);
                    }
                });

        /**
         * 监听键盘的弹出和收起
         */
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //Toast.makeText(DynamicActivity.this, "键盘显示 高度" + height, Toast.LENGTH_SHORT).show();
                etComment.setCursorVisible(true);
                ivLike.setVisibility(View.GONE);
                tvPublish.setVisibility(View.VISIBLE);
            }

            @Override
            public void keyBoardHide(int height) {
                // Toast.makeText(DynamicActivity.this, "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
                if (etComment.getText().toString().equals("")) {
                    etComment.setCursorVisible(false);
                    ivLike.setVisibility(View.VISIBLE);
                    tvPublish.setVisibility(View.GONE);
                } else {
                    etComment.setCursorVisible(true);
                    ivLike.setVisibility(View.GONE);
                    tvPublish.setVisibility(View.VISIBLE);
                }
            }
        });

        mAdBanner = (ConvenientBanner) findViewById(R.id.fragment_head_ad);
        mAdView = new CBViewHolderCreator<AdBannerHolderView>() {
            @Override
            public AdBannerHolderView createHolder() {
                return new AdBannerHolderView();
            }
        };

        mAdBanner.setPointViewVisible(true)//设置指示器是否可见
                .setPageIndicator(new int[]{R.drawable.circle_lead_unslected, R.drawable.circle_lead_slected})   //设置指示器圆点
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL) //设置指示器位置（左、中、右)
//                .startTurning(3000)//设置自动切换（同时设置了切换时间间隔）
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(DynamicActivity.this, PhotoActivity.class);
                        intent.putExtra("now", position);
                        intent.putStringArrayListExtra("imgs", new ArrayList<>(pics));
                        startActivity(intent);
                    }
                })//设置点击监听事件
                .setManualPageable(true);//设置手动影响（设置可否手动切换）

        mRecyclerViewCom.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewCom.setNestedScrollingEnabled(false);
        commentAdapter = new DynamicCommentAdapter(this);
        mRecyclerViewCom.setAdapter(commentAdapter);

        /**删除评论*/

        commentAdapter.setOnAdapterViewClickListener(new OnAdapterViewClickListener<DynamicDetailsData.DataList>() {
            @Override
            public void onAdapterClick(View view, int position, DynamicDetailsData.DataList dataList) {
                nowPosition = position;
                comID = dataList.getCommentsID();
                commerID = dataList.getCommentsPeopleId();
                dialog2.show();
            }
        });

        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userID != null && LikeData != null) {
                    addAndRemoveLikeData(LikeData.getState() == 0 ? 1 : 0, dynamicID + "", userID);
                }
            }
        });
        initData();
    }

    private void initData() {
        type = getIntent().getIntExtra("type", 1);
        who = getIntent().getIntExtra("who",0);
        if (type == 2) {
            llImages.setVisibility(View.GONE);
            mVideoLayout.setVisibility(View.VISIBLE);
            initVideo();
        }else {
            llImages.setVisibility(View.VISIBLE);
            mVideoLayout.setVisibility(View.GONE);
        }
        if (who == 1) {
            isMeDynamic(data.getObjectId());
        } else {
            ivShare.setImageResource(R.mipmap.ic_dynamicshare);
        }
        getInfo();
    }

    private void setData(final DynamicDetailsData data) {
        objectID = data.getObjectId();
        dynamicDetailsData = data;
        dynamicID = data.getDynamicID();
        this.LikeData = data;
        String identity = UserManager.getInstance(this).getProfession(data.getOccupationCode());
        if(data.getObjectId().equals(userID)){
            focus.setVisibility(View.GONE);
            focus1.setVisibility(View.GONE);
            TextLUtil.setLength(DynamicActivity.this,tvUserName,tvIdentity,data.getDynamicerName(),identity,86,6);
            TextLUtil.setLength(DynamicActivity.this,tvUserName1,tvIdentity1,data.getDynamicerName(),identity,125,6);
        }else {
            focus.setVisibility(View.VISIBLE);
            focus1.setVisibility(View.VISIBLE);
            isFocused = data.getFollowState() == 1;
            if(isFocused){
                focus.setText("已关注");
                focus.setTextColor(ContextCompat.getColor(this,R.color.gray_text));
                focus.setBackgroundResource(R.drawable.bg_gray_line);

                focus1.setText("已关注");
                focus1.setTextColor(ContextCompat.getColor(this,R.color.gray_text));
                focus1.setBackgroundResource(R.drawable.bg_gray_line);
            }else {
                focus.setText("+关注");
                focus.setTextColor(ContextCompat.getColor(this,R.color.white));
                focus.setBackgroundResource(R.drawable.bg_btn_pink_deep_rect);

                focus1.setText("+关注");
                focus1.setTextColor(ContextCompat.getColor(this,R.color.white));
                focus1.setBackgroundResource(R.drawable.bg_btn_pink_deep_rect);
            }
            TextLUtil.setLength(DynamicActivity.this,tvUserName,tvIdentity,data.getDynamicerName(),identity,151,6);
            TextLUtil.setLength(DynamicActivity.this,tvUserName1,tvIdentity1,data.getDynamicerName(),identity,190,6);
        }
        Glide.with(this).load(data.getDynamicerHeadportrait())
                .error(R.color.gray_background)
                .into(civLogo);
        Glide.with(this).load(data.getDynamicerHeadportrait())
                .error(R.color.gray_background)
                .into(civLogo1);
        tvTime.setText(data.getCreateTime());
        likeNum = data.getGivethumbCount();
        tvLikeNumber.setText(likeNum + " 喜欢");
        tvDesc.setText(data.getContent());
        tvTotalComment.setText(data.getCommentsCount() + " 评论");
        if (data.getState() == 0) {
            ivLike.setImageResource(R.mipmap.ic_like_nor);
        } else {
            ivLike.setImageResource(R.mipmap.ic_like_pre);
        }

        if (type == 1) {
            pics = Arrays.asList(data.getFileUrl().split(","));
            mAdBanner.setPages(mAdView, pics);
        } else {
            Glide.with(this).load(data.getCoverImg()).into(coverImageView);
            gsyVideoOption
                    .setThumbImageView(coverImageView)
                    .setUrl(data.getFileUrl())
                    .setVideoTitle("")
                    .build(detailPlayer);
        }
        commentAdapter.setList(data.getData());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_share) {
            if (isMe) {
                if (who == 1) {
                    sheetDialog.isTitleShow(false).show();
                } else {
                    showShare(dynamicDetailsData.getDynamicerName(), dynamicDetailsData.getContent(), String.valueOf(dynamicDetailsData.getDynamicID()));
                }
            } else {
                showShare(dynamicDetailsData.getDynamicerName(), dynamicDetailsData.getContent(), String.valueOf(dynamicDetailsData.getDynamicID()));
            }
        }
    }

    private void setLogoClick(){
        String code = dynamicDetailsData.getOccupationCode();
        if("F209497C-2F2E-4394-AF20-312ED665F67A".equalsIgnoreCase(code)||"70CD854E-D943-4607-B993-91912329C61E".equalsIgnoreCase(code)){//车手/用户
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", new LikePersonData.DataList(LikeData.getDynamicerHeadportrait(),
                    LikeData.getDynamicerName(), LikeData.getObjectId(),
                    UserManager.getInstance(DynamicActivity.this).getProfession(LikeData.getOccupationCode())));
            Intent intent = new Intent(DynamicActivity.this, LikeDetailsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if("99C06C5A-DDB8-46A1-B860-CD1227B4DB68".equalsIgnoreCase(code)){//酒店
            startActivity(new Intent(DynamicActivity.this, HotelInfoActivity.class)
                    .putExtra("id", dynamicDetailsData.getFacilitatorId()).putExtra("userid",dynamicDetailsData.getObjectId()));
        }else if("2526D327-B0AE-4D88-922E-1F7A91722422".equalsIgnoreCase(code)){//婚车
            startActivity(new Intent(DynamicActivity.this, CarInfoActivity.class)
                    .putExtra("id", dynamicDetailsData.getFacilitatorId()).putExtra("userid",dynamicDetailsData.getObjectId()));
        }else if("7DC8EDF8-A068-400F-AFD0-417B19DB3C7C".equalsIgnoreCase(code)){//婚庆
            startActivity(new Intent(DynamicActivity.this, CorpInfoActivity.class)
                    .putExtra("id", dynamicDetailsData.getFacilitatorId())
                    .putExtra("type",1).putExtra("userid",dynamicDetailsData.getObjectId()));
        }else {//其他
            startActivity(new Intent(DynamicActivity.this, CorpInfoActivity.class)
                    .putExtra("id", dynamicDetailsData.getFacilitatorId())
                    .putExtra("type",0)
                    .putExtra("userid",dynamicDetailsData.getObjectId()));
        }
    }

    /**
     * 动态详情
     */
    private void getInfo(){
        NetWorkUtil.setCallback("HQOAApi/GetDynamicInfo",
                new DynamicInfoBean(userID, data.getDynamicID()),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        Log.e("返回结果", respose);
                        try {
                            DynamicDetailsData detailsData = new Gson().fromJson(respose, DynamicDetailsData.class);
                            if (detailsData.getMessage().getCode().equals("200")) {
                                setData(detailsData);
                            } else {
                                Toast.makeText(DynamicActivity.this, detailsData.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("json解析出错",e.toString());
                        }
                    }
                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                    }
                });
    }

    /**
     * 刷新评论
     */
    private void refresh() {
        //  dynamicID = String.valueOf(data.getDynamicID());
        NetWorkUtil.setCallback("HQOAApi/GetDynamicInfo",
                new DynamicInfoBean(userID,data.getDynamicID()), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            Log.e("返回值", respose);
                            DynamicDetailsData detailsData = new Gson().fromJson(respose, DynamicDetailsData.class);
                            if (detailsData.getMessage().getCode().equals("200")) {
                                commentAdapter.setList(detailsData.getData());
                                etComment.setText("");
                                if (im == null) {
                                    im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                }
                                im.hideSoftInputFromWindow(etComment.getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                                tvTotalComment.setText(detailsData.getCommentsCount() + " 评论");
                            }
                        } catch (Exception e) {
                            Log.e("", e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {

                    }
                });
    }

    /**
     * 是否由-我的动态-跳转而来，初始化more
     */
    private void isMeDynamic(String objectID) {
        if (objectID.equals(userID)) {
            isMe = true;
            ivShare.setImageResource(R.mipmap.ic_app_gray_more);
            final String[] stringItems = {"分享", "删除"};
            sheetDialog = new ActionSheetDialog(this, stringItems, null);
            sheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            showShare(dynamicDetailsData.getDynamicerName(),
                                    dynamicDetailsData.getContent(), String.valueOf(dynamicDetailsData.getDynamicID()));
                            break;
                        case 1:
                            removeDynamic(dynamicDetailsData.getDynamicID());
                            break;
                        default:
                            break;
                    }
                    sheetDialog.dismiss();
                }
            });
        } else {
            isMe = false;
            ivShare.setImageResource(R.mipmap.ic_dynamicshare);
        }
    }

    /**
     * 删除动态
     */
    private void removeDynamic(String dynamicId) {
        if(!UserManager.getInstance(this).isLogin()){
            startActivity(new Intent(this, NewLoginActivity.class));
            return;
        }
        NetWorkUtil.setCallback("HQOAApi/DelDynamic",
                new RemoveDynamicBean(dynamicId),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        try {
                            ResultMessage message = new Gson().fromJson(respose, ResultMessage.class);
                            if (message.getMessage().getCode().equals("200")) {
                                EventBus.getDefault().post(new EventBusBean(Constants.DELETE_DYNAMIC,type));
                                Toast.makeText(DynamicActivity.this, "已删除", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DynamicActivity.this, message.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(String e) {}
        });
    }

    /**
     * 点赞
     */
    private void addAndRemoveLikeData(final int state, String objectId, String userId) {
        if(!UserManager.getInstance(this).isLogin()){
            startActivity(new Intent(this, NewLoginActivity.class));
            return;
        }
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/AddDelGivethumb",
                new DiscoverLikeBean(objectId, userId),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mDialog.dismiss();
                        try {
                            ResultMessage resultMessage = new Gson().fromJson(respose, ResultMessage.class);
                            if (resultMessage.getMessage().getCode().equals("200")) {
                                if (state == 0) {
                                    likeNum--;
                                    tvLikeNumber.setText(likeNum + " 喜欢");
                                    ivLike.setImageResource(R.mipmap.ic_like_nor);
                                } else {
                                    likeNum++;
                                    tvLikeNumber.setText(likeNum + " 喜欢");
                                    ivLike.setImageResource(R.mipmap.ic_like_pre);
                                }
                                System.out.println("第二----------" + state);
                                LikeData.setState(state);
                            } else {
                                Toast.makeText(DynamicActivity.this, resultMessage.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("", e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {
                mDialog.dismiss();
            }
        });
    }

    /**
     * 添加评论
     */
    private void addComments() {
        String comment = etComment.getText().toString();
        if ("".equals(comment.trim())) {
            Toast.makeText(this, "请填写评论内容", Toast.LENGTH_SHORT).show();
            return;
        }else if(!UserManager.getInstance(this).isLogin()){
            startActivity(new Intent(this, NewLoginActivity.class));
            return;
        }
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/AddComments",
                new CommentBean(data.getDynamicID(), userID, comment),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mDialog.dismiss();
                        try {
                            ResultMessage resultMessage = new Gson().fromJson(respose, ResultMessage.class);
                            if (resultMessage.getMessage().getCode().equals("200")) {
                                refresh();
                                //Toast.makeText(DynamicActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("", e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                    }
                });
    }

    private void focus() {
        if(!UserManager.getInstance(this).isLogin()){
            startActivity(new Intent(this, NewLoginActivity.class));
            return;
        }
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/UserFollow",
                new BeanFocus(userID, objectID), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mDialog.dismiss();
                        try {
                            Log.e("返回值", respose);
                            ResultMessage detailsData = new Gson().fromJson(respose, ResultMessage.class);
                            if (detailsData.getMessage().getCode().equals("200")) {
                                isFocused = true;
                                focus.setText("已关注");
                                focus.setTextColor(ContextCompat.getColor(DynamicActivity.this,R.color.gray_text));
                                focus.setBackgroundResource(R.drawable.bg_gray_line);
                                focus1.setText("已关注");
                                focus1.setTextColor(ContextCompat.getColor(DynamicActivity.this,R.color.gray_text));
                                focus1.setBackgroundResource(R.drawable.bg_gray_line);
                                EventBus.getDefault().post(new EventBusBean(Constants.FOCUS_CHANGE));
                                EventBus.getDefault().post(new EventBusBean(Constants.FOCUS_REFRESH_HOME_LIST));
                            }else {
                                Toast.makeText(DynamicActivity.this, detailsData.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("", e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                    }
                });
    }

    private void unfocus() {
        if(!UserManager.getInstance(this).isLogin()){
            startActivity(new Intent(this, NewLoginActivity.class));
            return;
        }
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/DeleteUserFollow",
                new BeanFocus(userID, objectID), new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mDialog.dismiss();
                        try {
                            Log.e("返回值", respose);
                            ResultMessage detailsData = new Gson().fromJson(respose, ResultMessage.class);
                            if (detailsData.getMessage().getCode().equals("200")) {
                                isFocused = false;
                                focus.setText("+关注");
                                focus.setTextColor(ContextCompat.getColor(DynamicActivity.this,R.color.white));
                                focus.setBackgroundResource(R.drawable.bg_btn_pink_deep_rect);
                                focus1.setText("+关注");
                                focus1.setTextColor(ContextCompat.getColor(DynamicActivity.this,R.color.white));
                                focus1.setBackgroundResource(R.drawable.bg_btn_pink_deep_rect);
                                EventBus.getDefault().post(new EventBusBean(Constants.FOCUS_CHANGE));
                                EventBus.getDefault().post(new EventBusBean(Constants.FOCUS_REFRESH_HOME_LIST));
                            }else {
                                Toast.makeText(DynamicActivity.this, detailsData.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("", e.toString());
                        }
                    }

                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                    }
                });
    }

    private void removeCom(final int position, String comId, String comerId) {
        if(!UserManager.getInstance(this).isLogin()){
            startActivity(new Intent(this, NewLoginActivity.class));
            return;
        }
        mDialog.show();
        NetWorkUtil.setCallback("HQOAApi/DelComments",
                new RemoveComBean(comId, comerId),
                new NetWorkUtil.CallBackListener() {
                    @Override
                    public void onFinish(String respose) {
                        mDialog.dismiss();
                        try {
                            ResultMessage message = new Gson().fromJson(respose, ResultMessage.class);
                            if (message.getMessage().getCode().equals("200")) {
                                refresh();
                                Toast.makeText(DynamicActivity.this, "完成", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DynamicActivity.this, message.getMessage().getInform(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(String e) {
                        mDialog.dismiss();
                    }
                });
    }

    private void setAlpha(float alpha){
        float a = alpha >= 1?1:alpha;
        if(a < 0.5){
            if(focus1.getVisibility()!=View.GONE){
                focus1.setVisibility(View.INVISIBLE);
            }
            civLogo1.setVisibility(View.INVISIBLE);
            tvUserName1.setVisibility(View.INVISIBLE);
            tvIdentity1.setVisibility(View.INVISIBLE);
        }else {
            if(focus1.getVisibility()!=View.GONE){
                focus1.setVisibility(View.VISIBLE);
                focus1.setAlpha(a);
            }
            civLogo1.setVisibility(View.VISIBLE);
            tvUserName1.setVisibility(View.VISIBLE);
            tvIdentity1.setVisibility(View.VISIBLE);
            civLogo1.setAlpha(a);
            tvUserName1.setAlpha(a);
            tvIdentity1.setAlpha(a);
        }
    }

    private void initVideo() {
//        GSYVideoManager.clearAllDefaultCache(this);
        //增加封面
        coverImageView = new ImageView(this);
//        coverImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        resolveNormalVideoUI();
        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
//                .setThumbImageView(imageView)
//                .setUrl(videoUrl)
//                .setVideoTitle(result.getLogTitle())
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setSeekRatio(1)
                .setCacheWithPlay(true)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        Debuger.printfError("***** onPrepared **** " + objects[0]);
                        Debuger.printfError("***** onPrepared **** " + objects[1]);
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[1]);//当前全屏player
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                    }

                    @Override
                    public void onClickStartError(String url, Object... objects) {
                        super.onClickStartError(url, objects);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                })
                .setGSYVideoProgressListener(new GSYVideoProgressListener() {
                    @Override
                    public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                        Debuger.printfLog(" progress " + progress + " secProgress " + secProgress + " currentPosition " + currentPosition + " duration " + duration);
                    }
                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (orientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            orientationUtils.setEnable(!lock);
                        }
                    }
                });
        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(DynamicActivity.this, true, true);
            }
        });
    }


    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }

    private void resolveNormalVideoUI() {
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.GONE);
    }

    private GSYVideoPlayer getCurPlay() {
        if (detailPlayer.getFullWindowPlayer() != null) {
            return detailPlayer.getFullWindowPlayer();
        }
        return detailPlayer;
    }

    @Override
    public void onBackPressed() {

        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }

        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * 广告HolderView
     */
    public class AdBannerHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Glide.with(context).load(data).placeholder(R.color.gray_background).into(imageView);
        }
    }

    @Override
    protected void onResume() {
        getCurPlay().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
        super.onDestroy();
        if (isPlay) {
            getCurPlay().release();
        }
        //GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    private void showShare(String name, String content, String id) {
        String url = "http://www.chenghunji.com/fenxiang/Index?id=" + id;
        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(content);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("来自 " + name + " 的成婚记动态");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://www.chenghunji.com/img/index/B_LOGO_01.png");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("成婚纪——婚礼原来如此简单");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("成婚纪");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        // 启动分享GUI
        oks.show(this);
    }
}
