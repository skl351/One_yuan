package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/26.
 */
public class kaicai_order_item implements Serializable {
    private String sy1;
    private String sy2;
    private String sy3;
    private String sy4;
    private String sy5;
    private String sy6;
    private String syjd;
    private String total_jd;
    private String xz_time;
    private String show_time;
    private String expect;
    private String kcjd;

    public String getKcjd() {
        return kcjd;
    }

    public void setKcjd(String kcjd) {
        this.kcjd = kcjd;
    }

    public String getShow_time() {
        return show_time;
    }

    public void setShow_time(String show_time) {
        this.show_time = show_time;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getSy1() {
        return sy1;
    }

    public void setSy1(String sy1) {
        this.sy1 = sy1;
    }

    public String getSy2() {
        return sy2;
    }

    public void setSy2(String sy2) {
        this.sy2 = sy2;
    }

    public String getSy3() {
        return sy3;
    }

    public void setSy3(String sy3) {
        this.sy3 = sy3;
    }

    public String getSy4() {
        return sy4;
    }

    public void setSy4(String sy4) {
        this.sy4 = sy4;
    }

    public String getSy5() {
        return sy5;
    }

    public void setSy5(String sy5) {
        this.sy5 = sy5;
    }

    public String getSy6() {
        return sy6;
    }

    public void setSy6(String sy6) {
        this.sy6 = sy6;
    }

    public String getSyjd() {
        return syjd;
    }

    public void setSyjd(String syjd) {
        this.syjd = syjd;
    }

    public String getTotal_jd() {
        return total_jd;
    }

    public void setTotal_jd(String total_jd) {
        this.total_jd = total_jd;
    }


    public String getXz_time() {
        return xz_time;
    }

    public void setXz_time(String xz_time) {
        this.xz_time = xz_time;
    }
}
