package com.yiyuangou.android.one_yuan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.widget.Toast;

import com.yiyuangou.android.one_yuan.util.User;

/**
 * Created by android on 2016/4/20.
 */
public class laun_activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User.is_first=true;
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.laun_activity);
        Handler x = new Handler();
        x.postDelayed(new splashhandler(), 2000);

//        if (!User.is_first){
//
//        }else {
//            Intent intent=new Intent(laun_activity.this,MainActivity.class);
//            startActivity(intent);
//            laun_activity.this.finish();
//        }

    }
    class splashhandler implements Runnable{
        public void run() {
            if (Build.VERSION.SDK_INT >= 23) {
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(laun_activity.this, Manifest.permission.READ_PHONE_STATE);
                if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(laun_activity.this,new String[]{Manifest.permission.READ_PHONE_STATE},REQUEST_CODE_ASK_CALL_PHONE);
                    return;
                }else{
                    //上面已经写好的拨号方法
                    ini_tiaozhuan();
                }
            } else {
                //上面已经写好的拨号方法
                ini_tiaozhuan();
            }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_ASK_CALL_PHONE:if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                ini_tiaozhuan();
            } else {
                // Permission Denied
                Toast.makeText(laun_activity.this, "请打开相应权限", Toast.LENGTH_SHORT)
                        .show();
                        laun_activity.this.finish();
            }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;
   private void ini_tiaozhuan(){
        Intent intent=new Intent(getApplication(),MainActivity.class);
        startActivity(intent);
        laun_activity.this.finish();
    }

}
