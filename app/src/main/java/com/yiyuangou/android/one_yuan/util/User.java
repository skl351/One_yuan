package com.yiyuangou.android.one_yuan.util;

import com.yiyuangou.android.one_yuan.bean.user_info;

/**
 * Created by android on 2016/3/25.
 */
public class User {
    public static boolean is_first=false;
    private static user_info user=new user_info();
    public static String city;
    public static boolean city_flag=false;


    public static  user_info getuser(){
        return user;
    }
}
