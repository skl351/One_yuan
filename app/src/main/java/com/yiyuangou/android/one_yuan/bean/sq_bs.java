package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/8/1.
 */
public class sq_bs implements Serializable {
    private String dw;
    private String value;

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
