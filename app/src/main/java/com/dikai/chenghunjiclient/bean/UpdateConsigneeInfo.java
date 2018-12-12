package com.dikai.chenghunjiclient.bean;

/**
 * Created by cmk03 on 2018/5/2.
 */

public class UpdateConsigneeInfo {
    private String Id;
    private String UserId;
    private String AreaId;
    private String DetailedAddress;
    private String Consignee;
    private String ConsigneePhone;
    private String PostCode;
    private String DefaultAddress;

    public UpdateConsigneeInfo(String id, String userId, String areaId, String detailedAddress, String consignee, String consigneePhone, String postCode, String defaultAddress) {
        Id = id;
        UserId = userId;
        AreaId = areaId;
        DetailedAddress = detailedAddress;
        Consignee = consignee;
        ConsigneePhone = consigneePhone;
        PostCode = postCode;
        DefaultAddress = defaultAddress;
    }
}
