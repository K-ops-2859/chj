package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;

/**
 * Created by cmk03 on 2017/12/21.
 */

public class WeddingKnowDetails implements Serializable {

    private String title;
    private String contentTitle;
    private String content;

    public WeddingKnowDetails(String title, String contentTitle, String content) {
        this.title = title;
        this.contentTitle = contentTitle;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getContentTitle() {
        return contentTitle;
    }
}
