package com.yiyuangou.android.one_yuan.page1.all_guess;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.Jd;
import com.yiyuangou.android.one_yuan.bean.caihao_qiu;
import com.yiyuangou.android.one_yuan.bean.kaijiang_result;
import com.yiyuangou.android.one_yuan.bean.open_qs;
import com.yiyuangou.android.one_yuan.bean.order_item;
import com.yiyuangou.android.one_yuan.util.DensityUtil;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by android on 2016/3/31.
 */
public class guess_caihao extends Activity {
    private View guess_topbar;
    private View back;
    List<Jd> list_jd=new ArrayList<Jd>();//网络得到金豆数
    List<caihao_qiu> list_qiu=new ArrayList<caihao_qiu>();//网络得到球数
    private List<Integer> list_jd_img_def;//jd_默认
    private List<Integer> list_jd_img;//jd—亮
    private List<Boolean> list_jd_flag;//jd——boolen

    private TextView guess_time;//时间文本
    Timer timer;
    private TimerTask task=null;
    int time_text_f=0;
    int time_text_s=10;

    //动画相关
    boolean flag=false;
    GestureDetector mygees;//手势
    private View guess_xiajiemian_all;//下面全部
    private ImageView guess_but_down;//点击动画按钮
    int top_height;
    int top_kaijiang;
    int w;
    int h;
    private LinearLayout all_guess_open_number;
    private Animation move_down;


    int  list_xiazhu_number;
    int list_xiazhu_jd_number;
    TextView text_list_xiazhu_jd_number;
    TextView text_list_xiazhu_number;
    TextView chongxin_xiazhu;
    private View please_wait;

