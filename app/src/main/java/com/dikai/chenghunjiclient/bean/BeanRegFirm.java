package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/6/21.
 */

public class BeanRegFirm {
    private String Orgcode;
    private String CorpName;
    private String CorpAlias;
    private String LicenseImg;
    private String Logo;
    private String ManagerName;
    private String UserTel;
    private String AreaID;
    private String Address;
    private String AuthCodeID;
    private String UserName;
    private String UserPwd;
    private String Nickname;
    private String Email;
    private String Phone;
    private String FrontIDcard;
    private String negativeIDcard;
    private String HandheldIDcard;


    public BeanRegFirm(String orgcode, String corpName, String corpAlias,
                       String licenseImg, String logo, String managerName, String userTel,
                       String areaID, String address, String authCodeID, String userName,
                       String userPwd, String nickname, String email, String phone, String frontIDcard,
                       String negativeIDcard, String handheldIDcard) {
        Orgcode = orgcode;
        CorpName = corpName;
        CorpAlias = corpAlias;
        LicenseImg = licenseImg;
        Logo = logo;
        ManagerName = managerName;
        UserTel = userTel;
        AreaID = areaID;
        Address = address;
        AuthCodeID = authCodeID;
        UserName = userName;
        UserPwd = userPwd;
        Nickname = nickname;
        Email = email;
        Phone = phone;
        FrontIDcard = frontIDcard;
        this.negativeIDcard = negativeIDcard;
        HandheldIDcard = handheldIDcard;
    }
}
