package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;
import com.yiyuangou.android.one_yuan.util.btn_cls;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by android on 2016/4/11.
 */
public class user_suggestion extends Activity {
    private View suggest_topbar;
    private View back;
    private Button user_suggest_submit;
    private EditText user_suggest_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_suggestion);
        init();
        init_back();
        init_submit();
    }
    private void init_submit() {
        user_suggest_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_cls.bt_disable(user_suggest_submit);
                String suggest = user_suggest_edit.getText().toString();
                System.out.println(suggest);
                if (suggest.isEmpty()) {
                    Toast.makeText(user_suggestion.this, "意见不能为空", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(user_suggest_submit);
                    return;
                }

                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                String url = all_url.url_suggest;
                RequestParams requestParams = new RequestParams();
                requestParams.put("FKNR", suggest);
                requestParams.put("SJLX", "2");
                requestParams.put("userId", User.getuser().getUser_uuid());
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);
                        try {
                            System.out.println(jsonObject);
                            if ("true".equals(jsonObject.getString("status"))) {
                                Toast.makeText(user_suggestion.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(user_suggestion.this, MainActivity.class);
                                startActivity(intent);
                            } else {
//                                Toast.makeText(user_suggestion.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                            btn_cls.bt_endisable(user_suggest_submit);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        super.onFailure(throwable);
                        Toast.makeText(user_suggestion.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
                        btn_cls.bt_endisable(user_suggest_submit);
                    }
                });

            }
        });
    }
    private void init() {
        suggest_topbar=findViewById(R.id.suggest_topbar);
        back=suggest_topbar.findViewById(R.id.logreg_left);
        user_suggest_edit= (EditText) findViewById(R.id.user_suggest_edit);
        user_suggest_submit= (Button) findViewById(R.id.user_suggest_submit);

    }

    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
