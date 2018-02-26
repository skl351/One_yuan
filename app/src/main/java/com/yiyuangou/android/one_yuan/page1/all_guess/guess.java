package com.yiyuangou.android.one_yuan.page1.all_guess;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.adapter.jiugong_guess_adapter;
import com.yiyuangou.android.one_yuan.bean.Hm;
import com.yiyuangou.android.one_yuan.bean.Jd;
import com.yiyuangou.android.one_yuan.bean.hm_key_value;
import com.yiyuangou.android.one_yuan.bean.kaijiang_result;
import com.yiyuangou.android.one_yuan.bean.open_qs;
import com.yiyuangou.android.one_yuan.bean.order_item;
import com.yiyuangou.android.one_yuan.bean.room;
import com.yiyuangou.android.one_yuan.page4.user_login;
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
 * Created by android on 2016/3/26.
 */
public class guess extends Activity {
    private View xianshikaijiang;
    private View dengdaikaijiang;
    boolean panduan_caihaoyoulma = false;
    public static String fangjian_id;//用来标示房间号码
    public static String fangjian_mes;//用来标示房间信息

    String[] panduan_has_hmmc = {"大", "小", "单", "双", "大单", "大双", "小单", "小双"};

    private int z = 0;

    private List<room> list_room;

    private TextView jincai_qs;
    private TextView jincai_qs_next;
    private TextView jincai_group_1_1;
    private TextView jincai_group_2_2;
    private TextView jincai_group_3_3;
    private TextView jincai_group_num;

    private View back;
    private View guess_topbar;
    int top_height;
    int top_kaijiang;
    int w;
    int h;
    TextView chongxin_xiazhu;

    Timer timer;
    private TimerTask task = null;
    private ImageView guess_but_down;//点击动画按钮
    private RelativeLayout guess_xiajiemian_all;
    private Animation move_down;
    private LinearLayout all_guess_open_number;
    private LinearLayout all_guess_open_number_da;

    GestureDetector mygees;//手势
    private View all_guess_home;//标题
    private View all_guess_kaijiang;//标题

    int time_text_f = 0;
    int time_text_s = 0;
    private TextView guess_time;

