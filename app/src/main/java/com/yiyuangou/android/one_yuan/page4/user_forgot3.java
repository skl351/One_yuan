package com.yiyuangou.android.one_yuan.page4;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by android on 2016/3/22.
 */
public class user_forgot3 extends Fragment {
    private View please_wait;
    private View forgot3_pwd;
    private EditText pwd;
    private View forgot3_relpwd;
    private View forgot3_noke;
    private EditText relpwd;
    private Button forgot3_finsh;
    private View view;
    private String pwd_number;
    private String relpwd_number;
    private String phone;
    private String yzm;
    private String pwd_number_md5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.user_forgot3,container,false);
        phone=getArguments().getString("phone");
        yzm=getArguments().getString("YZM");
        init();
        init_finsh();
        return view;
    }

    private void init_finsh() {
        forgot3_finsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_cls.bt_disable(forgot3_finsh);
                pwd_number=pwd.getText().toString();
                relpwd_number=relpwd.getText().toString();
                pwd_number_md5= MD5Utils.getMD5Str(pwd_number);
                String pwd_zhenz="\\w{5,19}";
                if(pwd_number.isEmpty()){
                    try{
                        Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    btn_cls.bt_endisable(forgot3_finsh);
                    return;
                }
                if (!pwd_number.matches(pwd_zhenz)){
                    btn_cls.bt_endisable(forgot3_finsh);
                    try{
                        Toast.makeText(getActivity(), "密码长度有误", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    return;
                }

                System.out.println(pwd_number+"{}"+relpwd_number);
                if(!pwd_number.equals(relpwd_number)){
                    try{
                        Toast.makeText(getActivity(), "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    btn_cls.bt_endisable(forgot3_finsh);
                    return;
                }
                please_wait.setVisibility(View.VISIBLE);
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                String url = all_url.url_forgot_modify;
                RequestParams requestParams = new RequestParams();
                requestParams.put("SJHM", phone);
                requestParams.put("YZM", yzm);
                requestParams.put("DLMM", pwd_number_md5);
                asyncHttpClient.setTimeout(20000);
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);

                        try {
                            if ("true".equals(jsonObject.getString("status"))) {
                                System.out.println(jsonObject);
                                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                User.getuser().setUser_pwd_number(pwd_number);

                                FileService service=new FileService(getActivity());
                                String user_info=User.getuser().getUser_phone_number()+"-"+User.getuser().getUser_pwd_number()+"-"+User.getuser().getUser_name()+"-"+User.getuser().getUser_uuid()+"-1";
                                service.saveToRom("user.txt", user_info);

                                Intent intent=new Intent(getActivity(),user_forgot4.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                            btn_cls.bt_endisable(forgot3_finsh);
                            please_wait.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        super.onFailure(throwable);
                        Toast.makeText(getActivity(), "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
                        please_wait.setVisibility(View.GONE);
                        btn_cls.bt_endisable(forgot3_finsh);
                    }
                });


            }
        });
    }

    private void init() {
        please_wait=view.findViewById(R.id.please_wait);
        forgot3_pwd=view.findViewById(R.id.forgot3_pwd);
        pwd= (EditText) forgot3_pwd.findViewById(R.id.view_intput_center);
        pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        forgot3_relpwd=view.findViewById(R.id.forgot3_relpwd);
        relpwd= (EditText) forgot3_relpwd.findViewById(R.id.view_intput_center);
        relpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        forgot3_finsh= (Button) view.findViewById(R.id.forgot3_finsh);




    }
}
