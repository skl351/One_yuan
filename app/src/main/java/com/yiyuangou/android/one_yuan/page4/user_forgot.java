package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.yiyuangou.android.one_yuan.R;

/**
 * Created by android on 2016/3/22.
 */
public class user_forgot extends Activity {
    private View forgot_topbar;
    private View back;
    user_forgot1 u1;
    public static boolean keyi=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.user_forgot);
        init();
        init_main();
        init_back();
    }

    private void init() {
        forgot_topbar=findViewById(R.id.forgot_topbar);
        back= forgot_topbar.findViewById(R.id.logreg_left);
        u1=new user_forgot1();
    }
    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void init_main() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction beginTransaction=fragmentManager.beginTransaction();

        beginTransaction.add(R.id.forgot_frame, u1);
        beginTransaction.commit();


    }
}
