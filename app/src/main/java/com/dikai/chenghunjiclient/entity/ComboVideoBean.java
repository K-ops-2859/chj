package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by Lucio on 2018/6/14.
 */

public class ComboVideoBean implements Serializable{
    private String Id;
    private String SchemeId;
    private String VideoId;
    private String CoverMap;

    public String getId() {
        return Id;
    }

    public String getSchemeId() {
        return SchemeId;
    }

    public String getVideoId() {
        return VideoId;
    }

    public String getCoverMap() {
        return CoverMap;
    }
}
