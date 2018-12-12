package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/1/25.
 */

public class ResultGetWebPro {
    private ResultCode Message;
    private String PlanID;
    private String PlanTitle;
    private String PlanKeyWord;
    private String ShowImg;
    private String PlanContent;
    private String Color;
    private String Imgs;

    public ResultCode getMessage() {
        return Message;
    }

    public String getPlanID() {
        return PlanID;
    }

    public String getPlanTitle() {
        return PlanTitle;
    }

    public String getPlanKeyWord() {
        return PlanKeyWord;
    }

    public String getShowImg() {
        return ShowImg;
    }

    public String getPlanContent() {
        return PlanContent;
    }

    public String getColor() {
        return Color;
    }

    public String getImgs() {
        return Imgs;
    }
}
