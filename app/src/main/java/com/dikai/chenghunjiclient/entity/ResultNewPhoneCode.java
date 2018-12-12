package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/7/30.
 */

public class ResultNewPhoneCode {
    private ResultCode Message;
    private String UserId;
    private String Name;
    private String Headportrait;
    private String Profession;
    private String Phone;
    private String FacilitatorId;

    public ResultNewPhoneCode() {
    }

    public ResultNewPhoneCode(String userId, String name, String headportrait, String profession, String phone, String facilitatorId) {
        UserId = userId;
        Name = name;
        Headportrait = headportrait;
        Profession = profession;
        Phone = phone;
        FacilitatorId = facilitatorId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setHeadportrait(String headportrait) {
        Headportrait = headportrait;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setFacilitatorId(String facilitatorId) {
        FacilitatorId = facilitatorId;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public String getUserId() {
        return UserId;
    }

    public String getName() {
        return Name;
    }

    public String getHeadportrait() {
        return Headportrait;
    }

    public String getProfession() {
        return Profession;
    }

    public String getPhone() {
        return Phone;
    }

    public String getFacilitatorId() {
        return FacilitatorId;
    }
}
