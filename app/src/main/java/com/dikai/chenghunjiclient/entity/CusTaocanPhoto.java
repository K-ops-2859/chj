package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by cmk03 on 2018/6/14.
 */

public class CusTaocanPhoto {

    private boolean title;
    private String AreaId;
    private String AreaName;
    private String Image;
    private int titleNum;

    public CusTaocanPhoto(boolean title, String areaId, String areaName, int titleNum) {
        AreaId = areaId;
        AreaName = areaName;
        this.title = title;
        this.titleNum = titleNum;
    }

    public CusTaocanPhoto(String image) {
        Image = image;
    }


    public boolean isTitle() {
        return title;
    }

    public String getAreaId() {
        return AreaId;
    }

    public String getAreaName() {
        return AreaName;
    }

    public String getImage() {
        return Image;
    }

    public int getTitleNum() {
        return titleNum;
    }
}
