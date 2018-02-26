package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/8/5.
 */
public class limit_bean implements Serializable {
    private String hmmc;
    private String uuid;

    public String getHmmc() {
        return hmmc;
    }

    public void setHmmc(String hmmc) {
        this.hmmc = hmmc;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
