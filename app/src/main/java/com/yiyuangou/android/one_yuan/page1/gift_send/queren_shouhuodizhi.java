package com.yiyuangou.android.one_yuan.page1.gift_send;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.page4.user_shouhuo;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/20.
 */
public class queren_shouhuodizhi extends Activity {
    private LinearLayout shouhuodizhi_scs_line;
    private View add_adress;
    private View shouhuodizhi_topbar;
    private View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.gift_querenshouhuodizhi);
        init();
        init_add();
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
    private void init_add() {
        add_adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(queren_shouhuodizhi.this,user_shouhuo.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        shouhuodizhi_scs_line.removeAllViews();
        list_panduan=new ArrayList<Boolean>();
        list_id=new ArrayList<String>();
        list_img=new ArrayList<View>();
        init_wangluo();
    }

    List<Boolean> list_panduan=new ArrayList<Boolean>();
    List<String> list_id=new ArrayList<String>();
    List<View> list_img=new ArrayList<View>();
    private void init_wangluo() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_getAddress;
        RequestParams requestParams = new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        asyncHttpClient.setTimeout(20000);
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);

                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        JSONArray jsonArray = jsonObject.getJSONArray("address");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            String sheng = jsonArray.getJSONObject(i).getString("province");
                            String shi = jsonArray.getJSONObject(i).getString("city");
                            String qu = jsonArray.getJSONObject(i).getString("district");
                            String name = jsonArray.getJSONObject(i).getString("consignee");
                            String phone = jsonArray.getJSONObject(i).getString("phone");
                            String xiangxi = jsonArray.getJSONObject(i).getString("address");
                            String id = jsonArray.getJSONObject(i).getString("id");
                            String status = jsonArray.getJSONObject(i).getString("status");
                            View view = LayoutInflater.from(queren_shouhuodizhi.this).inflate(R.layout.item_querenshouhuo_item, null);
                            TextView name_text = (TextView) view.findViewById(R.id.name);
                            TextView phone_text = (TextView) view.findViewById(R.id.phone);
                            TextView dizhi = (TextView) view.findViewById(R.id.dizhi);
                            ImageView img_click = (ImageView) view.findViewById(R.id.img_clicj);
                            if ("1".equals(status)) {
                                img_click.setSelected(true);
                                list_panduan.add(true);
                            } else {
                                list_panduan.add(false);
                            }
                            list_img.add(img_click);
                            name_text.setText(name);
                            phone_text.setText(phone);
                            dizhi.setText(sheng + shi + qu + xiangxi);
                            list_id.add(id);
                            shouhuodizhi_scs_line.addView(view);
                        }
                        for (int i = 0; i < list_id.size(); i++) {
                            final int a = i;
                            list_img.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    for (int i = 0; i < list_img.size(); i++) {
                                        list_panduan.set(i, false);
                                        list_img.get(i).setSelected(false);
                                    }
                                    list_panduan.set(a, true);
                                    v.setSelected(true);
                                }
                            });
                        }
                        init_ok();
                    } else {
                        Toast.makeText(queren_shouhuodizhi.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                Toast.makeText(queren_shouhuodizhi.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void init_ok() {
        lijiduihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = "";
                for (int i = 0; i < list_id.size(); i++) {
                    if (list_panduan.get(i)) {
                        id = list_id.get(i);
                    }
                }
                if (id == "") {
                    Toast.makeText(queren_shouhuodizhi.this, "请确认地址", Toast.LENGTH_SHORT).show();
                    return;
                }


                final AlertDialog.Builder builder = new AlertDialog.Builder(queren_shouhuodizhi.this);
//                builder.setIcon(R.mipmap.ic_launcher);//设置图标
//                builder.setTitle("标题");
                View view = LayoutInflater.from(queren_shouhuodizhi.this).inflate(R.layout.gift_beizhu, null);

                View but_ok = view.findViewById(R.id.zengs_dialo_zhongjian_ok);
                View no_do = view.findViewById(R.id.zengs_dialo_zhongjian_quxiao);
                final EditText beizhu = (EditText) view.findViewById(R.id.beizhu);



                no_do.setOnClickListener(new View.OnClickListener() {
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
                final AlertDialog alert = builder.create();
                alert.setView(view, 0, 0, 0, -10);
                alert.show();
                but_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text_beizhu = beizhu.getText().toString();
                        submit(text_beizhu);
                        alert.dismiss();

                    }
                });


            }
        });
    }

    private void submit(String text_beizhu) {
        String id = "";
        for (int i = 0; i < list_id.size(); i++) {
            if (list_panduan.get(i)) {
                id = list_id.get(i);
            }
        }
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_gift_duihuan;
        RequestParams requestParams = new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        requestParams.put("giftId", getIntent().getStringExtra("UUID"));
        requestParams.put("id", id);
        requestParams.put("message", text_beizhu);
        asyncHttpClient.setTimeout(20000);
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);

                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        Toast.makeText(queren_shouhuodizhi.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(queren_shouhuodizhi.this, gift_send_activity.class);
                        startActivity(intent);

                    } else {
                        tanchuang(jsonObject.getString("result"));
//                        Toast.makeText(queren_shouhuodizhi.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                Toast.makeText(queren_shouhuodizhi.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void tanchuang(String result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(queren_shouhuodizhi.this);
//                builder.setIcon(R.mipmap.ic_launcher);//设置图标
//                builder.setTitle("标题");
        View view= LayoutInflater.from(queren_shouhuodizhi.this).inflate(R.layout.gift_tanchuang, null);
        View but_ok= view.findViewById(R.id.zengs_dialo_zhongjian_ok);
       TextView beizhu= (TextView) view.findViewById(R.id.beizhu);
        beizhu.setText(result);
        but_ok.setOnClickListener(new View.OnClickListener() {
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

        final AlertDialog alert = builder.create();
        alert.setView(view, 0, 0, 0, -10);
        alert.show();
    }


    private TextView lijiduihuan;
    private void init() {
        shouhuodizhi_topbar=findViewById(R.id.shouhuodizhi_topbar);
        back=shouhuodizhi_topbar.findViewById(R.id.logreg_left);
        add_adress=findViewById(R.id.add_adress);
        lijiduihuan= (TextView) findViewById(R.id.lijiduihuan);
        shouhuodizhi_scs_line= (LinearLayout) findViewById(R.id.shouhuodizhi_scs_line);

    }
}
