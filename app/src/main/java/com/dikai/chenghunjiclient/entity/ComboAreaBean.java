package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lucio on 2018/6/14.
 */

public class ComboAreaBean implements Serializable{
    private String AreaId;
    private String AreaName;
    private List<AreaImgBean> ImageData;

    public String getAreaId() {
        return AreaId;
    }

    public String getAreaName() {
        return AreaName;
    }

    public List<AreaImgBean> getImageData() {
        return ImageData;
    }
}
