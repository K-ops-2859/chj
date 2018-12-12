package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2018/8/9.
 */

public class NewUserInfo implements Serializable{
    private ResultCode Message;
    private String UserId;
    private String Name;
    private String Headportrait;
    private String Profession;
    private String Phone;
    private String FacilitatorId;
    private String Address;
    private String Abstract;
    private String Identity;
    private String region;
    private String regionname;
    private int IsMotorcade;//是加入车队 0 yes 1 no
    private int IsNews;//是有邀请信息 0 yes 1 no
    private String CaptainID;
    private String ModelID;
    private String FollowNumber;
    private String FansNumber;
    private String StatusType;
    private String WeChatName;
    private int WeChatType;//是否绑定微信0未绑定1已绑定
    private String Wedding;//是否绑定微信0未绑定1已绑定

    public String getWedding() {
        return Wedding;
    }

    public NewUserInfo() {
    }

    public NewUserInfo(String userId, String name, String headportrait, String profession, String phone, String facilitatorId) {
        UserId = userId;
        Name = name;
        Headportrait = headportrait;
        Profession = profession;
        Phone = phone;
        FacilitatorId = facilitatorId;
    }

    public void setWeChatName(String weChatName) {
        WeChatName = weChatName;
    }

    public void setWeChatType(int weChatType) {
        WeChatType = weChatType;
    }

    public String getWeChatName() {
        return WeChatName;
    }

    public int getWeChatType() {
        return WeChatType;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHeadportrait() {
        return Headportrait;
    }

    public void setHeadportrait(String headportrait) {
        Headportrait = headportrait;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFacilitatorId() {
        return FacilitatorId;
    }

    public void setFacilitatorId(String facilitatorId) {
        FacilitatorId = facilitatorId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAbstract() {
        return Abstract;
    }

    public void setAbstract(String anAbstract) {
        Abstract = anAbstract;
    }

    public String getIdentity() {
        return Identity;
    }

    public void setIdentity(String identity) {
        Identity = identity;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionname() {
        return regionname;
    }

    public void setRegionname(String regionname) {
        this.regionname = regionname;
    }

    public int getIsMotorcade() {
        return IsMotorcade;
    }

    public void setIsMotorcade(int isMotorcade) {
        IsMotorcade = isMotorcade;
    }

    public int getIsNews() {
        return IsNews;
    }

    public void setIsNews(int isNews) {
        IsNews = isNews;
    }

    public String getCaptainID() {
        return CaptainID;
    }

    public void setCaptainID(String captainID) {
        CaptainID = captainID;
    }

    public String getModelID() {
        return ModelID;
    }

    public void setModelID(String modelID) {
        ModelID = modelID;
    }

    public String getFollowNumber() {
        return FollowNumber;
    }

    public void setFollowNumber(String followNumber) {
        FollowNumber = followNumber;
    }

    public String getFansNumber() {
        return FansNumber;
    }

    public void setFansNumber(String fansNumber) {
        FansNumber = fansNumber;
    }

    public String getStatusType() {
        return StatusType;
    }

    public void setStatusType(String statusType) {
        StatusType = statusType;
    }

    @Override
    public String toString() {
        return "NewUserInfo{" +
                "UserId='" + UserId + '\'' +
                ", Name='" + Name + '\'' +
                ", Headportrait='" + Headportrait + '\'' +
                ", Profession='" + Profession + '\'' +
                ", Phone='" + Phone + '\'' +
                ", FacilitatorId='" + FacilitatorId + '\'' +
                '}';
    }
}
