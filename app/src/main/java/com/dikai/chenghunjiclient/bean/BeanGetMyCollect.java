package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2017/9/17.
 */

public class BeanGetMyCollect{
    private String CollectorsType;
    private String CollectorsID;
    private String CollectionType;
    private String ProfessionID;
    private String CollectionTitle;
    private String PageIndex;
    private String PageCount;

    public BeanGetMyCollect(String collectorsType, String collectorsID,
                            String collectionType, String professionID,
                            String collectionTitle, String pageIndex, String pageCount) {
        CollectorsType = collectorsType;
        CollectorsID = collectorsID;
        CollectionType = collectionType;
        ProfessionID = professionID;
        CollectionTitle = collectionTitle;
        PageIndex = pageIndex;
        PageCount = pageCount;
    }
}
