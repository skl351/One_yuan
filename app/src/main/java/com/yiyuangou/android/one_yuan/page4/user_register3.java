package com.yiyuangou.android.one_yuan.page4;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
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
import com.yiyuangou.android.one_yuan.MainActivity;
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
public class user_register3 extends Fragment {
    private View please_wait;
    private View register3_pwd;
    private View register3_yqm;
    private EditText pwd;
    private EditText yqr;
    private View register3_relpwd;
    private View register3_noke;
    private EditText noke;
    private String noke_number;
    private EditText relpwd;
    private Button register3_finsh;
    private View view;
    private String pwd_number;
    private String yqr_number;
    private String relpwd_number;
    private String phone;
    private String yzm;
    private String pwd_number_md5;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.user_register3,container,false);
        phone=getArguments().getString("phone");
        yzm=getArguments().getString("YZM");
        init();
        init_finsh();
        return view;
    }

    private void init_finsh() {
        register3_finsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_cls.bt_disable(register3_finsh);
                pwd_number=pwd.getText().toString();
                pwd_number_md5= MD5Utils.getMD5Str(pwd_number);
                relpwd_number=relpwd.getText().toString();
                noke_number= noke.getText().toString();
                yqr_number=yqr.getText().toString();
                String phone_number_zz = "^1\\d{10}$";
                String pwd_zhenz="\\w{5,19}";
                if(noke_number.isEmpty()){
                    Toast.makeText(getActivity(), "昵称不能为空", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(register3_finsh);
                    return;
                }
                if(pwd_number.isEmpty()){
                    Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(register3_finsh);
                    return;
                }
                if (!pwd_number.matches(pwd_zhenz)){
                    btn_cls.bt_endisable(register3_finsh);
                    Toast.makeText(getActivity(), "密码输入长度有误", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!pwd_number.equals(relpwd_number)){
                    Toast.makeText(getActivity(), "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(register3_finsh);

                    return;
                }
                if(!yqr_number.isEmpty()){
                    if (!(yqr_number.matches(phone_number_zz))) {
                        Toast.makeText(getActivity(), "邀请人手机号输入不正确", Toast.LENGTH_SHORT).show();
                        btn_cls.bt_endisable(register3_finsh);
                        return;
                    }
                }

                please_wait.setVisibility(View.VISIBLE);

                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.setTimeout(20000);
                String url = all_url.url_regist;
                RequestParams requestParams = new RequestParams();
                requestParams.put("YZM", yzm);
                requestParams.put("SJLX", "2");
                requestParams.put("SJHM", phone);
                if (!yqr_number.isEmpty()){
                    requestParams.put("YQM", yqr_number);
                }
                requestParams.put("DLMM", pwd_number_md5);
                requestParams.put("YHMC", noke_number);
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);
                        try {
                            if ("true".equals(jsonObject.getString("status"))) {
                                System.out.println(jsonObject);
                                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                TelephonyManager TelephonyMgr = (TelephonyManager)getActivity().getSystemService(getActivity().TELEPHONY_SERVICE);
                                String szImei = TelephonyMgr.getDeviceId();
                                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                asyncHttpClient.setTimeout(20000);
                                String url = all_url.url_login;
                                RequestParams requestParams = new RequestParams();
                                requestParams.put("SJHM", phone);
                                requestParams.put("DLMM", pwd_number_md5);
                                requestParams.put("MAC", szImei);
                                requestParams.put("SJLX", "2");
                                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(JSONObject jsonObject) {
                                        super.onSuccess(jsonObject);
                                        try {
                                            if ("true".equals(jsonObject.getString("status"))) {
                                                System.out.println(jsonObject);
                                                Toast.makeText(getActivity(),jsonObject.getString("result"),Toast.LENGTH_SHORT).show();
                                                User.getuser().setUser_phone_number(jsonObject.getString("SJHM"));
                                                User.getuser().setUser_name(jsonObject.getString("YHMC"));
                                                User.getuser().setUser_pwd_number(pwd_number);
                                                User.getuser().setUser_uuid(jsonObject.getString("userId"));
                                                MainActivity.IS_login = true;
                                                FileService service=new FileService(getActivity());
                                                String user_info=User.getuser().getUser_phone_number()+"-"+User.getuser().getUser_pwd_number()+"-"+User.getuser().getUser_name()+"-"+User.getuser().getUser_uuid()+"-1";
                                                service.saveToRom("user.txt", user_info);
                                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                                startActivity(intent);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Throwable throwable) {
                                        super.onFailure(throwable);
                                        Toast.makeText(getActivity(), "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
                                    }


                                });



                            } else {
                                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                            btn_cls.bt_endisable(register3_finsh);
                            please_wait.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        super.onFailure(throwable);
                        please_wait.setVisibility(View.GONE);
                        btn_cls.bt_endisable(register3_finsh);
                        Toast.makeText(getActivity(), "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    private void init() {
        register3_yqm=view.findViewById(R.id.register3_yqm);
        please_wait=view.findViewById(R.id.please_wait);
        register3_pwd=view.findViewById(R.id.register3_pwd);
        pwd= (EditText) register3_pwd.findViewById(R.id.view_intput_center);
        pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        yqr= (EditText) register3_yqm.findViewById(R.id.view_intput_center);
        register3_relpwd=view.findViewById(R.id.register3_relpwd);
        relpwd= (EditText) register3_relpwd.findViewById(R.id.view_intput_center);
        relpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        register3_finsh= (Button) view.findViewById(R.id.register3_finsh);
        register3_noke=view.findViewById(R.id.register3_noke);
        noke= (EditText) register3_noke.findViewById(R.id.view_intput_center);



    }
}
