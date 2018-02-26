package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;

/**
 * Created by android on 2016/3/22.
 */
public class user_forgot4 extends Activity {
    private TextView logreg_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.user_forgot4);
        logreg_left= (TextView) findViewById(R.id.logreg_left);
        logreg_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(user_forgot4.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(user_forgot4.this,MainActivity.class);
        startActivity(intent);
    }
}
