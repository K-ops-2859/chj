package com.dikai.chenghunjiclient.entity;


import java.util.List;

/**
 * Created by cmk03 on 2017/12/7.
 */

public class ActivityRuleBean extends DataTreeEntity{
    private ResultCode Message;
    private List<GradeDataList> Data;

    public ActivityRuleBean(Object key, List value) {
        super(key, value);
    }

    public List<GradeDataList> getGradeData() {
        return Data;
    }

    public ResultCode getMessage() {
        return Message;
    }


    public static class GradeDataList {
        private String Title;
        private String[] Rule;

        public String[] getRule() {
            return Rule;
        }

        public String getTitle() {
            return Title;
        }
    }
}
