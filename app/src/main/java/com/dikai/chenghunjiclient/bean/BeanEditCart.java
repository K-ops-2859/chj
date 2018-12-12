package com.dikai.chenghunjiclient.bean;

/**
 * Created by Lucio on 2018/5/2.
 */

public class BeanEditCart {
    private String ShoppingCartId;
    private int Count;
    private String PlaceOriginId;
    private String ActivityId;

    public BeanEditCart(String shoppingCartId, int count, String placeOriginId, String activityId) {
        ShoppingCartId = shoppingCartId;
        Count = count;
        PlaceOriginId = placeOriginId;
        ActivityId = activityId;
    }
}
