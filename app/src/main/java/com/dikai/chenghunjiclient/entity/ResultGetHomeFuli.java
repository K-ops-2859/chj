package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/3/2.
 */

public class ResultGetHomeFuli {
    private ResultCode Message;
    private List<HomeFuliBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<HomeFuliBean> getData() {
        return Data;
    }
}
