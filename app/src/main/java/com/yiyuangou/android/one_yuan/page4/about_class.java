package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yiyuangou.android.one_yuan.R;

import java.io.IOException;

/**
 * Created by android on 2016/4/21.
 */
public class about_class extends Activity {
    private TextView versin;
    private View back;
    private View zengsong1_1_topbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.about_class);
        zengsong1_1_topbar=findViewById(R.id.about_topbar);
        back= zengsong1_1_topbar.findViewById(R.id.logreg_left);
        versin= (TextView) findViewById(R.id.versin);
        init_banben();
        init_back();
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
    private void init_banben() {
        /**
         * 获取版本号
         * @return 当前应用的版本号
         */
        try {
            PackageManager manager = about_class.this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(about_class.this.getPackageName(), 0);
            String version = info.versionName;
            versin.setText(version);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
