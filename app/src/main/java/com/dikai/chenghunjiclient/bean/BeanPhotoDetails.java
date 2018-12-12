package com.dikai.chenghunjiclient.bean;

/**
 * Created by cmk03 on 2018/3/20.
 */

public class BeanPhotoDetails {
    private String UserId;
    private String CustomerId;
    private String Status;
    private String UploadType;
    private String Phone;
    private String CorpName;
    private String PageIndex;
    private String PageCount;

    public BeanPhotoDetails(String userId, String customerId, String status, String uploadType, String phone, String corpName, String pageIndex, String pageCount) {
        UserId = userId;
        CustomerId = customerId;
        Status = status;
        UploadType = uploadType;
        Phone = phone;
        CorpName = corpName;
        PageIndex = pageIndex;
        PageCount = pageCount;
    }
}
