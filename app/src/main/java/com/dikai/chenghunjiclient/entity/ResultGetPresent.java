package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/9/17.
 */

public class ResultGetPresent {
    private ResultCode Message;
    private int TotalCount;
    private List<PresentBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public List<PresentBean> getData() {
        return Data;
    }
}
