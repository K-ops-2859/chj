package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/20.
 */

public class BeanCreateTeam {
    private String UserID;
    private String Logo;
    private String Region;
    private String Adress;
    private String Name;
    private String BriefinTroduction;
    private String OwnedCompany;

    public BeanCreateTeam(String userID, String logo, String region, String adress, String name, String briefinTroduction, String ownedCompany) {
        UserID = userID;
        Logo = logo;
        Region = region;
        Adress = adress;
        Name = name;
        BriefinTroduction = briefinTroduction;
        OwnedCompany = ownedCompany;
    }
}
