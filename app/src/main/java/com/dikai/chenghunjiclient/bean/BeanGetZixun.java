package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/1/16.
 */

public class BeanGetZixun {
    private int PageIndex;
    private int PageCount;
    private String WeddingInformationId;
    private String Title;

    public BeanGetZixun(int pageIndex, int pageCount, String weddingInformationId) {
        PageIndex = pageIndex;
        PageCount = pageCount;
        WeddingInformationId = weddingInformationId;
    }

    public BeanGetZixun(int pageIndex, int pageCount, String weddingInformationId, String title) {
        PageIndex = pageIndex;
        PageCount = pageCount;
        WeddingInformationId = weddingInformationId;
        Title = title;
    }
}
