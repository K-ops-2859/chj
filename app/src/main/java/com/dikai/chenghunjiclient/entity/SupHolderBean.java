package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/5/22.
 */

public class SupHolderBean {
    private String SupplierID;
    private String UserID;
    private String Name;
    private String OwnedCompany;
    private String BriefinTroduction;
    private String Region;
    private String Adress;
    private String Headportrait;
    private String ProfessionID;
    private String RegionName;
    private int TotalCount;
    private int StateCount;

    public String getRegionName() {
        return RegionName;
    }

    public int getStateCount() {
        return StateCount;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public String getUserID() {
        return UserID;
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

    public int getTotalCount() {
        return TotalCount;
    }
}
