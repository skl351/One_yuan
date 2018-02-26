package com.yiyuangou.android.one_yuan.util;

import android.content.Context;

import com.yiyuangou.android.one_yuan.bean.user_info;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by android on 2016/3/9.
 */
public class FileService {
    private Context context;
    public FileService(Context mcontext){
        context=mcontext;
    }

    /**
     16      * 把用户名和密码保存到手机ROM
     17      * @param password 输入要保存的密码
     18      * @param username 要保存的用户名
     19      * @param filename 保存到哪个文件
     20      * @return
     21      */
    public boolean saveToRom(String filename,String content) throws Exception{
        FileOutputStream outputStream=context.openFileOutput(filename,Context.MODE_PRIVATE);
        outputStream.write(content.getBytes("utf-8"));
        outputStream.close();
        return true;
    }
    public user_info read(String filename) throws Exception {
        FileInputStream instream=context.openFileInput(filename);
        user_info user_info=new user_info();
        if (instream==null){
            user_info.setUser_phone_number("");
            user_info.setUser_pwd_number("");
            user_info.setUser_name("");
            user_info.setUser_uuid("");
        }else{
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            byte[] buffer=new byte[1024];
            int len=0;
            while((len=instream.read(buffer))!=-1){
                outputStream.write(buffer,0,len);
            }
            byte[] date=outputStream.toByteArray();
            String tt=new String(date,"utf-8");
            String[] gg=tt.split("-");

            user_info.setUser_phone_number(gg[0]);
            user_info.setUser_pwd_number(gg[1]);
            user_info.setUser_name(gg[2]);
            user_info.setUser_uuid(gg[3]);
        }

        return  user_info;
    }
}
