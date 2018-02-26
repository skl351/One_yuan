package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/4/11.
 */
public class dingdan_zengsong_item implements Serializable {

    private String url_img;
    private String SPMC;
    private String name;
    private String other_phone;
    private String order_num;
    private String ZT;
    private String all_jd;
    private String uuid;
    private String sdje;

    public String getSdje() {
        return sdje;
    }

    public void setSdje(String sdje) {
        this.sdje = sdje;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public String getSPMC() {
        return SPMC;
    }

    public void setSPMC(String SPMC) {
        this.SPMC = SPMC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOther_phone() {
        return other_phone;
    }

    public void setOther_phone(String other_phone) {
        this.other_phone = other_phone;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getZT() {
        return ZT;
    }

    public void setZT(String ZT) {
        this.ZT = ZT;
    }

    public String getAll_jd() {
        return all_jd;
    }

    public void setAll_jd(String all_jd) {
        this.all_jd = all_jd;
    }
}
