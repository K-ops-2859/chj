package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/8/11.
 */

public class BeanMyFocusDynamic {
    private String UserId;
    private int GetFileType;
    private int PageIndex;
    private int PageCount;

    public BeanMyFocusDynamic(String userId, int getFileType, int pageIndex, int pageCount) {
        UserId = userId;
        GetFileType = getFileType;
        PageIndex = pageIndex;
        PageCount = pageCount;
    }
}
