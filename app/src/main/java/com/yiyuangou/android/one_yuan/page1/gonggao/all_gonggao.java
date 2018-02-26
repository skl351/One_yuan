package com.yiyuangou.android.one_yuan.page1.gonggao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;

/**
 * Created by android on 2016/8/4.
 */
public class all_gonggao extends Activity {

    private TextView gonggao_neirong;
    View gonggao_topbar;
    private TextView title;
    private String text_content;
    private String text_title;
    private View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.today_gonggao);
        text_title=getIntent().getStringExtra("title");
        text_content=getIntent().getStringExtra("content");
        init();
        init_back();
        title.setText(text_title);
        gonggao_neirong.setText(text_content);
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
        gonggao_topbar=findViewById(R.id.gonggao_topbar);
        back=gonggao_topbar.findViewById(R.id.logreg_left);
        title= (TextView) gonggao_topbar.findViewById(R.id.logreg_center);
        gonggao_neirong= (TextView) findViewById(R.id.gonggao_neirong);
    }


}
