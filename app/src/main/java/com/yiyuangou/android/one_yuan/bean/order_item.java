package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/4/6.
 */
public class order_item implements Serializable {
   private  String name;
    private String all_bean;
    private String pl;
    private String uuid;
    private String SY;

    @Override
    public String toString() {
        return "order_item{" +
                "name='" + name + '\'' +
                ", all_bean='" + all_bean + '\'' +
                ", pl='" + pl + '\'' +
                ", uuid='" + uuid + '\'' +
                ", SY='" + SY + '\'' +
                '}';
    }

    public String getSY() {
        return SY;
    }

    public void setSY(String SY) {
        this.SY = SY;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAll_bean() {
        return all_bean;
    }

    public void setAll_bean(String all_bean) {
        this.all_bean = all_bean;
    }

    public String getPl() {
        return pl;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }
}
