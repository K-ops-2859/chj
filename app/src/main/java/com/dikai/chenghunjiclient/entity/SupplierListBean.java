package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2017/9/8.
 */

public class SupplierListBean implements Serializable{
    private String SupplierID;
    private String UserID;
    private String Name;
    private String OwnedCompany;
    private String BriefinTroduction;
    private String Region;
    private String Adress;
    private String Headportrait;
    private String ProfessionID;
    private String TotalCount;


    public String getUserID() {
        return UserID;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public String getName() {
        return Name;
    }

    public String getOwnedCompany() {
        return OwnedCompany;
    }

    public String getBriefinTroduction() {
        return BriefinTroduction;
    }

    public String getRegion() {
        return Region;
    }

    public String getAdress() {
        return Adress;
    }

    public String getHeadportrait() {
        return Headportrait;
    }

    public String getProfessionID() {
        return ProfessionID;
    }

    public String getTotalCount() {
        return TotalCount;
    }
}
