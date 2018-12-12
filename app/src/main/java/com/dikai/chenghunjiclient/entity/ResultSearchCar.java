package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2017/9/20.
 */

public class ResultSearchCar {
    private ResultCode Message;
    private int TotalCount;
    private List<SearchCarBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public List<SearchCarBean> getData() {
        return Data;
    }
}
