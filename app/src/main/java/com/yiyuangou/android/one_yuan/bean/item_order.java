package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by android on 2016/4/19.
 */
public class item_order implements Serializable {
    private String date;
    private List<jingcai_item> qs_s;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<jingcai_item> getQs_s() {
        return qs_s;
    }

    public void setQs_s(List<jingcai_item> qs_s) {
        this.qs_s = qs_s;
    }
}
