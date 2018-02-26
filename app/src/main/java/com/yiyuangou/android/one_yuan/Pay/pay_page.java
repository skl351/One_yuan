package com.yiyuangou.android.one_yuan.Pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.page1.all_guess.guess;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by android on 2016/4/2.
 */
public class pay_page extends Activity {
    private Button pay_now;
    private String YHDDID;
    private TextView pay_all_jd;
    private String list_objects;
    private int  all_jd;
    private TextView pay_all_jd_reltive;
    private View pay_topbar;
    private String expect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.pay_page);
        YHDDID=getIntent().getStringExtra("YHDDID");
        list_objects= getIntent().getStringExtra("list_objects");
        expect=getIntent().getStringExtra("expect");
        init();
        init_user_info();
        init_text();
        init_ok();
        init_back();
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

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(pay_page.this);
        builder.setTitle("你确定取消支付吗");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(pay_page.this,MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();


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
//                        myself_keyong_je.setText(jsonObject.getJSONObject("user").getString("balance"));//金额
                        pay_all_jd_reltive.setText(jsonObject.getJSONObject("user").getString("commissionPoints"));//金豆
                    } else {
                        Toast.makeText(pay_page.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                Toast.makeText(pay_page.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private TextView except_text;
    private void init_text() {
        pay_all_jd.setText( list_objects);
        except_text.setText(expect);
    }

    private void init_ok() {
        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay_now.setEnabled(false);
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.setTimeout(20000);
                String url = all_url.url_pay;
                RequestParams requestParams = new RequestParams();
                requestParams.put("ZFYHID", User.getuser().getUser_uuid());
                requestParams.put("YHDDID",YHDDID);
                requestParams.put("ZFFS","0");
                requestParams.put("SJLX","2");
                requestParams.put("EXPECT",expect);
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);
                        System.out.println(jsonObject.toString());
                        try {
                            if ("true".equals(jsonObject.getString("status"))) {
                                Intent intent=new Intent(pay_page.this,guess.class);
                                startActivity(intent);
                                pay_now.setEnabled(true);
                                finish();
                                Toast.makeText(pay_page.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            } else {
                                pay_now.setEnabled(true);
                                Toast.makeText(pay_page.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        super.onFailure(throwable);
                        Toast.makeText(pay_page.this, "网络繁忙，求重试", Toast.LENGTH_SHORT).show();
                    }


                });
            }
        });
    }

    private View back;
    private void init() {
        except_text= (TextView) findViewById(R.id.except);
        pay_topbar=findViewById(R.id.pay_topbar);
       back= pay_topbar.findViewById(R.id.logreg_left);
        pay_all_jd_reltive= (TextView) findViewById(R.id.pay_all_jd_reltive);
        pay_all_jd= (TextView) findViewById(R.id.pay_all_jd);
        pay_now= (Button) findViewById(R.id.pay_now);

    }
}
