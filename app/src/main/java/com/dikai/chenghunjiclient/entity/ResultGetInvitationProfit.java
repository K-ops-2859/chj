package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2018/10/22.
 */

public class ResultGetInvitationProfit implements Serializable{
    private ResultCode Message;
    private int RefereeStatus;
    private String TopBanner;
    private String EndBanner;
    private String Money;

    public ResultCode getMessage() {
        return Message;
    }

    public int getRefereeStatus() {
        return RefereeStatus;
    }

    public String getTopBanner() {
        return TopBanner;
    }

    public String getEndBanner() {
        return EndBanner;
    }

    public String getMoney() {
        return Money;
    }
}
