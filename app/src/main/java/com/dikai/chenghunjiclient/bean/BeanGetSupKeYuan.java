package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/11/2.
 */

public class BeanGetSupKeYuan {
    private String FacilitatorId;
    private int SortField;
    private int Sort;
    private int PageIndex;
    private int PageCount;
    private String AreaId;

    public BeanGetSupKeYuan(String facilitatorId, int sortField, int sort, int pageIndex, int pageCount, String areaId) {
        FacilitatorId = facilitatorId;
        SortField = sortField;
        Sort = sort;
        PageIndex = pageIndex;
        PageCount = pageCount;
        AreaId = areaId;
    }
}
