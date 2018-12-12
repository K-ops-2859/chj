package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2017/9/11.
 */

public class ResultGetVersion {
    private ResultCode Message;
    private List<VersionBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<VersionBean> getData() {
        return Data;
    }
}
