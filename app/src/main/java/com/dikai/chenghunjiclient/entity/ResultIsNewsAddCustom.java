package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2017/12/11.
 */

public class ResultIsNewsAddCustom {
    private ResultCode Message;
    private String NewPeopleCustomID;
    private int CustomState;

    public ResultCode getMessage() {
        return Message;
    }

    public String getNewPeopleCustomID() {
        return NewPeopleCustomID;
    }

    public int getCustomState() {
        return CustomState;
    }
}
