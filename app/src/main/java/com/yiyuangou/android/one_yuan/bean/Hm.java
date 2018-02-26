package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/4/1.
 */
public class Hm implements Serializable{
    private String HMLX ;
    private String HMMC;
    private String HMPL;
    private String uuid;
    private String allBet;
    private String hasBet;
    private String SY;

    public String getAllBet() {
        return allBet;
    }

    public void setAllBet(String allBet) {
        this.allBet = allBet;
    }

    public String getHasBet() {
        return hasBet;
    }

    public void setHasBet(String hasBet) {
        this.hasBet = hasBet;
    }

    public String getSY() {
        return SY;
    }

    public void setSY(String SY) {
        this.SY = SY;
    }

    public String getHMLX() {
        return HMLX;
    }

    public void setHMLX(String HMLX) {
        this.HMLX = HMLX;
    }

    public String getHMMC() {
        return HMMC;
    }

    public void setHMMC(String HMMC) {
        this.HMMC = HMMC;
    }

    public String getHMPL() {
        return HMPL;
    }

    public void setHMPL(String HMPL) {
        this.HMPL = HMPL;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
