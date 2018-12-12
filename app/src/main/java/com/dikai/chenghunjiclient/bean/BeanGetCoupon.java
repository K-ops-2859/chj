package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/1/16.
 */

public class BeanGetCoupon {
    private int PageIndex;
    private int PageCount;
    private int IsHeat;

    public BeanGetCoupon(int pageIndex, int pageCount, int isHeat) {
        PageIndex = pageIndex;
        PageCount = pageCount;
        IsHeat = isHeat;
    }
}
