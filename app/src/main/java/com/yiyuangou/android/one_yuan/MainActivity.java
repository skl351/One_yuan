package com.yiyuangou.android.one_yuan;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.bean.user_info;
import com.yiyuangou.android.one_yuan.page1.Home_page;
import com.yiyuangou.android.one_yuan.page1.gift_send.gift_send_activity;
import com.yiyuangou.android.one_yuan.page1.gift_send.gift_send_activity_fragment;
import com.yiyuangou.android.one_yuan.page2.Share;
import com.yiyuangou.android.one_yuan.page3.Open_phone;
import com.yiyuangou.android.one_yuan.page4.Myself_center;
import com.yiyuangou.android.one_yuan.util.FileService;
import com.yiyuangou.android.one_yuan.util.MD5Utils;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {

    public static boolean  IS_login=false;
    public   static  int screenWidth;
    public static int bottom_height;
    private Home_page page1;
//    private Share  page2;
    private gift_send_activity_fragment page2;
    private Open_phone page3;
    private Myself_center page4;

    private LinearLayout but1;
    private LinearLayout but2;
    private LinearLayout but3;
    private LinearLayout but4;

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;

    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private View main_bottom_heifht;
    public static String hero_flag="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        init_home();
        init_version();
        init_click();
    }
    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    private String androidCurVersion;//当前版本
    private String androidLowVersion;//最低版本
    private String androidLink;//地址
    String version;//我的版本
    private void init_version() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            version= info.versionName;

            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            String url = all_url.url_getversion;
