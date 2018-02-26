package com.yiyuangou.android.one_yuan.page1.all_guess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.kaijiang_result;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by android on 2016/4/1.
 */
public class guess_open extends Activity {

    kaijiang_result kaijiang_results;
    private TextView kaijiang_qs;
    private TextView open_result_guanfang_line1;
    private TextView open_result_guanfang_line2;

    private TextView group_3line;
    private TextView group_2line;
    private TextView group_1line;
    private TextView kaijiang_suan_group_1;
    private TextView kaijiang_suan_group_2;
    private TextView kaijiang_suan_group_3;
    private TextView kaijiangjieguo_group_1;
    private TextView kaijiangjieguo_group_2;
    private TextView kaijiangjieguo_group_3;
    private TextView kaijiang_shuoming;
    private TextView kaijiang_yixiejieguo;
    private TextView kaijiangjieguo_group_num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.guess_open_view);
        kaijiang_results= (kaijiang_result) getIntent().getSerializableExtra("kaijiang_results");
        init();
        init_text();
        init_kaijiangshuoming();
        init_back();

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

        finish();
        Intent intent=new Intent(guess_open.this,guess.class);
        startActivity(intent);
    }

    private void init_text() {
        kaijiang_qs.setText(kaijiang_results.getQs());
        List<String> list=kaijiang_results.getList_20();
        String line1="";
        String line2="";
        String line3_1="";
        String line3_2="";
        String line3_3="";
        int lint3_1_int=0;
        int lint3_2_int=0;
        int lint3_3_int=0;
        for(int i=0;i<20;i++){
            if(i<10){
                line1=line1+" "+list.get(i);
            }else{
                line2=line2+" "+list.get(i);
            }
            if(i<6){
                lint3_1_int= lint3_1_int+Integer.parseInt(list.get(i));
                line3_1=list.get(i)+"+"+line3_1;
            }else if(i<12){
                lint3_2_int= lint3_2_int+Integer.parseInt(list.get(i));
                line3_2=list.get(i)+"+"+line3_2;
            }else if(i<18){
                lint3_3_int= lint3_3_int+Integer.parseInt(list.get(i));
                line3_3=list.get(i)+"+"+line3_3;
            }
        }
        open_result_guanfang_line1.setText(line1);
        open_result_guanfang_line2.setText(line2);
        group_1line.setText(line3_1+"="+lint3_1_int);
        group_2line.setText(line3_2+"="+lint3_2_int);
        group_3line.setText(line3_3+"="+lint3_3_int);
        kaijiang_suan_group_1.setText(kaijiang_results.getGroup_1());
        kaijiang_suan_group_2.setText(kaijiang_results.getGroup_2());
        kaijiang_suan_group_3.setText(kaijiang_results.getGroup_3());
        kaijiangjieguo_group_1.setText(kaijiang_results.getGroup_1());
        kaijiangjieguo_group_2.setText(kaijiang_results.getGroup_2());
        kaijiangjieguo_group_3.setText(kaijiang_results.getGroup_3());
        kaijiang_yixiejieguo.setText(kaijiang_results.getResult());
        kaijiangjieguo_group_num.setText(kaijiang_results.getGroup_num());

    }

    private void init_kaijiangshuoming() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_kaijiang_shuoming;
        asyncHttpClient.post(url,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        kaijiang_shuoming.setText(jsonObject.getJSONObject("config").getString("PZZ"));

                    } else {
                        Toast.makeText(guess_open.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                Toast.makeText(guess_open.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    View guess_open_result;
    View back;
    private void init() {
        guess_open_result=findViewById(R.id.guess_open_result);
        back= guess_open_result.findViewById(R.id.logreg_left);
        kaijiangjieguo_group_num= (TextView) findViewById(R.id.kaijiangjieguo_group_num);
        kaijiang_yixiejieguo= (TextView) findViewById(R.id.kaijiang_yixiejieguo);
        kaijiang_shuoming= (TextView) findViewById(R.id.kaijiang_shuoming);
        kaijiangjieguo_group_1= (TextView) findViewById(R.id.kaijiangjieguo_group_1);
        kaijiangjieguo_group_2= (TextView) findViewById(R.id.kaijiangjieguo_group_2);
        kaijiangjieguo_group_3= (TextView) findViewById(R.id.kaijiangjieguo_group_3);
        kaijiang_suan_group_1= (TextView) findViewById(R.id.kaijiang_suan_group_1);
        kaijiang_suan_group_2= (TextView) findViewById(R.id.kaijiang_suan_group_2);
        kaijiang_suan_group_3= (TextView) findViewById(R.id.kaijiang_suan_group_3);
        group_3line= (TextView) findViewById(R.id.group_3line);
        group_2line= (TextView) findViewById(R.id.group_2line);
        group_1line= (TextView) findViewById(R.id.group_1line);
        open_result_guanfang_line1= (TextView) findViewById(R.id.open_result_guanfang_line1);
        open_result_guanfang_line2= (TextView) findViewById(R.id.open_result_guanfang_line2);
        kaijiang_qs= (TextView) findViewById(R.id.kaijiang_qs);
    }
}
