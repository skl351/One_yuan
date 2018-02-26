package com.yiyuangou.android.one_yuan.page1.gift_send;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.DensityUtil;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/20.
 */
public class gift_send_activity extends Activity {
    private View gift_topbar;
    private View back;
    private LinearLayout fenlei_line;
    private List<String> list_id;
    public  static String flag="skl_quan";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.click_gift_activity);
        flag="skl_quan";
        init();
        init_wangluo();
        init_back();
        gift_send_v  gify_v=new gift_send_v();
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction  beginTransaction=fragmentManager.beginTransaction();
        beginTransaction.add(R.id.id_viewpager, gify_v);
        beginTransaction.commit();

    }

    private void init_wangluo(){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_gift;
        final RequestParams requestParams = new RequestParams();
        requestParams.put("PAGE", "1");
        requestParams.put("NUM", "10");
        asyncHttpClient.post(url,requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))){
                        list_id=new ArrayList<String>();
                        System.out.println(jsonObject);
                        JSONArray js=jsonObject.getJSONArray("categorys");
                        Button button_quanbu= new Button(gift_send_activity.this);
                        button_quanbu.setText("全部");
                        button_quanbu.setTextColor(getResources().getColor(R.color.home_orange));
                        button_quanbu.setBackgroundResource(R.drawable.biankuang_bottom_fenlei);
                        list_id.add("skl_quan");
                        fenlei_line.addView(button_quanbu);
                        LinearLayout.LayoutParams lp1= (LinearLayout.LayoutParams) button_quanbu.getLayoutParams();
                        lp1.height= DensityUtil.dip2px(gift_send_activity.this,44);
                        if(js.length()>=3){
                            lp1.width= MainActivity.screenWidth/4;
                        }else{
                            lp1.width= MainActivity.screenWidth/(js.length()+1);
                        }
                        button_quanbu.setLayoutParams(lp1);


                        for (int i=0;i<js.length();i++){//根据分类的数量，来添加按钮
                            Button button= new Button(gift_send_activity.this);
                            button.setText(js.getJSONObject(i).getString("SPFMC"));
                            list_id.add(js.getJSONObject(i).getString("UUID"));
                            button.setTextColor(getResources().getColor(R.color.gray));
                            button.setBackgroundResource(R.drawable.biankuang_bottom_wu_fenlei);
                            fenlei_line.addView(button);

                            LinearLayout.LayoutParams lp2= (LinearLayout.LayoutParams) button.getLayoutParams();
                            lp2.height= DensityUtil.dip2px(gift_send_activity.this,44);
                            if(js.length()>=3){
                                lp2.width= MainActivity.screenWidth/4;
                            }else{
                                lp2.width= MainActivity.screenWidth/(js.length()+1);
                            }
                            button_quanbu.setLayoutParams(lp2);

                        }
                        for(int i=0;i<fenlei_line.getChildCount();i++){
                            final int a=i;
                            fenlei_line.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    for(int i=0;i<fenlei_line.getChildCount();i++){
                                       Button button= (Button) fenlei_line.getChildAt(i);
                                        button.setTextColor(getResources().getColor(R.color.gray));
                                        button.setBackgroundResource(R.drawable.biankuang_bottom_wu_fenlei);
                                    }
                                    Button button= (Button) fenlei_line.getChildAt(a);
                                    button.setTextColor(getResources().getColor(R.color.red));
                                    button.setBackgroundResource(R.drawable.biankuang_bottom_fenlei);
                                    flag=list_id.get(a);
                                    gift_send_v  gify_v=new gift_send_v();
                                    FragmentManager fragmentManager=getFragmentManager();
                                    FragmentTransaction  beginTransaction=fragmentManager.beginTransaction();
                                    beginTransaction.replace(R.id.id_viewpager, gify_v);
                                    beginTransaction.commit();
                                }
                            });
                        }
                    }else{
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
//                please_wait.setVisibility(View.GONE);
//                btn_cls.bt_endisable(login_submit);
                Toast.makeText(gift_send_activity.this,"网络繁忙，请重试",Toast.LENGTH_SHORT).show();
            }


        });

    }

    private void init() {
        fenlei_line= (LinearLayout) findViewById(R.id.fenlei_line);
        gift_topbar=findViewById(R.id.gift_topbar);
        back=gift_topbar.findViewById(R.id.logreg_left);
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
