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
public class user_forgot1 extends Fragment {
    private View forgot_phone;
    private EditText phone;
    private String phone_number;
    private View view;
    private Button fotgot_next;
    private View please_wait;
    private user_forgot2 u2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.user_forgot1, container,false);
        init();
        init_next();
        return view;
    }

    private void init_next() {
        fotgot_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_forgot.keyi=true;
                btn_cls.bt_disable(fotgot_next);
                phone_number=phone.getText().toString();
                if (phone_number.isEmpty()){
                    Toast.makeText(getActivity(), "手机号输入不能为空", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(fotgot_next);
                    return;
                }
                String phone_number_zz = "^1\\d{10}$";
                if (!(phone_number.matches(phone_number_zz))) {
                    Toast.makeText(getActivity(), "手机号输入不正确", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(fotgot_next);
                    return;
                }

                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                String url = all_url.url_forgot;
                RequestParams requestParams = new RequestParams();
                requestParams.put("SJHM", phone_number);
                asyncHttpClient.setTimeout(20000);
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);

                        try {
                            if ("true".equals(jsonObject.getString("status"))) {
                                System.out.println(jsonObject);
                                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                                beginTransaction.addToBackStack(null);
                                Bundle bundle = new Bundle();
                                bundle.putString("phone", phone_number);
                                u2.setArguments(bundle);
                                beginTransaction.replace(R.id.forgot_frame, u2);
                                beginTransaction.commit();
                            } else {
                                Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                            btn_cls.bt_endisable(fotgot_next);
                            please_wait.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        super.onFailure(throwable);
                        Toast.makeText(getActivity(), "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
                        please_wait.setVisibility(View.GONE);
                        btn_cls.bt_endisable(fotgot_next);
                    }
                });




            }
        });
    }

    private void init() {
        please_wait= view.findViewById(R.id.please_wait);
        forgot_phone=view.findViewById(R.id.forgot_phone);
        phone= (EditText) forgot_phone.findViewById(R.id.view_intput_center);
        phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        fotgot_next= (Button) view.findViewById(R.id.fotgot_next);
        u2=new user_forgot2();
    }
}
