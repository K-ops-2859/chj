package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lucio on 2017/9/12.
 */

public class ResultGetRooms implements Serializable{
    private ResultCode Message;
    private List<RoomBean> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<RoomBean> getData() {
        return Data;
    }
}
