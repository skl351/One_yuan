package com.yiyuangou.android.one_yuan.bean;

/**
 * Created by android on 2016/4/1.
 */
public class open_qs {
    private String KJQS;
    private String group_1;
    private String group_2;
    private String group_3;
    private String group_num;
    private String result_1;
    private String result_2;

    @Override
    public String toString() {
        return "open_qs{" +
                "KJQS='" + KJQS + '\'' +
                ", group_1='" + group_1 + '\'' +
                ", group_2='" + group_2 + '\'' +
                ", group_3='" + group_3 + '\'' +
                ", group_num='" + group_num + '\'' +
                ", result_1='" + result_1 + '\'' +
                ", result_2='" + result_2 + '\'' +
                '}';
    }

    public String getResult_2() {
        return result_2;
    }

    public void setResult_2(String result_2) {
        this.result_2 = result_2;
    }

    public String getResult_1() {
        return result_1;
    }

    public void setResult_1(String result_1) {
        this.result_1 = result_1;
    }

    public String getKJQS() {
        return KJQS;
    }

    public void setKJQS(String KJQS) {
        this.KJQS = KJQS;
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
}
