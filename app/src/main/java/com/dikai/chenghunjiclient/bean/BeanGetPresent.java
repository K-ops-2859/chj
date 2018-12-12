package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/9/17.
 */

public class BeanGetPresent {
    private int Type;
    private int ActivityType;
    private String FacilitatorId;
    private String UserNamePhone;
    private int PageIndex;
    private int PageCount;

    public BeanGetPresent(int type, int activityType, String facilitatorId, String userNamePhone, int pageIndex, int pageCount) {
        Type = type;
        ActivityType = activityType;
        FacilitatorId = facilitatorId;
        UserNamePhone = userNamePhone;
        PageIndex = pageIndex;
        PageCount = pageCount;
    }
}
