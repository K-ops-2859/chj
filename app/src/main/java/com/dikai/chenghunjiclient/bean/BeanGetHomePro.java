package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/1/15.
 */

public class BeanGetHomePro {
    private int PageIndex;
    private int PageCount;

    public BeanGetHomePro(int pageIndex, int pageCount) {
        PageIndex = pageIndex;
        PageCount = pageCount;
    }
}
