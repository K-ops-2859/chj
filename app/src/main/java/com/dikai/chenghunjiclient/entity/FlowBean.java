package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2017/9/21.
 */

public class FlowBean implements Serializable{
    private String BeginTime;
    private String EndTime;
    private String Content;

    public String getBeginTime() {
        return BeginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public String getContent() {
        return Content;
    }
}
