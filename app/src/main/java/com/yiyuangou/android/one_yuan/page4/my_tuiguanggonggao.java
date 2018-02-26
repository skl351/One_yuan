package com.yiyuangou.android.one_yuan.page4;

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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by android on 2016/6/24.
 */
public class my_tuiguanggonggao extends Activity {
    private TextView tuiguang_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.my_tuiguang_view);
        tuiguang_text= (TextView) findViewById(R.id.tuiguang_text);
        init_wangluo();
        findViewById(R.id.tui_back_topbar).findViewById(R.id.logreg_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init_wangluo() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_ruheshouyi;
        RequestParams requestParams = new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        System.out.println(jsonObject);
                        tuiguang_text.setText(jsonObject.getJSONObject("config").getString("PZZ"));
                    } else {

                    }

                } catch (JSONException e) {
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
