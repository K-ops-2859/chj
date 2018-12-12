package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2017/9/12.
 */

public class DocumentBean {
    private String AssFileID;
    private String FileID;
    private String FileTitle;
    private String FileDescription;
    private String FileSize;
    private String UploadUserID;
    private String Remarks;
    private int ObjectTypes;
    private String CreateTime;

    public String getAssFileID() {
        return AssFileID;
    }

    public String getFileID() {
        return FileID;
    }

    public String getFileTitle() {
        return FileTitle;
    }

    public String getFileDescription() {
        return FileDescription;
    }

    public String getFileSize() {
        return FileSize;
    }

    public String getUploadUserID() {
        return UploadUserID;
    }

    public String getRemarks() {
        return Remarks;
    }

    public int getObjectTypes() {
        return ObjectTypes;
    }

    public String getCreateTime() {
        return CreateTime;
    }
}
