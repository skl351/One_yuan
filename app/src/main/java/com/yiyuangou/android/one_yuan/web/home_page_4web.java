package com.yiyuangou.android.one_yuan.web;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yiyuangou.android.one_yuan.R;

/**
 * Created by android on 2016/3/24.
 */
public class home_page_4web extends Activity {
    private WebView home_page4web;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.home_page4web);
        url= getIntent().getStringExtra("url");
        init();
    }

    private void init() {
        home_page4web= (WebView) findViewById(R.id.home_page4web);
        home_page4web.loadUrl(url);
       WebSettings seting= home_page4web.getSettings();
        seting.setJavaScriptEnabled(true);
        seting.setDefaultTextEncodingName("utf-8");
        home_page4web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
