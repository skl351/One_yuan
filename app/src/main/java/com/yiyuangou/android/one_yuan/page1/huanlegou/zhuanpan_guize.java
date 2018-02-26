package com.yiyuangou.android.one_yuan.page1.huanlegou;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONObject;

/**
 * Created by android on 2016/5/27.
 */
public class zhuanpan_guize extends Activity {
    private TextView xiahzu;
    private View back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.zhuanpan_guize);
        init();
        init_wangluo();
        init_back();
    }
    private View jiugongge_guize_topbar;
    private void init() {
        jiugongge_guize_topbar=findViewById(R.id.jiugongge_guize_topbar);
        back=jiugongge_guize_topbar.findViewById(R.id.logreg_left);
        xiahzu= (TextView) findViewById(R.id.xiahzu);
    }

    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void init_wangluo() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_zhuanpan_info;
        asyncHttpClient.post(url,  new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    System.out.println(jsonObject);
                    xiahzu.setText(jsonObject.getJSONObject("config").getString("PZZ"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
            }
        });
    }
}
