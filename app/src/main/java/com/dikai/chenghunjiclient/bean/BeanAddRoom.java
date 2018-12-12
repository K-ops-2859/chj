package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/18.
 */

public class BeanAddRoom {
    private String BanquetHallName;
    private String FloorPrice;
    private String MaxTableCount;
    private String MinTableCount;
    private String TypeQuestion;
    private String FacilitatorId;
    private String Acreage;
    private String Length;
    private String Width;
    private String Height;

    public BeanAddRoom(String banquetHallName, String floorPrice, String maxTableCount,
                       String minTableCount, String typeQuestion, String facilitatorId,
                       String acreage, String length, String width, String height) {
        BanquetHallName = banquetHallName;
        FloorPrice = floorPrice;
        MaxTableCount = maxTableCount;
        MinTableCount = minTableCount;
        TypeQuestion = typeQuestion;
        FacilitatorId = facilitatorId;
        Acreage = acreage;
        Length = length;
        Width = width;
        Height = height;
    }
}
