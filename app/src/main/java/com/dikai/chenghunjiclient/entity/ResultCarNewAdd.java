package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2017/9/22.
 */

public class ResultCarNewAdd {
    private ResultCode Message;
    private List<CarNewAddBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<CarNewAddBean> getData() {
        return Data;
    }
}
