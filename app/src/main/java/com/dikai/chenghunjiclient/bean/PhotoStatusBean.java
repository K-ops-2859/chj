package com.dikai.chenghunjiclient.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by cmk03 on 2018/3/23.
 */

public class PhotoStatusBean implements Parcelable{
    private long Id;
    private String FileUrl;
    private ArrayList<String> imageUrl = new ArrayList<>();
    private int Type;
    private int Status;
    private String CreateTime;
    private String Reason;
    private String OriginalFileUrl;

    public PhotoStatusBean(long id, String fileUrl, int type, int status, String createTime, String reason, String originalFileUrl) {
        Id = id;
        FileUrl = fileUrl;
        Type = type;
        Status = status;
        CreateTime = createTime;
        Reason = reason;
        OriginalFileUrl = originalFileUrl;
        imageUrl.add(fileUrl);
    }

    protected PhotoStatusBean(Parcel in) {
        Id = in.readLong();
        FileUrl = in.readString();
        Type = in.readInt();
        Status = in.readInt();
        CreateTime = in.readString();
        Reason = in.readString();
        OriginalFileUrl = in.readString();
    }

    public static final Creator<PhotoStatusBean> CREATOR = new Creator<PhotoStatusBean>() {
        @Override
        public PhotoStatusBean createFromParcel(Parcel in) {
            return new PhotoStatusBean(in);
        }

        @Override
        public PhotoStatusBean[] newArray(int size) {
            return new PhotoStatusBean[size];
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

    public String getReason() {
        return Reason;
    }

    public String getOriginalFileUrl() {
        return OriginalFileUrl;
    }

    public ArrayList<String> getImageUrl() {
        return imageUrl;
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
    }
}
