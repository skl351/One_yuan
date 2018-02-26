package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by android on 2016/4/7.
 */
public class jingcai_item implements Serializable {
    private String date;
    private String HMMC;
    private String qs;
    private String gmzj;
    private String dazt;
    private String syjd;
    private String grounp_1;
    private String grounp_2;
    private String grounp_3;
    private String grounp_num;
    private String hmpl;
    private String jt_date;
    private String UUID;

    public String getHMMC() {
        return HMMC;
    }

    public void setHMMC(String HMMC) {
        this.HMMC = HMMC;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getJt_date() {
        return jt_date;
    }

    public void setJt_date(String jt_date) {
        this.jt_date = jt_date;
    }

    public String getQs() {
        return qs;
    }

    public void setQs(String qs) {
        this.qs = qs;
    }

    public String getGmzj() {
        return gmzj;
    }

    public void setGmzj(String gmzj) {
        this.gmzj = gmzj;
    }

    public String getDazt() {
        return dazt;
    }

    public void setDazt(String dazt) {
        this.dazt = dazt;
    }

    public String getSyjd() {
        return syjd;
    }

    public void setSyjd(String syjd) {
        this.syjd = syjd;
    }

    public String getGrounp_1() {
        return grounp_1;
    }

    public void setGrounp_1(String grounp_1) {
        this.grounp_1 = grounp_1;
    }

    public String getGrounp_2() {
        return grounp_2;
    }

    public void setGrounp_2(String grounp_2) {
        this.grounp_2 = grounp_2;
    }

    public String getGrounp_3() {
        return grounp_3;
    }

    public void setGrounp_3(String grounp_3) {
        this.grounp_3 = grounp_3;
    }

    public String getGrounp_num() {
        return grounp_num;
    }

    public void setGrounp_num(String grounp_num) {
        this.grounp_num = grounp_num;
    }

    public String getHmpl() {
        return hmpl;
    }

    public void setHmpl(String hmpl) {
        this.hmpl = hmpl;
    }
}
