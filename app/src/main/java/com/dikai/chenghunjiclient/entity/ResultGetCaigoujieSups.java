package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/7/5.
 */

public class ResultGetCaigoujieSups {
    private ResultCode Message;
    private int TotalCount;
    private List<CaigoujieSupBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public List<CaigoujieSupBean> getData() {
        return Data;
    }
}
