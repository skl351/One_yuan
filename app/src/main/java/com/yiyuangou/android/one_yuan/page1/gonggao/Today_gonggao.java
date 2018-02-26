package com.yiyuangou.android.one_yuan.page1.gonggao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONObject;

/**
 * Created by android on 2016/8/4.
 */
public class Today_gonggao extends Activity {

    private TextView gonggao_neirong;
    private View back;
    View gonggao_topbar;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.today_gonggao);
        init();
        init_back();
        init_wangluo();
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
        gonggao_topbar=findViewById(R.id.gonggao_topbar);
       title= (TextView) gonggao_topbar.findViewById(R.id.logreg_center);
       back= gonggao_topbar.findViewById(R.id.logreg_left);
        gonggao_neirong= (TextView) findViewById(R.id.gonggao_neirong);
    }

    private void init_wangluo() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_gonggao;
        RequestParams requestParams=new RequestParams();
        requestParams.put("NUM","1");
        requestParams.put("PAGE","1");
        asyncHttpClient.post(url,requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        System.out.println(jsonObject);
                        title.setText(jsonObject.getJSONArray("list").getJSONObject(0).getString("title"));
                        gonggao_neirong.setText(jsonObject.getJSONArray("list").getJSONObject(0).getString("content"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
//                    Toast.makeText(getActivity(), "网络繁忙，请重试", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
