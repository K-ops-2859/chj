package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/6/14.
 */

public class BeanAddComboComment {
    private String PeopleId;
    private String Content;
    private String Image;
    private String SchemeId;

    public BeanAddComboComment(String peopleId, String content, String image, String schemeId) {
        PeopleId = peopleId;
        Content = content;
        Image = image;
        SchemeId = schemeId;
    }
}
