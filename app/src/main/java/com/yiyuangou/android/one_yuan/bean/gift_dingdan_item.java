package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/4/21.
 */
public class gift_dingdan_item implements Serializable {
    private String img;
    private String name;
    private String status;
    private String jd;
    private String gmsl;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public String getGmsl() {
        return gmsl;
    }

    public void setGmsl(String gmsl) {
        this.gmsl = gmsl;
    }
}
