package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2017/9/22.
 */

public class ResultAssignDriver {
    private ResultCode Message;
    private List<AssignDriverBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<AssignDriverBean> getData() {
        return Data;
    }
}
