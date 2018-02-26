package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.all_url;
import com.yiyuangou.android.one_yuan.util.btn_cls;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by android on 2016/3/21.
 */
public class user_register1 extends Fragment{
    private View please_wait;
    private user_register2 u2;
    private TextView tiaokuan;
    private View  register_phone;
    private EditText phone;//----------------手机号的编辑框
    private String phone_number;//-------手机号码
    private Button register_next;
    private ImageView register_kuang;
    private  boolean flag_kuang=true;
    private View view;
    private TextView register_tiaokuan;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.user_register1, container,false);

        init();
        init_yes_no_kuang();
        init_next();
        init_tiaokuan();
        return view;
    }

    private void init_tiaokuan() {
        register_tiaokuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),myself_register_tiaokuan.class);
                startActivity(intent);
            }
        });
    }

    private void init_next() {
        register_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag_kuang) {
                    Toast.makeText(getActivity(), "未同意使用条款和隐私政策", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(register_next);
                    return;
                }
                phone_number = phone.getText().toString();
                String phone_number_zz = "^1\\d{10}$";
                if (phone_number.isEmpty()) {
                    Toast.makeText(getActivity(), "手机号输入不能为空", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(register_next);
                    return;
                }
                if (!(phone_number.matches(phone_number_zz))) {
                    Toast.makeText(getActivity(), "手机号输入不正确", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(register_next);
                    return;
                }



                please_wait.setVisibility(View.VISIBLE);

                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                String url = all_url.url_register_check;
                RequestParams requestParams = new RequestParams();
                requestParams.put("SJHM", phone_number);
                asyncHttpClient.setTimeout(20000);
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);

                        try {
                            if("true".equals(jsonObject.getString("status"))){

                                System.out.println(jsonObject);
                                FragmentManager fragmentManager=getFragmentManager();
                                FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
                                beginTransaction.addToBackStack(null);
                                Bundle bundle=new Bundle();
                                bundle.putString("phone",phone_number);
//                                bundle.putString("weiyi","false");
                                u2.setArguments(bundle);
                                beginTransaction.replace(R.id.register_frame, u2);
                                beginTransaction.commit();
                            }else{
                                Toast.makeText(getActivity(),jsonObject.getString("result"),Toast.LENGTH_SHORT).show();
                            }
                            btn_cls.bt_endisable(register_next);
                            please_wait.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        super.onFailure(throwable);
                        Toast.makeText(getActivity(),"网络繁忙，请重试",Toast.LENGTH_SHORT).show();
                        please_wait.setVisibility(View.GONE);
                        btn_cls.bt_endisable(register_next);
                    }
                });

            }
        });



    }

    private void init_yes_no_kuang() {
        register_kuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_kuang=!flag_kuang;
                if(flag_kuang){
                    register_kuang.setImageResource(R.mipmap.register_ok);
                }else{
                    register_kuang.setImageResource(R.mipmap.register_kuang);

                }
            }
        });
    }

    private void init() {
        register_tiaokuan= (TextView) view.findViewById(R.id.register_tiaokuan);
        tiaokuan= (TextView) view.findViewById(R.id.register_tiaokuan);
        tiaokuan.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tiaokuan.getPaint().setFakeBoldText(true);
        register_phone=view.findViewById(R.id.register1_phone);
        phone= (EditText) register_phone.findViewById(R.id.view_intput_center);
        phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        register_next= (Button) view.findViewById(R.id.register1_next);
        register_kuang= (ImageView) view.findViewById(R.id.register_kuang);
        u2=new user_register2();
        please_wait=view.findViewById(R.id.please_wait);
    }

}
