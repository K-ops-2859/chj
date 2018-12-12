package com.dikai.chenghunjiclient.citypicker;

import java.io.Serializable;

/**
 * Created by Lucio on 2016/7/31.
 */
public class Country implements Serializable {
//    {
//        "areaId": "370102",
//        "areaName": "历下区"
//    }

    private String id;
    private String regionId;
    private String regionName;

    public boolean isLocation;

    public Country() {
    }

    public Country(String id, String regionId, String regionName) {
        this.id = id;
        this.regionId = regionId;
        this.regionName = regionName;
    }

    public Country(boolean isLocation) {
        this.isLocation = isLocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public String toString() {
        return "Country{" +
                "regionName='" + regionName + '\'' +
                '}';
    }
}
