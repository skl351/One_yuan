package com.yiyuangou.android.one_yuan;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by android on 2016/7/6.
 */
public class newclkas extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            //随便测试用的，没有什么实质

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
               System.out.println( urlCon("--------"));//你直接运行我这个代码看看
                /*
                handler里面本来就是主线程，你不能在主线程进行耗时操作。你要清楚，handler之所以用来更新主线程，是因为配合threed来实现的，，在run()中你可以调用handler进行一些ui更新，
                 */
                Message message = handler.obtainMessage();
                message.arg1 = 0123;
            }
        });
        thread.start();
    }

    public String urlCon( String input){
        try {
            URL url = new URL(input);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(8000);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.flush();
            connection.getResponseCode();
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            //    System.out.println(response);
            Log.i("返回的数据是 ", response.toString());
            return response.toString();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (ProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 != 0123){
                //你要知道，这个地方就是主线程。
                //不能放一些耗时操作或者ui更新，
            }
        }
    };
}
