package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/9/19.
 */

public class BeanReject {
    private String Id;
    private int RefusalType;
    private int DeleteType;

    public BeanReject(String id, int refusalType, int deleteType) {
        Id = id;
        RefusalType = refusalType;
        DeleteType = deleteType;
    }
}
