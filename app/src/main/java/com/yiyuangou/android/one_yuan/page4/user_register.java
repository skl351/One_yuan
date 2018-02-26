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
public class user_register extends Activity {
    private user_register1 u1;
    private View register_topbar;
    private View back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.user_register);

        init();
        init_main();
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

    private void init() {
        register_topbar=findViewById(R.id.register_topbar);

        back=  register_topbar.findViewById(R.id.logreg_left);

        u1=new user_register1();
    }

    private void init_main() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction beginTransaction=fragmentManager.beginTransaction();

        beginTransaction.add(R.id.register_frame, u1);
        beginTransaction.commit();


    }
}