//            String url ="http://120.26.214.143:8082/jc/mobile/index/getAppVersion";
            asyncHttpClient.post(url,new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    super.onSuccess(jsonObject);
                    try {
                        if ("true".equals(jsonObject.getString("status"))){
                            androidLowVersion=  jsonObject.getString("androidLowVersion");
                            androidCurVersion=  jsonObject.getString("androidCurVersion");//
                            androidLink=  jsonObject.getString("androidLink");
                            init_load(androidCurVersion,androidLowVersion,version,androidLink);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Throwable arg0) {
                    super.onFailure(arg0);
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void init_load(String androidCurVersion, String androidLowVersion, String version, final String androidLink1) {
        if (version.compareTo(androidLowVersion)<0){
            UpdateManager updateManager=new UpdateManager(this);
            updateManager.showDownloadDialog_now();
        }else if (version.compareTo(androidCurVersion)<0){
            UpdateManager updateManager=new UpdateManager(this);
            updateManager.checkUpdate();
        }
    }


    /**
     * 底部4按钮的功能
     */
    private void init_click() {

        but1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction beginTransaction2 = fragmentManager2.beginTransaction();
                beginTransaction2.replace(R.id.home_layout, page1);
                beginTransaction2.commit();

                setChageok(img1, text1);
                setChageno(img2, text2);
                setChageno(img3, text3);
                setChageno(img4, text4);
//            Toast.makeText(MainActivity.this,)

            }
        });
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction beginTransaction2 = fragmentManager2.beginTransaction();
                beginTransaction2.replace(R.id.home_layout, page2);
                beginTransaction2.commit();
                setChageno(img1, text1);
                setChageok(img2, text2);
                setChageno(img3, text3);
                setChageno(img4, text4);

            }
        });
        but3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction beginTransaction2 = fragmentManager2.beginTransaction();
                beginTransaction2.replace(R.id.home_layout, page3);
                beginTransaction2.commit();
                setChageno(img1, text1);
                setChageno(img2, text2);
                setChageok(img3, text3);
                setChageno(img4, text4);
            }
        });
        but4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction beginTransaction2 = fragmentManager2.beginTransaction();

                beginTransaction2.replace(R.id.home_layout, page4);
                beginTransaction2.commit();
                setChageno(img1, text1);
                setChageno(img2, text2);
                setChageno(img3, text3);
                setChageok(img4, text4);
            }
        });

    }


    private String mima;//临时
    private void init() {
        TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();

        //没登陆
        if(!MainActivity.IS_login){
            FileService fileService=new FileService(this);
            try {
                user_info user_info=fileService.read("user.txt");
                mima=user_info.getUser_pwd_number();

                //如果不为空说明有信息，登录一下
                if (!(user_info.getUser_phone_number().isEmpty())){

                    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                    RequestParams requestParams = new RequestParams();
                    String url = all_url.url_login;
                    requestParams.put("SJHM", user_info.getUser_phone_number());
                    requestParams.put("DLMM", MD5Utils.getMD5Str(user_info.getUser_pwd_number()));
                    requestParams.put("MAC", szImei);
                    requestParams.put("SJLX", "2");

                    asyncHttpClient.post(url,requestParams, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            super.onSuccess(jsonObject);
                            try {
                                if ("true".equals(jsonObject.getString("status"))){
                                    System.out.println(jsonObject);
                                    User.getuser().setUser_phone_number(jsonObject.getString("SJHM"));
                                    User.getuser().setUser_name(jsonObject.getString("YHMC"));
                                    User.getuser().setUser_pwd_number(mima);
                                    User.getuser().setUser_uuid(jsonObject.getString("userId"));
                                    MainActivity.IS_login = true;
                                }else{
                                    Toast.makeText(MainActivity.this,jsonObject.getString("result"),Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(Throwable arg0) {
                            super.onFailure(arg0);
                        }
                    });
                }





            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //得到频宽；
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        page1=new Home_page();
        page2=new gift_send_activity_fragment();
        page3=new Open_phone();
        page4=new Myself_center();
        but1= (LinearLayout) findViewById(R.id.but_1);
        but2= (LinearLayout) findViewById(R.id.but_2);
        but3= (LinearLayout) findViewById(R.id.but_3);
        but4= (LinearLayout) findViewById(R.id.but_4);
        img1= (ImageView) findViewById(R.id.home_page_bottom_img);
        img2= (ImageView) findViewById(R.id.share_bottom_img);
        img3= (ImageView) findViewById(R.id.open_phone_bottom_img);
        img4= (ImageView) findViewById(R.id.myself_center_bottom_img);
        text1= (TextView) findViewById(R.id.home_page_bottom_text);
        text2= (TextView) findViewById(R.id.share_bottom_text);
        text3= (TextView) findViewById(R.id.open_phone_bottom_text);
        text4= (TextView) findViewById(R.id.myself_center_bottom_text);
        main_bottom_heifht=findViewById(R.id.main_bottom_heifht);

        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);

        main_bottom_heifht.measure(w, h);
        int height = main_bottom_heifht.getMeasuredHeight();
        bottom_height= height;
    }
    private void init_home() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
        beginTransaction.add(R.id.home_layout,page1);
        beginTransaction.commit();


    }
    public void setChageok(ImageView imageView,TextView textView){
        textView.setTextColor(getResources().getColor(R.color.white));
        if(textView.getText().equals("首页")){
            imageView.setImageResource(R.mipmap.home_page_orange);
        }
        if(textView.getText().equals("自主一元购")){
            imageView.setImageResource(R.mipmap.share_orange);
        }
        if(textView.getText().equals("开奖号码")){
            imageView.setImageResource(R.mipmap.open_phone_orange);
        }
        if(textView.getText().equals("个人中心")){
            imageView.setImageResource(R.mipmap.myself_center_orange);
        }

    }
    public void setChageno(ImageView imageView,TextView textView){
        textView.setTextColor(getResources().getColor(R.color.main_text));
        if(textView.getText().equals("首页")){
            imageView.setImageResource(R.mipmap.home_page_white);
        }
        if(textView.getText().equals("自主一元购")){
            imageView.setImageResource(R.mipmap.share_white);
        }
        if(textView.getText().equals("开奖号码")){
            imageView.setImageResource(R.mipmap.open_phone_white);
        }
        if(textView.getText().equals("个人中心")){
            imageView.setImageResource(R.mipmap.myself_center_white);
        }

    }
    //控制退出键
    private long mExitTime;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                MainActivity.IS_login=false;
                User.is_first=false;
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
