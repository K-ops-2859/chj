package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/7/6.
 */

public class ResultGetMyTicket {
    private ResultCode Message;
    private List<MyTicketBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<MyTicketBean> getData() {
        return Data;
    }
}
