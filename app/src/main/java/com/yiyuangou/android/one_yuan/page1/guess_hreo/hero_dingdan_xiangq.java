package com.yiyuangou.android.one_yuan.page1.guess_hreo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by android on 2016/4/19.
 */
public class hero_dingdan_xiangq extends Activity {


    private String qs;
    private String shifou_zj;
    private String gmzj;
    private String grounp_1;
    private String grounp_2;
    private String grounp_3;
    private String grounp_num;
    private String pl;
    private String jt_date;
    private String syjd;
    private View back;
    private String UUID;
    private TextView xunijigncai_xiangq_kjjg;
    private String hmmc;
    private TextView xunijigncai_xiangq_xiazhm;
    private TextView kcjd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.hero_dingdan_xiangq);
        UUID=getIntent().getStringExtra("UUID");
        hmmc=getIntent().getStringExtra("hmmc");
        init();
        xunijigncai_xiangq_xiazhm.setText(hmmc);
        init_wangluo();
        init_back();
    }

    protected void init_wangluo() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_orderinfo_lol;
        RequestParams requestParams=new RequestParams();
        requestParams.put("INFOID",UUID);
        asyncHttpClient.post(url,requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());
                try {
                    if("true".equals(arg1.getString("status"))){
                        JSONArray jsonArray=arg1.getJSONArray("orderInfos");
                        for (int i=0;i<jsonArray.length();i++){
                            xunidingdna_xiangq_qs.setText(jsonArray.getJSONObject(i).getString("EXPECT"));
                            if("1".equals(jsonArray.getJSONObject(i).getString("DDZT"))){
                                xunijigncai_xiangq_shif.setText("未中奖");
                            }else{
                                xunijigncai_xiangq_shif.setTextColor(getResources().getColor(R.color.red));
                                xunijigncai_xiangq_shif.setText("中奖" +String.valueOf(jsonArray.getJSONObject(i).getInt("SYJD")+jsonArray.getJSONObject(i).getInt("GMZJ")) +"金豆");
                            }

                            kcjd.setText(jsonArray.getJSONObject(i).getString("KCJD"));
                            xunijigncai_xiangq_kjjg.setText(jsonArray.getJSONObject(i).getString("result"));
                            xunijigncai_xiangq_gmzj.setText(String.valueOf(jsonArray.getJSONObject(i).getInt("GMZJ")));
                            xunijigncai_xiangq_group1.setText(jsonArray.getJSONObject(i).getString("code_1"));
                            xunijigncai_xiangq_group2.setText(jsonArray.getJSONObject(i).getString("code_2"));
                            xunijigncai_xiangq_group3.setText(jsonArray.getJSONObject(i).getString("code_3"));
                            xunijigncai_xiangq_group4.setText(jsonArray.getJSONObject(i).getString("code_4"));
                            xunijigncai_xiangq_groupnum.setText(jsonArray.getJSONObject(i).getString("position"));
                            xunijigncai_xiangq_pl.setText(jsonArray.getJSONObject(i).getString("HMPL"));
                            xunijigncai_xiangq_date.setText(jsonArray.getJSONObject(i).getString("DDCSSJ"));
                        }
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }
            @Override
            public void onFailure(Throwable arg0) {
                // TODO Auto-generated method stub
                super.onFailure(arg0);
                System.out.println("失败");
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


    private TextView xunidingdna_xiangq_qs;
    private TextView xunijigncai_xiangq_shif;
    private TextView xunijigncai_xiangq_gmzj;
    private TextView xunijigncai_xiangq_group1;
    private TextView xunijigncai_xiangq_group2;
    private TextView xunijigncai_xiangq_group3;
    private TextView xunijigncai_xiangq_group4;
    private TextView xunijigncai_xiangq_groupnum;
    private TextView xunijigncai_xiangq_pl;
    private TextView xunijigncai_xiangq_date;
    private View jigncai_click_topbar;

    private void init() {
        kcjd= (TextView) findViewById(R.id.kcjd);
        xunijigncai_xiangq_group4= (TextView) findViewById(R.id.xunijigncai_xiangq_group4);
        xunijigncai_xiangq_xiazhm= (TextView) findViewById(R.id.xunijigncai_xiangq_xiazhm);
        xunijigncai_xiangq_kjjg= (TextView) findViewById(R.id.xunijigncai_xiangq_kjjg);
        jigncai_click_topbar=findViewById(R.id.xunidingdan_xiangq_topbar);
        back=jigncai_click_topbar.findViewById(R.id.logreg_left);
        xunijigncai_xiangq_pl= (TextView) findViewById(R.id.xunijigncai_xiangq_pl);
        xunijigncai_xiangq_date= (TextView) findViewById(R.id.xunijigncai_xiangq_date);
        xunijigncai_xiangq_group1= (TextView) findViewById(R.id.xunijigncai_xiangq_group1);
        xunijigncai_xiangq_group2= (TextView) findViewById(R.id.xunijigncai_xiangq_group2);
        xunijigncai_xiangq_group3= (TextView) findViewById(R.id.xunijigncai_xiangq_group3);
        xunijigncai_xiangq_groupnum= (TextView) findViewById(R.id.xunijigncai_xiangq_groupnum);
        xunijigncai_xiangq_gmzj= (TextView) findViewById(R.id.xunijigncai_xiangq_gmzj);
        xunijigncai_xiangq_shif= (TextView) findViewById(R.id.xunijigncai_xiangq_shif);
        xunidingdna_xiangq_qs= (TextView) findViewById(R.id.xunidingdna_xiangq_qs);
    }
}
