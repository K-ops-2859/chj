package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/4/26.
 */

public class ResultGoodsTypeList {
    private ResultCode Message;
    private List<GoodsTypeBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<GoodsTypeBean> getData() {
        return Data;
    }
}
