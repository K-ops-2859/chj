package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lucio on 2018/6/14.
 */

public class ResultGetComboVideo implements Serializable{
    private ResultCode Message;
    private List<ComboVideoBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<ComboVideoBean> getData() {
        return Data;
    }
}
