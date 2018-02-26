package com.yiyuangou.android.one_yuan.page1.pk_ten;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.Pay.pay_page2;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.Hm;
import com.yiyuangou.android.one_yuan.bean.hm_key_value;
import com.yiyuangou.android.one_yuan.bean.order_item;
import com.yiyuangou.android.one_yuan.util.DensityUtil;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/5.
 */
public class jincai_order2 extends Activity {
    private View back;
    private View guess_oeder;
    private TextView guess_order_qs;
    private TextView guess_order_bs;
    private Button guess_order_ok;
    private String object;
    private View please_wait;
    private LinearLayout list_guess_order;
    private List<order_item> list_objects;
    private List<ImageView> list_delete=new ArrayList<ImageView>();
    private JSONObject jsonObject_new;
    private List<Boolean> list_delete_panduan=new ArrayList<Boolean>();
    private TextView list_xiazhu_number;
    private TextView list_xiazhu_jd_number;
    private List<hm_key_value> list_hm_key;
    private List<Hm> list_Hm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.jincai_order_pk);
        object= getIntent().getStringExtra("object");

        try {
            jsonObject_new=new JSONObject(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        list_objects= (List<order_item>) getIntent().getSerializableExtra("list_objects");
        list_Hm= (List<Hm>) getIntent().getSerializableExtra("list_Hm");
        list_hm_key= (List<hm_key_value>) getIntent().getSerializableExtra("list_hm_key");//键值对
        for(int i=0;i<list_objects.size();i++){
            System.out.println(list_objects.get(i).toString());
        }

        System.out.println(object.toString()+"]]]]]]]]]]]]]]]]]]");
        init();
        init_text();
        init_object();
        init_back();
        init_ok();
    }
    int cs=0;
    String zj="";
    private void init_text() {
        try {

            JSONObject jsonobject=new JSONObject(object);
            cs=jsonobject.getJSONArray("INFO").length();
            zj=jsonobject.getString("DDZJ");
            list_xiazhu_number.setText(String.valueOf(cs));
            list_xiazhu_jd_number.setText(zj);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(jincai_order2.this,pk_ten.class);
        startActivity(intent);
        finish();
    }

    private void init_object() {
        try {
            JSONObject jsonobject=new JSONObject(object);
            System.out.println(jsonobject+"[][][]");

            for (int i=0;i<list_objects.size();i++){
                list_delete_panduan.add(false);
            }
            for (int i=0;i<list_objects.size();i++){
                final int a=i;
                final RelativeLayout relativeLayout=new RelativeLayout(this);
                RelativeLayout.LayoutParams rp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this,40));
                relativeLayout.setLayoutParams(rp);
                relativeLayout.setBackgroundResource(R.mipmap.jingcai_2back);


                ImageView imageView=new ImageView(this);
                imageView.setBackgroundResource(R.mipmap.delete);
                list_delete.add(imageView);
                LinearLayout linearLayout=new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setGravity(Gravity.CENTER);
                TextView textView=new TextView(this);
                textView.setText(list_objects.get(i).getName());
                linearLayout.addView(textView);
                textView.setTextSize(24);
                textView.setTextColor(getResources().getColor(R.color.red));
                TextView textView2=new TextView(this);
                textView2.setText("共" + list_objects.get(i).getAll_bean() + "金豆  1赔" + list_objects.get(i).getPl());
                RelativeLayout.LayoutParams center_lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                center_lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                center_lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                LinearLayout.LayoutParams text_lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                text_lp.setMargins(DensityUtil.dip2px(jincai_order2.this,30),0,0,0);
                textView2.setLayoutParams(text_lp);
                linearLayout.addView(textView2);
//                ImageView imageView2=new ImageView(this);
//                imageView2.setBackgroundResource(R.mipmap.kaijiang);
                RelativeLayout.LayoutParams left_lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                left_lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                left_lp.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
                left_lp.setMargins(DensityUtil.dip2px(jincai_order2.this,10),0,0,0);
//                RelativeLayout.LayoutParams right_lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                right_lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//                right_lp.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
//                relativeLayout.addView(imageView,left_lp);
                relativeLayout.addView(linearLayout, center_lp);
//                relativeLayout.addView(imageView2,right_lp);
                list_guess_order.addView(relativeLayout);

            }
            RelativeLayout relativeLayout_bo=new RelativeLayout(this);
            RelativeLayout.LayoutParams rp_bo=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this,90));
            relativeLayout_bo.setLayoutParams(rp_bo);
            list_guess_order.addView(relativeLayout_bo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        list_xiazhu_number= (TextView) findViewById(R.id.list_xiazhu_number);
        list_xiazhu_jd_number= (TextView) findViewById(R.id.list_xiazhu_jd_number);
        guess_oeder=findViewById(R.id.guess_oeder);
        back= guess_oeder.findViewById(R.id.logreg_left);
        guess_order_ok= (Button) findViewById(R.id.guess_order_ok);
        guess_order_qs= (TextView) findViewById(R.id.guess_order_qs);
//        guess_order_qs.setInputType(InputType.TYPE_CLASS_NUMBER);
        guess_order_bs= (TextView) findViewById(R.id.guess_order_bs);
//        guess_order_bs.setInputType(InputType.TYPE_CLASS_NUMBER);
//        guess_order_bs.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if ("".equals(guess_order_bs.getText().toString())){
//                    guess_order_bs.setText("1");
//                }
//                if ("0".equals(guess_order_bs.getText().toString())){
//                    guess_order_bs.setText("1");
//                }
//                list_xiazhu_jd_number.setText(String.valueOf(Integer.parseInt(zj)*Integer.parseInt(guess_order_bs.getText().toString())));
//            }
//        });
        list_guess_order= (LinearLayout) findViewById(R.id.list_guess_order);
        please_wait=findViewById(R.id.please_wait);
    }
    int ddzj=0;
    private void init_ok() {

        guess_order_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess_order_ok.setEnabled(false);


                String bs = guess_order_bs.getText().toString();
                try {
                    JSONObject jsonobject = new JSONObject(object);
                    jsonObject_new.put("SJLX", "2");
                    JSONArray jsonArray = jsonobject.getJSONArray("INFO");
                    for (int i = 0; i < list_delete_panduan.size(); i++) {
//                        if(!list_delete_panduan.get(i)){
                        int gmsl = jsonArray.getJSONObject(i).getInt("GMSL") * Integer.parseInt(bs);
                        int gmzj = jsonArray.getJSONObject(i).getInt("GMDJ") * Integer.parseInt(bs);
                        String name=   list_objects.get(i).getName();
                        if(Integer.parseInt(list_objects.get(i).getSY())<gmzj){
                            Toast.makeText(jincai_order2.this,name+"下注金额超过50万",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        jsonObject_new.getJSONArray("INFO").getJSONObject(i).put("GMSL", gmsl);
                        jsonObject_new.getJSONArray("INFO").getJSONObject(i).put("GMZJ", gmzj);
                        ddzj = ddzj + gmzj;
//                        }else {
////                            list_xiazhu_number.setText(String.valueOf( Integer.parseInt(list_xiazhu_number.getText().toString())-1));
////                           list_xiazhu_jd_number.setText(Integer.parseInt( list_xiazhu_jd_number.getText().toString())-jsonArray.getJSONObject(i).getInt("GMDJ"));
//                            jsonObject_new.getJSONArray("INFO").remove(i);
//                        }
                    }
                    jsonObject_new.put("DDZJ", ddzj);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.setTimeout(20000);
                String url = all_url.url_order2;
                RequestParams requestParams = new RequestParams();
                requestParams.put("DATA", jsonObject_new.toString());

                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);
                        System.out.println(jsonObject.toString());
                        try {
                            if ("true".equals(jsonObject.getString("status"))) {
                                Intent intent = new Intent(jincai_order2.this, pay_page2.class);
                                intent.putExtra("list_objects", String.valueOf(ddzj));
                                intent.putExtra("expect", jsonObject_new.getString("EXPECT"));
                                intent.putExtra("YHDDID", jsonObject.getString("YHDDID"));
                                startActivity(intent);
                                guess_order_ok.setEnabled(true);
                                finish();
                            } else {
                                guess_order_ok.setEnabled(true);
                                Toast.makeText(jincai_order2.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }

                            please_wait.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        super.onFailure(throwable);
                        please_wait.setVisibility(View.GONE);
                        Toast.makeText(jincai_order2.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
//                finish();
                    }


                });
            }
        });
    }
}
