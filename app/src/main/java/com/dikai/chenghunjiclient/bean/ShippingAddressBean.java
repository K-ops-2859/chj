package com.dikai.chenghunjiclient.bean;

/**
 * Created by cmk03 on 2017/11/17.
 */

public class ShippingAddressBean {

    private String ObjectTypes;
    private String ObjectID;
    private String AreaID;
    private String Address;
    private String Consignee;
    private String ConsigneePhone;
    private String PostCode;

    public ShippingAddressBean(String objectTypes, String objectID, String areaID, String address, String consignee, String consigneePhone, String postCode) {
        ObjectTypes = objectTypes;
        ObjectID = objectID;
        AreaID = areaID;
        Address = address;
        Consignee = consignee;
        ConsigneePhone = consigneePhone;
        PostCode = postCode;
    }
}
