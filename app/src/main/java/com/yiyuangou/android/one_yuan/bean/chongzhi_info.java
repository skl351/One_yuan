package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/4/11.
 */
public class chongzhi_info implements Serializable {

    private String DDHM;
    private String CZJE;
    private String ZFLX;
    private String CZZT;
    private String CZFS;
    private String XDSJ;

    public String getXDSJ() {
        return XDSJ;
    }

    public void setXDSJ(String XDSJ) {
        this.XDSJ = XDSJ;
    }

    public String getDDHM() {
        return DDHM;
    }

    public void setDDHM(String DDHM) {
        this.DDHM = DDHM;
    }

    public String getCZJE() {
        return CZJE;
    }

    public void setCZJE(String CZJE) {
        this.CZJE = CZJE;
    }

    public String getZFLX() {
        return ZFLX;
    }

    public void setZFLX(String ZFLX) {
        this.ZFLX = ZFLX;
    }

    public String getCZZT() {
        return CZZT;
    }

    public void setCZZT(String CZZT) {
        this.CZZT = CZZT;
    }

    public String getCZFS() {
        return CZFS;
    }

    public void setCZFS(String CZFS) {
        this.CZFS = CZFS;
    }
}
