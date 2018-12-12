package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lucio on 2017/9/21.
 */

public class CalendarBean implements Serializable{
    private String ScheduleTime;
    private List<DayPlanBean> Data;

    public String getScheduleTime() {
        return ScheduleTime;
    }

    public List<DayPlanBean> getData() {
        return Data;
    }
}
