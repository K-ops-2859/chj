package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lucio on 2018/6/14.
 */

public class ResultGetCombos implements Serializable{
    private ResultCode Message;
    private int TotalCount;
    private List<CombosBean> Data;

    public int getTotalCount() {
        return TotalCount;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public List<CombosBean> getData() {
        return Data;
    }
}
