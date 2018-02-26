package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.FileService;
import com.yiyuangou.android.one_yuan.util.MD5Utils;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;
import com.yiyuangou.android.one_yuan.util.btn_cls;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by android on 2016/3/26.
 */
public class user_modify extends Activity {
    private View user_modify_actbar;
    private View back;
    private View modify_pwd;
    private View modify_newpwd;
    private View modify_repwd;
    private Button modify_next;
    private EditText pwd;
    private EditText newpwd;
    private EditText repwd;
    private String pwd_number;
    private String newpwd_number;
    private String repwd_number;
    private View please_wait;
    private String newpwd_number_md5;
    private String pwd_number_md5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.user_modify);
        init();
        init_back();
        init_finsh();
    }

    private void init_finsh() {

        modify_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwd_number=pwd.getText().toString();
                newpwd_number=newpwd.getText().toString();
                repwd_number=repwd.getText().toString();
                newpwd_number_md5= MD5Utils.getMD5Str(newpwd_number);
                pwd_number_md5= MD5Utils.getMD5Str(pwd_number);
                btn_cls.bt_disable(modify_next);
                String pwd_zhenz="\\w{5,19}";
                if(pwd_number.isEmpty()){
                    Toast.makeText(user_modify.this, "旧密码不能为空", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(modify_next);
                    return;
                }
                if(newpwd_number.isEmpty()){
                    Toast.makeText(user_modify.this, "新密码不能为空", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(modify_next);
                    return;
                }
                if (!newpwd_number.matches(pwd_zhenz)){
                    btn_cls.bt_endisable(modify_next);
                    Toast.makeText(user_modify.this, "密码输入长度有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(repwd_number.isEmpty()){
                    Toast.makeText(user_modify.this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(modify_next);
                    return;
                }
                if(!newpwd_number.equals(repwd_number)){
                    Toast.makeText(user_modify.this, "确认密码输入不一致", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(modify_next);
                    return;
                }

                please_wait.setVisibility(View.VISIBLE);

                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                String url = all_url.url_modify;
                RequestParams requestParams = new RequestParams();
                requestParams.put("userId", User.getuser().getUser_uuid());
                requestParams.put("XDLMM", newpwd_number_md5);
                requestParams.put("DLMM", pwd_number_md5);
                asyncHttpClient.setTimeout(20000);
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);

                        try {
                            if ("true".equals(jsonObject.getString("status"))) {
                                User.getuser().setUser_pwd_number(newpwd_number);
                                FileService service=new FileService(user_modify.this);
                                String user_info=User.getuser().getUser_phone_number()+"-"+User.getuser().getUser_pwd_number()+"-"+User.getuser().getUser_name()+"-"+User.getuser().getUser_uuid()+"-1";
                                service.saveToRom("user.txt", user_info);
                                Toast.makeText(user_modify.this,jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(user_modify.this,jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                            btn_cls.bt_endisable(modify_next);
                            please_wait.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        super.onFailure(throwable);
                        Toast.makeText(user_modify.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
                        please_wait.setVisibility(View.GONE);
                        btn_cls.bt_endisable(modify_next);
                    }
                });



            }
        });
    }

    private void init() {
        please_wait=findViewById(R.id.please_wait);
        user_modify_actbar=findViewById(R.id.user_modify_actbar);
        back=  user_modify_actbar.findViewById(R.id.logreg_left);
        modify_pwd=findViewById(R.id.modify_pwd);
        pwd= (EditText) modify_pwd.findViewById(R.id.view_intput_center);
        pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        modify_newpwd=findViewById(R.id.modify_newpwd);
        newpwd= (EditText) modify_newpwd.findViewById(R.id.view_intput_center);
        newpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        modify_repwd=findViewById(R.id.modify_repwd);
        repwd= (EditText) modify_repwd.findViewById(R.id.view_intput_center);
        repwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        modify_next= (Button) findViewById(R.id.modify_next);
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
}
