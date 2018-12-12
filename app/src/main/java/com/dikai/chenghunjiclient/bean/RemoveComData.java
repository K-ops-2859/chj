package com.dikai.chenghunjiclient.bean;

/**
 * Created by cmk03 on 2018/4/13.
 */

public class RemoveComData {
    private String CommentsID;
    private String CommentserId;

    public RemoveComData(String commentsID, String commentserId) {
        CommentsID = commentsID;
        CommentserId = commentserId;
    }
}
