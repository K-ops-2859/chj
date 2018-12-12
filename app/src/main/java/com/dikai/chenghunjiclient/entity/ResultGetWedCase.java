package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2017/12/14.
 */

public class ResultGetWedCase {
    private ResultCode Message;
    private int TotalCount;
    private List<GetProjectBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public List<GetProjectBean> getData() {
        return Data;
    }
}
