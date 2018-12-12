package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lucio on 2018/8/17.
 */

public class ResultGetRoomInfo implements Serializable{
    private ResultCode Message;
    private String BanquetID;
    private String BanquetHallName;
    private String FloorPrice;
    private String MaxTableCount;
    private String MinTableCount;
    private String HotelLogo;
    private String TypeContent;
    private String HotelName;
    private String FacilitatorId;
    private String Acreage;
    private String Length;
    private String Width;
    private String Height;
    private List<ImgBean> Data;

    public List<ImgBean> getData() {
        return Data;
    }

    public String getMinTableCount() {
        return MinTableCount;
    }

    public String getTypeContent() {
        return TypeContent;
    }

    public String getHotelName() {
        return HotelName;
    }

    public String getAcreage() {
        return Acreage;
    }

    public String getLength() {
        return Length;
    }

    public String getWidth() {
        return Width;
    }

    public String getHeight() {
        return Height;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public String getBanquetID() {
        return BanquetID;
    }

    public String getBanquetHallName() {
        return BanquetHallName;
    }

    public String getFloorPrice() {
        return FloorPrice;
    }

    public String getMaxTableCount() {
        return MaxTableCount;
    }

    public String getHotelLogo() {
        return HotelLogo;
    }

    public String getFacilitatorId() {
        return FacilitatorId;
    }
}
