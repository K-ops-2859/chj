package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by cmk03 on 2018/4/4.
 */

public class GetColorProjectData {
    private ResultCode Message;
    private long TotalCount;
    private List<DataList> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public List<DataList> getData() {
        return Data;
    }

    public long getTotalCount() {
        return TotalCount;
    }

    public static class DataList {
        private String PlanID;
        private String PlanTitle;
        private String PlanKeyWord;
        private String ShowImg;
        private String PlanContent;
        private String Color;
        private String Createtime;

        public String getPlanID() {
            return PlanID;
        }

        public String getPlanTitle() {
            return PlanTitle;
        }

        public String getPlanKeyWord() {
            return PlanKeyWord;
        }

        public String getShowImg() {
            return ShowImg;
        }

        public String getPlanContent() {
            return PlanContent;
        }

        public String getColor() {
            return Color;
        }

        public String getCreatetime() {
            return Createtime;
        }
    }

}
