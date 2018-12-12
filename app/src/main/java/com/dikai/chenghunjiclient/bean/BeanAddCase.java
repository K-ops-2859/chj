package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/20.
 */

public class BeanAddCase {
    private String FacilitatorId;
    private String CoverMap;
    private String LogTitle;
    private String LogContent;
    private String Imgs;
    private String VIDeos;

    public BeanAddCase(String facilitatorId, String coverMap, String logTitle, String logContent, String imgs, String VIDeos) {
        FacilitatorId = facilitatorId;
        CoverMap = coverMap;
        LogTitle = logTitle;
        LogContent = logContent;
        Imgs = imgs;
        this.VIDeos = VIDeos;
    }
}
