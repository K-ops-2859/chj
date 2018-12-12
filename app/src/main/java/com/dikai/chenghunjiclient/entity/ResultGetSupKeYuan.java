package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/11/2.
 */

public class ResultGetSupKeYuan {
    private ResultCode Message;
    private int iTotalCount;
    private String Img;
    private List<SupKeYuanBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public int getiTotalCount() {
        return iTotalCount;
    }

    public String getImg() {
        return Img;
    }

    public List<SupKeYuanBean> getData() {
        return Data;
    }
}
