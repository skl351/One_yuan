package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.shouhuodizhi;
import com.yiyuangou.android.one_yuan.page4.shouhuodizhi_pack.activity.shouhuo_fffra;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/16.
 */
public class user_shouhuodizhi_xiugai extends Activity {
    private View shouhuodizhi_more;
    private EditText shouhuodizhi_name;
    shouhuodizhi s1;
    private EditText shouhuodizhi_phone;
    private TextView shouhuodizhi_diqu;
    private EditText shouhuodizhi_xiangxi_dizhi;
    private Button shouhuodizhi_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.user_shouhuodizhi_xiugai);
        s1= (shouhuodizhi) getIntent().getSerializableExtra("list_dizhi");
        list.add(s1.getPlace_provie());
        list.add(s1.getCity());
        list.add(s1.getDisqu());
        init();
        init_view();
        init_more();
        init_ok();
        init_back();

        init_zhuru_diqu();

    }

    private void init_ok() {
        shouhuodizhi_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = shouhuodizhi_name.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(user_shouhuodizhi_xiugai.this, "收货人姓名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String phone = shouhuodizhi_phone.getText().toString();
                String phone_zhenz = "^1\\d{10}$";
                if (phone.isEmpty() || !phone.matches(phone_zhenz)) {
                    Toast.makeText(user_shouhuodizhi_xiugai.this, "手机号码输入有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                String diqu = shouhuodizhi_diqu.getText().toString();
                if (diqu.isEmpty()) {
                    Toast.makeText(user_shouhuodizhi_xiugai.this, "省市区不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String xiangxi = shouhuodizhi_xiangxi_dizhi.getText().toString();
                if (xiangxi.isEmpty()) {
                    Toast.makeText(user_shouhuodizhi_xiugai.this, "详细地址不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                init_wangluo(name, phone, diqu, xiangxi);
            }
        });
    }

    private void init_wangluo(String name, String phone, String diqu, String xiangxi) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_updateAddress;
        RequestParams requestParams = new RequestParams();
        requestParams.put("id", s1.getId());
        requestParams.put("address",xiangxi );
        requestParams.put("consignee", name);
        requestParams.put("phone",phone );
        requestParams.put("province", list.get(0));
        requestParams.put("city", list.get(1));
        requestParams.put("district", list.get(2));
        System.out.print(User.getuser().getUser_uuid()+"{}"+xiangxi+"{}"+name+"{}"+phone+"{}"+list.get(0)+list.get(1)+list.get(2));
        asyncHttpClient.setTimeout(20000);
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                System.out.println(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        Toast.makeText(user_shouhuodizhi_xiugai.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(user_shouhuodizhi_xiugai.this,user_shouhuo.class);
                        startActivity(intent);


                    } else {
                        Toast.makeText(user_shouhuodizhi_xiugai.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                Toast.makeText(user_shouhuodizhi_xiugai.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();

            }
        });
    }
    View view;
    List<String> list=new ArrayList<String>();
    private void init_zhuru_diqu() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(user_shouhuodizhi_xiugai.this);
        final AlertDialog alert = builder.create();
        yonglaiclick_diqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (view == null) {
                    view = mInflater.inflate(R.layout.item_content, null);
                }
                Button but = (Button) view.findViewById(R.id.btn_confirm);
                but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!list.isEmpty()){
                            list.clear();
                        }
                        list = shouhuo_fffra.getlist();
                        shouhuodizhi_diqu.setText(list.get(0) + list.get(1) + list.get(2));

                        try {
                            Runtime runtime = Runtime.getRuntime();
                            runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                        } catch (IOException e) {
                            Log.e("Exception when doBack", e.toString());
                        }
                    }
                });
                alert.setView(view, 0, 0, 0, -10);
                alert.show();
            }
        });
    }

    private void init_view() {
        shouhuodizhi_name.setText(s1.getName());
        shouhuodizhi_phone.setText(s1.getPhone());
        shouhuodizhi_diqu.setText(s1.getPlace_ssq());
        shouhuodizhi_xiangxi_dizhi.setText(s1.getPlace_xiangxi());

    }

    boolean flag=false;
    private void init_more() {
        shouhuodizhi_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    shouhuodizhi_more.setSelected(true);
                    flag = true;
                } else {
                    shouhuodizhi_more.setSelected(false);
                    flag=false;
                }
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

    private View yonglaiclick_diqu;
    private LayoutInflater mInflater;
    private View shouhuo_topbar;
    private View back;
    private void init() {
        mInflater=LayoutInflater.from(user_shouhuodizhi_xiugai.this);
        yonglaiclick_diqu = findViewById(R.id.yonglaiclick_diqu);
        shouhuodizhi_save= (Button) findViewById(R.id.shouhuodizhi_save);
        shouhuodizhi_xiangxi_dizhi= (EditText) findViewById(R.id.shouhuodizhi_xiangxi_dizhi);
        shouhuodizhi_diqu= (TextView) findViewById(R.id.shouhuodizhi_diqu);
        shouhuodizhi_phone= (EditText) findViewById(R.id.shouhuodizhi_phone);
        shouhuodizhi_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        shouhuodizhi_name= (EditText) findViewById(R.id.shouhuodizhi_name);
        shouhuodizhi_more=findViewById(R.id.shouhuodizhi_more);
        shouhuo_topbar=findViewById(R.id.shouhuo_topbar);
        back=shouhuo_topbar.findViewById(R.id.logreg_left);

    }
}
