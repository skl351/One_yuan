package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.all_url;

/**
 * Created by android on 2016/3/25.
 */
public class myself_register_tiaokuan extends Activity {
    private String url= all_url.url_xieyi;
    private WebView myself_tiaokuan;
    private View fuwu_avtbar;
    private View back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.myself_register_tiaokuan);
        init();
        init_back();
    }

    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        myself_tiaokuan= (WebView) findViewById(R.id.myself_tiaokuan);
        fuwu_avtbar=findViewById(R.id.fuwu_avtbar);
        back= fuwu_avtbar.findViewById(R.id.logreg_left);
        myself_tiaokuan.loadUrl(url);
        WebSettings setting = myself_tiaokuan.getSettings();
        setting.setJavaScriptEnabled(true);//支持js
        setting.setDefaultTextEncodingName("utf-8");//设置字符编码
        myself_tiaokuan.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //true 控制网页在webview中打开 false 在浏览器中打开
                view.loadUrl(url);
                return true;
            }
        });


    }
}
