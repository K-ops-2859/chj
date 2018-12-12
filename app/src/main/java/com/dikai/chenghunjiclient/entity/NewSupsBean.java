package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2018/8/11.
 */

public class NewSupsBean implements Serializable{
    private String Id;
    private String SupplierIdentity;
    private String UserId;
    private String Name;
    private String Logo;
    private String Phone;
    private String Address;
    private String Abstract;
    private int AnliCount;
    private int StateCount;
    private String Tag;
    private int Hldb;
    private int Xfyl;
    private boolean noData;

    public int getHldb() {
        return Hldb;
    }

    public int getXfyl() {
        return Xfyl;
    }

    public NewSupsBean() {
    }

    public NewSupsBean(boolean noData) {
        this.noData = noData;
    }

    public boolean isNoData() {
        return noData;
    }

    public void setNoData(boolean noData) {
        this.noData = noData;
    }

    public String getId() {
        return Id;
    }

    public String getSupplierIdentity() {
        return SupplierIdentity;
    }

    public String getUserId() {
        return UserId;
    }

    public String getName() {
        return Name;
    }

    public String getLogo() {
        return Logo;
    }

    public String getPhone() {
        return Phone;
    }

    public String getAddress() {
        return Address;
    }

    public String getAbstract() {
        return Abstract;
    }

    public int getAnliCount() {
        return AnliCount;
    }

    public int getStateCount() {
        return StateCount;
    }

    public String getTag() {
        return Tag;
    }
}
