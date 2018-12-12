package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lucio on 2018/10/22.
 */

public class ResultInvitation implements Serializable{
    private ResultCode Message;
    private List<InvitationBean> Data;
    private int iTotalCount;

    public ResultCode getMessage() {
        return Message;
    }

    public List<InvitationBean> getData() {
        return Data;
    }

    public int getiTotalCount() {
        return iTotalCount;
    }
}
