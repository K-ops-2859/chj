package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by cmk03 on 2018/1/22.
 */

public class GetWeddingData {
    private ResultCode Message;
    private long TotalCount;
    private List<DataList> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public long getTotalCount() {
        return TotalCount;
    }

    public List<DataList> getData() {
        return Data;
    }

    public static class DataList {
        public DataList(long weddingInformationID, String title, String createTime) {
            WeddingInformationID = weddingInformationID;
            Title = title;
            CreateTime = createTime;
        }

        public DataList() {

        }

        private long WeddingInformationID;
        private String Title;
        private String CreateTime;
        private boolean isChecked;

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public boolean getChecked() {
            return isChecked;
        }

        public long getWeddingInformationID() {
            return WeddingInformationID;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public String getTitle() {
            return Title;
        }


    }
}
