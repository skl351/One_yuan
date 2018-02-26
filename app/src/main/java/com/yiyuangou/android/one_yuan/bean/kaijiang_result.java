package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by android on 2016/4/8.
 */
public class kaijiang_result implements Serializable {

    private List<String> list_20;
    private String group_1;
    private String group_2;
    private String group_3;
    private String group_num;
    private String result;
    private String qs;

    public String getQs() {
        return qs;
    }

    public void setQs(String qs) {
        this.qs = qs;
    }

    public List<String> getList_20() {
        return list_20;
    }

    public void setList_20(List<String> list_20) {
        this.list_20 = list_20;
    }

    public String getGroup_1() {
        return group_1;
    }

    public void setGroup_1(String group_1) {
        this.group_1 = group_1;
    }

    public String getGroup_2() {
        return group_2;
    }

    public void setGroup_2(String group_2) {
        this.group_2 = group_2;
    }

    public String getGroup_3() {
        return group_3;
    }

    public void setGroup_3(String group_3) {
        this.group_3 = group_3;
    }

    public String getGroup_num() {
        return group_num;
    }

    public void setGroup_num(String group_num) {
        this.group_num = group_num;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