    private LayoutInflater mInflater;
    private List<kaijiang_result> list_kaijiang_results;
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
                        jingd_caihao.setText( new DecimalFormat("#,##0").format(new Double(jsonObject.getJSONObject("user").getString("commissionPoints"))));//金豆
                    } else {
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
    private TextView jingd_caihao;
    private void init_jigncai_dingdan_click_meth() {
        jigncai_dingdan_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(guess_caihao.this,click_jingcaidingdan.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private TextView jigncai_dingdan_click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.guess_caihao);
        mInflater=LayoutInflater.from(guess_caihao.this);
        init();
        init_jigncai_dingdan_click_meth();
        init_user_info();
        init_wangluo();
        init_back();
        init_ok();
        init_guizhe();
        init_but_animation();//定义动画
        mygees=new GestureDetector(guess_caihao.this,new myGestureListener());
        guess_xiajiemian_all.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mygees.onTouchEvent(event);
                return true;
            }
        });

    }

    private List<open_qs> list_open_qs=new ArrayList<open_qs>();
    private String now_expect;
    private String next_expect;
    private View xianshikaijiang;
    private View dengdaikaijiang;
    String xuyaos_qishu;
    private void init_wangluo() {
        please_wait.setVisibility(View.VISIBLE);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_jincai;
        RequestParams requestParams=new RequestParams();
        requestParams.put("HMLX","2");
        asyncHttpClient.post(url, requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        if (!jsonObject.has("end_min")) {
                            init_wangluo();
                            return;
                        }
                        time_text_f = Integer.parseInt(jsonObject.getString("end_min"));

                        time_text_s = Integer.parseInt(jsonObject.getString("end_ss"));
                        if (time_text_f == 0 && time_text_s == 0) {
                            init_wangluo();
                            return;
                        } else {
                            String opening = jsonObject.getString("opening");
                            now_expect = jsonObject.getString("expect");
                            next_expect = jsonObject.getString("next_expect");
                            xuyaos_qishu = next_expect;
                            if ("true".equals(opening)) {
                                dengdaikaijiang.setVisibility(View.VISIBLE);
                                xianshikaijiang.setVisibility(View.GONE);
                                System.out.println("正在开奖");
                                now_expect = (Integer.parseInt(now_expect) + 1) + "";
                                next_expect = (Integer.parseInt(next_expect) ) + "";
                                xuyaos_qishu = now_expect;
                                init_xiaoshuaxin();
                            }
                            System.out.println(time_text_f + "{}" + time_text_s);
                            startTime();
                            JSONArray open_jiang = jsonObject.getJSONArray("open");
                            list_open_qs.clear();
                            list_kaijiang_results = new ArrayList<kaijiang_result>();
                            for (int i = 0; i < open_jiang.length(); i++) {
                                open_qs o1 = new open_qs();
                                o1.setKJQS(open_jiang.getJSONObject(i).getString("expect"));
                                o1.setGroup_1(open_jiang.getJSONObject(i).getString("group_1"));
                                o1.setGroup_2(open_jiang.getJSONObject(i).getString("group_2"));
                                o1.setGroup_3(open_jiang.getJSONObject(i).getString("group_3"));
                                o1.setGroup_num(open_jiang.getJSONObject(i).getString("group_num"));
                                String result = open_jiang.getJSONObject(i).getString("result");
                                String[] test = result.split(",");
                                System.out.println(test[0] + "," + test[1]);
                                o1.setResult_1(test[0]);
                                o1.setResult_2(test[1]);
                                list_open_qs.add(o1);
                                kaijiang_result kai1 = new kaijiang_result();
                                kai1.setQs(open_jiang.getJSONObject(i).getString("expect"));
                                kai1.setGroup_1(open_jiang.getJSONObject(i).getString("group_1"));
                                kai1.setGroup_2(open_jiang.getJSONObject(i).getString("group_2"));
                                kai1.setGroup_3(open_jiang.getJSONObject(i).getString("group_3"));
                                kai1.setGroup_num(open_jiang.getJSONObject(i).getString("group_num"));
                                kai1.setResult(open_jiang.getJSONObject(i).getString("result"));
                                List<String> list=new ArrayList<String>();
                                for (int j = 1; j <= 20; j++) {
                                    list.add(open_jiang.getJSONObject(i).getString("code_"+j));
                                }
                                kai1.setList_20(list);
                                list_kaijiang_results.add(kai1);
                            }

                            JSONArray Hm_list=jsonObject.getJSONArray("hm");
                            list_qiu.clear();
                            for (int i = 0; i < Hm_list.length(); i++) {
                                caihao_qiu qiu1=new caihao_qiu();
                                qiu1.setUUID(Hm_list.getJSONObject(i).getString("UUID"));
                                qiu1.setHMMC(Hm_list.getJSONObject(i).getString("HMMC"));
                                qiu1.setHMPL(Hm_list.getJSONObject(i).getString("HMPL"));
                                if(Hm_list.getJSONObject(i).has("SY")){
                                    qiu1.setSY(String.valueOf(Hm_list.getJSONObject(i).getInt("SY")));
                                }else{
                                    qiu1.setSY("500000");
                                }
                                list_qiu.add(qiu1);
                            }
                            JSONArray jd_list = jsonObject.getJSONArray("jd");
                            list_jd.clear();
                            for (int i = 0; i < jd_list.length(); i++) {
                                Jd jd = new Jd();
                                jd.setJDDW(jd_list.getJSONObject(i).getString("JDDW"));
                                jd.setJDGS(jd_list.getJSONObject(i).getString("JDGS"));
                                jd.setUUid(jd_list.getJSONObject(i).getString("UUID"));
                                list_jd.add(jd);
                            }

                            init_jbd(now_expect, next_expect);
                            init_juli();
                            init_chongx();
                        }


                    } else {
                        Toast.makeText(guess_caihao.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }
                    please_wait.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                please_wait.setVisibility(View.GONE);
                Toast.makeText(guess_caihao.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
//                finish();
            }


        });

    }

    private TextView jincai_qs;
    private TextView jincai_qs_next;
    private TextView jincai_group_1_1;
    private TextView jincai_group_2_2;
    private TextView jincai_group_3_3;
    private TextView jincai_group_num;
    List<View> list_qs_view=new ArrayList<View>();
    private void init_jbd(String my_qs, String next_qs) {
        jincai_qs.setText(my_qs);
        jincai_qs_next.setText(next_qs);
        jincai_group_1_1.setText(list_open_qs.get(0).getGroup_1());
        jincai_group_2_2.setText(list_open_qs.get(0).getGroup_2());
        jincai_group_3_3.setText(list_open_qs.get(0).getGroup_3());
        jincai_group_num.setText(list_open_qs.get(0).getGroup_num());

        list_qs_view.clear();
        all_guess_open_number.removeAllViews();
        for (int i=0;i<list_open_qs.size();i++){
            View qs_view=mInflater.inflate(R.layout.guess_qs_xiangp,null);
            TextView jincai_line1_1=(TextView)qs_view.findViewById(R.id.jincai_line1_1);
            TextView jincai_line1_2=(TextView)qs_view.findViewById(R.id.jincai_line1_2);
            TextView jincai_line1_3=(TextView)qs_view.findViewById(R.id.jincai_line1_3);
            TextView jincai_line1_4=(TextView)qs_view.findViewById(R.id.jincai_line1_4);
            TextView jincai_line1_5=(TextView)qs_view.findViewById(R.id.jincai_line1_5);
            jincai_line1_1.setTextSize(12);
            jincai_line1_2.setTextSize(12);
            jincai_line1_3.setTextSize(12);
            jincai_line1_4.setTextSize(12);
            jincai_line1_5.setTextSize(12);
            jincai_line1_1.setText(list_open_qs.get(i).getKJQS());
            jincai_line1_2.setText(list_open_qs.get(i).getGroup_1()+list_open_qs.get(i).getGroup_2()+list_open_qs.get(i).getGroup_3());
            jincai_line1_3.setText(list_open_qs.get(i).getGroup_num());
            jincai_line1_4.setText(list_open_qs.get(i).getResult_1());
            jincai_line1_5.setText(list_open_qs.get(i).getResult_2());
            all_guess_open_number.addView(qs_view);
            list_qs_view.add(qs_view);
        }
        View more_qs_view=mInflater.inflate(R.layout.item_more_kaijiang_qishu,null);
        more_qs_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guess_caihao.this, more_qs2.class);
                startActivity(intent);
                finish();

            }
        });
        all_guess_open_number.addView(more_qs_view);
        for (int i=0;i<list_qs_view.size();i++){
            final int a=i;
            list_qs_view.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(guess_caihao.this,guess_open2.class);
                    intent.putExtra("kaijiang_results", list_kaijiang_results.get(a));
                    startActivity(intent);
                    finish();
                }
            });
        }



    }

    List<Integer> list_jd_jmg_def=new ArrayList<Integer>();//几个没发光的
    List<Integer> list_jd_img_fg=new ArrayList<Integer>();//几个发光的
    private LinearLayout xiajiemia_1;
    private LinearLayout xiajiemia_2;
    private LinearLayout xiajiemia_3;
    private LinearLayout xiajiemia_4;
    private LinearLayout xiajiemia_6;
    private LinearLayout xiajiemia_7;
    List<View> list_27=new ArrayList<View>();
    List<View> list_8=new ArrayList<View>();
    private void init_juli() {
        list_27.clear();
        xiajiemia_1.removeAllViews();
        xiajiemia_2.removeAllViews();
        xiajiemia_3.removeAllViews();
        xiajiemia_4.removeAllViews();
        for (int i=0;i<list_qiu.size();i++) {
            if (i <= 6) {
                LinearLayout re = new LinearLayout(this);//一个item
                re.setGravity(Gravity.CENTER_HORIZONTAL);
                re.setBackgroundResource(R.drawable.caihao_but_skin);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(whith_rct_qiu, whith_rct_qiu+5);
                lp.setMargins(20, 10, 20, 10);
                re.setLayoutParams(lp);
                TextView textView=new TextView(this);
                textView.setText(list_qiu.get(i).getHMMC());
                re.addView(textView);
                xiajiemia_1.addView(re);
                list_27.add(re);
            }
            if (i > 6 && i <= 13) {
                LinearLayout re = new LinearLayout(this);//一个item
                re.setGravity(Gravity.CENTER_HORIZONTAL);
                re.setBackgroundResource(R.drawable.caihao_but_skin);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(whith_rct_qiu, whith_rct_qiu+5);
                lp.setMargins(20, 10, 20, 10);
                re.setLayoutParams(lp);
                TextView textView=new TextView(this);
                textView.setText(list_qiu.get(i).getHMMC());
                re.addView(textView);
                xiajiemia_2.addView(re);
                list_27.add(re);
            }
            if (i > 13 && i <= 20) {
                LinearLayout re = new LinearLayout(this);//一个item
                re.setGravity(Gravity.CENTER_HORIZONTAL);
                re.setBackgroundResource(R.drawable.caihao_but_skin);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(whith_rct_qiu, whith_rct_qiu+5);
                lp.setMargins(20, 10, 20, 10);
                re.setLayoutParams(lp);
                TextView textView=new TextView(this);
                textView.setText(list_qiu.get(i).getHMMC());
                re.addView(textView);
                xiajiemia_3.addView(re);
                list_27.add(re);

            }
            if (i > 20 && i <= 27) {
                LinearLayout re = new LinearLayout(this);//一个item
                re.setGravity(Gravity.CENTER_HORIZONTAL);
                re.setBackgroundResource(R.drawable.caihao_but_skin);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(whith_rct_qiu, whith_rct_qiu+5);
                lp.setMargins(20, 10, 20, 10);
                re.setLayoutParams(lp);
                TextView textView=new TextView(this);
                textView.setText(list_qiu.get(i).getHMMC());
                re.addView(textView);
                xiajiemia_4.addView(re);
                list_27.add(re);
            }
        }
        list_8.clear();
        xiajiemia_6.removeAllViews();
        xiajiemia_7.removeAllViews();
        for (int i=0;i<list_jd.size();i++){
            if(i<=3){
                LinearLayout re=new LinearLayout(this);
                re.setGravity(Gravity.CENTER);
                if (i==0){
                    re.setBackgroundResource(list_jd_img_fg.get(i));
                }else {
                    re.setBackgroundResource(list_jd_jmg_def.get(i));
                }
                re.setId(Integer.parseInt(list_jd.get(i).getJDGS()));
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(whith_rct_jd+10,whith_rct_jd+10);
                lp.setMargins(10, 10, 10, 10);
                re.setLayoutParams(lp);
                TextView shulia=new TextView(this);
                shulia.setGravity(Gravity.CENTER_HORIZONTAL);
                shulia.setTextSize(12);
                shulia.setText(list_jd.get(i).getJDDW());
                re.addView(shulia);
                list_8.add(re);
                xiajiemia_6.addView(re);
            }
            if(i>3){
                LinearLayout re=new LinearLayout(this);
                re.setGravity(Gravity.CENTER);
                re.setBackgroundResource(list_jd_jmg_def.get(i));
                re.setId(Integer.parseInt(list_jd.get(i).getJDGS()));
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(whith_rct_jd+10,whith_rct_jd+10);
                lp.setMargins(10, 10, 10, 10);
                re.setLayoutParams(lp);
                TextView shulia=new TextView(this);
                shulia.setGravity(Gravity.CENTER_HORIZONTAL);
                shulia.setTextSize(12);
                shulia.setText(list_jd.get(i).getJDDW());
                re.addView(shulia);
                list_8.add(re);
                xiajiemia_7.addView(re);
            }

        }
        init_panduan();
        init_8click();
        init_27click();
    }
    private void init_panduan() {

        list_jd_select_panduna.clear();
        list_jd_select_panduna.add(true);
        for (int i=1;i<list_jd.size();i++){
            list_jd_select_panduna.add(false);
        }
    }
    private List<Boolean> list_jd_select_panduna=new ArrayList<Boolean>();
    private void init_8click() {
        for (int i=0;i<list_8.size();i++){
            final int a=i;
            list_8.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i=0;i<list_8.size();i++){
                        list_jd_select_panduna.set(i,false);
                        list_8.get(i).setBackgroundResource(list_jd_jmg_def.get(i));
                    }
                    list_jd_select_panduna.set(a,true);
                    v.setBackgroundResource(list_jd_img_fg.get(a));

                }
            });
        }

    }

    int test_jd_50=0;
    private void init_27click() {
        for (int i=0;i<list_27.size();i++){
            final int a=i;
            list_27.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if (!v.isSelected()){
                        list_xiazhu_jd_number=0;
                    }
                    clearer2();
                    for (int i=0;i<list_27.size();i++){
                        list_27.get(i).setSelected(false);
                    }
                    v.setSelected(true);

                    for (int i=0;i<list_jd.size();i++){
                        if(list_jd_select_panduna.get(i)){

                            test_jd_50= list_xiazhu_jd_number+Integer.parseInt(list_jd.get(i).getJDGS());
                            if(Integer.parseInt(list_qiu.get(a).getSY())<test_jd_50){
                                Toast.makeText(guess_caihao.this,"单注金额超过50万",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            list_xiazhu_jd_number= list_xiazhu_jd_number+Integer.parseInt(list_jd.get(i).getJDGS());

                        }
                    }
                    text_list_xiazhu_jd_number.setText("" + list_xiazhu_jd_number);
                }
            });
        }

    }
    //    private void init_12_money() {
