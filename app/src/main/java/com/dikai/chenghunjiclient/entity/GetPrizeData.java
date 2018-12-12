package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by cmk03 on 2017/11/17.
 */

public class GetPrizeData {

    private ResultCode Message;
    private List<GradeData> GradeData;

    public ResultCode getMessage() {
        return Message;
    }

    public List<GetPrizeData.GradeData> getGradeData() {
        return GradeData;
    }

    public static class GradeData {
        private int Grade;
        private List<DagaList> Data;

        public int getGrade() {
            return Grade;
        }

        public List<DagaList> getData() {
            return Data;
        }

        public static class DagaList {


            private int ActivityPrizesID;
            private String CommodityName;
            private String ShowImg;
            private String Country;
            private String MarketPrice;
            private String CreateTime;
            private String Grade;

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

            public String getMarketPrice() {
                return MarketPrice;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public String getGrade() {
                return Grade;
            }
        }
    }
}
