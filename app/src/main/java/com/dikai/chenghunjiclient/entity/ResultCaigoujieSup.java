package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/7/5.
 */

public class ResultCaigoujieSup {
    private ResultCode Message;
    private String SponsorLogo;
    private String SponsorName;
    private String SponsorInfo;
    private String TicketPrice;
    private String SponsorPrice;

    public ResultCode getMessage() {
        return Message;
    }

    public String getSponsorLogo() {
        return SponsorLogo;
    }

    public String getSponsorName() {
        return SponsorName;
    }

    public String getSponsorInfo() {
        return SponsorInfo;
    }

    public String getTicketPrice() {
        return TicketPrice;
    }

    public String getSponsorPrice() {
        return SponsorPrice;
    }
}
