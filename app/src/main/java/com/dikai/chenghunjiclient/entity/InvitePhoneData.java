package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by cmk03 on 2017/12/14.
 */

public class InvitePhoneData {
    private ResultCode Message;
    private int TotalCount;
    private List<InviteDataList> Data;

    public List<InviteDataList> getData() {
        return Data;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public ResultCode getMessage() {
        return Message;
    }

    public static class InviteDataList {
        private int BeInviteID;
        private long InviteID;
        private String InviteName;
        private String BeinvitedPhone;
        private String BeinvitedName;
        private String CreateTime;

        public int getBeInviteID() {
            return BeInviteID;
        }

        public long getInviteID() {
            return InviteID;
        }

        public String getInviteName() {
            return InviteName;
        }

        public String getBeinvitedPhone() {
            return BeinvitedPhone;
        }

        public String getBeinvitedName() {
            return BeinvitedName;
        }

        public String getCreateTime() {
            return CreateTime;
        }
    }
}
