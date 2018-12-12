package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2017/9/13.
 */

public class ResultCar {
    private ResultCode Message;
    private List<CarsBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<CarsBean> getData() {
        return Data;
    }
}
