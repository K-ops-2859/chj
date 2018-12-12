package com.dikai.chenghunjiclient.bean;

/**
 * Created by cmk03 on 2018/1/23.
 */

public class DynamicBean {
    private String UserId;
    private String GetUserId;
    private int GetFileType;
    private int PageIndex;
    private int PageCount;

    public DynamicBean(String userId, String getUserId, int getFileType, int pageIndex, int pageCount) {
        UserId = userId;
        GetUserId = getUserId;
        GetFileType = getFileType;
        PageIndex = pageIndex;
        PageCount = pageCount;
    }
}
