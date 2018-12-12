package com.dikai.chenghunjiclient.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cmk03 on 2017/12/21.
 */

public class WeddingKnowData implements Serializable {

    private int image;
    private String title;
    private String[] docs;

    public WeddingKnowData(int image, String title, String[] docs) {
        this.image = image;
        this.title = title;
        this.docs = docs;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String[] getDocs() {
        return docs;
    }
}
