package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by cmk03 on 2017/11/18.
 */

public class GetAddressData {
    private ResultCode Message;
    private int TotalCount;
    List<GetAddressList> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public List<GetAddressList> getData() {
        return Data;
    }

    static class GetAddressList {
        private int DeliveryAddressID;
        private int AreaID;
        private String Address;
        private String Consignee;
        private String ConsigneePhone;
        private String PostCode;
        private String CreateTime;

        public int getDeliveryAddressID() {
            return DeliveryAddressID;
        }

        public int getAreaID() {
            return AreaID;
        }

        public String getAddress() {
            return Address;
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

        public String getCreateTime() {
            return CreateTime;
        }
    }
}
