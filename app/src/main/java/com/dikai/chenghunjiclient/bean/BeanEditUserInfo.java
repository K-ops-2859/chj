package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/17.
 */

public class BeanEditUserInfo {
    private String UserId;
    private String Name;
    private String Headportrait;
    private String Wedding;

    public BeanEditUserInfo(String userId, String name, String headportrait, String wedding) {
        UserId = userId;
        Name = name;
        Headportrait = headportrait;
        Wedding = wedding;
    }
}
