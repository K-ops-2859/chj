package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/10/26.
 */

public class ResultSearchJiedanren {
    private ResultCode Message;
    private List<JiedanRenBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<JiedanRenBean> getData() {
        return Data;
    }
}
