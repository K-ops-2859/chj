package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by cmk03 on 2018/3/26.
 */

public class CustomerInfoData extends WeddingPhotoData{

    private ResultCode Message;
    private List<CustomerInfoData.DataList> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<DataList> getData() {
        return Data;
    }

    public static class DataList extends WeddingPhotoData {
        private long CustomerInfoID;
        private long CorpID;
        private String CorpName;
        private String Logo;
        private String Address;

        public long getCustomerInfoID() {
            return CustomerInfoID;
        }

        public long getCorpID() {
            return CorpID;
        }

        public String getCorpName() {
            return CorpName;
        }

        public String getLogo() {
            return Logo;
        }

        public String getAddress() {
            return Address;
        }
    }

}
