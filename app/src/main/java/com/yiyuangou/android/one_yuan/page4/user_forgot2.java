package com.yiyuangou.android.one_yuan.page4;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.all_url;
import com.yiyuangou.android.one_yuan.util.btn_cls;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by android on 2016/3/22.
 */
public class user_forgot2 extends Fragment {
    private View view;
    private View user_forgot2_text1;
    private View user_forgot2_text2;

    private View forgot2_phone;
    private EditText YZM;
    private Button forgot2_next;
    private Button forgot2_reple;
    private View user_forgot2_118 ;//----时间到消失
    private View user_forgot2_118_dian ;//----时间到出现
    private TextView user_forgot2_time;
    private View please_wait;
    private String YZM_number;
    private user_forgot3 u3;
    private String phone;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.user_forgot2, container,false);
        phone=getArguments().getString("phone");

        init();
        if(user_forgot.keyi){
            user_forgot.keyi=false;
            forgot2_reple.setText("重新发送");
            user_forgot2_text1.setVisibility(View.VISIBLE);
            user_forgot2_text2.setVisibility(View.VISIBLE);
            init_time();
        }

        init_next();
        init_replace();
        return view;
    }
    android.os.Handler handler=new android.os.Handler();
    private int falg2;
    Runnable runnable;
    private void init_time() {

        user_forgot2_118_dian.setVisibility(View.GONE);
        user_forgot2_118.setVisibility(View.VISIBLE);
        falg2=10;
        user_forgot2_time.setText("" + falg2);
        forgot2_reple.setEnabled(false);
        runnable=new Runnable() {
            @Override
            public void run() {
                try {

                    if (falg2 <= 1) {
                        user_forgot2_118.setVisibility(View.GONE);
                        user_forgot2_118_dian.setVisibility(View.VISIBLE);
                        forgot2_reple.setEnabled(true);
                        falg2=10;
                        return;
                    }
                    user_forgot2_time.setText(Integer.toString(--falg2));
                    handler.postDelayed(this, 1000);



                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0); //每隔1s执行
    }
    private void init_replace(){
        forgot2_reple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                String url = all_url.url_forgot;
                RequestParams requestParams = new RequestParams();
                requestParams.put("SJHM", phone);
                asyncHttpClient.setTimeout(20000);
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);
                        try {
                            if ("true".equals(jsonObject.getString("status"))) {
                                System.out.println(jsonObject);
                                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                forgot2_reple.setText("重新发送");
                                user_forgot2_text1.setVisibility(View.VISIBLE);
                                user_forgot2_text2.setVisibility(View.VISIBLE);
                                init_time();

                            } else {
                                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        super.onFailure(throwable);
                        Toast.makeText(getActivity(), "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });
    }

    private void init_next() {
        forgot2_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_cls.bt_disable(forgot2_next);
                YZM_number = YZM.getText().toString();
                if (YZM_number.isEmpty()) {
                    Toast.makeText(getActivity(), "验证码输入不能为空", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(forgot2_next);
                    return;
                }

                please_wait.setVisibility(View.VISIBLE);
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                String url = all_url.url_register_sms_check;
                RequestParams requestParams = new RequestParams();
                requestParams.put("YZM", YZM_number);
                requestParams.put("SJHM", phone);
                requestParams.put("DXLX", "1");
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);

                        try {
                            if("true".equals(jsonObject.getString("status"))){
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                                beginTransaction.addToBackStack(null);
                                Bundle bundle = new Bundle();
                                bundle.putString("phone", phone);
                                bundle.putString("YZM", YZM_number);
                                u3.setArguments(bundle);
                                beginTransaction.replace(R.id.forgot_frame, u3);
                                beginTransaction.commit();
                                handler.removeCallbacks(runnable);
                                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                            btn_cls.bt_endisable(forgot2_next);
                            please_wait.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Throwable throwable, JSONObject jsonObject) {
                        super.onFailure(throwable, jsonObject);
                        Toast.makeText(getActivity(),"网络繁忙，请重试", Toast.LENGTH_SHORT).show();
                        btn_cls.bt_endisable(forgot2_next);
                        please_wait.setVisibility(View.GONE);
                    }
                });


            }
        });
    }
    private void init() {

        forgot2_phone=view.findViewById(R.id.forgot2_phone);
        YZM= (EditText) forgot2_phone.findViewById(R.id.view_intput_center);
        YZM.setInputType(InputType.TYPE_CLASS_NUMBER);
        forgot2_next= (Button) view.findViewById(R.id.forgot2_next);
        forgot2_reple= (Button) view.findViewById(R.id.forgot2_reple);

        user_forgot2_118=view.findViewById(R.id.user_forgot2_118);
        user_forgot2_118_dian=view.findViewById(R.id.user_forgot2_118_dian);
        user_forgot2_time= (TextView) view.findViewById(R.id.user_forgot2_time);
        please_wait=view.findViewById(R.id.please_wait);
        u3=new user_forgot3();
        user_forgot2_text1=view.findViewById(R.id.user_forgot2_text1);
        user_forgot2_text2=view.findViewById(R.id.user_forgot2_text2);

    }
}