//        //每个按钮的钱；
//        list_money.clear();
//        for(int i=0;i<list_27.size();i++){
//            list_money.add(0);
//        }
//    }
    private void init_chongx() {
        chongxin_xiazhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearer();
            }
        });
    }

    private void clearer() {
        //清除
        for (int i=0;i<12;i++){
            list_27.get(i).setSelected(false);
        }
//        init_12_money();
        list_xiazhu_jd_number=0;
        text_list_xiazhu_jd_number.setText("" + list_xiazhu_jd_number);
    }
    private void clearer2() {
        //清除
        for (int i=0;i<12;i++){
            list_27.get(i).setSelected(false);
        }
        text_list_xiazhu_jd_number.setText(""+list_xiazhu_jd_number);
    }

    private void init_xiaoshuaxin() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_open_shuaxin;
        asyncHttpClient.post(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        //判断期数是否最新不是最新就在请求知道成功
                        JSONArray open_jiang = jsonObject.getJSONArray("open");
                        list_open_qs.clear();
                        list_kaijiang_results = new ArrayList<kaijiang_result>();
                        for (int i = 0; i < open_jiang.length(); i++) {
                            open_qs o1 = new open_qs();
                            o1.setKJQS(open_jiang.getJSONObject(i).getString("expect"));
                            o1.setGroup_1(open_jiang.getJSONObject(i).getString("group_1"));
                            o1.setGroup_2(open_jiang.getJSONObject(i).getString("group_2"));
                            o1.setGroup_3(open_jiang.getJSONObject(i).getString("group_3"));
                            o1.setGroup_num(open_jiang.getJSONObject(i).getString("group_num"));
                            String result = open_jiang.getJSONObject(i).getString("result");
                            String[] test = result.split(",");
                            System.out.println(test[0] + "," + test[1]);
                            o1.setResult_1(test[0]);
                            o1.setResult_2(test[1]);
                            list_open_qs.add(o1);
                            kaijiang_result kai1 = new kaijiang_result();
                            kai1.setQs(open_jiang.getJSONObject(i).getString("expect"));
                            kai1.setGroup_1(open_jiang.getJSONObject(i).getString("group_1"));
                            kai1.setGroup_2(open_jiang.getJSONObject(i).getString("group_2"));
                            kai1.setGroup_3(open_jiang.getJSONObject(i).getString("group_3"));
                            kai1.setGroup_num(open_jiang.getJSONObject(i).getString("group_num"));
                            kai1.setResult(open_jiang.getJSONObject(i).getString("result"));
                            List<String> list=new ArrayList<String>();
                            for (int j = 1; j <= 20; j++) {
                                list.add(open_jiang.getJSONObject(i).getString("code_"+j));
                            }
                            kai1.setList_20(list);
                            list_kaijiang_results.add(kai1);
                        }
                        if (!list_open_qs.get(0).getKJQS().equals(xuyaos_qishu)) {
                            System.out.println("需要刷新");
                            thread1= new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(5000);
                                        init_xiaoshuaxin();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            thread1.start();
                        } else {
                            Message message = handler2.obtainMessage();
                            message.arg1 = 1;
                            handler2.sendMessage(message);

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                Toast.makeText(guess_caihao.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
//                finish();
            }


        });
    }
    Thread thread1;
    //可以刷新了
    Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1==1){
                init_user_info();
                dengdaikaijiang.setVisibility(View.GONE);
                xianshikaijiang.setVisibility(View.VISIBLE);
                init_jbd2();

            }
        }
    };
    private void init_jbd2() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jincai_group_1_1.setText(list_open_qs.get(0).getGroup_1());
        jincai_group_2_2.setText(list_open_qs.get(0).getGroup_2());
        jincai_group_3_3.setText(list_open_qs.get(0).getGroup_3());
        jincai_group_num.setText(list_open_qs.get(0).getGroup_num());
        list_qs_view.clear();
        all_guess_open_number.removeAllViews();
        for (int i=0;i<list_open_qs.size();i++){
            View qs_view=mInflater.inflate(R.layout.guess_qs_xiangp,null);
            TextView jincai_line1_1=(TextView)qs_view.findViewById(R.id.jincai_line1_1);
            TextView jincai_line1_2=(TextView)qs_view.findViewById(R.id.jincai_line1_2);
            TextView jincai_line1_3=(TextView)qs_view.findViewById(R.id.jincai_line1_3);
            TextView jincai_line1_4=(TextView)qs_view.findViewById(R.id.jincai_line1_4);
            TextView jincai_line1_5=(TextView)qs_view.findViewById(R.id.jincai_line1_5);
            jincai_line1_1.setTextSize(12);
            jincai_line1_2.setTextSize(12);
            jincai_line1_3.setTextSize(12);
            jincai_line1_4.setTextSize(12);
            jincai_line1_5.setTextSize(12);
            jincai_line1_1.setText(list_open_qs.get(i).getKJQS());
            jincai_line1_2.setText(list_open_qs.get(i).getGroup_1()+list_open_qs.get(i).getGroup_2()+list_open_qs.get(i).getGroup_3());
            jincai_line1_3.setText(list_open_qs.get(i).getGroup_num());
            jincai_line1_4.setText(list_open_qs.get(i).getResult_1());
            jincai_line1_5.setText(list_open_qs.get(i).getResult_2());
            all_guess_open_number.addView(qs_view);
            list_qs_view.add(qs_view);
        }
        View more_qs_view=mInflater.inflate(R.layout.item_more_kaijiang_qishu,null);
        more_qs_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guess_caihao.this, more_qs2.class);
                startActivity(intent);
                finish();
            }
        });
        all_guess_open_number.addView(more_qs_view);
        for (int i=0;i<list_qs_view.size();i++){
            final int a=i;
            list_qs_view.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(guess_caihao.this,guess_open2.class);
                    intent.putExtra("kaijiang_results", list_kaijiang_results.get(a));
                    startActivity(intent);
                    finish();
                }
            });
        }




    }

    private void init_guizhe() {
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(guess_caihao.this,guize_jingcai.class);
                startActivity(intent);
            }
        });
    }


    boolean bbq=false;
    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(msg.arg1!=-2){
                if (bbq){
                    bbq=false;
                }
                if(msg.arg1<10){
                    guess_time.setText(msg.arg2+":0"+msg.arg1);
                }else{
                    guess_time.setText(msg.arg2+":"+msg.arg1);

                }
            }else if(msg.arg1==-2){
                if(!bbq){

                    bbq=true;
                    System.out.println("出现了好几次呢");
                    timer.cancel();
                    clearer();
                    init_wangluo();
                    return;
                }
            }
        }
    };

    public void startTime(){
        timer=new Timer();
        final boolean[] yigesuo = {false};
        task=new TimerTask() {
            @Override
            public void run() {
                Message message=mhandler.obtainMessage();
                System.out.println(time_text_f+","+time_text_s);
                if (time_text_s==0){
                    if(time_text_f==0&&(!yigesuo[0])){
                        yigesuo[0] =true;
                        message.arg1=-2;
                        mhandler.sendMessage(message);

                        return;
                    }
                    time_text_s=60;
                    time_text_f--;
                }
                time_text_s--;
                message.arg1=time_text_s;
                message.arg2=time_text_f;
                mhandler.sendMessage(message);
            }
        };
        timer.schedule(task, 1000, 1000);
    }
    //手势
    class myGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {


            if(e2.getY()-e1.getY()>50){
                if(!flag){
                    for (int i=0;i<26;i++){
                        list_27.get(i).setEnabled(false);
                    }
                    int height = all_guess_open_number_da.getMeasuredHeight();
                    down_move_shuxingdonghua(guess_xiajiemian_all, height);
                    flag=true;
                    guess_but_down.setImageResource(R.mipmap.all_guess_shangla);
                }
            }
            if(e1.getY()-e2.getY()>50){
                if(flag){
                    for (int i=0;i<26;i++){
                        list_27.get(i).setEnabled(true);
                    }
                    down_move_shuxingdonghua(guess_xiajiemian_all,0);
                    flag=false;
                    guess_but_down.setImageResource(R.mipmap.all_guess_xiala);
                }
            }

            return super.onFling(e1, e2, velocityX, velocityY);

        }
    }
    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                try {
//                    Runtime runtime = Runtime.getRuntime();
//                    runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
//                } catch (IOException e) {
//                    Log.e("Exception when doBack", e.toString());
//                }
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(guess_caihao.this,guess.class);
        startActivity(intent);
        finish();
    }


    int whith_rct_jd;
    int whith_rct_qiu;
    private LinearLayout all_guess_open_number_da;
    private void init() {
        jigncai_dingdan_click= (TextView) findViewById(R.id.jigncai_dingdan_click);
        jingd_caihao= (TextView) findViewById(R.id.jingd_caihao);
        jincai_ok= (Button) findViewById(R.id.jincai_ok);
        list_jd_jmg_def.add(R.mipmap.jd1);
        list_jd_jmg_def.add(R.mipmap.jd2);
        list_jd_jmg_def.add(R.mipmap.jd3);
        list_jd_jmg_def.add(R.mipmap.jd4);
        list_jd_jmg_def.add(R.mipmap.jd5);
        list_jd_jmg_def.add(R.mipmap.jd6);
        list_jd_jmg_def.add(R.mipmap.jd7);
        list_jd_jmg_def.add(R.mipmap.jd8);
        list_jd_img_fg.add(R.mipmap.jd1_fg);
        list_jd_img_fg.add(R.mipmap.jd2_fg);
        list_jd_img_fg.add(R.mipmap.jd3_fg);
        list_jd_img_fg.add(R.mipmap.jd4_fg);
        list_jd_img_fg.add(R.mipmap.jd5_fg);
        list_jd_img_fg.add(R.mipmap.jd6_fg);
        list_jd_img_fg.add(R.mipmap.jd7_fg);
        list_jd_img_fg.add(R.mipmap.jd8_fg);


        whith_rct_qiu=( MainActivity.screenWidth-8* DensityUtil.dip2px(guess_caihao.this, 20))/7;
        whith_rct_jd=( MainActivity.screenWidth-5* DensityUtil.dip2px(guess_caihao.this, 30))/4;
        xiajiemia_1= (LinearLayout) findViewById(R.id.xiajiemia_1);
        xiajiemia_2= (LinearLayout) findViewById(R.id.xiajiemia_2);
        xiajiemia_3= (LinearLayout) findViewById(R.id.xiajiemia_3);
        xiajiemia_4= (LinearLayout) findViewById(R.id.xiajiemia_4);
        xiajiemia_6= (LinearLayout) findViewById(R.id.xiajiemia_6);
        xiajiemia_7= (LinearLayout) findViewById(R.id.xiajiemia_7);
        jincai_qs= (TextView) findViewById(R.id.jincai_qs);
        jincai_qs_next= (TextView) findViewById(R.id.jincai_qs_next);
        jincai_group_1_1= (TextView) findViewById(R.id.jincai_group_1_1);
        jincai_group_2_2= (TextView) findViewById(R.id.jincai_group_2_2);
        jincai_group_3_3= (TextView) findViewById(R.id.jincai_group_3_3);
        jincai_group_num= (TextView) findViewById(R.id.jincai_group_num);

        xianshikaijiang=findViewById(R.id.xianshikaijiang);
        dengdaikaijiang=findViewById(R.id.dengdaikaijiang);
        please_wait=findViewById(R.id.please_wait);
        text_list_xiazhu_jd_number= (TextView) findViewById(R.id.list_xiazhu_jd_number);
        chongxin_xiazhu= (TextView) findViewById(R.id.chongxin_xiazhu);
        all_guess_open_number_da= (LinearLayout) findViewById(R.id.all_guess_open_number_da);
        all_guess_open_number= (LinearLayout) findViewById(R.id.all_guess_open_number);
        guess_but_down= (ImageView) findViewById(R.id.guess_but_down);
        guess_xiajiemian_all=findViewById(R.id.guess_xiajiemian_all);

        guess_time= (TextView) findViewById(R.id.guess_time);
        Typeface type = Typeface.createFromAsset(guess_caihao.this.getAssets(), "yjsz.ttf");
        guess_time.setTypeface(type);
        //--------- 金豆

        list_jd_img_def=new ArrayList<Integer>();
        list_jd_img_def.add(R.mipmap.jd1);
        list_jd_img_def.add(R.mipmap.jd2);
        list_jd_img_def.add(R.mipmap.jd3);
        list_jd_img_def.add(R.mipmap.jd4);
        list_jd_img_def.add(R.mipmap.jd5);
        list_jd_img_def.add(R.mipmap.jd6);
        list_jd_img_def.add(R.mipmap.jd7);
        list_jd_img_def.add(R.mipmap.jd8);
        list_jd_flag=new ArrayList<Boolean>();
        list_jd_flag.add(true);
        for (int i=0;i<7;i++){
            list_jd_flag.add(false);
        }

        guess_topbar=findViewById(R.id.guess_caihao_topbar);
        back= guess_topbar.findViewById(R.id.logreg_left);
        right= guess_topbar.findViewById(R.id.logreg_right);
    }
    private View right;

    List<order_item> list_objects;
    Button jincai_ok;
    private void init_ok() {
        list_objects=new ArrayList<order_item>();
        jincai_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok_panduan = false;
                final JSONObject object = new JSONObject();//创建一个总的对象，这个对象对整个json串
                JSONArray jsonarray = new JSONArray();//json数组，里面包含的内容为pet的所有对象
                try {
                    object.put("DDZJ", list_xiazhu_jd_number);
                    object.put("ZFYHID", User.getuser().getUser_uuid());
                    object.put("ZFLX", 2);
                    object.put("ZFFS", 0);
                    object.put("EXPECT", jincai_qs_next.getText());
                    for (int i = 0; i < list_27.size(); i++) {
                        if (list_27.get(i).isSelected()) {
                            order_item order_item1 = new order_item();
                            ok_panduan = true;
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("SPID", list_qiu.get(i).getUUID());
                            order_item1.setName(list_qiu.get(i).getHMMC());
                            order_item1.setAll_bean(String.valueOf(list_xiazhu_jd_number));
                            order_item1.setPl(list_qiu.get(i).getHMPL());
                            order_item1.setUuid(list_qiu.get(i).getUUID());
                            order_item1.setSY(list_qiu.get(i).getSY());
                            list_objects.add(order_item1);
                            jsonObject.put("GMDJ", String.valueOf(list_xiazhu_jd_number));
                            jsonObject.put("GMZJ", String.valueOf(list_xiazhu_jd_number));
                            jsonObject.put("GMSL", "1");
                            jsonarray.put(jsonObject);

                        }
                    }
                    if (!ok_panduan) {
                        Toast.makeText(guess_caihao.this, "您未下注，请点击下注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    object.put("INFO", jsonarray);
                } catch (Exception e) {

                }


                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.setTimeout(20000);
                String url = all_url.url_before_order;
                RequestParams requestParams = new RequestParams();
                requestParams.put("ZFYHID", User.getuser().getUser_uuid());
                requestParams.put("DDZJ", String.valueOf(list_xiazhu_jd_number));

                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);
                        System.out.println(jsonObject.toString());
                        try {
                            if ("true".equals(jsonObject.getString("status"))) {
                                Intent intent = new Intent(guess_caihao.this, jincai_order.class);
                                intent.putExtra("object", object.toString());
                                intent.putExtra("list_objects", (Serializable) list_objects);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(guess_caihao.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }

                            please_wait.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        super.onFailure(throwable);
                        please_wait.setVisibility(View.GONE);
                        Toast.makeText(guess_caihao.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
//                finish();
                    }


                });
            }
        });
    }


    private void getmove(View v,Animation animation) {
        v.startAnimation(animation);
    }
    //下拉动画
    private void init_but_animation() {
        guess_but_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    for (int i = 0; i < 26; i++) {
                        list_27.get(i).setEnabled(false);
                    }
                    int height = all_guess_open_number_da.getMeasuredHeight();
                    down_move_shuxingdonghua(guess_xiajiemian_all, height);
                    flag = true;
                    guess_but_down.setImageResource(R.mipmap.all_guess_shangla);
                } else {
                    for (int i = 0; i < 26; i++) {
                        list_27.get(i).setEnabled(true);
                    }
                    down_move_shuxingdonghua(guess_xiajiemian_all, 0);
                    flag = false;
                    guess_but_down.setImageResource(R.mipmap.all_guess_xiala);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null){

            timer.cancel();
        }
        if (thread1!=null){

            thread1.interrupt();
        }

    }
    private void down_move_shuxingdonghua(View view,int height){
        ObjectAnimator animator=ObjectAnimator.ofFloat(view,"translationY",height);
        animator.setDuration(300);
        animator.start();
    }
}
