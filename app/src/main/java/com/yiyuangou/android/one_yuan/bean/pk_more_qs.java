package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by android on 2016/7/2.
 */
public class pk_more_qs implements Serializable {
    private  String expect;
    private  List<String> hm_list;

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public List<String> getHm_list() {
        return hm_list;
    }

    public void setHm_list(List<String> hm_list) {
        this.hm_list = hm_list;
    }
}
