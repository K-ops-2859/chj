package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/8/29.
 */

public class ResultGetNewADList {
    private ResultCode Message;
    private int TotalCount;
    private List<NewAdHomeList> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<NewAdHomeList> getData() {
        return Data;
    }

    public int getTotalCount() {
        return TotalCount;
    }
}
