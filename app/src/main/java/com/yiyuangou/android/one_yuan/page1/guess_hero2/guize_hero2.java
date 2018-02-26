package com.yiyuangou.android.one_yuan.page1.guess_hero2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.page1.guess_hreo.guess_hero;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by android on 2016/4/22.
 */
public class guize_hero2 extends Activity {

    private TextView guize_jingcai;
    private View back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.guess_guizhe_hero);
        init();
        init_wangluo();
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

    @Override
    public void onBackPressed() {
        finish();
        Intent intent=new Intent(guize_hero2.this,guess_hero2.class);
        startActivity(intent);
    }
    private void init_wangluo() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_hero2_guizhe;
        asyncHttpClient.post(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        guize_jingcai.setText(jsonObject.getJSONObject("config").getString("PZZ"));

                    }
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

    private View jingcaiguize_topbar;

    private void init() {
        jingcaiguize_topbar=findViewById(R.id.jingcaiguize_topbar);

        back=jingcaiguize_topbar.findViewById(R.id.logreg_left);
        guize_jingcai= (TextView) findViewById(R.id.guize_jingcai);
    }
}
