package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by cmk03 on 2018/3/26.
 */

public class CustomerInfoBySupplierData extends WeddingPhotoData {

    private ResultCode Message;
    private List<CustomerInfoBySupplierData.DataList> Data;

    public List getData() {
        return Data;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public static class DataList extends WeddingPhotoData{
        private long Customerid;
        private String BrideName;
        private String BridePhoneNo;
        private String GroomName;
        private String GroomPhoneNo;

        public long getCustomerid() {
            return Customerid;
        }

        public String getBrideName() {
            return BrideName;
        }

        public String getBridePhoneNo() {
            return BridePhoneNo;
        }

        public String getGroomName() {
            return GroomName;
        }

        public String getGroomPhoneNo() {
            return GroomPhoneNo;
        }
    }
}
