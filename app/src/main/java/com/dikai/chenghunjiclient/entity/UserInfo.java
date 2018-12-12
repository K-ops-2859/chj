package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2017/9/5.
 */

public class UserInfo {
    private ResultCode Message;
    private String UserID;
    private String Phone;
    private String TrueName;
    private String Profession;
    private String Headportrait;
    private String Age;
    private String StatusType;
    private String CorpID;
    private String SupplierID;
    private String ModelID;
    private String RummeryID;
    private String IsRummeryInfo;
    private String CreateTime;
    private String Region;
    private String BriefinTroduction;
    private String Name;
    private String OwnedCompany;
    private String Adress;
    private String IsSearch;
    private String SuppLierCreateTime;
    private int FollowNumber;
    private int FansNumber;

    public int getFansNumber() {
        return FansNumber;
    }

    public int getFollowNumber() {
        return FollowNumber;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public String getUserID() {
        return UserID;
    }

    public String getPhone() {
        return Phone;
    }

    public String getTrueName() {
        return TrueName;
    }

    public String getProfession() {
        return Profession;
    }

    public String getHeadportrait() {
        return Headportrait;
    }

    public String getAge() {
        return Age;
    }

    public String getStatusType() {
        return StatusType;
    }

    public String getCorpID() {
        return CorpID;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public String getModelID() {
        return ModelID;
    }

    public String getRummeryID() {
        return RummeryID;
    }

    public String getIsRummeryInfo() {
        return IsRummeryInfo;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getRegion() {
        return Region;
    }

    public String getBriefinTroduction() {
        return BriefinTroduction;
    }

    public String getName() {
        return Name;
    }

    public String getOwnedCompany() {
        return OwnedCompany;
    }

    public String getAdress() {
        return Adress;
    }

    public String getIsSearch() {
        return IsSearch;
    }

    public String getSuppLierCreateTime() {
        return SuppLierCreateTime;
    }
}
