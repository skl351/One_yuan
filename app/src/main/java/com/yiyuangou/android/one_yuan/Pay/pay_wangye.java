package com.yiyuangou.android.one_yuan.Pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.all_url;

import java.io.IOException;

/**
 * Created by android on 2016/4/7.
 */
public class pay_wangye extends Activity {

    private  View pay_yemian;
    private WebView zhifuwangye_web;
    private View zhifuwangye_back;
    private String ddbh;
    private String zfddid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.pay_wangye);
        ddbh=getIntent().getStringExtra("ddbh");
        zfddid= getIntent().getStringExtra("zfddid");
        System.out.println(ddbh+"---"+zfddid);
        init();
        zhifuwangye_web.loadUrl(all_url.url_jingdongwangying+"?DDBH="+ddbh+"&ZFDDID="+zfddid);
        WebSettings setting = zhifuwangye_web.getSettings();
        setting.setJavaScriptEnabled(true);//支持js
        setting.setDefaultTextEncodingName("utf-8");//设置字符编码


        //覆盖默认打开网页的行为，使在webview中显示
        zhifuwangye_web.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //true 控制网页在webview中打开 false 在浏览器中打开
                view.loadUrl(url);
                return true;
            }
        });
        init_back();
    }
    private void init() {
        pay_yemian=findViewById(R.id.pay_yemian);
        zhifuwangye_back=pay_yemian.findViewById(R.id.logreg_left);
        zhifuwangye_web= (WebView) findViewById(R.id.zhifuwangye_web);
    }
    private void init_back() {
        zhifuwangye_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                } catch (IOException e) {
                    Log.e("Exception when doBack", e.toString());
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(pay_wangye.this, MainActivity.class);
        startActivity(intent);

    }
}
