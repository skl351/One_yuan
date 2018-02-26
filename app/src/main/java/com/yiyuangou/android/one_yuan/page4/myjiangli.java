package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.yiyuangou.android.one_yuan.R;

/**
 * Created by android on 2016/6/23.
 */
public class myjiangli extends Activity {
    private View user_dingdan;
    private View back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.jiangli_activity);
        dingdan_jiangli dingdan_jiangli=new dingdan_jiangli();
        FragmentManager fragmentManager2 = getFragmentManager();
        FragmentTransaction beginTransaction2 = fragmentManager2.beginTransaction();
        beginTransaction2.replace(R.id.jiangli_layout, dingdan_jiangli);
        beginTransaction2.commit();


        user_dingdan=findViewById(R.id.user_dingdan);
        back=user_dingdan.findViewById(R.id.logreg_left);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
