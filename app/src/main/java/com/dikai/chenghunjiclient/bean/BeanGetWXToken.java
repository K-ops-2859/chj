package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/8/9.
 */

public class BeanGetWXToken {
    private String appid;
    private String secret;
    private String code;
    private String grant_type;

    public BeanGetWXToken(String appid, String secret, String code, String grant_type) {
        this.appid = appid;
        this.secret = secret;
        this.code = code;
        this.grant_type = grant_type;
    }
}
