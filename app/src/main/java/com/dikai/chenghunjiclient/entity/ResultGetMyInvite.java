package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/2/8.
 */

public class ResultGetMyInvite {
    private ResultCode Message;
    private int TotalCount;
    private List<MyInviteDayBean> TimeData;

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public List<MyInviteDayBean> getData() {
        return TimeData;
    }
}
