package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/4/9.
 */
public class jingcai_item_info implements Serializable {
    private String buy_hm;
    private String shifouzhongjiang;
    private String pjd;
    private String pl;
    private String kjrq;

    public String getKjrq() {
        return kjrq;
    }

    public void setKjrq(String kjrq) {
        this.kjrq = kjrq;
    }

    public String getBuy_hm() {
        return buy_hm;
    }

    public void setBuy_hm(String buy_hm) {
        this.buy_hm = buy_hm;
    }

    public String getShifouzhongjiang() {
        return shifouzhongjiang;
    }

    public void setShifouzhongjiang(String shifouzhongjiang) {
        this.shifouzhongjiang = shifouzhongjiang;
    }

    public String getPjd() {
        return pjd;
    }

    public void setPjd(String pjd) {
        this.pjd = pjd;
    }

    public String getPl() {
        return pl;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }
}
