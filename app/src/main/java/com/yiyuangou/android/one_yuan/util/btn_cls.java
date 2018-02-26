package com.yiyuangou.android.one_yuan.util;

import android.view.View;

/**
 * Created by android on 2016/3/18.
 */
public class btn_cls {
    public static void  bt_disable(View v){
        v.setEnabled(false);
    }
    public static void  bt_endisable(View v){
        v.setEnabled(true);
    }

    public static  void bt_enselected(View v){
        v.setSelected(true);
    }
    public static  void bt_disselected(View v){
        v.setSelected(false);
    }
}
