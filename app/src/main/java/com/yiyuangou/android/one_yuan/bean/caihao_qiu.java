package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/4/6.
 */
public class caihao_qiu implements Serializable {
    private String HMMC;
    private String HMPL;
    private String HMTBH;
    private String UUID;
    private String SY;

    public String getSY() {
        return SY;
    }

    public void setSY(String SY) {
        this.SY = SY;
    }

    public String getHMMC() {
        return HMMC;
    }

    public void setHMMC(String HMMC) {
        this.HMMC = HMMC;
    }

    public String getHMPL() {
        return HMPL;
    }

    public void setHMPL(String HMPL) {
        this.HMPL = HMPL;
    }

    public String getHMTBH() {
        return HMTBH;
    }

    public void setHMTBH(String HMTBH) {
        this.HMTBH = HMTBH;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
