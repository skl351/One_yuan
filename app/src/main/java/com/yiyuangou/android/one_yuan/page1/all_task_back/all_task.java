package com.yiyuangou.android.one_yuan.page1.all_task_back;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;
import com.yiyuangou.android.one_yuan.util.btn_cls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by android on 2016/4/13.
 */
public class all_task extends Activity {
    private LinearLayout task_rect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.all_task);
        init();
        init_wngluo();
        init_back();


    }

    private void init_wngluo() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_task_list;
        RequestParams requestParams=new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                try {
                    System.out.println(arg1.toString());
                    boolean flag=false;//第一次签到，true是
                    String signInFirst="";
                    String signInNum=arg1.getString("signInNum");
                    if("0".equals(signInNum)){
                        signInFirst = arg1.getString("signInFirst");
                        flag=true;
                    }


                    String czjs = arg1.getString("czJs");
                    String isCz = arg1.getString("isCz");
                    String czPer = arg1.getString("czPer");

                    String sign_jd = arg1.getString("signIn");
                    String reliefDay = arg1.getString("reliefDay");//jiuji
                    String reliefNum = arg1.getString("reliefNum");//jiuji
                    String reliefJd = arg1.getString("reliefJd");//jiuji
                    JSONArray playCon = arg1.getJSONArray("playCon");
                    String playnum = arg1.getString("playNum");
                    String invitenum = arg1.getString("inviteNum");
                    String invite = arg1.getString("invite");
                    String isRelief = arg1.getString("isRelief");
                    String isSignIn = arg1.getString("isSignIn");
                    init_2(sign_jd, reliefDay, reliefJd, reliefNum, playCon, playnum, invitenum, isSignIn, isRelief, czjs, czPer, isCz,flag,signInFirst,invite);


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Throwable arg0) {
                // TODO Auto-generated method stub
                super.onFailure(arg0);
            }
        });
    }

    private void init_3(JSONArray playCon, String playnum) {
        for (int i=0;i<playCon.length();i++){
            try {
                String isplay=playCon.getJSONObject(i).getString("isPlay");
                final String uuid=playCon.getJSONObject(i).getString("UUID");
              String pzz=  playCon.getJSONObject(i).getString("PZZ");
                String[] pz=pzz.split("\\|");
                View view= LayoutInflater.from(this).inflate(R.layout.item_all_task_yonglaizuo, null);
               TextView jushu= (TextView) view.findViewById(R.id.item_all_task_yonglaizuo_jushu);
                TextView jd= (TextView) view.findViewById(R.id.item_all_task_task_js);
               final Button but= (Button) view.findViewById(R.id.item_all_task_lingqu);
                if("true".equals(isplay)){
                    but.setText("已领取");
                    btn_cls.bt_disable(but);
                }

                if(!(Integer.parseInt(playnum)>=Integer.parseInt(pz[0]))){
                        btn_cls.bt_disable(but);
                }
                System.out.println(pz[0]+","+pz[1]);
                jd.setText(pz[1]);
                jushu.setText(pz[0]);
                but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                        String url = all_url.url_task_give_jd;
                        RequestParams requestParams=new RequestParams();
                        requestParams.put("userId", User.getuser().getUser_uuid());
                        requestParams.put("PZID",uuid);
                        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int arg0, JSONObject arg1) {
                                super.onSuccess(arg0, arg1);
                                try {
                                    System.out.println(arg1.toString());
                                    but.setText("已领取");
                                    btn_cls.bt_disable(but);

                                 Toast.makeText(all_task.this,arg1.getString("result"),Toast.LENGTH_SHORT).show();

                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void onFailure(Throwable arg0) {
                                // TODO Auto-generated method stub
                                super.onFailure(arg0);
                            }
                        });
                    }
                });
                task_rect.addView(view);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void init_2(final String a, String b, String c, String d, final JSONArray playCon, String playnum, final String invitenum,String issign,String isjieji,String js,String duoshao,String status,boolean flag,String signInFirst,String invite) {

        //签到
        View view= LayoutInflater.from(this).inflate(R.layout.item_all_task_task,null);
        TextView meitian= (TextView) view.findViewById(R.id.meitian);
        TextView textView= (TextView) view.findViewById(R.id.item_all_task_task_js);
        if(flag){
            meitian.setText("首次签到领金豆");
            textView.setText(signInFirst);
        }else{
            meitian.setText("每天签到领金豆");
            textView.setText(a);
        }
        Button qiandao= (Button) view.findViewById(R.id.item_all_task_lingqu);
        if("true".equals(issign)){
            btn_cls.bt_disable(qiandao);
            qiandao.setText("已领取");
        }
        qiandao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_cls.bt_disable(v);
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                String url = all_url.url_qiandao;
                RequestParams requestParams = new RequestParams();
                requestParams.put("userId", User.getuser().getUser_uuid());
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, JSONObject arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            System.out.println(arg1.toString());
                            if("true".equals(arg1.getString("status"))){
                                Toast.makeText(all_task.this, arg1.getString("result"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0) {
                        // TODO Auto-generated method stub
                        super.onFailure(arg0);
                    }
                });
            }
        });

        task_rect.addView(view);

        //
        View view2= LayoutInflater.from(this).inflate(R.layout.item_all_task_task2,null);
        TextView num_te= (TextView) view2.findViewById(R.id.num);
        num_te.setText(d);
        TextView dyds_jd= (TextView) view2.findViewById(R.id.item_all_task_dyds_jd);
        dyds_jd.setText(b);
        TextView song_jd= (TextView) view2.findViewById(R.id.item_all_task_song_jd);
        song_jd.setText(c);
        Button lingqu= (Button) view2.findViewById(R.id.item_all_task2_but);
        if("true".equals(isjieji)){
            btn_cls.bt_disable(lingqu);
        }
        if (Integer.parseInt(d)<0){
            btn_cls.bt_disable(lingqu);
        }
        lingqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_cls.bt_disable(v);
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                String url = all_url.url_task_relief;
                RequestParams requestParams = new RequestParams();
                requestParams.put("userId", User.getuser().getUser_uuid());
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, JSONObject arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            System.out.println(arg1.toString());
                            Toast.makeText(all_task.this, arg1.getString("result"), Toast.LENGTH_SHORT).show();


                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0) {
                        // TODO Auto-generated method stub
                        super.onFailure(arg0);
                    }
                });

            }
        });
        task_rect.addView(view2);
        View view3= LayoutInflater.from(this).inflate(R.layout.item_all_task_task3,null);
        TextView song_jd_yaoq= (TextView) view3.findViewById(R.id.item_all_task_task_js);
        final Button lingqu_yaoq= (Button) view3.findViewById(R.id.item_all_task_lingqu);
        if(Integer.parseInt(invitenum)<=0){
            btn_cls.bt_disable(lingqu_yaoq);

        }
        final TextView num= (TextView) view3.findViewById(R.id.task_yaoqing_num);
        song_jd_yaoq.setText(invite);
        num.setText(invitenum);
        lingqu_yaoq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                btn_cls.bt_disable(v);
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                String url = all_url.url_task_yaoq;
                RequestParams requestParams = new RequestParams();
                requestParams.put("userId", User.getuser().getUser_uuid());
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int arg0, JSONObject arg1) {
                        super.onSuccess(arg0, arg1);
                        try {
                            System.out.println(arg1.toString());
                            Toast.makeText(all_task.this, arg1.getString("result"), Toast.LENGTH_SHORT).show();
                            if ("true".equals(arg1.getString("status"))){
                                int i=Integer.parseInt(invitenum)-1;
                                num.setText(""+i);
                                if (i>0){
                                    btn_cls.bt_endisable(lingqu_yaoq);
                                }
                            }


                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0) {
                        // TODO Auto-generated method stub
                        super.onFailure(arg0);
                    }
                });

            }
        });
        task_rect.addView(view3);
        View view4= LayoutInflater.from(this).inflate(R.layout.item_all_task_task4,null);
        TextView task_js= (TextView) view4.findViewById(R.id.task_js);
        TextView item_all_task_task_fenshu= (TextView) view4.findViewById(R.id.item_all_task_task_fenshu);
        Button bte= (Button) view4.findViewById(R.id.item_all_task_lingqu);
        task_js.setText(js);
        item_all_task_task_fenshu.setText(duoshao);
        if("false".equals(status)){
            bte.setText("未完成");
            bte.setEnabled(false);
        }else{
            bte.setEnabled(false);
            bte.setText("已完成");
        }


        task_rect.addView(view4);




        init_3(playCon,playnum);
    }
    private View all_task_topbar;
    private View back;


    private void init() {

        all_task_topbar=findViewById(R.id.all_task_topbar);
        back=all_task_topbar.findViewById(R.id.logreg_left);
        task_rect= (LinearLayout) findViewById(R.id.task_rect);
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
}
