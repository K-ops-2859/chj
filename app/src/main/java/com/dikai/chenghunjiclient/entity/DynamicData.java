package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cmk03 on 2018/1/23.
 */

public class DynamicData implements Serializable {
    private ResultCode Message;
    private int TotalCount;
    private List<DataList> Data;

    public List<DataList> getData() {
        return Data;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public static class DataList implements Serializable {
        private String DynamicID;
        private String ObjectId;
        private String DynamicerHeadportrait;
        private String DynamicerName;
        private String OccupationCode;
        private String Title;
        private String Content;
        private String FileId;
        private String CoverImg;
        private int FileType;
        private int BrowseCount;
        private int GivethumbCount;
        private int State;
        private int ShareCount;
        private int CommentsCount;
        private String CreateTime;

        public String getCoverImg() {
            return CoverImg;
        }

        public String getDynamicID() {
            return DynamicID;
        }

        public String getObjectId() {
            return ObjectId;
        }

        public String getDynamicerHeadportrait() {
            return DynamicerHeadportrait;
        }

        public String getDynamicerName() {
            return DynamicerName;
        }

        public String getOccupationCode() {
            return OccupationCode;
        }

        public String getTitle() {
            return Title;
        }

        public String getContent() {
            return Content;
        }

        public String getFileId() {
            return FileId;
        }

        public int getFileType() {
            return FileType;
        }

        public int getBrowseCount() {
            return BrowseCount;
        }

        public int getGivethumbCount() {
            return GivethumbCount;
        }

        public int getState() {
            return State;
        }

        public int getShareCount() {
            return ShareCount;
        }

        public int getCommentsCount() {
            return CommentsCount;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setState(int state) {
            State = state;
        }

        public void setGivethumbCount(int givethumbCount) {
            GivethumbCount = givethumbCount;
        }
    }
}
