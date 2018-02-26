package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/4/15.
 */
public class shouhuodizhi implements Serializable{
    private String name;
    private String phone;
    private String place;
    private String place_ssq;
    private String place_xiangxi;
    private String place_provie;
    private String city;
    private String disqu;
    private String id;
    private String statues;

    public String getStatues() {
        return statues;
    }

    public void setStatues(String statues) {
        this.statues = statues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace_provie() {
        return place_provie;
    }

    public void setPlace_provie(String place_provie) {
        this.place_provie = place_provie;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDisqu() {
        return disqu;
    }

    public void setDisqu(String disqu) {
        this.disqu = disqu;
    }

    public String getPlace_ssq() {
        return place_ssq;
    }

    public void setPlace_ssq(String place_ssq) {
        this.place_ssq = place_ssq;
    }

    public String getPlace_xiangxi() {
        return place_xiangxi;
    }

    public void setPlace_xiangxi(String place_xiangxi) {
        this.place_xiangxi = place_xiangxi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
