package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/8/29.
 */

public class BeanAddADCustom {
    private String Profession;
    private String UserId;
    private String RegionId;
    private String MarriagePeriod;
    private String PsychologicalPrice;
    private String TableNumber;
    private String Remarks;

    public BeanAddADCustom(String profession, String userId, String regionId,
                           String marriagePeriod, String psychologicalPrice, String tableNumber, String remarks) {
        Profession = profession;
        UserId = userId;
        RegionId = regionId;
        MarriagePeriod = marriagePeriod;
        PsychologicalPrice = psychologicalPrice;
        TableNumber = tableNumber;
        Remarks = remarks;
    }
}