    private View please_wait;
    private int whith_rct;//一个按钮的宽
    private LinearLayout xiajiemia_1;
    private LinearLayout xiajiemia_2;
    private LinearLayout xiajiemia_3;
    private LinearLayout xiajiemia_4;
    private LinearLayout xiajiemia_5;
    private LinearLayout xiajiemia_6;
    private LinearLayout xiajiemia_7;
    List<LinearLayout> list_12 = new ArrayList<LinearLayout>();
    List<View> list_8 = new ArrayList<View>();
    List<Hm> list_Hm = new ArrayList<Hm>();//网络得到号码数
    List<Jd> list_jd = new ArrayList<Jd>();//网络得到金豆数
    List<String> list_has = new ArrayList<String>();//看是否能下的东西
    List<Integer> list_jd_jmg_def = new ArrayList<Integer>();//几个没发光的
    List<Integer> list_jd_img_fg = new ArrayList<Integer>();//几个发光的
    int list_xiazhu_number;//下注数量
    int list_xiazhu_jd_number;//下注总数
    private List<Boolean> list_zhushu_panduan = new ArrayList<Boolean>();//判断哪些已经下注的
    private List<Boolean> list_jd_select_panduna = new ArrayList<Boolean>();
    TextView text_list_xiazhu_jd_number;
    TextView text_list_xiazhu_number;
    private List<View> list_small_jd = new ArrayList<View>();
    private List<Integer> list_small_img = new ArrayList<Integer>();
    private List<ImageView> list_fang_difang = new ArrayList<ImageView>();
    private List<open_qs> list_open_qs = new ArrayList<open_qs>();
    boolean yonglaipanduan = true;
    List<Integer> list_money = new ArrayList<Integer>();//点了几个，就几个money
    private Button jincai_ok;
    private LinearLayout xiajiemia_8;
    private String now_expect;
    private String next_expect;
    private List<kaijiang_result> list_kaijiang_results;

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        if (thread1 != null) {

            thread1.interrupt();
        }
        if (alert2 != null) {
            if (alert2.isShowing()) {
                alert2.dismiss();
            }
        }
        if (alert != null) {
            if (alert.isShowing()) {
                alert.dismiss();
            }
        }

    }

    public static void init_user_info() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_user_info;
        asyncHttpClient.setTimeout(20000);
        RequestParams requestParams = new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        System.out.println(jsonObject);
                        jincai_jd_info.setText(new DecimalFormat("#,##0").format(new Double(jsonObject.getJSONObject("user").getString("commissionPoints"))));//金豆
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

    private LayoutInflater mInflater;
    private static TextView jincai_jd_info;
    private TextView jigncai_dingdan_click;
    private ImageView img_jiugong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.all_guess);
        mInflater = LayoutInflater.from(guess.this);
        init();
        init_guizhe();//规则
        init_user_info();//用户信息
        init_wangluo();
        init_ok();//可以的按钮
        init_back();//返回
        init_jigncai_dingdan_click_meth();
        init_but_animation();
        mygees = new GestureDetector(guess.this, new myGestureListener());
        guess_xiajiemian_all.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mygees.onTouchEvent(event);
                return true;
            }
        });


    }

    View view;


    private void init_guizhe() {
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guess.this, guize_jingcai.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init_jigncai_dingdan_click_meth() {
        jigncai_dingdan_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guess.this, click_jingcaidingdan.class);
                startActivity(intent);
                finish();
            }
        });
    }

    List<order_item> list_objects;
    List<hm_key_value> list_hm_key;

    private void init_ok() {

        jincai_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jincai_ok.setEnabled(false);
                if (!MainActivity.IS_login) {
                    Intent intent = new Intent(guess.this, user_login.class);
                    startActivity(intent);
                    finish();
                }
                boolean ok_panduan = false;
                final JSONObject object = new JSONObject();//创建一个总的对象，这个对象对整个json串
                JSONArray jsonarray = new JSONArray();//json数组，里面包含的内容为pet的所有对象
                try {
                    object.put("DDZJ", list_xiazhu_jd_number);
                    object.put("ZFYHID", User.getuser().getUser_uuid());
                    object.put("ZFLX", 1);
                    object.put("ZFFS", 0);
                    object.put("EXPECT", jincai_qs_next.getText());

                    list_objects = new ArrayList<order_item>();
                    list_hm_key = new ArrayList<hm_key_value>();
                    for (int i = 0; i < list_money.size(); i++) {
                        if (list_money.get(i) != 0) {
                            order_item order_item1 = new order_item();
                            ok_panduan = true;
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("SPID", list_Hm.get(i).getUuid());
                            hm_key_value hm1 = new hm_key_value();
                            hm1.setKey(list_Hm.get(i).getUuid());
                            hm1.setVlaue(list_Hm.get(i).getHMMC());
                            list_hm_key.add(hm1);
                            order_item1.setName(list_Hm.get(i).getHMMC());
                            order_item1.setAll_bean(list_money.get(i).toString());
                            order_item1.setPl(list_Hm.get(i).getHMPL());
                            order_item1.setUuid(list_Hm.get(i).getUuid());
                            order_item1.setSY(list_Hm.get(i).getSY());
                            list_objects.add(order_item1);
                            jsonObject.put("GMDJ", list_money.get(i));
                            jsonObject.put("GMZJ", list_money.get(i));
                            jsonObject.put("GMSL", "1");
                            jsonarray.put(jsonObject);

                        }
                    }
                    if (!ok_panduan) {
                        jincai_ok.setEnabled(true);
                        Toast.makeText(guess.this, "您未下注，请点击下注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    object.put("INFO", jsonarray);
                } catch (JSONException e) {
                    e.printStackTrace();
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

                                Intent intent = new Intent(guess.this, jincai_order.class);
                                intent.putExtra("object", object.toString());
                                intent.putExtra("list_objects", (Serializable) list_objects);
                                intent.putExtra("list_Hm", (Serializable) list_Hm);//有一个sy
                                intent.putExtra("list_hm_key", (Serializable) list_hm_key);//键值对
                                startActivity(intent);
                                jincai_ok.setEnabled(true);
                                finish();
                            } else {
                                //金豆不足——或者
                                Toast.makeText(guess.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                jincai_ok.setEnabled(true);
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
                        Toast.makeText(guess.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    String xuyaos_qishu;
    boolean sayaya = false;

    private void init_wangluo() {
        please_wait.setVisibility(View.VISIBLE);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_jincai;
        RequestParams requestParams = new RequestParams();
        requestParams.put("HMLX", "1");
        requestParams.put("userId", User.getuser().getUser_uuid());
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        if (!jsonObject.has("end_min")) {
                            System.out.println("----2----"+jsonObject);
                            init_wangluo();
                            return;
                        }
                        time_text_f = Integer.parseInt(jsonObject.getString("end_min"));
                        time_text_s = Integer.parseInt(jsonObject.getString("end_ss"));
                            System.out.println(time_text_f + "开始" + time_text_s);
                        if (time_text_f == 0 && time_text_s == 0) {
                            System.out.println("----3----"+jsonObject);
                            sayaya = true;
                            init_wangluo();
                            return;
                        } else {
                            System.out.println("-----4---"+jsonObject);
                            String opening = jsonObject.getString("opening");
                            now_expect = jsonObject.getString("expect");
                            next_expect = jsonObject.getString("next_expect");
                            xuyaos_qishu = next_expect;
                            if ("true".equals(opening)) {
                                dengdaikaijiang.setVisibility(View.VISIBLE);
                                xianshikaijiang.setVisibility(View.GONE);
                                now_expect = (Integer.parseInt(now_expect) ) + 1+"";
                                next_expect = (Integer.parseInt(next_expect) ) + "";
                                xuyaos_qishu = now_expect;
                                clearer();
                                init_xiaoshuaxin();
                            }
//                            System.out.println(time_text_f + "{}" + time_text_s);
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
//                                System.out.println(test[0] + "," + test[1]);
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
                                List<String> list = new ArrayList<String>();
                                for (int j = 1; j <= 20; j++) {
                                    list.add(open_jiang.getJSONObject(i).getString("code_" + j));
                                }
                                kai1.setList_20(list);
                                list_kaijiang_results.add(kai1);
                            }
                            JSONArray hm_list = jsonObject.getJSONArray("hm");
                            list_Hm.clear();
                            //list_Hm -------得到的号码数字
                            for (int i = 0; i < hm_list.length(); i++) {
                                Hm hm = new Hm();
                                hm.setHMLX(hm_list.getJSONObject(i).getString("HMLX"));
                                hm.setHMMC(hm_list.getJSONObject(i).getString("HMMC"));
                                hm.setHMPL(hm_list.getJSONObject(i).getString("HMPL"));
                                hm.setUuid(hm_list.getJSONObject(i).getString("UUID"));
                                if (hm_list.getJSONObject(i).has("SY")) {
                                    hm.setSY(String.valueOf(hm_list.getJSONObject(i).getInt("SY")));
                                } else {
                                    hm.setSY("500000");
                                }
                                list_Hm.add(hm);
                            }
                            //list_jd------得到的金豆的数量
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
                            init_panduan();
                            init_juli();
                            if (jsonObject.has("has")) {
                                JSONArray has_list = jsonObject.getJSONArray("has");
                                list_has.clear();
                                for (int i = 0; i < has_list.length(); i++) {
                                    String hmmc = has_list.getJSONObject(i).getString("hmmc");
                                    list_has.add(hmmc);
                                }
                                System.out.println("看一下这个" + has_list);
                                if (limit_boolen) {
                                    init_limit();//用来限制
                                }

                            }

                            init_chongx();
                            yonglaipanduan = false;
                        }


                    } else {
                        Toast.makeText(guess.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
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
                finish();
            }


        });

    }

    boolean limit_boolen = false;

    private void init_limit() {

        for (int i = 0; i < list_has.size(); i++) {
            for (int j = 0; j < panduan_has_hmmc.length; j++) {
                if (panduan_has_hmmc[j].equals(list_has.get(i))) {
                    case_panduan(j);
                }
            }


        }

    }

    List<View> list_qs_view = new ArrayList<View>();

    private void init_jbd(String my_qs, String next_qs) {
        jincai_qs.setText(my_qs);
        jincai_qs_next.setText(next_qs);

        jincai_group_1_1.setText(list_open_qs.get(0).getGroup_1());
        jincai_group_2_2.setText(list_open_qs.get(0).getGroup_2());
        jincai_group_3_3.setText(list_open_qs.get(0).getGroup_3());
        jincai_group_num.setText(list_open_qs.get(0).getGroup_num());
        list_qs_view.clear();

        all_guess_open_number.removeAllViews();
        for (int i = 0; i < list_open_qs.size(); i++) {
            View qs_view = mInflater.inflate(R.layout.guess_qs_xiangp, null);
            TextView jincai_line1_1 = (TextView) qs_view.findViewById(R.id.jincai_line1_1);
            TextView jincai_line1_2 = (TextView) qs_view.findViewById(R.id.jincai_line1_2);
            TextView jincai_line1_3 = (TextView) qs_view.findViewById(R.id.jincai_line1_3);
            TextView jincai_line1_4 = (TextView) qs_view.findViewById(R.id.jincai_line1_4);
            TextView jincai_line1_5 = (TextView) qs_view.findViewById(R.id.jincai_line1_5);
            jincai_line1_1.setTextSize(12);
            jincai_line1_2.setTextSize(12);
            jincai_line1_3.setTextSize(12);
            jincai_line1_4.setTextSize(12);
            jincai_line1_5.setTextSize(12);
            jincai_line1_1.setText(list_open_qs.get(i).getKJQS());
            jincai_line1_2.setText(list_open_qs.get(i).getGroup_1() + list_open_qs.get(i).getGroup_2() + list_open_qs.get(i).getGroup_3());
            jincai_line1_3.setText(list_open_qs.get(i).getGroup_num());
            jincai_line1_4.setText(list_open_qs.get(i).getResult_1());
            jincai_line1_5.setText(list_open_qs.get(i).getResult_2());
            all_guess_open_number.addView(qs_view);
            list_qs_view.add(qs_view);
        }
        View more_qs_view = mInflater.inflate(R.layout.item_more_kaijiang_qishu, null);
        more_qs_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guess.this, more_qs.class);
                startActivity(intent);
                finish();

            }
        });
        all_guess_open_number.addView(more_qs_view);
        for (int i = 0; i < list_qs_view.size(); i++) {
            final int a = i;
            list_qs_view.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(guess.this, guess_open.class);
                    intent.putExtra("kaijiang_results", list_kaijiang_results.get(a));
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    private void init_panduan() {

        list_zhushu_panduan.clear();//判断哪些号码下注
        for (int i = 0; i < list_Hm.size(); i++) {
            list_zhushu_panduan.add(false);
        }
        list_jd_select_panduna.clear();//判断哪些jd选中
        list_jd_select_panduna.add(true);
        for (int i = 1; i < list_jd.size(); i++) {
            list_jd_select_panduna.add(false);
        }
    }

    AlertDialog alert2;
    AlertDialog alert;
    AlertDialog.Builder builder;
    AlertDialog.Builder builder2;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void init_juli() {
        /**
         * 初始化12按钮
         */
        list_fang_difang.clear();
        list_12.clear();
        xiajiemia_1.removeAllViews();
        xiajiemia_2.removeAllViews();
        xiajiemia_3.removeAllViews();
        xiajiemia_4.removeAllViews();
        xiajiemia_5.removeAllViews();
        for (int i = 0; i < list_Hm.size(); i++) {
            if (i <= 3) {
                LinearLayout re = new LinearLayout(this);//一个item
                re.setGravity(Gravity.CENTER_HORIZONTAL);
                re.setBackgroundResource(R.drawable.guess_but_skin);
                re.setId(Integer.parseInt(list_Hm.get(i).getHMLX()));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(whith_rct, whith_rct);
                lp.setMargins(20, 10, 20, 10);
                re.setLayoutParams(lp);
                LinearLayout re_nei = new LinearLayout(this);//内部一个item
                LinearLayout.LayoutParams lp_nei = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                re_nei.setOrientation(LinearLayout.VERTICAL);
                re_nei.setLayoutParams(lp_nei);
                re_nei.setGravity(Gravity.CENTER_HORIZONTAL);
                RelativeLayout r1 = new RelativeLayout(this);
                RelativeLayout.LayoutParams lp_r1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                r1.setGravity(Gravity.CENTER);
                r1.setLayoutParams(lp_r1);
                TextView name = new TextView(this);
                TextView pl = new TextView(this);
                name.setTextColor(getResources().getColor(R.color.white));
                name.setTextSize(20);
                name.setGravity(Gravity.CENTER_HORIZONTAL);
                pl.setTextSize(10);
                pl.setTextColor(getResources().getColor(R.color.white));
                name.setText(list_Hm.get(i).getHMMC());
                pl.setText("1赔" + list_Hm.get(i).getHMPL());
                LinearLayout re_img = new LinearLayout(this);
                re_nei.addView(name);
                ImageView imageView = new ImageView(this);
                re_img.setGravity(Gravity.CENTER_HORIZONTAL);
                re_img.addView(imageView);
                RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                r1.addView(pl, lp2);
                r1.addView(re_img, lp1);
                re_nei.addView(r1);
                re.addView(re_nei);
                list_fang_difang.add(imageView);
                list_12.add(re);
                xiajiemia_1.addView(re);
            }
            if (i > 3 && i <= 7) {
                LinearLayout re = new LinearLayout(this);//一个item
                re.setGravity(Gravity.CENTER_HORIZONTAL);
                re.setBackgroundResource(R.drawable.guess_but_skin);
                re.setId(Integer.parseInt(list_Hm.get(i).getHMLX()));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(whith_rct, whith_rct);
                lp.setMargins(20, 10, 20, 10);
                re.setLayoutParams(lp);
                LinearLayout re_nei = new LinearLayout(this);//内部一个item
                LinearLayout.LayoutParams lp_nei = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                re_nei.setOrientation(LinearLayout.VERTICAL);
                re_nei.setLayoutParams(lp_nei);
                re_nei.setGravity(Gravity.CENTER_HORIZONTAL);
                RelativeLayout r1 = new RelativeLayout(this);
                RelativeLayout.LayoutParams lp_r1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                r1.setGravity(Gravity.CENTER);
                r1.setLayoutParams(lp_r1);
                TextView name = new TextView(this);
                TextView pl = new TextView(this);
                name.setTextColor(getResources().getColor(R.color.white));
                name.setTextSize(20);
                name.setGravity(Gravity.CENTER_HORIZONTAL);
                pl.setTextSize(10);
                pl.setTextColor(getResources().getColor(R.color.white));
                name.setText(list_Hm.get(i).getHMMC());
                pl.setText("1赔" + list_Hm.get(i).getHMPL());
                LinearLayout re_img = new LinearLayout(this);
                re_nei.addView(name);
                ImageView imageView = new ImageView(this);
                re_img.setGravity(Gravity.CENTER_HORIZONTAL);
                re_img.addView(imageView);
                RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                r1.addView(pl, lp2);
                r1.addView(re_img, lp1);
                re_nei.addView(r1);
                re.addView(re_nei);
                list_fang_difang.add(imageView);
                list_12.add(re);
                xiajiemia_2.addView(re);
            }
            if (i > 7 && i <= 11) {
                LinearLayout re = new LinearLayout(this);//一个item
                re.setGravity(Gravity.CENTER_HORIZONTAL);
                re.setBackgroundResource(R.drawable.guess_but_skin);
                re.setId(Integer.parseInt(list_Hm.get(i).getHMLX()));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(whith_rct, whith_rct);
                lp.setMargins(20, 10, 20, 10);
                re.setLayoutParams(lp);
                LinearLayout re_nei = new LinearLayout(this);//内部一个item
                LinearLayout.LayoutParams lp_nei = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                re_nei.setOrientation(LinearLayout.VERTICAL);
                re_nei.setLayoutParams(lp_nei);
                re_nei.setGravity(Gravity.CENTER_HORIZONTAL);
                RelativeLayout r1 = new RelativeLayout(this);
                RelativeLayout.LayoutParams lp_r1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                r1.setGravity(Gravity.CENTER);
                r1.setLayoutParams(lp_r1);
                TextView name = new TextView(this);
                TextView pl = new TextView(this);
                name.setTextColor(getResources().getColor(R.color.white));
                name.setTextSize(20);
                name.setGravity(Gravity.CENTER_HORIZONTAL);
                pl.setTextSize(10);
                pl.setTextColor(getResources().getColor(R.color.white));
                name.setText(list_Hm.get(i).getHMMC());
                pl.setText("1赔" + list_Hm.get(i).getHMPL());
                LinearLayout re_img = new LinearLayout(this);
                re_nei.addView(name);
                ImageView imageView = new ImageView(this);
                re_img.setGravity(Gravity.CENTER_HORIZONTAL);
                re_img.addView(imageView);
                RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                r1.addView(pl, lp2);
                r1.addView(re_img, lp1);
                re_nei.addView(r1);
                re.addView(re_nei);
                list_12.add(re);
                list_fang_difang.add(imageView);
                xiajiemia_3.addView(re);

            }
            if (i > 11 && i <= 15) {
                LinearLayout re = new LinearLayout(this);//一个item
                re.setGravity(Gravity.CENTER_HORIZONTAL);
                re.setBackgroundResource(R.drawable.guess_but_skin);
                re.setId(Integer.parseInt(list_Hm.get(i).getHMLX()));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(whith_rct, whith_rct);
                lp.setMargins(20, 10, 20, 10);
                re.setLayoutParams(lp);
                LinearLayout re_nei = new LinearLayout(this);//内部一个item
                LinearLayout.LayoutParams lp_nei = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                re_nei.setOrientation(LinearLayout.VERTICAL);
                re_nei.setLayoutParams(lp_nei);
                re_nei.setGravity(Gravity.CENTER_HORIZONTAL);
                RelativeLayout r1 = new RelativeLayout(this);
                RelativeLayout.LayoutParams lp_r1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                r1.setGravity(Gravity.CENTER);
                r1.setLayoutParams(lp_r1);
                TextView name = new TextView(this);
                TextView pl = new TextView(this);
                name.setTextColor(getResources().getColor(R.color.white));
                name.setTextSize(20);
                name.setGravity(Gravity.CENTER_HORIZONTAL);
                pl.setTextSize(10);
                pl.setTextColor(getResources().getColor(R.color.white));
                name.setText(list_Hm.get(i).getHMMC());
                pl.setText("1赔" + list_Hm.get(i).getHMPL());
                LinearLayout re_img = new LinearLayout(this);
                re_nei.addView(name);
                ImageView imageView = new ImageView(this);
                re_img.setGravity(Gravity.CENTER_HORIZONTAL);
                re_img.addView(imageView);
                RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                r1.addView(pl, lp2);
                r1.addView(re_img, lp1);
                re_nei.addView(r1);
                re.addView(re_nei);
                list_12.add(re);
                list_fang_difang.add(imageView);
                xiajiemia_4.addView(re);
            }
            if (i > 15 && i <= 19) {
                LinearLayout re = new LinearLayout(this);//一个item
                re.setGravity(Gravity.CENTER_HORIZONTAL);
                re.setBackgroundResource(R.drawable.guess_but_skin);
                re.setId(Integer.parseInt(list_Hm.get(i).getHMLX()));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(whith_rct, whith_rct);
                lp.setMargins(20, 10, 20, 10);
                re.setLayoutParams(lp);
                LinearLayout re_nei = new LinearLayout(this);//内部一个item
                LinearLayout.LayoutParams lp_nei = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                re_nei.setOrientation(LinearLayout.VERTICAL);
                re_nei.setLayoutParams(lp_nei);
                re_nei.setGravity(Gravity.CENTER_HORIZONTAL);
                RelativeLayout r1 = new RelativeLayout(this);
                RelativeLayout.LayoutParams lp_r1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                r1.setGravity(Gravity.CENTER);
                r1.setLayoutParams(lp_r1);
                TextView name = new TextView(this);
                TextView pl = new TextView(this);
                name.setTextColor(getResources().getColor(R.color.white));
                name.setTextSize(20);
                name.setGravity(Gravity.CENTER_HORIZONTAL);
                pl.setTextSize(10);
                pl.setTextColor(getResources().getColor(R.color.white));
                name.setText(list_Hm.get(i).getHMMC());
                pl.setText("1赔" + list_Hm.get(i).getHMPL());
                LinearLayout re_img = new LinearLayout(this);
                re_nei.addView(name);
                ImageView imageView = new ImageView(this);
                re_img.setGravity(Gravity.CENTER_HORIZONTAL);
                re_img.addView(imageView);
                RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                r1.addView(pl, lp2);
                r1.addView(re_img, lp1);
                re_nei.addView(r1);
                re.addView(re_nei);
                list_12.add(re);
                list_fang_difang.add(imageView);
                xiajiemia_5.addView(re);
            }
        }
        if (!panduan_caihaoyoulma) {//猜号的无所谓
            panduan_caihaoyoulma = true;
            LinearLayout re = new LinearLayout(this);//一个item
            re.setGravity(Gravity.CENTER_HORIZONTAL);
            re.setBackgroundResource(R.drawable.guess_but_skin);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(whith_rct, whith_rct);
            lp.setMargins(20, 10, 20, 10);
            re.setLayoutParams(lp);
            LinearLayout re_nei = new LinearLayout(this);//内部一个item
            LinearLayout.LayoutParams lp_nei = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            re_nei.setOrientation(LinearLayout.VERTICAL);
            re_nei.setLayoutParams(lp_nei);
            re_nei.setGravity(Gravity.CENTER_HORIZONTAL);
            RelativeLayout r1 = new RelativeLayout(this);
            RelativeLayout.LayoutParams lp_r1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            r1.setGravity(Gravity.CENTER);
            r1.setLayoutParams(lp_r1);
            TextView name = new TextView(this);
            TextView pl = new TextView(this);
            name.setTextColor(getResources().getColor(R.color.white));
            name.setTextSize(20);
            name.setGravity(Gravity.CENTER_HORIZONTAL);
            pl.setTextSize(10);
            pl.setTextColor(getResources().getColor(R.color.white));
            name.setText("选号");
            pl.setText("1赔10");
            re_nei.addView(name);
            RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            r1.addView(pl, lp2);
            re_nei.addView(r1);
            re.addView(re_nei);
            re.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(guess.this, guess_caihao.class);
                    startActivity(intent);
                    finish();
                }
            });
            xiajiemia_8.addView(re);


            /**
             * 选择抽奖
             */
            builder = new AlertDialog.Builder(guess.this);
            alert = builder.create();
            builder2 = new AlertDialog.Builder(guess.this);
            builder2.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                        if (z == 1) {
                            Intent intent = new Intent(guess.this, guess.class);
                            startActivity(intent);
                            System.out.println("出现111111111");
                        }
                    }
                    return false;
                }
            });

            img_jiugong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //来一个请求
                    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                    RequestParams requestParams = new RequestParams();
                    String url = all_url.url_room;
                    asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            super.onSuccess(jsonObject);
                            try {
                                if ("true".equals(jsonObject.getString("status"))) {
                                    final List<View> list_view = new ArrayList<View>();
                                    System.out.println(jsonObject);
                                    JSONArray rooms = jsonObject.getJSONArray("room");
                                    list_room = new ArrayList<room>();
                                    for (int i = 0; i < rooms.length(); i++) {
                                        room r1 = new room();
                                        r1.setId(rooms.getJSONObject(i).getString("uuid"));
                                        r1.setVal(rooms.getJSONObject(i).getString("val"));
                                        r1.setInfo(rooms.getJSONObject(i).getString("info"));
                                        r1.setName(rooms.getJSONObject(i).getString("name"));
                                        list_room.add(r1);
                                    }
                                    view = mInflater.inflate(R.layout.jiugongge_fangjian, null);
                                    GridView gridView = (GridView) view.findViewById(R.id.jiugong_list);
                                    gridView.setAdapter(new jiugong_guess_adapter(guess.this, list_room));
                                    alert.setView(view, 0, 0, 0, -10);
                                    alert.show();
                                    alert2 = builder2.create();
                                    alert2.setCanceledOnTouchOutside(false);
                                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            if (alert != null && alert.isShowing()) {
                                                alert.dismiss();
                                            }
                                            fangjian_id = list_room.get(position).getId();
                                            if (view_2 == null) {
                                                view_2 = mInflater.inflate(R.layout.item_jiugongge, null);
                                            }
                                            z = 1;
                                            try {
                                                alert2.setView(view_2, 0, 0, 0, -10);
                                                alert2.show();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(guess.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(Throwable arg0) {
                            super.onFailure(arg0);
                        }
                    });
                }
            });
        }

        /**
         * 初始化8按钮
         */
        list_8.clear();
        xiajiemia_6.removeAllViews();
        xiajiemia_7.removeAllViews();
        for (int i = 0; i < list_jd.size(); i++) {
            if (i <= 3) {
                LinearLayout re = new LinearLayout(this);
                re.setGravity(Gravity.CENTER);
                if (i == 0) {
                    re.setBackgroundResource(list_jd_img_fg.get(i));
                } else {
                    re.setBackgroundResource(list_jd_jmg_def.get(i));
                }
                re.setId(Integer.parseInt(list_jd.get(i).getJDGS()));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(whith_rct + 10, whith_rct + 10);
                lp.setMargins(10, 10, 10, 10);
                re.setLayoutParams(lp);
                TextView shulia = new TextView(this);
                shulia.setGravity(Gravity.CENTER_HORIZONTAL);
                shulia.setTextSize(12);
                shulia.setText(list_jd.get(i).getJDDW());
                re.addView(shulia);
                list_8.add(re);
                xiajiemia_6.addView(re);
            }
            if (i > 3) {
                LinearLayout re = new LinearLayout(this);
                re.setGravity(Gravity.CENTER);
                re.setBackgroundResource(list_jd_jmg_def.get(i));
                re.setId(Integer.parseInt(list_jd.get(i).getJDGS()));
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(whith_rct + 10, whith_rct + 10);
                lp.setMargins(10, 10, 10, 10);
                re.setLayoutParams(lp);
                TextView shulia = new TextView(this);
                shulia.setGravity(Gravity.CENTER_HORIZONTAL);
                shulia.setTextSize(12);
                shulia.setText(list_jd.get(i).getJDDW());
                re.addView(shulia);
                list_8.add(re);
                xiajiemia_7.addView(re);
            }
        }
        init_12_money();//每个按钮的钱
        init_12_click();
        init_8_click();
    }
    View view_2;

    private void init_12_money() {
        //每个按钮的钱；
        list_money.clear();
        for (int i = 0; i < list_12.size(); i++) {
            list_money.add(0);
        }
    }

    private void init_8_click() {
        for (int i = 0; i < list_8.size(); i++) {
            final int a = i;
            list_8.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < list_8.size(); i++) {
                        list_jd_select_panduna.set(i, false);
                        list_8.get(i).setBackgroundResource(list_jd_jmg_def.get(i));
                    }
                    list_jd_select_panduna.set(a, true);
                    v.setBackgroundResource(list_jd_img_fg.get(a));
                }
            });
        }
    }

    private void init_12_click() {
        final int[] zi_sy = new int[1];
        for (int i = 0; i < list_12.size(); i++) {
            final int a = i;
            list_12.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < list_jd.size(); i++) {
                        if (list_jd_select_panduna.get(i)) {
                            zi_sy[0] = list_money.get(a) + Integer.parseInt(list_jd.get(i).getJDGS());
                        }
                    }
                    //判断够不够
                    if (zi_sy[0] > Integer.parseInt(list_Hm.get(a).getSY())) {
                        Toast.makeText(guess.this, "单注上限50万", Toast.LENGTH_LONG).show();
                    } else {

                        v.setSelected(true);
                        if (!list_zhushu_panduan.get(a)) {
                            list_xiazhu_number++;
                            list_zhushu_panduan.set(a, true);
                        }
                        for (int i = 0; i < list_jd.size(); i++) {
                            if (list_jd_select_panduna.get(i)) {
                                list_xiazhu_jd_number = list_xiazhu_jd_number + Integer.parseInt(list_jd.get(i).getJDGS());
                                int zj = list_money.get(a) + Integer.parseInt(list_jd.get(i).getJDGS());
                                list_money.set(a, zj);

                            }
                        }
                        text_list_xiazhu_jd_number.setText("" + list_xiazhu_jd_number);
                        text_list_xiazhu_number.setText("" + list_xiazhu_number);
                        fling(a);//金豆飞
                    }

                }
            });
        }
    }
    private void case_panduan(int a) {
        switch (a) {
            //大
            case 0:
                list_12.get(1).setBackgroundResource(R.color.gray);
                list_12.get(1).setEnabled(false);
                list_12.get(6).setBackgroundResource(R.color.gray);
                list_12.get(6).setEnabled(false);
                list_12.get(7).setBackgroundResource(R.color.gray);
                list_12.get(7).setEnabled(false);
                break;
            //小
            case 1:
                list_12.get(0).setBackgroundResource(R.color.gray);
                list_12.get(0).setEnabled(false);
                list_12.get(4).setBackgroundResource(R.color.gray);
                list_12.get(4).setEnabled(false);
                list_12.get(5).setBackgroundResource(R.color.gray);
                list_12.get(5).setEnabled(false);

                break;
            //单
            case 2:
                list_12.get(3).setBackgroundResource(R.color.gray);
                list_12.get(3).setEnabled(false);
                list_12.get(7).setBackgroundResource(R.color.gray);
                list_12.get(7).setEnabled(false);
                list_12.get(5).setBackgroundResource(R.color.gray);
                list_12.get(5).setEnabled(false);
                break;
            //双
            case 3:
                list_12.get(2).setBackgroundResource(R.color.gray);
                list_12.get(2).setEnabled(false);
                list_12.get(6).setBackgroundResource(R.color.gray);
                list_12.get(6).setEnabled(false);
                list_12.get(4).setBackgroundResource(R.color.gray);
                list_12.get(4).setEnabled(false);
                break;
            //大单
            case 4:
                list_12.get(1).setBackgroundResource(R.color.gray);
                list_12.get(1).setEnabled(false);
                list_12.get(3).setBackgroundResource(R.color.gray);
                list_12.get(3).setEnabled(false);
                list_12.get(7).setBackgroundResource(R.color.gray);
                list_12.get(7).setEnabled(false);
                break;
            //大双
            case 5:
                list_12.get(2).setBackgroundResource(R.color.gray);
                list_12.get(2).setEnabled(false);
                list_12.get(1).setBackgroundResource(R.color.gray);
                list_12.get(1).setEnabled(false);
                list_12.get(6).setBackgroundResource(R.color.gray);
                list_12.get(6).setEnabled(false);
                break;
            //小单
            case 6:

                list_12.get(0).setBackgroundResource(R.color.gray);
                list_12.get(0).setEnabled(false);
                list_12.get(3).setBackgroundResource(R.color.gray);
                list_12.get(3).setEnabled(false);
                list_12.get(5).setBackgroundResource(R.color.gray);
                list_12.get(5).setEnabled(false);
                break;
            //小双
            case 7:
                list_12.get(0).setBackgroundResource(R.color.gray);
                list_12.get(0).setEnabled(false);
                list_12.get(2).setBackgroundResource(R.color.gray);
                list_12.get(2).setEnabled(false);
                list_12.get(4).setBackgroundResource(R.color.gray);
                list_12.get(4).setEnabled(false);
                break;
        }
    }
    private void fling(int aa) {
        int zz = 0;
        int[] location = new int[2];
        int[] location2 = new int[2];
        for (int i = 0; i < list_jd.size(); i++) {
            if (list_jd_select_panduna.get(i)) {
                zz = i;
                list_8.get(i).getLocationInWindow(location);//获得当前jd按钮坐标
                location[0] = location[0] + whith_rct / 4;
//                location[1]=location[1]+whith_rct/4;
            }
        }
        list_12.get(aa).getLocationInWindow(location2);
        location2[0] = location2[0] + whith_rct / 4;
//        location2[1]=location2[1]+whith_rct/2;
        top_kaijiang = top_height / 50 * 65;
        Animation fliiinf = new TranslateAnimation(location[0], location2[0], location[1] - DensityUtil.dip2px(guess.this, 115), location2[1] - DensityUtil.dip2px(guess.this, 115));
        fliiinf.setDuration(200);
        list_small_jd.get(zz).setVisibility(View.VISIBLE);
        list_small_jd.get(zz).startAnimation(fliiinf);
        final int finalaa = aa;
        final int finalZz = zz;
        fliiinf.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                list_fang_difang.get(finalaa).setBackgroundResource(list_small_img.get(finalZz));
                list_small_jd.get(finalZz).setVisibility(View.GONE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

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
        for (int i = 0; i < list_12.size(); i++) {
            list_12.get(i).setSelected(false);
            list_zhushu_panduan.set(i, false);
            list_fang_difang.get(i).setBackgroundResource(0);
        }
        init_12_money();
        list_xiazhu_jd_number = 0;
        list_xiazhu_number = 0;
        text_list_xiazhu_jd_number.setText("" + list_xiazhu_jd_number);
        text_list_xiazhu_number.setText("" + list_xiazhu_number);
        for (int i = 0; i < list_12.size(); i++) {
            list_12.get(i).setBackgroundResource(R.drawable.guess_but_skin);
            list_12.get(i).setEnabled(true);
        }
        if (limit_boolen) {

            init_limit();//用来限制
        }
    }

    //手势
    class myGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (e2.getY() - e1.getY() > 50) {
                if (!flag) {
                    for (int i = 0; i < 12; i++) {
                        if (list_12.size() > 0) {//防止超标
                            list_12.get(i).setEnabled(false);
                        }
                    }
//                    down_move();
                    int height = all_guess_open_number_da.getMeasuredHeight();
                    down_move_shuxingdonghua(guess_xiajiemian_all, height);
//                    int height = all_guess_open_number.getMeasuredHeight();
//                    slideview(0,height);
                    flag = true;
                    guess_but_down.setImageResource(R.mipmap.all_guess_shangla);
                }
            }
            if (e1.getY() - e2.getY() > 50) {
                if (flag) {
                    for (int i = 0; i < 12; i++) {
                        if(list_12.size()>0) {//防止超标
                            list_12.get(i).setEnabled(true);
                        }
                    }
                    int height = all_guess_open_number_da.getMeasuredHeight();
                    down_move_shuxingdonghua(guess_xiajiemian_all, 0);
//                    up_move();
//                    int height = all_guess_open_number.getMeasuredHeight();
//                    slideview(0,-height);
                    flag = false;
                    guess_but_down.setImageResource(R.mipmap.all_guess_xiala);
                }
            }
            if (limit_boolen) {

                init_limit();//用来限制
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    boolean flag;

    private void init_but_animation() {
        guess_but_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    for (int i = 0; i < 12; i++) {
                        list_12.get(i).setEnabled(false);
                    }
                    int height = all_guess_open_number_da.getMeasuredHeight();
                    down_move_shuxingdonghua(guess_xiajiemian_all, height);
                    flag = true;
                    guess_but_down.setImageResource(R.mipmap.all_guess_shangla);
                } else {
                    for (int i = 0; i < 12; i++) {
                        list_12.get(i).setEnabled(true);
                    }
                    int height = all_guess_open_number_da.getMeasuredHeight();
                    down_move_shuxingdonghua(guess_xiajiemian_all, 0);
//                    up_move();
//                    int height = all_guess_open_number.getMeasuredHeight();
//                    slideview(0,-height);
                    flag = false;
                    guess_but_down.setImageResource(R.mipmap.all_guess_xiala);
                }
                if (limit_boolen) {
                    init_limit();//用来限制
                }
            }
        });
    }

    boolean bbq = false;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 != -2) {
                if (bbq) {
                    bbq = false;
                }
                if (msg.arg1 < 10) {
                    guess_time.setText(msg.arg2 + ":0" + msg.arg1);
                } else {
                    guess_time.setText(msg.arg2 + ":" + msg.arg1);

                }
            } else if (msg.arg1 == -2) {
                if (!bbq) {

                    bbq = true;
                    timer.cancel();
                    init_wangluo();
                    return;
                }
            }
        }
    };
    private Handler lhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg2 == 1) {//开始下注
            }
            if (msg.arg1 == 1) {
                please_wait_2.setVisibility(View.VISIBLE);
                xuyaochuandehua.setText("停止下注");
            }
            if (msg.arg1 == 2) {
                please_wait_2.setVisibility(View.GONE);
            }
            if (msg.arg2 == 2) {//停止下注
                Toast.makeText(guess.this, next_expect + "停止下注", Toast.LENGTH_SHORT).show();
            }

        }
    };
    Thread thread1;

    private void init_xiaoshuaxin() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(30000);
        String url = all_url.url_jigncai_config;
        asyncHttpClient.post(url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);
                        try {
                            System.out.println(jsonObject.toString());
                            if ("true".equals(jsonObject.getString("status"))) {

                                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                asyncHttpClient.setTimeout(30000);
                                String url = jsonObject.getJSONObject("config").getString("PZZ")+"jc/mobile/guess/openCode";
                                asyncHttpClient.post(url, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(JSONObject jsonObject) {
                                        super.onSuccess(jsonObject);
                                        try {
                                            System.out.println(jsonObject.toString());
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
                                                    List<String> list = new ArrayList<String>();
                                                    for (int j = 1; j <= 20; j++) {
                                                        list.add(open_jiang.getJSONObject(i).getString("code_" + j));
                                                    }
                                                    kai1.setList_20(list);
                                                    list_kaijiang_results.add(kai1);
                                                }
                                                if (!list_open_qs.get(0).getKJQS().equals(xuyaos_qishu)) {
                                                    thread1 = new Thread(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            try {
                                                                Thread.sleep(25000);
                                                                init_xiaoshuaxin();
                                                            } catch (InterruptedException e) {
                                                                thread1.interrupt();
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
                                            System.out.println("-+_+-+'''''"+e);
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Throwable throwable) {
                                        super.onFailure(throwable);
                                    }
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("-+_+-+'''''"+e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        super.onFailure(throwable);
                    }




        });
    }

    //可以刷新了
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == 1) {
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
        for (int i = 0; i < list_open_qs.size(); i++) {
            View qs_view = mInflater.inflate(R.layout.guess_qs_xiangp, null);
            TextView jincai_line1_1 = (TextView) qs_view.findViewById(R.id.jincai_line1_1);
            TextView jincai_line1_2 = (TextView) qs_view.findViewById(R.id.jincai_line1_2);
            TextView jincai_line1_3 = (TextView) qs_view.findViewById(R.id.jincai_line1_3);
            TextView jincai_line1_4 = (TextView) qs_view.findViewById(R.id.jincai_line1_4);
            TextView jincai_line1_5 = (TextView) qs_view.findViewById(R.id.jincai_line1_5);
            jincai_line1_1.setTextSize(12);
            jincai_line1_2.setTextSize(12);
            jincai_line1_3.setTextSize(12);
            jincai_line1_4.setTextSize(12);
            jincai_line1_5.setTextSize(12);
            jincai_line1_1.setText(list_open_qs.get(i).getKJQS());
            jincai_line1_2.setText(list_open_qs.get(i).getGroup_1() + list_open_qs.get(i).getGroup_2() + list_open_qs.get(i).getGroup_3());
            jincai_line1_3.setText(list_open_qs.get(i).getGroup_num());
            jincai_line1_4.setText(list_open_qs.get(i).getResult_1());
            jincai_line1_5.setText(list_open_qs.get(i).getResult_2());
            all_guess_open_number.addView(qs_view);
            list_qs_view.add(qs_view);
        }
        View more_qs_view = mInflater.inflate(R.layout.item_more_kaijiang_qishu, null);
        more_qs_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guess.this, more_qs.class);
                startActivity(intent);
                finish();

            }
        });
        all_guess_open_number.addView(more_qs_view);
        for (int i = 0; i < list_qs_view.size(); i++) {
            final int a = i;
            list_qs_view.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(guess.this, guess_open.class);
                    intent.putExtra("kaijiang_results", list_kaijiang_results.get(a));
                    startActivity(intent);
                    finish();
                }
            });
        }


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(guess.this, MainActivity.class);
        startActivity(intent);

        onDestroy();


    }

    public void startTime() {
        timer = new Timer();
        final boolean[] yigesuo = {false};
        task = new TimerTask() {
            @Override
            public void run() {
                Message message = mhandler.obtainMessage();
                Message m2 = lhandler.obtainMessage();
                if (time_text_s == 0) {
                    if (time_text_f == 0 && (!yigesuo[0])) {

                        yigesuo[0] = true;
                        message.arg1 = -2;
                        mhandler.sendMessage(message);

                        return;
                    }
                    time_text_s = 60;
                    time_text_f--;
                    if (time_text_f == 0) {
                        m2.arg2 = 2;
                        if (guess
                                .this != null) {

                            lhandler.sendMessage(m2);
                        }

                    }
                }
                time_text_s--;
                message.arg1 = time_text_s;
                message.arg2 = time_text_f;
                mhandler.sendMessage(message);
            }
        };
        timer.schedule(task, 1000, 1000);
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

    private View right;
    private TextView xuyaochuandehua;
    private void init() {
        img_jiugong = (ImageView) findViewById(R.id.img_jiugong);
        xuyaochuandehua = (TextView) findViewById(R.id.xuyaochuandehua);
        jigncai_dingdan_click = (TextView) findViewById(R.id.jigncai_dingdan_click);
        jincai_jd_info = (TextView) findViewById(R.id.jincai_jd_info);
        dengdaikaijiang = findViewById(R.id.dengdaikaijiang);
        xianshikaijiang = findViewById(R.id.xianshikaijiang);
        xiajiemia_8 = (LinearLayout) findViewById(R.id.xiajiemia_8);
        jincai_ok = (Button) findViewById(R.id.jincai_ok);
        jincai_qs_next = (TextView) findViewById(R.id.jincai_qs_next);
        jincai_qs = (TextView) findViewById(R.id.jincai_qs);
        jincai_group_1_1 = (TextView) findViewById(R.id.jincai_group_1_1);
        jincai_group_2_2 = (TextView) findViewById(R.id.jincai_group_2_2);
        jincai_group_3_3 = (TextView) findViewById(R.id.jincai_group_3_3);
        jincai_group_num = (TextView) findViewById(R.id.jincai_group_num);
        whith_rct = (MainActivity.screenWidth - 5 * DensityUtil.dip2px(guess.this, 30)) / 4;
        list_small_img.add(R.mipmap.jd1_small);
        list_small_img.add(R.mipmap.jd2_small);
        list_small_img.add(R.mipmap.jd3_small);
        list_small_img.add(R.mipmap.jd4_small);
        list_small_img.add(R.mipmap.jd5_small);
        list_small_img.add(R.mipmap.jd6_small);
        list_small_img.add(R.mipmap.jd7_small);
        list_small_img.add(R.mipmap.jd8_small);
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
        xiajiemia_1 = (LinearLayout) findViewById(R.id.xiajiemia_1);
        xiajiemia_2 = (LinearLayout) findViewById(R.id.xiajiemia_2);
        xiajiemia_3 = (LinearLayout) findViewById(R.id.xiajiemia_3);
        xiajiemia_4 = (LinearLayout) findViewById(R.id.xiajiemia_4);
        xiajiemia_5 = (LinearLayout) findViewById(R.id.xiajiemia_5);
        xiajiemia_6 = (LinearLayout) findViewById(R.id.xiajiemia_6);
        xiajiemia_7 = (LinearLayout) findViewById(R.id.xiajiemia_7);
        guess_topbar = findViewById(R.id.all_guess_home);
        right = guess_topbar.findViewById(R.id.logreg_right);
        back = guess_topbar.findViewById(R.id.logreg_left);
        w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        chongxin_xiazhu = (TextView) findViewById(R.id.chongxin_xiazhu);
        all_guess_kaijiang = findViewById(R.id.all_guess_kaijiang);
        all_guess_home = findViewById(R.id.all_guess_home);
        all_guess_home.measure(w, h);
        all_guess_kaijiang.measure(w, h);
        top_kaijiang = all_guess_kaijiang.getMeasuredHeight();
        top_height = all_guess_home.getMeasuredHeight();
        text_list_xiazhu_jd_number = (TextView) findViewById(R.id.list_xiazhu_jd_number);
        text_list_xiazhu_number = (TextView) findViewById(R.id.list_xiazhu_number);


        guess_time = (TextView) findViewById(R.id.guess_time);
        Typeface type = Typeface.createFromAsset(guess.this.getAssets(), "yjsz.ttf");
        guess_time.setTypeface(type);

        all_guess_open_number = (LinearLayout) findViewById(R.id.all_guess_open_number);
        all_guess_open_number_da = (LinearLayout) findViewById(R.id.all_guess_open_number_da);
        guess_xiajiemian_all = (RelativeLayout) findViewById(R.id.guess_xiajiemian_all);
        guess_but_down = (ImageView) findViewById(R.id.guess_but_down);
        for (int i = 0; i < 8; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(list_small_img.get(i));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(whith_rct / 2, whith_rct / 2);
            imageView.setLayoutParams(lp);
            imageView.setVisibility(View.GONE);
            list_small_jd.add(imageView);
            guess_xiajiemian_all.addView(imageView);
        }


        please_wait = findViewById(R.id.please_wait);
        please_wait_2 = findViewById(R.id.please_wait_2);

    }

    private View please_wait_2;


    private void down_move_shuxingdonghua(View view, int height) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", height);
        animator.setDuration(300);
        animator.start();
    }


}
