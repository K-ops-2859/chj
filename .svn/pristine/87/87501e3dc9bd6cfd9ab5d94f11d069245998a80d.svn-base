package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cmk03 on 2018/1/26.
 */

public class LikePersonData implements Serializable{
    private ResultCode Message;
    private int TotalCount;
    private List<DataList> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public List<DataList> getData() {
        return Data;
    }

    public static class DataList implements Serializable{
        private long GivethumbID;
        private int GivethumbTypes;
        private String ObjectId;
        private String SupplierID;
        private String GivethumbHeadportrait;
        private String GivethumbName;
        private String GivethumberId;
        private int State;
        private String OccupationCode;
        private String CreateTime;

        public DataList( String givethumbHeadportrait, String givethumbName, String givethumberId, String occupationCode) {
            GivethumbHeadportrait = givethumbHeadportrait;
            GivethumbName = givethumbName;
            GivethumberId = givethumberId;
            OccupationCode = occupationCode;
        }

        public void setOccupationCode(String occupationCode) {
            OccupationCode = occupationCode;
        }

        public String getSupplierID() {
            return SupplierID;
        }

        public long getGivethumbID() {
            return GivethumbID;
        }

        public int getGivethumbTypes() {
            return GivethumbTypes;
        }

        public String getObjectId() {
            return ObjectId;
        }

        public String getGivethumbHeadportrait() {
            return GivethumbHeadportrait;
        }

        public String getGivethumbName() {
            return GivethumbName;
        }

        public String getGivethumberId() {
            return GivethumberId;
        }

        public int getState() {
            return State;
        }

        public String getOccupationCode() {
            return OccupationCode;
        }

        public String getCreateTime() {
            return CreateTime;
        }
    }
}
