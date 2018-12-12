package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/4/3.
 */

public class ResultGetPrize {
    private ResultCode Message;
    private String PrizeId;
    private String PrizeName;
    private String Imgurl;
    private int PrizeSubscript;

    public ResultCode getMessage() {
        return Message;
    }

    public String getPrizeId() {
        return PrizeId;
    }

    public String getPrizeName() {
        return PrizeName;
    }

    public String getImgurl() {
        return Imgurl;
    }

    public int getPrizeSubscript() {
        return PrizeSubscript;
    }
}
