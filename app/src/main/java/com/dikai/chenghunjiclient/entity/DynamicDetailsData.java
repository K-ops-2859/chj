package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cmk03 on 2018/1/25.
 */

public class DynamicDetailsData implements Serializable{

    private ResultCode Message;
    private String DynamicID;
    private String ObjectId;
    private String DynamicerName;
    private String DynamicerHeadportrait;
    private String Title;
    private String Content;
    private String FileUrl;
    private String CoverImg;
    private int FileType;
    private int BrowseCount;
    private String OccupationCode;
    private String FacilitatorId;
    private int ShareCount;
    private int GivethumbCount;
    private int CommentsCount;
    private int State;
    private String CreateTime;
    private int FollowState;
    private List<DataList> Data;

    public String getFacilitatorId() {
        return FacilitatorId;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public String getDynamicID() {
        return DynamicID;
    }

    public String getObjectId() {
        return ObjectId;
    }

    public String getDynamicerName() {
        return DynamicerName;
    }

    public String getDynamicerHeadportrait() {
        return DynamicerHeadportrait;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }

    public String getFileUrl() {
        return FileUrl;
    }

    public String getCoverImg() {
        return CoverImg;
    }

    public int getFileType() {
        return FileType;
    }

    public int getBrowseCount() {
        return BrowseCount;
    }

    public String getOccupationCode() {
        return OccupationCode;
    }

    public int getShareCount() {
        return ShareCount;
    }

    public int getGivethumbCount() {
        return GivethumbCount;
    }

    public int getCommentsCount() {
        return CommentsCount;
    }

    public int getState() {
        return State;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public int getFollowState() {
        return FollowState;
    }

    public void setState(int state) {
        State = state;
    }

    public List<DataList> getData() {
        return Data;
    }

    public static class DataList implements Serializable{
        private String CommentsID;
        private String CommentserName;
        private String CommentsCreateTime;
        private String CommentsContent;
        private String CommentsHeadportrait;
        private String CommentsPeopleId;
        private String Profession;

        public String getCommentsPeopleId() {
            return CommentsPeopleId;
        }

        public String getProfession() {
            return Profession;
        }

        public String getCommentsID() {
            return CommentsID;
        }

        public String getCommentserName() {
            return CommentserName;
        }

        public String getCommentsCreateTime() {
            return CommentsCreateTime;
        }

        public String getCommentsContent() {
            return CommentsContent;
        }

        public String getCommentsHeadportrait() {
            return CommentsHeadportrait;
        }
    }
}
