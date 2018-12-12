package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lucio on 2017/9/21.
 */

public class ResultGetCalendar implements Serializable{
    private ResultCode Message;
    private int TotalCount;
    private List<CalendarBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public List<CalendarBean> getData() {
        return Data;
    }
}
