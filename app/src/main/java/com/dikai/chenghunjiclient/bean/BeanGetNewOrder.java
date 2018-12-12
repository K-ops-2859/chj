package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/9/20.
 */

public class BeanGetNewOrder {
    private String UserId;
    private String ActivityId;
    private int PageIndex;
    private int PageCount;
    private int Type;

    public BeanGetNewOrder(String userId, String activityId, int pageIndex, int pageCount, int type) {
        UserId = userId;
        ActivityId = activityId;
        PageIndex = pageIndex;
        PageCount = pageCount;
        Type = type;
    }
}
