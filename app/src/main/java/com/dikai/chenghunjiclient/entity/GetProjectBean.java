package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2017/7/18.
 */

public class GetProjectBean implements Serializable{
    private String PlanTitle;
    private String PlanKeyWord;
    private String ShowImg;
    private String PlanContent;
    private String Color;
    private String Imgs;

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
