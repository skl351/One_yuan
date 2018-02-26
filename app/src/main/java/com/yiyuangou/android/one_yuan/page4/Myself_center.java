package com.yiyuangou.android.one_yuan.page4;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.socks.library.KLog;
import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.FileService;
import com.yiyuangou.android.one_yuan.util.MD5Utils;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * Created by android on 2016/3/17.
 */
public class Myself_center extends Fragment {
    private View view;
    private View myself_mydingdan;

    private View myself_shouhuodingzhi;
    private View myself_zengdong;
    private View myself_yijianfankui;
    private View myself_guanyuwomen;
    public int screenWidth;
    private View myself_no_login;
    private View myself_ok_login;
    private Button myself_login;
    private Button myself_register;
    private View myself_out;
    private boolean ok_back;
    private TextView myself_name;
    private TextView myself_phone;
    private View myself_modify;
    private TextView myself_keyong_jd;
    private TextView myself_keyong_je;
    private Button myself_chongzhi;
    private ImageView myself_user_img;
    private View myself_tuiguang;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.myself_center, container,false);
        init();
        init_xiugai_name();
        init_img_click();
        init_suggestions();//意见反馈
        init_chongzhi();//充值
        init_shouhuo();//收货
        init_dingdan();//订单
        init_login_register();//初始化登陆注册
        init_out();//初始化退出
        init_modif();//初始化修改密码
        init_sendgift();//赠送礼物
        init_about();//关于我们
        init_tuiguang();//推广
        ((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
                .setOnRefreshListener(new MyListener());
        return view;

    }
    AlertDialog alert_hero;
    AlertDialog.Builder builder_hero;

    private void init_xiugai_name() {
        myself_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert_hero = null;
                 builder_hero= new AlertDialog.Builder(getActivity());
                alert_hero=builder_hero.create();
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.xiugai_name, null);
                   final EditText login_pwd= (EditText) view.findViewById(R.id.login_pwd);
                   final EditText new_name= (EditText) view.findViewById(R.id.new_name);
                view.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert_hero.dismiss();
                    }
                });
                view.findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       final String name= new_name.getText().toString();
                        String pwd=login_pwd.getText().toString();
                      String   pwd_number_md5= MD5Utils.getMD5Str(pwd);
                        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                        asyncHttpClient.setTimeout(20000);
                        String url = all_url.url_xiugainame;//userId 用户id  userNameh 新用户昵称  DLMM 登陆密码（一次MD5）
                        RequestParams requestParams = new RequestParams();
                        requestParams.put("userId", User.getuser().getUser_uuid());
                        requestParams.put("userNameh", name);
                        requestParams.put("DLMM", pwd_number_md5);
                        System.out.println(User.getuser().getUser_uuid()+","+name+","+pwd_number_md5);
                        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                super.onSuccess(jsonObject);
                                try {
                                    KLog.json("skl",jsonObject.toString());
                                    if ("true".equals(jsonObject.getString("status"))) {
                                        Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
                                        User.getuser().setUser_name(name);
                                        myself_name.setText(name);
                                        alert_hero.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                super.onFailure(throwable);
                            }
                        });
                    }
                });
                alert_hero.setView(view, 0, 0, 0, -10);
                alert_hero.show();
            }
        });
    }

    private void init_tuiguang() {
        myself_tuiguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.IS_login){
                    Intent intent=new Intent(getActivity(),myself_jiangli_page.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(getActivity(),user_login.class);
                    startActivity(intent);
                }
            }
        });

    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener
    {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
        {
            // 下拉刷新操作
            new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    System.err.println("下拉");
                    init_user_info();
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1500);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
        {
            // 加载操作
            new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    System.err.println("上拉");
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 1500);
        }

    }
    private void init_about() {
        myself_guanyuwomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),about_class.class);
                startActivity(intent);
            }
        });
    }
    private void init_img_click() {
        myself_user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),user_self_open.class);
                startActivity(intent);
            }
        });
    }




    private void init_sendgift() {
        myself_zengdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.IS_login){
                    Intent intent=new Intent(getActivity(),user_zengdong_big.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(getActivity(),user_login.class);
                    startActivity(intent);
                }

            }
        });
    }

    /**
     * 意见反馈
     */
    private void init_suggestions() {
        myself_yijianfankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MainActivity.IS_login){
                    Intent intent=new Intent(getActivity(),user_suggestion_ceshi.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(getActivity(),user_login.class);
                    startActivity(intent);
                }
            }
        });

    }
    /**
     * 充值
     */
    private void init_chongzhi() {
        myself_chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),user_chongzhi.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 订单
     */
    private void init_dingdan() {
        myself_mydingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.IS_login){
                    Intent intent=new Intent(getActivity(),my_dingdan.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(getActivity(),user_login.class);
                    startActivity(intent);
                }

            }
        });
    }

    /**
     * 收货地址
     */
    private void init_shouhuo() {
        myself_shouhuodingzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MainActivity.IS_login){
                    Intent intent = new Intent(getActivity(), user_shouhuo.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(getActivity(),user_login.class);
                    startActivity(intent);
                }
            }
        });
    }


    /**
     * 修改密码
     */
    private void init_modif() {
        myself_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MainActivity.IS_login){
                    Intent intent = new Intent(getActivity(), user_modify.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(getActivity(),user_login.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 退出
     */
    private void init_out() {
        myself_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.IS_login) {
                    init_tip();
                }
            }
        });
    }
    private void init_tip(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("你确定退出吗");
        builder.setPositiveButton("确定退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent=new Intent(getActivity(),MainActivity.class);
                MainActivity.IS_login = false;
                User.getuser().setUser_uuid("");
                User.getuser().setUser_name("");
                User.getuser().setUser_pwd_number("");
                User.getuser().setUser_phone_number("");
                FileService service=new FileService(getActivity());
                String user_info=User.getuser().getUser_phone_number()+"-"+User.getuser().getUser_pwd_number()+"-"+User.getuser().getUser_name()+"-"+User.getuser().getUser_uuid();
                try {
                    service.saveToRom("user.txt", user_info);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                return;
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    //每次进来都要请求豆子
    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.IS_login){
            myself_no_login.setVisibility(View.GONE);
            myself_ok_login.setVisibility(View.VISIBLE);
            myself_out.setVisibility(View.VISIBLE);
            myself_phone.setText(User.getuser().getUser_phone_number().toString());
            myself_name.setText(User.getuser().getUser_name().toString());
            init_user_info();

        }else{
            myself_out.setVisibility(View.INVISIBLE);
            myself_no_login.setVisibility(View.VISIBLE);
            myself_ok_login.setVisibility(View.GONE);
        }
    }

    private void init_user_info() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_user_info;
        RequestParams requestParams = new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        System.out.println(jsonObject);

                        myself_keyong_je.setText(new DecimalFormat("#,##0").format(new Double(jsonObject.getJSONObject("user").getDouble("signJindou"))));//任务金豆
                        myself_keyong_jd.setText(new DecimalFormat("#,##0").format(new Double(jsonObject.getJSONObject("user").getDouble("commissionPoints"))));//金豆
                        if(jsonObject.getJSONObject("user").has("faceImg")){
                            ImageLoader.getInstance().displayImage(jsonObject.getJSONObject("user").getString("faceImg"),myself_user_img);
                        }
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
            }
        });
    }


    /**
     * 登陆注册
     */
    private void init_login_register() {
        myself_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),user_login.class);
                startActivity(intent);
            }
        });
        myself_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), user_register.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化
     */
    private void init() {
        myself_tuiguang=view.findViewById(R.id.myself_tuiguang);
        myself_user_img= (ImageView) view.findViewById(R.id.myself_user_img);
        myself_chongzhi= (Button) view.findViewById(R.id.myself_chongzhi);
        myself_keyong_jd= (TextView) view.findViewById(R.id.myself_keyong_jd);
        myself_keyong_je= (TextView) view.findViewById(R.id.myself_keyong_je);
        myself_modify=view.findViewById(R.id.myself_modify);
        myself_phone= (TextView) view.findViewById(R.id.myself_phone);
        myself_name= (TextView) view.findViewById(R.id.myself_name);
        myself_out=view.findViewById(R.id.myself_out);
        myself_no_login=view.findViewById(R.id.myself_no_login);
        myself_ok_login=view.findViewById(R.id.myself_ok_login);
        myself_login= (Button) view.findViewById(R.id.myself_login);
        myself_register= (Button) view.findViewById(R.id.myself_register);
        screenWidth= MainActivity.screenWidth;
        myself_mydingdan=view.findViewById(R.id.myself_mydingdan);
        myself_modify = view.findViewById(R.id.myself_modify);
        myself_shouhuodingzhi=view.findViewById(R.id.myself_shouhuodingzhi);
        myself_zengdong=view.findViewById(R.id.myself_zengdong);
        myself_yijianfankui=view.findViewById(R.id.myself_yijianfankui);
        myself_guanyuwomen=view.findViewById(R.id.myself_guanyuwomen);

        setwandh(myself_mydingdan);
        setwandh(myself_modify);
        setwandh(myself_shouhuodingzhi);
        setwandh(myself_zengdong);
        setwandh(myself_yijianfankui);
        setwandh(myself_guanyuwomen);
        setwandh(myself_tuiguang);
//        init_banben();
//        init_tel();
    }
//
//    /**
//     * 电话
//     */
//    private void init_tel() {
//        myself_aboutwo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Uri url=Uri.parse("tel:"+myself_aboutwo.getText().toString());
//                Intent intent=new Intent(Intent.ACTION_DIAL,url);
//                startActivity(intent);
//
//            }
//        });
//    }

    public void setwandh(View view){
        LinearLayout.LayoutParams lp_user_guanyuwomende = (LinearLayout.LayoutParams) view
                .getLayoutParams();
        lp_user_guanyuwomende.height=(screenWidth/3);
        lp_user_guanyuwomende.width=(screenWidth/3);
        view.setLayoutParams(lp_user_guanyuwomende);

    }

//    private void init_banben() {
//        /**
//         * 获取版本号
//         * @return 当前应用的版本号
//         */
//        try {
//            PackageManager manager = getActivity().getPackageManager();
//            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
//            String version = info.versionName;
//            versionName.setText(version);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


}
