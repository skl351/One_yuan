package com.yiyuangou.android.one_yuan.page1.home_paihangbang;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONObject;

/**
 * Created by android on 2016/5/9.
 */
public class guizhe extends Activity {
    private TextView xiahzu;
    private View back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.paimingguizhe);
        init();
        init_wangluo();
        init_back();


    }

    private  View paihangbang_topbar;
    private void init() {
        paihangbang_topbar=findViewById(R.id.paihangbang_topbar);
        back=paihangbang_topbar.findViewById(R.id.logreg_left);
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
        String url = all_url.url_xiazhubangshuoming;
        RequestParams requestParams = new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
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
