package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/4/13.
 */
public class play_con implements Serializable {

    private String lianxu;
    private String jd;

    public String getLianxu() {
        return lianxu;
    }

    public void setLianxu(String lianxu) {
        this.lianxu = lianxu;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }
}
