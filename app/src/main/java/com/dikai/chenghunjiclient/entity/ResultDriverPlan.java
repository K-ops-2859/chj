package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lucio on 2017/9/26.
 */

public class ResultDriverPlan implements Serializable{
    private ResultCode Message;
    private List<DriverPlanListBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<DriverPlanListBean> getData() {
        return Data;
    }
}
