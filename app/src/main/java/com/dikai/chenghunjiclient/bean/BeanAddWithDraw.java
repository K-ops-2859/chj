package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/9/28.
 */

public class BeanAddWithDraw {
    private String FacilitatorID;
    private String AccountNumberId;
    private String Money;
    private int Type;

    public BeanAddWithDraw(String facilitatorID, String accountNumberId, String money, int type) {
        FacilitatorID = facilitatorID;
        AccountNumberId = accountNumberId;
        Money = money;
        Type = type;
    }
}
