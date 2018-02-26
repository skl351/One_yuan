package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/6/24.
 */
public class tuiguang_huiyuan implements Serializable {

    private String name;
    private String phone;
    private String money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
