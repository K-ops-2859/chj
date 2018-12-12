package com.dikai.chenghunjiclient.entity;

import java.util.List;

/**
 * Created by Lucio on 2018/7/5.
 */

public class ResultGetCaigoujieArea {
    private ResultCode Message;
    private List<CaigoujieAreaBean> Data;
    private List<CaigoujieImgBean> ImgData;
    private List<CaigoujieImgBean> EndImgData;

    public ResultCode getMessage() {
        return Message;
    }

    public List<CaigoujieImgBean> getEndImgData() {
        return EndImgData;
    }

    public List<CaigoujieAreaBean> getData() {
        return Data;
    }

    public List<CaigoujieImgBean> getImgData() {
        return ImgData;
    }
}
