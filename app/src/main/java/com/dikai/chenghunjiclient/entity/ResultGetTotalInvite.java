package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/3/7.
 */

public class ResultGetTotalInvite {
    private ResultCode Message;
    private int NowCount;
    private int AllCount;

    public ResultCode getMessage() {
        return Message;
    }

    public int getNowCount() {
        return NowCount;
    }

    public int getAllCount() {
        return AllCount;
    }
}
