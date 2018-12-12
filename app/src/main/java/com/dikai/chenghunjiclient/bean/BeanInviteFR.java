package com.dikai.chenghunjiclient.bean;

/**
 * Created by cmk03 on 2018/10/22.
 */

public class BeanInviteFR {
    private String UserId;
    private int PageIndex;
    private int PageCount;

    public BeanInviteFR(String userId, int pageIndex, int pageCount) {
        UserId = userId;
        PageIndex = pageIndex;
        PageCount = pageCount;
    }
}
