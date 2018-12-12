package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lucio on 2018/5/2.
 */

public class ResultGetCartList implements Serializable{
    private ResultCode Message;
    private List<CartBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<CartBean> getData() {
        return Data;
    }
}
