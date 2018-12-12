package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2017/9/21.
 */

public class GetSupOrderBean implements Serializable{
    private String SupplierOrderID;
    private String CorpID;
    private String CorpPhone;
    private String CorpName;
    private String CorpLogo;
    private String CorpRummeryID;
    private String CorpRummeryName;
    private String CorpRummeryAddress;
    private String CustomerID;
    private String WeddingDate;
    private String InsidePrice;
    private String ExternalPrice;
    private String SettlementPrice;
    private String AnswerStatus;
    private String Status;
    private String SettlementTime;

    public String getSupplierOrderID() {
        return SupplierOrderID;
    }

    public String getCorpID() {
        return CorpID;
    }

    public String getCorpNamePhone() {
        return CorpPhone;
    }

    public String getCorpName() {
        return CorpName;
    }

    public String getCorpLogo() {
        return CorpLogo;
    }

    public String getCorpRummeryID() {
        return CorpRummeryID;
    }

    public String getCorpRummeryName() {
        return CorpRummeryName;
    }

    public String getCorpRummeryAddress() {
        return CorpRummeryAddress;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public String getWeddingDate() {
        return WeddingDate;
    }

    public String getInsidePrice() {
        return InsidePrice;
    }

    public String getExternalPrice() {
        return ExternalPrice;
    }

    public String getSettlementPrice() {
        return SettlementPrice;
    }

    public String getAnswerStatus() {
        return AnswerStatus;
    }

    public String getStatus() {
        return Status;
    }

    public String getSettlementTime() {
        return SettlementTime;
    }
}
