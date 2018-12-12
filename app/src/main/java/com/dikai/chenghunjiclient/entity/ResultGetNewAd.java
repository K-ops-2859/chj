package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/5/15.
 */

public class ResultGetNewAd {
    private ResultCode Message;
    private String SortField;
    private List<NewAdBean> Data;

    public String getSortField() {
        return SortField;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public List<NewAdBean> getData() {
        return Data;
    }
}
