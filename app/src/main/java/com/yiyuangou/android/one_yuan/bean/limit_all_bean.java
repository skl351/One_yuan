package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by android on 2016/8/5.
 */
public class limit_all_bean implements Serializable {
    private String limitNum;
    private List<limit_bean> list;

    public String getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(String limitNum) {
        this.limitNum = limitNum;
    }

    public List<limit_bean> getList() {
        return list;
    }

    public void setList(List<limit_bean> list) {
        this.list = list;
    }
}
