package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cmk03 on 2018/3/20.
 */

public class VideoPhotoData implements Serializable {

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

    public static class DataList implements Serializable{
        private String ImgUrl;
        private int FileType;
        private long CustomerId;
        private String GroomName;
        private String GroomPhone;
        private String BrideName;
        private String BridePhone;
        private String UpsName;
        private String CreateTime;
        private int RejectCount;
        private String CorpName;

        public String getImgUrl() {
            return ImgUrl;
        }

        public int getFileType() {
            return FileType;
        }

        public long getCustomerId() {
            return CustomerId;
        }

        public String getGroomName() {
            return GroomName;
        }

        public String getGroomPhone() {
            return GroomPhone;
        }

        public String getBrideName() {
            return BrideName;
        }

        public String getBridePhone() {
            return BridePhone;
        }

        public String getUpsName() {
            return UpsName;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public int getRejectCount() {
            return RejectCount;
        }

        public String getCorpName() {
            return CorpName;
        }
    }
}
