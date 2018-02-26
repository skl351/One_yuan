package com.yiyuangou.android.one_yuan.page2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.all_url;

/**
 * Created by android on 2016/3/17.
 */
public class Share extends Fragment {

    private View view;
    WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.share, container,false);
        webView= (WebView) view.findViewById(R.id.oen_buy_web);
        webView.loadUrl(all_url.url_one_buy);
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);//支持js
        setting.setDefaultTextEncodingName("utf-8");//设置字符编码
        //覆盖默认打开网页的行为，使在webview中显示
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //true 控制网页在webview中打开 false 在浏览器中打开
                view.loadUrl(url);
                return true;
            }
        });
        return view;
    }
}
