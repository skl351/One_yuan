package com.yiyuangou.android.one_yuan.bean;

import java.io.Serializable;

/**
 * Created by android on 2016/8/4.
 */
public class talk_bean implements Serializable {

    private String Text;
    private String time;
    private String revetext;
    private String revetime;

    public String getRevetime() {
        return revetime;
    }

    public void setRevetime(String revetime) {
        this.revetime = revetime;
    }

    public String getRevetext() {
        return revetext;
    }

    public void setRevetext(String revetext) {
        this.revetext = revetext;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
