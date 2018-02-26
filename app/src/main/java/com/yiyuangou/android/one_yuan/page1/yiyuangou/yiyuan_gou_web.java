package com.yiyuangou.android.one_yuan.page1.yiyuangou;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.User;

import java.io.IOException;

/**
 * Created by android on 2016/5/27.
 */
public class yiyuan_gou_web extends Activity {
    WebView webView;
    private View back;
    private View topbat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.yiyuangou_web);
        topbat=findViewById(R.id.oen_buy_topbar);
        back=topbat.findViewById(R.id.logreg_left);
        webView= (WebView) findViewById(R.id.oen_buy_web);
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);//支持js
        setting.setDefaultTextEncodingName("utf-8");//设置字符编码
        webView.loadUrl(getIntent().getStringExtra("html")+"?forward=&userName="+ User.getuser().getUser_phone_number()+"&pwd="+User.getuser().getUser_pwd_number());
        //覆盖默认打开网页的行为，使在webview中显示
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //true 控制网页在webview中打开 false 在浏览器中打开
                view.loadUrl(url);
                return true;
            }
        });
        init_back();
    }
    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
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
}
