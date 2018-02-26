package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.FileService;
import com.yiyuangou.android.one_yuan.util.MD5Utils;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;
import com.yiyuangou.android.one_yuan.util.btn_cls;

import org.json.JSONObject;

/**
 * Created by android on 2016/3/21.
 */
public class user_login extends Activity {
    private String phone_number;
    private String pwd_number_md5;
    private String pwd_number;
    private EditText phone;
    private EditText pwd;
    private View login_phone;
    private View login_pwd;
    private TextView login_forget;
    private TextView login_register;
    private Button login_submit;
    private View please_wait;
    private View login_topbar;
    private View back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.user_login);
        init();
        init_submit();
        init_back();
        init_forgot();
        init_register();
    }

    private void init_register() {
        login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(user_login.this,user_register.class);
                startActivity(intent);
            }
        });
    }

    private void init_forgot() {
        login_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(user_login.this,user_forgot.class);
                startActivity(intent);
            }
        });
    }

    private void init_submit() {
        login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btn_cls.bt_disable(login_submit);
                phone_number=phone.getText().toString();
                pwd_number=pwd.getText().toString();
                pwd_number_md5= MD5Utils.getMD5Str(pwd_number);

                if ((phone_number.isEmpty())||(pwd_number.isEmpty())){
                    Toast.makeText(user_login.this, "手机号码或密码不能为空", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(login_submit);
                    return;
                }
                String phone_zhenz="^1\\d{10}$";
//                String pwd_zhenz="\\w{5,19}";

                if (!phone_number.matches(phone_zhenz)){
                    Toast.makeText(user_login.this, "手机号码输入有误", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(login_submit);
                    return;
                }
//                if (!pwd_number.matches(pwd_zhenz)){
//                    Toast.makeText(user_login.this, "密码输入有误", Toast.LENGTH_SHORT).show();
//                    btn_cls.bt_endisable(login_submit);
//                    return;
//                }
                TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
                String szImei = TelephonyMgr.getDeviceId();
                please_wait.setVisibility(View.VISIBLE);
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.setTimeout(20000);
                String url = all_url.url_login;
                RequestParams requestParams = new RequestParams();
                requestParams.put("SJHM", phone_number);
                requestParams.put("DLMM", pwd_number_md5);
                requestParams.put("MAC", szImei);
                requestParams.put("SJLX", "2");
                asyncHttpClient.post(url,requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);
                        try {
                            if ("true".equals(jsonObject.getString("status"))){
                                Toast.makeText(user_login.this,jsonObject.getString("result"),Toast.LENGTH_SHORT).show();
                                System.out.println(jsonObject);
                                User.getuser().setUser_phone_number(jsonObject.getString("SJHM"));
                                User.getuser().setUser_name(jsonObject.getString("YHMC"));
                                User.getuser().setUser_pwd_number(pwd_number);
                                User.getuser().setUser_uuid(jsonObject.getString("userId"));
                                MainActivity.IS_login = true;

                                FileService service=new FileService(user_login.this);
                                String user_info=User.getuser().getUser_phone_number()+"-"+User.getuser().getUser_pwd_number()+"-"+User.getuser().getUser_name()+"-"+User.getuser().getUser_uuid();
                                service.saveToRom("user.txt", user_info);
                                finish();
                            }
                            else{
                                Toast.makeText(user_login.this,jsonObject.getString("result"),Toast.LENGTH_SHORT).show();
                            }
                            btn_cls.bt_endisable(login_submit);
                            please_wait.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        super.onFailure(throwable);
                        please_wait.setVisibility(View.GONE);
                        btn_cls.bt_endisable(login_submit);
                        Toast.makeText(user_login.this,"网络繁忙，请重试",Toast.LENGTH_SHORT).show();
                    }


                });


            }
        });
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
        login_register= (TextView) findViewById(R.id.login_register);
        login_topbar=findViewById(R.id.login_topbar);
        back= login_topbar.findViewById(R.id.logreg_left);
        please_wait=findViewById(R.id.please_wait);
        login_submit= (Button) findViewById(R.id.login_submit);
        login_phone=findViewById(R.id.login_phone);
        phone= (EditText) login_phone.findViewById(R.id.view_intput_center);
        phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        login_pwd=findViewById(R.id.login_pwd);
        pwd= (EditText) login_pwd.findViewById(R.id.view_intput_center);
        pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        login_forget= (TextView) findViewById(R.id.login_forget);

    }
}
