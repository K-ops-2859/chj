package com.dikai.chenghunjiclient.entity;

/**
 * Created by Lucio on 2018/8/31.
 */

public class ServiceModule {
    private int type;
    private int icon;
    private String name;

    public ServiceModule(int type, int icon, String name) {
        this.type = type;
        this.icon = icon;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
