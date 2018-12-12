package com.dikai.chenghunjiclient.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by cmk03 on 2018/3/20.
 */

public class PhotoDetailsData implements Parcelable {

    private ResultCode Message;

    private int TotalCount;
    private String BrideName;
    private String BridePhone;
    private String GroomName;
    private String GroomPhone;
    private String CorpName;
    private long CorpId;
    private ArrayList<DataList> Data;

    protected PhotoDetailsData(Parcel in) {
        TotalCount = in.readInt();
        BrideName = in.readString();
        BridePhone = in.readString();
        GroomName = in.readString();
        GroomPhone = in.readString();
        CorpName = in.readString();
        CorpId = in.readLong();
        Data = in.createTypedArrayList(DataList.CREATOR);
    }

    public static final Creator<PhotoDetailsData> CREATOR = new Creator<PhotoDetailsData>() {
        @Override
        public PhotoDetailsData createFromParcel(Parcel in) {
            return new PhotoDetailsData(in);
        }

        @Override
        public PhotoDetailsData[] newArray(int size) {
            return new PhotoDetailsData[size];
        }
    };

    public ResultCode getMessage() {
        return Message;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public String getBrideName() {
        return BrideName;
    }

    public String getBridePhone() {
        return BridePhone;
    }

    public String getGroomName() {
        return GroomName;
    }

    public String getGroomPhone() {
        return GroomPhone;
    }

    public String getCorpName() {
        return CorpName;
    }

    public long getCorpId() {
        return CorpId;
    }

    public ArrayList<DataList> getData() {
        return Data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(TotalCount);
        parcel.writeString(BrideName);
        parcel.writeString(BridePhone);
        parcel.writeString(GroomName);
        parcel.writeString(GroomPhone);
        parcel.writeString(CorpName);
        parcel.writeLong(CorpId);
        parcel.writeTypedList(Data);
    }

    public static class DataList implements Parcelable{
        private long Id;
        private String FileUrl;
        private int Type;
        private int Status;
        private String CreateTime;
        private String Reason;
        private String OriginalFileUrl;
        private int position;
        private int count;

        protected DataList(Parcel in) {
            Id = in.readLong();
            FileUrl = in.readString();
            Type = in.readInt();
            Status = in.readInt();
            CreateTime = in.readString();
            Reason = in.readString();
            OriginalFileUrl = in.readString();
            position = in.readInt();
            count = in.readInt();
        }

        public static final Creator<DataList> CREATOR = new Creator<DataList>() {
            @Override
            public DataList createFromParcel(Parcel in) {
                return new DataList(in);
            }

            @Override
            public DataList[] newArray(int size) {
                return new DataList[size];
            }
        };

        public long getId() {
            return Id;
        }

        public String getFileUrl() {
            return FileUrl;
        }

        public int getType() {
            return Type;
        }

        public int getStatus() {
            return Status;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public String getOriginalFileUrl() {
            return OriginalFileUrl;
        }

        public String getReason() {
            return Reason;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(Id);
            parcel.writeString(FileUrl);
            parcel.writeInt(Type);
            parcel.writeInt(Status);
            parcel.writeString(CreateTime);
            parcel.writeString(Reason);
            parcel.writeString(OriginalFileUrl);
            parcel.writeInt(position);
            parcel.writeInt(count);
        }
    }

}
