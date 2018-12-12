package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/8/18.
 */

public class ResultBoundWechat {
    private ResultCode Message;
    private String WeChatName;
    private int WeChatType;

    public ResultCode getMessage() {
        return Message;
    }

    public String getWeChatName() {
        return WeChatName;
    }

    public int getWeChatType() {
        return WeChatType;
    }
}
