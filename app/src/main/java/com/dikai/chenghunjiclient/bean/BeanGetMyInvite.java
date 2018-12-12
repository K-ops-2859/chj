package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/2/8.
 */

public class BeanGetMyInvite {
    private String SubmittingID;
    private String ObjectTypes;
    private String TerminalTypes;
    private String PageIndex;
    private String PageCount;

    public BeanGetMyInvite(String submittingID, String objectTypes, String terminalTypes, String pageIndex, String pageCount) {
        SubmittingID = submittingID;
        ObjectTypes = objectTypes;
        TerminalTypes = terminalTypes;
        PageIndex = pageIndex;
        PageCount = pageCount;
    }
}
