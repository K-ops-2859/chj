package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/10/22.
 */

public class BeanPublishInvitation {
    private String Name;
    private String Phone;
    private String MarriagePeriod;
    private String AreaId;
    private int PredefinedType;
    private int RefereeStatus;
    private String RecommendId;
    private String MealMark;
    private String TableNumber;
    private String Budget;
    private String WeddingCeremony;

    public BeanPublishInvitation(String name, String phone, String marriagePeriod, String areaId,
                                 int predefinedType, int refereeStatus, String recommendId,
                                 String mealMark, String tableNumber, String budget, String weddingCeremony) {
        Name = name;
        Phone = phone;
        MarriagePeriod = marriagePeriod;
        AreaId = areaId;
        PredefinedType = predefinedType;
        RefereeStatus = refereeStatus;
        RecommendId = recommendId;
        MealMark = mealMark;
        TableNumber = tableNumber;
        Budget = budget;
        WeddingCeremony = weddingCeremony;
    }
}
