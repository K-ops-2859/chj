package com.dikai.chenghunjiclient.adapter.wedding;
import java.io.Serializable;

/**
 * Created by Lucio on 2017/12/13.
 */

public class SelectBean implements Serializable{
    private String name;
    private boolean isSelected = false;

    public SelectBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
