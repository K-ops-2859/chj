package com.dikai.chenghunjiclient.util;

import com.dikai.chenghunjiclient.citypicker.City;
import com.dikai.chenghunjiclient.citypicker.Country;
import com.dikai.chenghunjiclient.citypicker.Province;
import com.dikai.chenghunjiclient.entity.CarsBean;
import com.dikai.chenghunjiclient.entity.IdentityBean;
import com.dikai.chenghunjiclient.entity.JiedanRenBean;
import com.dikai.chenghunjiclient.entity.NewSupsBean;
import com.dikai.chenghunjiclient.entity.PersonAddressData;
import com.dikai.chenghunjiclient.entity.ResultGetHotelInfo;
import com.dikai.chenghunjiclient.entity.ResultGetSupplierInfo;
import com.dikai.chenghunjiclient.entity.ResultNewSupInfo;
import com.dikai.chenghunjiclient.tongxunlu.CarBean;

/**
 * Created by Lucio on 2017/5/19.
 */

public class EventBusBean {
    private int type;
    private String data;
    private Country mCountry;
    private IdentityBean mIdentityBean;
    private CarBean mCarBean;
    private ResultGetSupplierInfo mHotelInfo;
    private ResultNewSupInfo mNewSupInfo;
    private int progress;

    private Province mProvince;
    private City mCity;
    private NewSupsBean mSupsBean;
    private JiedanRenBean mJiedanRenBean;

    private PersonAddressData.DataList addressInfo;

    public EventBusBean(int type, NewSupsBean supsBean) {
        this.type = type;
        mSupsBean = supsBean;
    }

    public EventBusBean(int type, JiedanRenBean jiedanRenBean) {
        this.type = type;
        mJiedanRenBean = jiedanRenBean;
    }

    public EventBusBean(int type, PersonAddressData.DataList addressInfo) {
        this.type = type;
        this.addressInfo = addressInfo;
    }

    public EventBusBean(int type, Province province) {
        this.type = type;
        mProvince = province;
    }

    public EventBusBean(int type, City city) {
        this.type = type;
        mCity = city;
    }


    public JiedanRenBean getJiedanRenBean() {
        return mJiedanRenBean;
    }

    public EventBusBean(int type) {
        this.type = type;
    }

    public EventBusBean(int type, int progress) {
        this.type = type;
        this.progress = progress;
    }

    public EventBusBean(int type, String data) {
        this.type = type;
        this.data = data;
    }

    public EventBusBean(int type, Country country) {
        this.type = type;
        mCountry = country;
    }

    public EventBusBean(int type, IdentityBean identityBean) {
        this.type = type;
        mIdentityBean = identityBean;
    }

    public EventBusBean(int type, CarBean carBean) {
        this.type = type;
        mCarBean = carBean;
    }

    public EventBusBean(int type, ResultGetSupplierInfo hotelInfo) {
        this.type = type;
        mHotelInfo = hotelInfo;
    }

    public EventBusBean(int type, ResultNewSupInfo newSupInfo) {
        this.type = type;
        mNewSupInfo = newSupInfo;
    }

    public ResultNewSupInfo getNewSupInfo() {
        return mNewSupInfo;
    }

    public PersonAddressData.DataList getAddressInfo() {
        return addressInfo;
    }

    public int getProgress() {
        return progress;
    }

    public String getData() {
        return data;
    }

    public ResultGetSupplierInfo getHotelInfo() {
        return mHotelInfo;
    }

    public CarBean getCarBean() {
        return mCarBean;
    }

    public int getType() {
        return type;
    }

    public Country getCountry() {
        return mCountry;
    }

    public IdentityBean getIdentityBean() {
        return mIdentityBean;
    }

    public Province getProvince() {
        return mProvince;
    }


    public NewSupsBean getSupsBean() {
        return mSupsBean;
    }

    public City getCity() {
        return mCity;
    }
}
