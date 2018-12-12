package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lucio on 2017/9/12.
 */

public class ResultGetSupplierInfo implements Serializable{
    private ResultCode Message;
    private String SupplierID;
    private String ProfessionID;
    private String Region;
    private String BriefinTroduction;
    private String PhoneNo;
    private String Name;
    private String OwnedCompany;
    private String RummeryID;
    private String CorpID;
    private String Adress;
    private String CreateTime;
    private String Headportrait;
    private String Age;
    private String TrueName;
    private String Tag;
    private int IsSign;
    private int IsGifts;
    private int IsCollection;
    private String IsSearch;
    private List<CasesBean> Data;

    public String getTag() {
        return Tag;
    }

    public int getIsSign() {
        return IsSign;
    }

    public int getIsGifts() {
        return IsGifts;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public String getProfessionID() {
        return ProfessionID;
    }

    public String getRegion() {
        return Region;
    }

    public String getBriefinTroduction() {
        return BriefinTroduction;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public String getName() {
        return Name;
    }

    public String getOwnedCompany() {
        return OwnedCompany;
    }

    public String getRummeryID() {
        return RummeryID;
    }

    public String getCorpID() {
        return CorpID;
    }

    public String getAdress() {
        return Adress;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getHeadportrait() {
        return Headportrait;
    }

    public String getAge() {
        return Age;
    }

    public String getTrueName() {
        return TrueName;
    }

    public int getIsCollection() {
        return IsCollection;
    }

    public String getIsSearch() {
        return IsSearch;
    }

    public List<CasesBean> getData() {
        return Data;
    }
}
