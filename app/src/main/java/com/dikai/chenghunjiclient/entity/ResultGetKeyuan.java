package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/10/30.
 */

public class ResultGetKeyuan {
    private ResultCode Message;
    private int iTotalCount;
    private String Img;
    private List<KeYuanBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return iTotalCount;
    }

    public String getImg() {
        return Img;
    }

    public List<KeYuanBean> getData() {
        return Data;
    }
}
