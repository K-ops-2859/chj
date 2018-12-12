package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/6/14.
 */

public class BeanGetCombos {
    private int Type;
    private String PageIndex;
    private String PageCount;

    public BeanGetCombos(String pageIndex, String pageCount, int type) {
        PageIndex = pageIndex;
        PageCount = pageCount;
        Type = type;
    }
}
