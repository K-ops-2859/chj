package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/8/29.
 */

public class BeanGetAdList {
    private String FacilitatorId;
    private int PageIndex;
    private int PageCount;

    public BeanGetAdList(String facilitatorId, int pageIndex, int pageCount) {
        FacilitatorId = facilitatorId;
        PageIndex = pageIndex;
        PageCount = pageCount;
    }
}
