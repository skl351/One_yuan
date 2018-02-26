package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/4/19.
 */
public class item_gift_send_v implements Serializable{
    private String  name;
    private String price;
    private String info;
    private String gift_info;
    private String IMG;
    private String UUID;
    private String IMg_5;

    public String getGift_info() {
        return gift_info;
    }

    public void setGift_info(String gift_info) {
        this.gift_info = gift_info;
    }

    public String getIMg_5() {
        return IMg_5;
    }

    public void setIMg_5(String IMg_5) {
        this.IMg_5 = IMg_5;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getIMG() {
        return IMG;
    }

    public void setIMG(String IMG) {
        this.IMG = IMG;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
