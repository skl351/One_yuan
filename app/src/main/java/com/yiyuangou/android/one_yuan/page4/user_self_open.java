package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by android on 2016/4/25.
 */
public class user_self_open extends Activity {
    private View touxiang;
    private ImageView myself_user_img;
    private TextView name;
    private TextView phone;
    private View back;
    private View erweima_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.user_self_open);
        init();
        init_touxiang();//拍照
        init_user_info();
        init_text();
        init_back();
        init_erweima();//二维码

    }

    private void init_erweima() {

        erweima_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(user_self_open.this,CreateActivity.class);
                startActivity(intent);
            }
        });
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

    private void init_text() {
        name.setText(User.getuser().getUser_name());
        phone.setText(User.getuser().getUser_phone_number());
    }

    private void init_touxiang() {
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(user_self_open.this, user_touxiang_cunfang.class);
                startActivity(intent);
            }
        });
    }

    private View center_topbar;
    private void init() {
        erweima_click=findViewById(R.id.erweima_click);
        center_topbar=findViewById(R.id.center_topbar);
        back=center_topbar.findViewById(R.id.logreg_left);
        phone= (TextView) findViewById(R.id.phone);
        name= (TextView) findViewById(R.id.name);
        touxiang=findViewById(R.id.touxiang);
        myself_user_img= (ImageView) findViewById(R.id.myself_user_img);
    }

    private void init_user_info() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_user_info;
        RequestParams requestParams = new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        System.out.println(jsonObject);
                        if(jsonObject.getJSONObject("user").has("faceImg")){
                            ImageLoader.getInstance().displayImage(jsonObject.getJSONObject("user").getString("faceImg"),myself_user_img);
                        }
                    } else {
                        Toast.makeText(user_self_open.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
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
