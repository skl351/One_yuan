package com.yiyuangou.android.one_yuan.util;

/**
 * Created by android on 2016/4/1.
 */
public class Util_string {


    public static String quchu_0(String s){
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)!='0'){
                s=s.substring(i,s.length());
                break;
            }
        }
        return s;

    }

    public static  String jiequ(String str,int i,int j){
        String str_1=str;
        if (str.length()!=0){
//            System.out.println(str);
            str_1=str_1.substring(i,j);
            return str_1;
        }
        return "****";

    }
    public static  String jiequ2(String str,int i){
        String str_1=str;
        if (str.length()!=0){
//            System.out.println(str);
            str_1=str_1.substring(i);
            return str_1;
        }
        return "****";

    }
}
