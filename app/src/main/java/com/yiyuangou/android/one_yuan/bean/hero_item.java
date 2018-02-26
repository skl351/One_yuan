package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/7/13.
 */
public class hero_item implements Serializable {
    private String name;
    private String url;
    private String number;
    private String imgbig;
    private String shuxing;
    private String isBlue;
    private String isAttack;
    private String isHit;

    public String getIsBlue() {
        return isBlue;
    }

    public void setIsBlue(String isBlue) {
        this.isBlue = isBlue;
    }

    public String getIsAttack() {
        return isAttack;
    }

    public void setIsAttack(String isAttack) {
        this.isAttack = isAttack;
    }

    public String getIsHit() {
        return isHit;
    }

    public void setIsHit(String isHit) {
        this.isHit = isHit;
    }

    public String getImgbig() {
        return imgbig;
    }

    public void setImgbig(String imgbig) {
        this.imgbig = imgbig;
    }

    public String getShuxing() {
        return shuxing;
    }

    public void setShuxing(String shuxing) {
        this.shuxing = shuxing;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
