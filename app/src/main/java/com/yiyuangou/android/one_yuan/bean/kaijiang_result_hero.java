package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/4/8.
 */
public class kaijiang_result_hero implements Serializable {

    private String name;
    private String code_1;
    private String code_2;
    private String code_3;
    private String code_4;
    private String code_all;
    private String last_code_all;
    private String expect;
    private String result;

    public String getLast_code_all() {
        return last_code_all;
    }

    public void setLast_code_all(String last_code_all) {
        this.last_code_all = last_code_all;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode_1() {
        return code_1;
    }

    public void setCode_1(String code_1) {
        this.code_1 = code_1;
    }

    public String getCode_2() {
        return code_2;
    }

    public void setCode_2(String code_2) {
        this.code_2 = code_2;
    }

    public String getCode_3() {
        return code_3;
    }

    public void setCode_3(String code_3) {
        this.code_3 = code_3;
    }

    public String getCode_4() {
        return code_4;
    }

    public void setCode_4(String code_4) {
        this.code_4 = code_4;
    }

    public String getCode_all() {
        return code_all;
    }

    public void setCode_all(String code_all) {
        this.code_all = code_all;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
