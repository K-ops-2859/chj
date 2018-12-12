package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/7/6.
 */

public class BeanBuyCaigoujie {
    private String UserId;
    private String PayType;
    private String ObjectType;
    private String ObjectId;

    public BeanBuyCaigoujie(String userId, String payType, String objectType, String objectId) {
        UserId = userId;
        PayType = payType;
        ObjectType = objectType;
        ObjectId = objectId;
    }
}
