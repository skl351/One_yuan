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
import android.widget.LinearLayout;
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
import com.yiyuangou.android.one_yuan.util.btn_cls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/1.
 */
public class user_shouhuo extends Activity {
    private View yonglaiclick_diqu;
    private LayoutInflater mInflater;
    private View shouhuo_add_place;
    private View user_shuohuo_bianji;
    private View shouhuodizhi_save;
    private EditText shouhuodizhi_name;
    private EditText shouhuodizhi_phone;
    private TextView shouhuodizhi_diqu;
    private EditText shouhuodizhi_xiangxi_dizhi;
    private LinearLayout shouhuodizhi_scs_line;
    private View back;
    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.user_shouhuo);
        init();
        init_wang_qingqiu();
        init_add();
        init_save();
        init_back();
        init_zhuru_diqu();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private List<shouhuodizhi> list_dizhi;
    private void init_wang_qingqiu() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_getAddress;
        RequestParams requestParams = new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        asyncHttpClient.setTimeout(20000);
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                System.out.println(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        list_dizhi = new ArrayList<shouhuodizhi>();
                        JSONArray address = jsonObject.getJSONArray("address");
                        for (int i = 0; i < address.length(); i++) {
                            shouhuodizhi s1 = new shouhuodizhi();
                            s1.setName(address.getJSONObject(i).getString("consignee"));
                            s1.setPhone(address.getJSONObject(i).getString("phone"));
                            s1.setPlace(address.getJSONObject(i).getString("province") + address.getJSONObject(i).getString("city") + address.getJSONObject(i).getString("district") + address.getJSONObject(i).getString("address"));
                            s1.setPlace_ssq(address.getJSONObject(i).getString("province") + address.getJSONObject(i).getString("city") + address.getJSONObject(i).getString("district"));
                            s1.setPlace_xiangxi(address.getJSONObject(i).getString("address"));
                            s1.setPlace_provie(address.getJSONObject(i).getString("province"));
                            s1.setCity(address.getJSONObject(i).getString("city"));
                            s1.setDisqu(address.getJSONObject(i).getString("district"));
                            s1.setId(address.getJSONObject(i).getString("id"));
                            s1.setStatues(address.getJSONObject(i).getString("status"));
                            list_dizhi.add(s1);
                        }
                        init_intit(list_dizhi);//


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                Toast.makeText(user_shouhuo.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();

            }
        });
    }
    List<Boolean> list_flag=new ArrayList<Boolean>();
    List<View> list_dizhi_view=new ArrayList<View>();
    List<View> list_img_click=new ArrayList<View>();
    public void setkong(){
        for (int i=0;i<list_flag.size();i++){
            list_flag.set(i,false);
            list_img_click.get(i).setSelected(false);
        }
    }
    private void init_intit(final List<shouhuodizhi> list_dizhi) {
        for(int i=0;i<list_dizhi.size();i++){
            list_flag.add(false);
        }
        for(int i=0;i<list_dizhi.size();i++){
           final String id= list_dizhi.get(i).getId();
            final int a=i;
            View view1=mInflater.inflate(R.layout.item_shouhuodizhi_item,null);
           Button delete= (Button) view1.findViewById(R.id.shouhuodizhi_delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init_delete_shouhuo(id);
                }
            });
            View img=view1.findViewById(R.id.shouhuodizhi_more);
            list_img_click.add(img);
            if("1".equals(list_dizhi.get(i).getStatues())){
                img.setSelected(true);
                list_flag.set(i,true);
            }
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!list_flag.get(a)) {
                        setkong();
                        v.setSelected(true);
                        list_flag.set(a, true);
                        btn_cls.bt_disable(v);
                        init_def_shouhuo(id, v);

                    }
                }
            });
           TextView name= (TextView) view1.findViewById(R.id.item_shouhuo_item_name);
            name.setText(list_dizhi.get(i).getName());
            TextView phone= (TextView) view1.findViewById(R.id.item_shouhuo_item_phone);
            phone.setText(list_dizhi.get(i).getPhone());
            TextView dizhi= (TextView) view1.findViewById(R.id.item_shouhuo_item_dizhi);
            dizhi.setText(list_dizhi.get(i).getPlace());
            shouhuodizhi_scs_line.addView(view1);
            list_dizhi_view.add(view1);


        }
        for (int i=0;i<list_dizhi_view.size();i++){
           final int a=i;
            list_dizhi_view.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(user_shouhuo.this, user_shouhuodizhi_xiugai.class);
                    intent.putExtra("list_dizhi", list_dizhi.get(a));
                    startActivity(intent);
                }
            });
        }



    }
    private void init_delete_shouhuo(String id) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_delAddress;
        RequestParams requestParams = new RequestParams();
        requestParams.put("id", id);
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                System.out.println(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        Toast.makeText(user_shouhuo.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(user_shouhuo.this,user_shouhuo.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(user_shouhuo.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);

            }
        });
    }
    private void init_def_shouhuo(String id, final View v) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_setDefault;
        RequestParams requestParams = new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        requestParams.put("id", id);
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                System.out.println(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        Toast.makeText(user_shouhuo.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(user_shouhuo.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }
                    btn_cls.bt_endisable(v);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);

            }
        });
    }

    private void init_save() {
        shouhuodizhi_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=shouhuodizhi_name.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(user_shouhuo.this,"收货人姓名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                String phone=shouhuodizhi_phone.getText().toString();
                String phone_zhenz="^1\\d{10}$";
                if(phone.isEmpty()||!phone.matches(phone_zhenz)){
                    Toast.makeText(user_shouhuo.this, "手机号码输入有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                String diqu=shouhuodizhi_diqu.getText().toString();
                if(diqu.isEmpty()){
                    Toast.makeText(user_shouhuo.this, "省市区不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String xiangxi=shouhuodizhi_xiangxi_dizhi.getText().toString();
                if(xiangxi.isEmpty()){
                    Toast.makeText(user_shouhuo.this, "详细地址不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                init_wangluo(name,phone,diqu,xiangxi);


            }
        });
    }

    private View shouhuodizhi_scs;
    private void init_add() {
        shouhuo_add_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                user_shuohuo_bianji.setVisibility(View.VISIBLE);
                shouhuodizhi_scs.setVisibility(View.GONE);
            }
        });
    }

    private void init_wangluo(String name, String phone, String diqu, String xiangxi) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_addAddress;
        RequestParams requestParams = new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        requestParams.put("address",xiangxi );
        requestParams.put("consignee", name);
        requestParams.put("phone",phone );
        requestParams.put("province", list.get(0));
        requestParams.put("city", list.get(1));
        requestParams.put("district", list.get(2));
        asyncHttpClient.setTimeout(20000);
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
            System.out.println(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        Toast.makeText(user_shouhuo.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(user_shouhuo.this,user_shouhuo.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(user_shouhuo.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                Toast.makeText(user_shouhuo.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();

            }
        });
    }

    View view;

    List<String> list;
    private void init_zhuru_diqu() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(user_shouhuo.this);
        final AlertDialog alert = builder.create();
        yonglaiclick_diqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(view==null){
                    view=mInflater.inflate(R.layout.item_content,null);
                }
                Button but= (Button) view.findViewById(R.id.btn_confirm);
                but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       list= shouhuo_fffra.getlist();
                        shouhuodizhi_diqu.setText(list.get(0)+list.get(1)+list.get(2));

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

    private View shouhuo_topbar;
    private void init() {
        shouhuo_topbar=findViewById(R.id.shouhuo_topbar);
        back=shouhuo_topbar=findViewById(R.id.logreg_left);
        shouhuodizhi_scs=findViewById(R.id.shouhuodizhi_scs);
        shouhuodizhi_scs_line= (LinearLayout) findViewById(R.id.shouhuodizhi_scs_line);
        shouhuodizhi_xiangxi_dizhi= (EditText) findViewById(R.id.shouhuodizhi_xiangxi_dizhi);
        shouhuodizhi_diqu= (TextView) findViewById(R.id.shouhuodizhi_diqu);
        shouhuodizhi_phone= (EditText) findViewById(R.id.shouhuodizhi_phone);
        shouhuodizhi_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        shouhuodizhi_name= (EditText) findViewById(R.id.shouhuodizhi_name);
        shouhuodizhi_save=findViewById(R.id.shouhuodizhi_save);
        user_shuohuo_bianji=findViewById(R.id.user_shuohuo_bianji);
        shouhuo_add_place=findViewById(R.id.shouhuo_add_place);
        mInflater= LayoutInflater.from(user_shouhuo.this);
        yonglaiclick_diqu=findViewById(R.id.yonglaiclick_diqu);
    }
}
