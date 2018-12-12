package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/20.
 */

public class BeanEditCase {
    private String FacilitatorId;
    private String CaseID;
    private String CoverMap;
    private String LogTitle;
    private String LogContent;
    private String Imgs;
    private String VIDeos;

    public BeanEditCase(String facilitatorId, String caseID, String coverMap, String logTitle, String logContent, String imgs, String VIDeos) {
        FacilitatorId = facilitatorId;
        CaseID = caseID;
        CoverMap = coverMap;
        LogTitle = logTitle;
        LogContent = logContent;
        Imgs = imgs;
        this.VIDeos = VIDeos;
    }
}
