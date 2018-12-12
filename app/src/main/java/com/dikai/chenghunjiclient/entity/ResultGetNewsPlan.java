package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2017/9/21.
 */

public class ResultGetNewsPlan {
    private ResultCode Message;
    private List<NewsPlanBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<NewsPlanBean> getData() {
        return Data;
    }
}
