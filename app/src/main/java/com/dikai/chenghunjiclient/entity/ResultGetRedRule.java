package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/3/2.
 */

public class ResultGetRedRule {
    private ResultCode Message;
    private String FriendMoney;
    private String CircleMoney;

    public ResultCode getMessage() {
        return Message;
    }

    public String getFriendMoney() {
        return FriendMoney;
    }

    public String getCircleMoney() {
        return CircleMoney;
    }
}
