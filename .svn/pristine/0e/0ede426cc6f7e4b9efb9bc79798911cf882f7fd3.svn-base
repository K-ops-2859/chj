package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by cmk03 on 2018/6/13.
 */

public class TaocanPhotoData {

    private ResultCode Message;
    private int ImgCount;
    private List<DataList> Data;

    public ResultCode getMessage() {
        return Message;
    }

    public int getImgCount() {
        return ImgCount;
    }

    public List<DataList> getData() {
        return Data;
    }

    public static class DataList {
        private String AreaId;
        private String AreaName;
        private boolean isTitle = true;
        private List<ImageList> ImageData;

        public String getAreaId() {
            return AreaId;
        }

        public String getAreaName() {
            return AreaName;
        }

        public void setTitle(boolean title) {
            isTitle = title;
        }

        public boolean getTitle() {
            return  isTitle;
        }

        public List<ImageList> getImageData() {
            return ImageData;
        }
    }

    public static class ImageList {
        private String Image;

        public String getImage() {
            return Image;
        }
    }
}
