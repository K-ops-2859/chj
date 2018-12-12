package com.dikai.chenghunjiclient.bean;

/**
 * Created by cmk03 on 2017/11/18.
 */

public class BeanAddPrize {
    private String ObjectTypes;
    private String ObjectID;
    private String PrizesCode;
    private String DeliveryId;
    private String Grade;
    private int ActivityPrizesID;
    private String CargoCode;

    public BeanAddPrize(String objectTypes, String objectID, String prizesCode, String deliveryId, String grade, int activityPrizesID, String cargoCode) {
        ObjectTypes = objectTypes;
        ObjectID = objectID;
        PrizesCode = prizesCode;
        DeliveryId = deliveryId;
        Grade = grade;
        ActivityPrizesID = activityPrizesID;
        CargoCode = cargoCode;
    }
}
