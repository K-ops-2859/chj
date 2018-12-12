package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2018/9/27.
 */

public class WalletAccountBean implements Serializable{
    private String Number;
    private String Id;
    private String AccountName;
    private int SourceType = 0;
    private String AffiliatedBank;
    private int isValid = 0;

    public WalletAccountBean(String number, String id, String accountName, int sourceType, String affiliatedBank, int isValid) {
        Number = number;
        Id = id;
        AccountName = accountName;
        SourceType = sourceType;
        AffiliatedBank = affiliatedBank;
        this.isValid = isValid;
    }

    public String getId() {
        return Id;
    }

    public String getNumber() {
        return Number;
    }

    public String getAccountName() {
        return AccountName;
    }

    public int getSourceType() {
        return SourceType;
    }

    public String getAffiliatedBank() {
        return AffiliatedBank;
    }

    public int getIsValid() {
        return isValid;
    }
}
