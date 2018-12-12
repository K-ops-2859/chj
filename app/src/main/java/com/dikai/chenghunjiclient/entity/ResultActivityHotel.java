package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/10/26.
 */

public class ResultActivityHotel {
    private ResultCode Message;
    private int TotalCount;
    private String RulesActivity;
    private String HeadImg;
    private List<ActivityHotelBean> Data;
    private List<HotelAdImg> StartImgData;
    private List<HotelAdEndImg> EndImgListData;

    public ResultCode getMessage() {
        return Message;
    }

    public String getRulesActivity() {
        return RulesActivity;
    }

    public String getHeadImg() {
        return HeadImg;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public List<ActivityHotelBean> getData() {
        return Data;
    }

    public List<HotelAdImg> getStartImgData() {
        return StartImgData;
    }

    public List<HotelAdEndImg> getEndImgListData() {
        return EndImgListData;
    }
}
