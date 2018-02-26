package com.yiyuangou.android.one_yuan.page1.guess_hero2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;

import java.io.IOException;

/**
 * Created by android on 2016/4/19.
 */
public class hero_kaicai_xiangq2 extends Activity {
    private View back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hero_kaicai_xiangq);
        init();
        init_text();
        init_back();
    }

    private void init_text() {
        xunidingdna_xiangq_qs.setText(getIntent().getStringExtra("expect"));
        shuyinjd.setText(""+Math.round(Double.parseDouble(getIntent().getStringExtra("syjd"))));
        zongjd.setText(""+Math.round(Double.parseDouble(getIntent().getStringExtra("total"))));
        youlan.setText(""+Math.round(Double.parseDouble(getIntent().getStringExtra("sy1"))));
        jinzhan.setText(""+Math.round(Double.parseDouble(getIntent().getStringExtra("sy2"))));
        dalian.setText(""+Math.round(Double.parseDouble(getIntent().getStringExtra("sy3"))));
        wulan.setText(""+Math.round(Double.parseDouble(getIntent().getStringExtra("sy4"))));
        yuancheng.setText(""+Math.round(Double.parseDouble(getIntent().getStringExtra("sy5"))));
        danilian.setText(""+Math.round(Double.parseDouble(getIntent().getStringExtra("sy6"))));
        xunijigncai_xiangq_date.setText(getIntent().getStringExtra("show_time"));
        kouchu.setText(""+Math.round(Double.parseDouble(getIntent().getStringExtra("kcjd"))));
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

    private View jigncai_click_topbar;
    private TextView shuyinjd;
    private TextView zongjd;
    private TextView youlan;
    private TextView jinzhan;
    private TextView dalian;
    private TextView wulan;
    private TextView yuancheng;
    private TextView danilian;
    private TextView xunijigncai_xiangq_date;
    private TextView xunidingdna_xiangq_qs;
    private TextView kouchu;

    private void init() {
        kouchu= (TextView) findViewById(R.id.xiazhujd);
        xunidingdna_xiangq_qs= (TextView) findViewById(R.id.xunidingdna_xiangq_qs);
        xunijigncai_xiangq_date= (TextView) findViewById(R.id.xunijigncai_xiangq_date);
        danilian= (TextView) findViewById(R.id.danilian);
        yuancheng= (TextView) findViewById(R.id.yuancheng);
        wulan= (TextView) findViewById(R.id.wulan);
        dalian= (TextView) findViewById(R.id.dalian);
        jinzhan= (TextView) findViewById(R.id.jinzhan);
        youlan= (TextView) findViewById(R.id.youlan);
        shuyinjd= (TextView) findViewById(R.id.shuyinjd);
        zongjd= (TextView) findViewById(R.id.zongjd);
        xunidingdna_xiangq_qs= (TextView) findViewById(R.id.xunidingdna_xiangq_qs);
        jigncai_click_topbar=findViewById(R.id.xunidingdan_xiangq_topbar);
        back=jigncai_click_topbar.findViewById(R.id.logreg_left);
    }
}
