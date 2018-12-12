package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2017/6/20.
 */

public class BeanFirmInfo implements Serializable{

    private String Orgcode;
    private String CorpName;
    private String CorpAlias;
    private String LicenseImg;
    private String ManagerName;
    private String UserTel;
    private String AreaID;
    private String Address;
    private String AuthCodeID;
    private String UserName;
    private String phone;

    public BeanFirmInfo(String orgcode, String corpName, String corpAlias,
                        String licenseImg, String managerName, String userTel,
                        String areaID, String address, String authCodeID,
                        String userName, String phone) {
        Orgcode = orgcode;
        CorpName = corpName;
        CorpAlias = corpAlias;
        LicenseImg = licenseImg;
        ManagerName = managerName;
        UserTel = userTel;
        AreaID = areaID;
        Address = address;
        AuthCodeID = authCodeID;
        UserName = userName;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getAuthCodeID() {
        return AuthCodeID;
    }

    public String getOrgcode() {
        return Orgcode;
    }

    public String getCorpName() {
        return CorpName;
    }

    public String getCorpAlias() {
        return CorpAlias;
    }

    public String getLicenseImg() {
        return LicenseImg;
    }

    public String getManagerName() {
        return ManagerName;
    }

    public String getUserTel() {
        return UserTel;
    }

    public String getAreaID() {
        return AreaID;
    }

    public String getAddress() {
        return Address;
    }

    public String getUserName() {
        return UserName;
    }
}
