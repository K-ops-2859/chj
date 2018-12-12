package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by cmk03 on 2018/10/22.
 */

public class InviteFRData {

    private ResultCode Message;
    private List<InviteFRData.DataList> Data;
    private int iTotalCount;

    public int getiTotalCount() {
        return iTotalCount;
    }

    public List<DataList> getData() {
        return Data;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public static class DataList {
        private String Id;
        private String Name;
        private String Phone;
        private int Ranking;
        private double Money;
        private String MarriagePeriod;
        private String Area;
        private String MakeMoneyNumber;
        private String MealMark;
        private String TableNumber;
        private String Budget;
        private String WeddingCeremony;
        private int AuditStatus;
        private int IsMakeMoney;
        private List<MakeMoneyList> MakeMoneyData;

        public List<MakeMoneyList> getMakeMoneyData() {
            return MakeMoneyData;
        }

        public String getId() {
            return Id;
        }

        public String getName() {
            return Name;
        }

        public String getPhone() {
            return Phone;
        }

        public int getRanking() {
            return Ranking;
        }

        public double getMoney() {
            return Money;
        }

        public String getMarriagePeriod() {
            return MarriagePeriod;
        }

        public String getArea() {
            return Area;
        }

        public String getMakeMoneyNumber() {
            return MakeMoneyNumber;
        }

        public String getMealMark() {
            return MealMark;
        }

        public String getTableNumber() {
            return TableNumber;
        }

        public String getBudget() {
            return Budget;
        }

        public String getWeddingCeremony() {
            return WeddingCeremony;
        }

        public int getAuditStatus() {
            return AuditStatus;
        }

        public int getIsMakeMoney() {
            return IsMakeMoney;
        }

        static class MakeMoneyList {
            private double monry;
            private String Meno;

            public double getMonry() {
                return monry;
            }

            public String getMeno() {
                return Meno;
            }
        }
    }

}
