package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cmk03 on 2018/5/2.
 */

public class PersonAddressData implements Serializable {

    private ResultCode Message;
    private List<DataList> Data;

    public List<DataList> getData() {
        return Data;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public static class DataList implements Serializable {
        private String Id;
        private String Area;
        private String DetailedAddress;
        private String Consignee;
        private String ConsigneePhone;
        private String PostCode;
        private String AreaId;
        private int DefaultAddress;


        public String getId() {
            return Id;
        }

        public String getArea() {
            return Area;
        }

        public String getDetailedAddress() {
            return DetailedAddress;
        }

        public String getConsignee() {
            return Consignee;
        }

        public String getConsigneePhone() {
            return ConsigneePhone;
        }

        public String getPostCode() {
            return PostCode;
        }

        public int getDefaultAddress() {
            return DefaultAddress;
        }

        public String getAreaId() {
            return AreaId;
        }
    }
}
