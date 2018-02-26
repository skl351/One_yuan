package com.yiyuangou.android.one_yuan.page1.all_guess;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.page4.dingdan_jincai_click;

import java.io.IOException;

/**
 * Created by android on 2016/4/9.
 */
public class click_jingcaidingdan extends Activity {


    private dingdan_jincai_click jin;
    private View jigncai_click_topbar;
    private View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.click_jingcaidingdan);
        jigncai_click_topbar=findViewById(R.id.jigncai_click_topbar);
        back=jigncai_click_topbar.findViewById(R.id.logreg_left);
        init_back();
        jin=new dingdan_jincai_click();
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
        beginTransaction.add(R.id.id_viewpager, jin);
        beginTransaction.commit();
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

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(click_jingcaidingdan.this,guess.class);
        startActivity(intent);
        finish();
    }
}
