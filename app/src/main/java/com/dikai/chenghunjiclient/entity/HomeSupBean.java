package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/1/16.
 */

public class HomeSupBean {

    private String SupplierID;
    private String UserID;
    private String Name;
    private String ImgUrl;
    private String CaseCount;
    private String StateCount;
    private int IsSign;
    private int IsGifts;
    private String ProfessionID;
    private String RummeryId;
    private String CorpId;
    private String Adress;
    private String Tag;
    private boolean noData;

    public HomeSupBean(boolean noData) {
        this.noData = noData;
    }

    public boolean isNoData() {
        return noData;
    }

    public void setNoData(boolean noData) {
        this.noData = noData;
    }

    public String getRummeryId() {
        return RummeryId;
    }

    public String getCorpId() {
        return CorpId;
    }

    public String getAdress() {
        return Adress;
    }

    public String getTag() {
        return Tag;
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

    public String getImgUrl() {
        return ImgUrl;
    }

    public String getCaseCount() {
        return CaseCount;
    }

    public String getStateCount() {
        return StateCount;
    }

    public int getIsSign() {
        return IsSign;
    }

    public int getIsGifts() {
        return IsGifts;
    }

    public String getProfessionID() {
        return ProfessionID;
    }
}
