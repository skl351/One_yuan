package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/4/9.
 */
public class User_present implements Serializable {

    private String url_img;
    private String SPMC;
    private String SPJG;
    private String uuid;
    private String ok_xia;
    private String shouxufei;

    public String getShouxufei() {
        return shouxufei;
    }

    public void setShouxufei(String shouxufei) {
        this.shouxufei = shouxufei;
    }

    public String getOk_xia() {
        return ok_xia;
    }

    public void setOk_xia(String ok_xia) {
        this.ok_xia = ok_xia;
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

    public String getSPJG() {
        return SPJG;
    }

    public void setSPJG(String SPJG) {
        this.SPJG = SPJG;
    }
}
