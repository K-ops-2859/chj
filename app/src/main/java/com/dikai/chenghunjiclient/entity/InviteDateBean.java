package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/3/7.
 */

public class InviteDateBean {
    private String date;
    private int number;

    public InviteDateBean(String date, int number) {
        this.date = date;
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public int getNumber() {
        return number;
    }
}
