package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/19.
 */

public class BeanEditRoom {
    private String BanquetHallName;
    private String FloorPrice;
    private String MaxTableCount;
    private String MinTableCount;
    private String TypeQuestion;
    private String SupplierID;
    private String BanquetID;
    private String Acreage;
    private String Length;
    private String Width;
    private String Height;

    public BeanEditRoom(String banquetHallName, String floorPrice, String maxTableCount,
                        String minTableCount, String typeQuestion, String supplierID,
                        String banquetID, String acreage, String length, String width, String height) {
        BanquetHallName = banquetHallName;
        FloorPrice = floorPrice;
        MaxTableCount = maxTableCount;
        MinTableCount = minTableCount;
        TypeQuestion = typeQuestion;
        SupplierID = supplierID;
        BanquetID = banquetID;
        Acreage = acreage;
        Length = length;
        Width = width;
        Height = height;
    }
}
