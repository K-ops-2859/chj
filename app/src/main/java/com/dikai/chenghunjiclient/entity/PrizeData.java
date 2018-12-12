package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by cmk03 on 2017/12/7.
 */

public class PrizeData {
    private ResultCode Message;
    private int TotalCount;
    private List<PrizeDataList> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public List<PrizeDataList> getData() {
        return Data;
    }

    public static class PrizeDataList {
        private int ActivityPrizesID;
        private String CommodityName;
        private String ShowImg;
        private String Country;
        private int MarketPrice;
        private String CreateTime;

        public int getActivityPrizesID() {
            return ActivityPrizesID;
        }

        public String getCommodityName() {
            return CommodityName;
        }

        public String getShowImg() {
            return ShowImg;
        }

        public String getCountry() {
            return Country;
        }

        public int getMarketPrice() {
            return MarketPrice;
        }

        public String getCreateTime() {
            return CreateTime;
        }
    }
}
