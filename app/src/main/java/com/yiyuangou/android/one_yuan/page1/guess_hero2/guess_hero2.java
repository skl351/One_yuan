package com.yiyuangou.android.one_yuan.page1.guess_hero2;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.liao.GifView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.socks.library.KLog;
import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.adapter.kaicaliebiao_adapter;
import com.yiyuangou.android.one_yuan.adapter.lol_pay_list_adapter;
import com.yiyuangou.android.one_yuan.bean.Hm;
import com.yiyuangou.android.one_yuan.bean.Jd;
import com.yiyuangou.android.one_yuan.bean.hero_item;
import com.yiyuangou.android.one_yuan.bean.hm_key_value;
import com.yiyuangou.android.one_yuan.bean.kaicai_item;
import com.yiyuangou.android.one_yuan.bean.kaijiang_result_hero;
import com.yiyuangou.android.one_yuan.bean.open_qs;
import com.yiyuangou.android.one_yuan.bean.order_item;
import com.yiyuangou.android.one_yuan.bean.room;
import com.yiyuangou.android.one_yuan.bean.sq_bs;
import com.yiyuangou.android.one_yuan.bean.sq_cm;
import com.yiyuangou.android.one_yuan.bean.sq_qs;
import com.yiyuangou.android.one_yuan.page1.guess_hreo.guize_hero;
import com.yiyuangou.android.one_yuan.page1.guess_hreo.hero_jingcaidingdan;
import com.yiyuangou.android.one_yuan.page1.guess_hreo.more_qs_hero;
import com.yiyuangou.android.one_yuan.page4.user_login;
import com.yiyuangou.android.one_yuan.util.DensityUtil;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;
import com.yiyuangou.android.one_yuan.zidingyi_view.sezi_view;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by android on 2016/3/26.
 */
public class guess_hero2 extends Activity {
    List<hero_item> list_hero;
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
    private View guess_but_down;//点击动画按钮
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
    private int whith_rct_3;//一个按钮的宽
    private int whith_rct_7;//一个按钮的宽
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
    private List<kaijiang_result_hero> list_kaijiang_results;
    private TextView zongxia;
    private TextView yixia;
    private String moer_50="5000000";

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sp.release();
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

    public  void init_user_info() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_user_info;
        RequestParams requestParams = new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        KLog.e("money",new DecimalFormat("#,##0").format(new Double(jsonObject.getJSONObject("user").getString("commissionPoints"))));
                        jincai_jd_info.setText(new DecimalFormat("#,##0").format(new Double(jsonObject.getJSONObject("user").getString("commissionPoints"))));//金豆
                        System.out.println("个人信息"+jsonObject);
                        if(jsonObject.getString("bankerStatus").equals("false")){
                            shengqingkaicai.setText("停止开猜");
                            shengqingkaicai.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog alert_hero = null;
                                    AlertDialog.Builder builder_hero;
                                    builder_hero = new AlertDialog.Builder(guess_hero2.this);
                                    alert_hero = builder_hero.create();
                                    View view = mInflater.inflate(R.layout.calcel_queding, null);

                                    final AlertDialog finalAlert_hero = alert_hero;
                                    view.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            finalAlert_hero.dismiss();
                                        }
                                    });
                                    view.findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                            asyncHttpClient.setTimeout(20000);
                                            String url = all_url.url_calcel_kaicai2;
                                            RequestParams requestParams = new RequestParams();
                                            requestParams.put("userId", User.getuser().getUser_uuid());
                                            asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                                                @Override
                                                public void onSuccess(JSONObject jsonObject) {
                                                    super.onSuccess(jsonObject);
                                                    finalAlert_hero.dismiss();
                                                    shengqingkaicai.setText("申请开猜");
                                                    shengqingkaicai.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                                            asyncHttpClient.setTimeout(20000);
                                                            String url = all_url.url_shenqingliebiao_before2;
                                                            RequestParams requestParams = new RequestParams();
                                                            asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                                                                @Override
                                                                public void onSuccess(JSONObject jsonObject) {
                                                                    super.onSuccess(jsonObject);

                                                                    try {
                                                                        if ("true".equals(jsonObject.getString("status"))) {
                                                                            System.out.println(jsonObject);
                                                                            list_cm=new ArrayList<sq_cm>();
                                                                            for (int i=0;i<jsonObject.getJSONArray("bankerCm").length();i++){
                                                                                sq_cm item=new sq_cm();
                                                                                item.setDw(jsonObject.getJSONArray("bankerCm").getJSONObject(i).getString("dw"));
                                                                                item.setValue(jsonObject.getJSONArray("bankerCm").getJSONObject(i).getString("value"));
                                                                                list_cm.add(item);
                                                                            }
                                                                            list_bs=new ArrayList<sq_bs>();
                                                                            for (int i=0;i<jsonObject.getJSONArray("bankerBs").length();i++){
                                                                                sq_bs item=new sq_bs();
                                                                                item.setDw(jsonObject.getJSONArray("bankerBs").getJSONObject(i).getString("dw"));
                                                                                item.setValue(jsonObject.getJSONArray("bankerBs").getJSONObject(i).getString("value"));
                                                                                list_bs.add(item);
                                                                            }
                                                                            final String[] m = new String[list_bs.size()];
                                                                            for (int i=0;i<list_bs.size();i++){
                                                                                m[i]=list_bs.get(i).getValue();
                                                                            }
                                                                            list_qs=new ArrayList<sq_qs>();
                                                                            for (int i=0;i<jsonObject.getJSONArray("bankerQs").length();i++){
                                                                                sq_qs item=new sq_qs();
                                                                                item.setDw(jsonObject.getJSONArray("bankerQs").getJSONObject(i).getString("dw"));
                                                                                item.setValue(jsonObject.getJSONArray("bankerQs").getJSONObject(i).getString("value"));
                                                                                list_qs.add(item);
                                                                            }
                                                                            String[] m2 = new String[list_qs.size()];
                                                                            for (int i=0;i<list_qs.size();i++){
                                                                                m2[i]=list_qs.get(i).getValue();
                                                                            }

                                                                            AlertDialog alert_hero = null;
                                                                            AlertDialog.Builder builder_hero;
                                                                            builder_hero = new AlertDialog.Builder(guess_hero2.this);
                                                                            View view = mInflater.inflate(R.layout.shengqingkaicai_item, null);
                                                                            final TextView sqjds= (TextView) view.findViewById(R.id.sqjds);
                                                                            Button button1= (Button) view.findViewById(R.id.but_1);
                                                                            Button button2= (Button) view.findViewById(R.id.but_2);
                                                                            Button button3= (Button) view.findViewById(R.id.but_3);
                                                                            Button button4= (Button) view.findViewById(R.id.but_4);
                                                                            Button button5= (Button) view.findViewById(R.id.but_5);
                                                                            Button button6= (Button) view.findViewById(R.id.but_6);
                                                                            Button button7= (Button) view.findViewById(R.id.but_7);
                                                                            Button button8= (Button) view.findViewById(R.id.but_8);
                                                                            list_but_cm=new ArrayList<Button>();
                                                                            list_but_cm.add(button1);
                                                                            list_but_cm.add(button2);
                                                                            list_but_cm.add(button3);
                                                                            list_but_cm.add(button4);
                                                                            list_but_cm.add(button5);
                                                                            list_but_cm.add(button6);
                                                                            list_but_cm.add(button7);
                                                                            list_but_cm.add(button8);
                                                                            for (int i=0;i<list_cm.size();i++){
                                                                                list_but_cm.get(i).setVisibility(View.VISIBLE);
                                                                                list_but_cm.get(i).setText(list_cm.get(i).getDw());
                                                                            }
                                                                            for (int i=0;i<list_but_cm.size();i++){
                                                                                final  int a=i;
                                                                                list_but_cm.get(i).setOnClickListener(new View.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(View v) {
                                                                                        sqjds.setText(list_cm.get(a).getValue());
                                                                                    }
                                                                                });
                                                                            }

                                                                            spinner= (Spinner) view.findViewById(R.id.sqsl);
                                                                            adapter=new ArrayAdapter<String>(guess_hero2.this,android.R.layout.simple_spinner_item,m);
                                                                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                                                            spinner.setAdapter(adapter);

                                                                            spinner2= (Spinner) view.findViewById(R.id.sqlx);
                                                                            adapter2=new ArrayAdapter<String>(guess_hero2.this,android.R.layout.simple_spinner_item,m2);
                                                                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                                                            spinner2.setAdapter(adapter2);

                                                                            Button ok= (Button) view.findViewById(R.id.ok);
                                                                            Button quxiao= (Button) view.findViewById(R.id.quxiao);
                                                                            alert_hero = builder_hero.create();
                                                                            final AlertDialog finalAlert_hero = alert_hero;
                                                                            ok.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    if(sqjds.getText().toString().isEmpty()){
                                                                                        Toast.makeText(guess_hero2.this,"申请金豆数不能为空",Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                    else{
                                                                                        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                                                                        asyncHttpClient.setTimeout(20000);
                                                                                        String url = all_url.url_shenqingliebiao2;
                                                                                        RequestParams requestParams = new RequestParams();
                                                                                        requestParams.put("userId", User.getuser().getUser_uuid());
                                                                                        requestParams.put("expect", jincai_qs_next.getText().toString());
                                                                                        requestParams.put("jd", sqjds.getText().toString());
                                                                                        requestParams.put("number", spinner.getSelectedItem().toString());
                                                                                        requestParams.put("expectNum", spinner2.getSelectedItem().toString());
                                                                                        System.out.println("bs"+ spinner.getSelectedItem().toString()+"jd"+sqjds.getText().toString()+"qs"+spinner2.getSelectedItem().toString());
                                                                                        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                                                                                            @Override
                                                                                            public void onSuccess(JSONObject jsonObject) {
                                                                                                super.onSuccess(jsonObject);
                                                                                                try {
                                                                                                    if ("true".equals(jsonObject.getString("status"))) {
                                                                                                        System.out.println("xuyao:"+jsonObject);
                                                                                                        init_user_info();
                                                                                                        zongxia.setText(jsonObject.getString("sumBanker"));
                                                                                                        yixia.setText(jsonObject.getString("allBet"));
                                                                                                        if(jsonObject.getString("bankerStatus").equals("false")){
                                                                                                            shengqingkaicai.setText("停止开猜");
                                                                                                            shengqingkaicai.setOnClickListener(new View.OnClickListener() {
                                                                                                                @Override
                                                                                                                public void onClick(View v) {
                                                                                                                    AlertDialog alert_hero = null;
                                                                                                                    AlertDialog.Builder builder_hero;
                                                                                                                    builder_hero = new AlertDialog.Builder(guess_hero2.this);
                                                                                                                    alert_hero = builder_hero.create();
                                                                                                                    View view = mInflater.inflate(R.layout.calcel_queding, null);

                                                                                                                    final AlertDialog finalAlert_hero = alert_hero;
                                                                                                                    view.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                                                                                                                        @Override
                                                                                                                        public void onClick(View v) {
                                                                                                                            finalAlert_hero.dismiss();
                                                                                                                        }
                                                                                                                    });
                                                                                                                    view.findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
                                                                                                                        @Override
                                                                                                                        public void onClick(View v) {
                                                                                                                            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                                                                                                            asyncHttpClient.setTimeout(20000);
                                                                                                                            String url = all_url.url_calcel_kaicai2;
                                                                                                                            RequestParams requestParams = new RequestParams();
                                                                                                                            requestParams.put("userId", User.getuser().getUser_uuid());
                                                                                                                            asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                                                                                                                                @Override
                                                                                                                                public void onSuccess(JSONObject jsonObject) {
                                                                                                                                    super.onSuccess(jsonObject);
                                                                                                                                    try {
                                                                                                                                        KLog.json("skl",jsonObject.toString());
                                                                                                                                        if ("true".equals(jsonObject.getString("status"))) {

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
                                                                                                        Toast.makeText(guess_hero2.this,jsonObject.getString("result"),Toast.LENGTH_SHORT).show();
                                                                                                    } else{
                                                                                                        Toast.makeText(guess_hero2.this,jsonObject.getString("result"),Toast.LENGTH_SHORT).show();
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
                                                                                    finalAlert_hero.dismiss();
                                                                                }
                                                                            });

                                                                            alert_hero.setView(view, 0, 0, 0, -10);
                                                                            alert_hero.show();
                                                                            quxiao.setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    finalAlert_hero.dismiss();
                                                                                }
                                                                            });


                                                                        } else{
                                                                            Toast.makeText(guess_hero2.this,jsonObject.getString("result"),Toast.LENGTH_SHORT).show();
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
    AlertDialog alert_kaicai;
    AlertDialog.Builder builder_kaicai;
    private TextView jincai_group_name;
    private Button kaicailiebiao;
    private Button shengqingkaicai;
    private boolean flag_shenqingkaicai=false;
    private ImageView show_hero;
    private TextView name2;
    private TextView shuxin2;
    private TextView bianhao2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.guess_hero2);
        mInflater = LayoutInflater.from(guess_hero2.this);
        init();
//        init_user_info();//用户信息
        wangluo_config();//时间
        init_kaicailiebiao();
        init_shengqingkaicai();
        init_guizhe();//规则
        init_wangluo(0);
        init_ok();//可以的按钮
        init_back();//返回
        init_jigncai_dingdan_click_meth();
        init_but_animation();
        initSoundPool();//初始化声音
        mygees = new GestureDetector(guess_hero2.this, new myGestureListener());
        guess_xiajiemian_all.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mygees.onTouchEvent(event);
                return true;
            }
        });
    }
    int button_id=0;
    int jinbi_id=0;
    int saizi_id=0;
  //播放下注金币按钮的声音
    public void playSound1(int sound,int loop){
        AudioManager am=(AudioManager)this.getSystemService(AUDIO_SERVICE);
        float currentStreamVolume=am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxStreamVolume=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float setVolume=(float)currentStreamVolume/maxStreamVolume;
        button_id=sp.play(hm.get(sound), setVolume, setVolume, 1, loop, 1.0f);
    }
    //播放筛子的声音
    public void playSound2(int sound,int loop){
        AudioManager am=(AudioManager)this.getSystemService(AUDIO_SERVICE);
        float currentStreamVolume=am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxStreamVolume=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float setVolume=(float)currentStreamVolume/maxStreamVolume;
        saizi_id=sp.play(hm.get(sound), setVolume, setVolume, 1, loop, 1.0f);
    }
    //播放弹出两边金币的声音
    public void playSound3(int sound,int loop){
        AudioManager am=(AudioManager)this.getSystemService(AUDIO_SERVICE);
        float currentStreamVolume=am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxStreamVolume=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float setVolume=(float)currentStreamVolume/maxStreamVolume;
        jinbi_id=sp.play(hm.get(sound), setVolume, setVolume, 1, loop, 1.0f);
    }
    //初始化4中声音
    public void initSoundPool(){
        sp=new SoundPool(4, AudioManager.STREAM_MUSIC,0);//创建SoundPool对象
        hm=new HashMap<Integer,Integer>();//创建HashMap对象
        hm.put(1, sp.load(this, R.raw.xiazhu, 0));//加载dudu声音文件并设置为1号文件放入hm
        hm.put(2, sp.load(this, R.raw.saizi, 0));//加载musictest声音文件并设置为2号文件放入hm
        hm.put(3, sp.load(this, R.raw.jinbi, 0));//加载musictest声音文件并设置为3号文件放入hm
        hm.put(4, sp.load(this, R.raw.button, 0));//加载musictest声音文件并设置为3号文件放入hm
    }
    private Spinner spinner;
    private Spinner spinner2;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter2;
    private List<sq_cm> list_cm;
    private List<sq_bs> list_bs;
    private List<sq_qs> list_qs;
    private List<Button> list_but_cm;

    /**
     * 申请开猜
     */
    private void init_shengqingkaicai() {

            shengqingkaicai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                    asyncHttpClient.setTimeout(20000);
                    String url = all_url.url_shenqingliebiao_before2;
                    RequestParams requestParams = new RequestParams();
                    asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            super.onSuccess(jsonObject);

                            try {
                                if ("true".equals(jsonObject.getString("status"))) {
                                    System.out.println(jsonObject);
                                    list_cm=new ArrayList<sq_cm>();
                                    for (int i=0;i<jsonObject.getJSONArray("bankerCm").length();i++){
                                        sq_cm item=new sq_cm();
                                        item.setDw(jsonObject.getJSONArray("bankerCm").getJSONObject(i).getString("dw"));
                                        item.setValue(jsonObject.getJSONArray("bankerCm").getJSONObject(i).getString("value"));
                                        list_cm.add(item);
                                    }
                                    list_bs=new ArrayList<sq_bs>();
                                    for (int i=0;i<jsonObject.getJSONArray("bankerBs").length();i++){
                                        sq_bs item=new sq_bs();
                                        item.setDw(jsonObject.getJSONArray("bankerBs").getJSONObject(i).getString("dw"));
                                        item.setValue(jsonObject.getJSONArray("bankerBs").getJSONObject(i).getString("value"));
                                        list_bs.add(item);
                                    }
                                    final String[] m = new String[list_bs.size()];
                                    for (int i=0;i<list_bs.size();i++){
                                        m[i]=list_bs.get(i).getValue();
                                    }
                                    list_qs=new ArrayList<sq_qs>();
                                    for (int i=0;i<jsonObject.getJSONArray("bankerQs").length();i++){
                                        sq_qs item=new sq_qs();
                                        item.setDw(jsonObject.getJSONArray("bankerQs").getJSONObject(i).getString("dw"));
                                        item.setValue(jsonObject.getJSONArray("bankerQs").getJSONObject(i).getString("value"));
                                        list_qs.add(item);
                                    }
                                    String[] m2 = new String[list_qs.size()];
                                    for (int i=0;i<list_qs.size();i++){
                                        m2[i]=list_qs.get(i).getValue();
                                    }

                                    AlertDialog alert_hero = null;
                                    AlertDialog.Builder builder_hero;
                                    builder_hero = new AlertDialog.Builder(guess_hero2.this);
                                    View view = mInflater.inflate(R.layout.shengqingkaicai_item, null);
                                    final TextView sqjds= (TextView) view.findViewById(R.id.sqjds);
                                    Button button1= (Button) view.findViewById(R.id.but_1);
                                    Button button2= (Button) view.findViewById(R.id.but_2);
                                    Button button3= (Button) view.findViewById(R.id.but_3);
                                    Button button4= (Button) view.findViewById(R.id.but_4);
                                    Button button5= (Button) view.findViewById(R.id.but_5);
                                    Button button6= (Button) view.findViewById(R.id.but_6);
                                    Button button7= (Button) view.findViewById(R.id.but_7);
                                    Button button8= (Button) view.findViewById(R.id.but_8);
                                    list_but_cm=new ArrayList<Button>();
                                    list_but_cm.add(button1);
                                    list_but_cm.add(button2);
                                    list_but_cm.add(button3);
                                    list_but_cm.add(button4);
                                    list_but_cm.add(button5);
                                    list_but_cm.add(button6);
                                    list_but_cm.add(button7);
                                    list_but_cm.add(button8);
                                    for (int i=0;i<list_cm.size();i++){
                                        list_but_cm.get(i).setVisibility(View.VISIBLE);
                                        list_but_cm.get(i).setText(list_cm.get(i).getDw());
                                    }
                                    for (int i=0;i<list_but_cm.size();i++){
                                        final  int a=i;
                                        list_but_cm.get(i).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                sqjds.setText(list_cm.get(a).getValue());
                                            }
                                        });
                                    }

                                    spinner= (Spinner) view.findViewById(R.id.sqsl);
                                    adapter=new ArrayAdapter<String>(guess_hero2.this,android.R.layout.simple_spinner_item,m);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    spinner.setAdapter(adapter);

                                    spinner2= (Spinner) view.findViewById(R.id.sqlx);
                                    adapter2=new ArrayAdapter<String>(guess_hero2.this,android.R.layout.simple_spinner_item,m2);
                                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
                                    spinner2.setAdapter(adapter2);

                                    Button ok= (Button) view.findViewById(R.id.ok);
                                    Button quxiao= (Button) view.findViewById(R.id.quxiao);
                                    alert_hero = builder_hero.create();
                                    final AlertDialog finalAlert_hero = alert_hero;
                                    ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(sqjds.getText().toString().isEmpty()){
                                                Toast.makeText(guess_hero2.this,"申请金豆数不能为空",Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                                asyncHttpClient.setTimeout(20000);
                                                String url = all_url.url_shenqingliebiao2;
                                                RequestParams requestParams = new RequestParams();
                                                requestParams.put("userId", User.getuser().getUser_uuid());
                                                requestParams.put("expect", jincai_qs_next.getText().toString());
                                                requestParams.put("jd", sqjds.getText().toString());
                                                requestParams.put("number", spinner.getSelectedItem().toString());
                                                requestParams.put("expectNum", spinner2.getSelectedItem().toString());
                                                System.out.println("bs"+ spinner.getSelectedItem().toString()+"jd"+sqjds.getText().toString()+"qs"+spinner2.getSelectedItem().toString());
                                                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                                                    @Override
                                                    public void onSuccess(JSONObject jsonObject) {
                                                        super.onSuccess(jsonObject);
                                                        try {
                                                            if ("true".equals(jsonObject.getString("status"))) {
                                                                System.out.println("xuyao:"+jsonObject);
                                                                init_user_info();
                                                                zongxia.setText(jsonObject.getString("sumBanker"));
                                                                yixia.setText(jsonObject.getString("allBet"));
                                                                if(jsonObject.getString("bankerStatus").equals("false")){
                                                                    shengqingkaicai.setText("停止开猜");
                                                                    shengqingkaicai.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            AlertDialog alert_hero = null;
                                                                            AlertDialog.Builder builder_hero;
                                                                            builder_hero = new AlertDialog.Builder(guess_hero2.this);
                                                                            alert_hero = builder_hero.create();
                                                                            View view = mInflater.inflate(R.layout.calcel_queding, null);

                                                                            final AlertDialog finalAlert_hero = alert_hero;
                                                                            view.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    finalAlert_hero.dismiss();
                                                                                }
                                                                            });
                                                                            view.findViewById(R.id.ok_btn).setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                                                                    asyncHttpClient.setTimeout(20000);
                                                                                    String url = all_url.url_calcel_kaicai2;
                                                                                    RequestParams requestParams = new RequestParams();
                                                                                    requestParams.put("userId", User.getuser().getUser_uuid());
                                                                                    asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                                                                                        @Override
                                                                                        public void onSuccess(JSONObject jsonObject) {
                                                                                            super.onSuccess(jsonObject);
                                                                                            try {
                                                                                                KLog.json("skl",jsonObject.toString());
                                                                                                if ("true".equals(jsonObject.getString("status"))) {

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
                                                                Toast.makeText(guess_hero2.this,jsonObject.getString("result"),Toast.LENGTH_SHORT).show();
                                                            } else{
                                                                Toast.makeText(guess_hero2.this,jsonObject.getString("result"),Toast.LENGTH_SHORT).show();
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
                                            finalAlert_hero.dismiss();
                                        }
                                    });

                                    alert_hero.setView(view, 0, 0, 0, -10);
                                    alert_hero.show();
                                    quxiao.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            finalAlert_hero.dismiss();
                                        }
                                    });


                                } else{
                                    Toast.makeText(guess_hero2.this,jsonObject.getString("result"),Toast.LENGTH_SHORT).show();
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


    }

    private List<kaicai_item> list_kaicai;
    /**
     * 开猜列表
     */
    private void init_kaicailiebiao() {

        kaicailiebiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder_kaicai = new AlertDialog.Builder(guess_hero2.this);
                alert_kaicai = builder_kaicai.create();
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.setTimeout(20000);
                String url = all_url.url_kaicailliebiao2;
                RequestParams requestParams = new RequestParams();
                requestParams.put("expect", jincai_qs_next.getText().toString());
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);
                        try {
                            if ("true".equals(jsonObject.getString("status"))) {
                                Log.v("guess_herokaicailiebiao",jsonObject.toString());
                                list_kaicai=new ArrayList<kaicai_item>();
                                for(int i=0;i<jsonObject.getJSONArray("list").length();i++){
                                    kaicai_item item=new kaicai_item();
                                    item.setBh(String.valueOf(i+1));
                                    item.setName(jsonObject.getJSONArray("list").getJSONObject(i).getString("userName"));
                                    item.setTotal(jsonObject.getJSONArray("list").getJSONObject(i).getString("totalJd"));
                                    list_kaicai.add(item);
                                }
                                view = mInflater.inflate(R.layout.kaicalieibao_list_view, null);
                                ListView gridView = (ListView) view.findViewById(R.id.kaicai_list);
                                gridView.setAdapter(new kaicaliebiao_adapter(guess_hero2.this, list_kaicai));
                                alert_kaicai.setView(view, 0, 0, 0, -10);
                                alert_kaicai.show();


                            } else{
                                Toast.makeText(guess_hero2.this,jsonObject.getString("result"),Toast.LENGTH_SHORT).show();
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

    }

    View view;

    /**
     *规则
     */
    private void init_guizhe() {
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guess_hero2.this, guize_hero2.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void init_jigncai_dingdan_click_meth() {
        jigncai_dingdan_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guess_hero2.this, hero_jingcaidingdan2.class);
                startActivity(intent);
                finish();
            }
        });
    }

    List<order_item> list_objects;
    List<hm_key_value> list_hm_key;
    JSONObject object ;
    private void init_ok() {

        jincai_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jincai_ok.setEnabled(false);
                if (!MainActivity.IS_login) {
                    Intent intent = new Intent(guess_hero2.this, user_login.class);
                    startActivity(intent);
                    finish();
                }
                boolean ok_panduan = false;
                object = new JSONObject();//创建一个总的对象，这个对象对整个json串
                JSONArray jsonarray = new JSONArray();//json数组，里面包含的内容为pet的所有对象
                try {
                    object.put("DDZJ", list_xiazhu_jd_number);
                    object.put("ZFYHID", User.getuser().getUser_uuid());
                    object.put("ZFLX", 1);
                    object.put("ZFFS", 0);
                    object.put("EXPECT", jincai_qs_next.getText());
                    System.out.println("期数+------"+jincai_qs_next.getText());
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
                        Toast.makeText(guess_hero2.this, "您未下注，请点击下注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    object.put("INFO", jsonarray);
                    object.put("SJLX", "2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //before--1
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.setTimeout(20000);
                String url = all_url.url_before_order_lol2;
                RequestParams requestParams = new RequestParams();
                requestParams.put("ZFYHID", User.getuser().getUser_uuid());
                requestParams.put("DDZJ", String.valueOf(list_xiazhu_jd_number));
                requestParams.put("DATA", object.toString());
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);
                        System.out.println(jsonObject.toString());
                        try {
                            if ("true".equals(jsonObject.getString("status"))) {

                                //下单--2
                                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                asyncHttpClient.setTimeout(20000);
                                String url = all_url.url_order2_lol2;
                                RequestParams requestParams = new RequestParams();
                                requestParams.put("DATA", object.toString());

                                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(JSONObject jsonObject) {
                                        super.onSuccess(jsonObject);
                                        System.out.println(jsonObject.toString());
                                        try {
                                            if ("true".equals(jsonObject.getString("status"))) {
                                                final String YHDDID=jsonObject.getString("YHDDID");

                                                AlertDialog alert_hero = null;
                                                AlertDialog.Builder builder_hero;


                                                builder_hero = new AlertDialog.Builder(guess_hero2.this);

                                                View view = mInflater.inflate(R.layout.item_hero_ok, null);
                                                ListView listView= (ListView) view.findViewById(R.id.hero_pay_list);
                                                lol_pay_list_adapter lol_pay_list_adapter=new lol_pay_list_adapter(guess_hero2.this,list_objects);
                                                listView.setAdapter(lol_pay_list_adapter);


                                                Button ok= (Button) view.findViewById(R.id.ok);
                                                Button quxiao= (Button) view.findViewById(R.id.quxiao);
                                                ok.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        //支付--3
                                                        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                                        asyncHttpClient.setTimeout(20000);
                                                        String url = all_url.url_paylol2;
                                                        RequestParams requestParams = new RequestParams();
                                                        requestParams.put("ZFYHID", User.getuser().getUser_uuid());
                                                        requestParams.put("YHDDID",YHDDID);
                                                        requestParams.put("ZFFS","0");
                                                        requestParams.put("SJLX","2");
                                                        try {
                                                            requestParams.put("EXPECT",object.getString("EXPECT"));
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                                                            @Override
                                                            public void onSuccess(JSONObject jsonObject) {
                                                                super.onSuccess(jsonObject);
                                                                System.out.println(jsonObject.toString());
                                                                try {
                                                                    if ("true".equals(jsonObject.getString("status"))) {
                                                                        finish();
                                                                        Intent intent=new Intent(guess_hero2.this,guess_hero2.class);
                                                                        startActivity(intent);
                                                                        Toast.makeText(guess_hero2.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                                                        jincai_ok.setEnabled(true);
                                                                    } else {
                                                                        jincai_ok.setEnabled(true);
                                                                        Toast.makeText(guess_hero2.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                                                    }

                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                }

                                                            }

                                                            @Override
                                                            public void onFailure(Throwable throwable) {
                                                                super.onFailure(throwable);
                                                                Toast.makeText(guess_hero2.this, "网络繁忙，求重试", Toast.LENGTH_SHORT).show();
                                                            }


                                                        });

                                                    }
                                                });
                                                alert_hero = builder_hero.create();
                                                final AlertDialog finalAlert_hero = alert_hero;
                                                alert_hero.setView(view, 0, 0, 0, -10);
                                                alert_hero.show();
                                                quxiao.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        finalAlert_hero.dismiss();


                                                    }
                                                });

                                                jincai_ok.setEnabled(true);
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    @Override
                                    public void onFailure(Throwable throwable) {
                                        super.onFailure(throwable);
                                        please_wait.setVisibility(View.GONE);
                                    }
                                });

                            } else {
                                //金豆不足——或者
                                Toast.makeText(guess_hero2.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(guess_hero2.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    String xuyaos_qishu;
    boolean sayaya = false;

//弹出筛子的框
    private void sezi(final int ii, int[] a){
        init_jbd(now_expect, next_expect);
        final AlertDialog alert;
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(guess_hero2.this);
        alert = builder.create();
        view=LayoutInflater.from(guess_hero2.this).inflate(R.layout.sezi_four,null);

        sezi_view view1= (sezi_view) view.findViewById(R.id.ballview1);
        sezi_view view2= (sezi_view) view.findViewById(R.id.ballview2);
        sezi_view view3= (sezi_view) view.findViewById(R.id.ballview3);
        sezi_view view4= (sezi_view) view.findViewById(R.id.ballview4);
        view1.setnumber(a[0]-1);
        view2.setnumber(a[1]-1);
        view3.setnumber(a[2]-1);
        view4.setnumber(a[3]-1);
        view1.run();
        view2.run();
        view3.run();
        view4.run();
        alert.setView(view,0,-10,0,-10);
        alert.show();
        playSound2(2,0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=3;
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if((--i)<0){
                        break;
                    }
                }
                alert.dismiss();
                Message message= hero_show_handler.obtainMessage();
                message.arg1=1;
                message.arg2=ii-1;
                hero_show_handler.sendMessage(message);
            }
        }).start();

    }

    Handler hero_show_handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.arg1==1){
                show_hero2(msg.arg2);

                for (int i=0;i<list_view_hero_holder.size();i++){
                    list_view_hero_holder.get(i).setBackgroundColor(getResources().getColor(R.color.touming));
                }
                list_view_hero_holder.get(msg.arg2).setBackgroundColor(getResources().getColor(R.color.touming2));
            }
        }
    };
    private void show_hero(int a){
        final AlertDialog alert;
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(guess_hero2.this);
        alert = builder.create();
        view = mInflater.inflate(R.layout.item_hero_changebig, null);
        ImageView imageView= (ImageView) view.findViewById(R.id.hero_img);
        ImageLoader.getInstance().displayImage(list_hero.get(a).getImgbig().trim(),imageView);
        TextView number= (TextView) view.findViewById(R.id.here_bianhao);
        TextView name= (TextView) view.findViewById(R.id.name);
        TextView hereo_shux= (TextView) view.findViewById(R.id.hereo_shux);
        String shux="";
        if(list_hero.get(a).getIsBlue().equals("0")){
            shux=shux+"无蓝　";
        }else{
            shux=shux+"有蓝　";
        }
        if(list_hero.get(a).getIsAttack().equals("0")){
            shux=shux+"近战　";
        }else{
            shux=shux+"远程　";
        }
        if(list_hero.get(a).getIsHit().equals("0")){
            shux=shux+"打脸";
        }else{
            shux=shux+"打你妹";
        }
        hereo_shux.setText(shux);
        number.setText(list_hero.get(a).getNumber());
        name.setText(list_hero.get(a).getName());

        if (biaoshi_hero){
            alert.setView(view, 0, 0, 0, -10);
            alert.show();
        }




        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=3;
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if((--i)<0){
                        break;
                    }
                }
                alert.dismiss();
            }
        }).start();
    }


    /**
     * 显示金币的动画
     * @param a
     */
    private void show_hero2(int a){
        playSound3(3,0);
        final AlertDialog alert;
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(guess_hero2.this);
        alert = builder.create();
        view = mInflater.inflate(R.layout.item_hero_changebig_2, null);
        GifView gifView= (GifView) view.findViewById(R.id.gift_1);
        GifView gifView2= (GifView) view.findViewById(R.id.gift_2);
        gifView.setGifImage(R.drawable.jingbi);
        gifView2.setGifImage(R.drawable.jingbi);
        ImageView imageView= (ImageView) view.findViewById(R.id.hero_img);
        ImageLoader.getInstance().displayImage(list_hero.get(a).getImgbig().trim(),imageView);
        TextView number= (TextView) view.findViewById(R.id.here_bianhao);
        TextView name= (TextView) view.findViewById(R.id.name);
        TextView hereo_shux= (TextView) view.findViewById(R.id.hereo_shux);
        String shux="";
        if(list_hero.get(a).getIsBlue().equals("0")){
            shux=shux+"无蓝　";
        }else{
            shux=shux+"有蓝　";
        }
        if(list_hero.get(a).getIsAttack().equals("0")){
            shux=shux+"近战　";
        }else{
            shux=shux+"远程　";
        }
        if(list_hero.get(a).getIsHit().equals("0")){
            shux=shux+"打脸";
        }else{
            shux=shux+"打你妹";
        }
        hereo_shux.setText(shux);
        number.setText(list_hero.get(a).getNumber());
        name.setText(list_hero.get(a).getName());

        if (biaoshi_hero){
            alert.setView(view, 0, 0, 0, -10);
            alert.show();
        }




        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.pause(jinbi_id);
                sp.stop(jinbi_id);
                alert.dismiss();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=3;
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if((--i)<0){
                        break;
                    }
                }
                alert.dismiss();
            }
        }).start();
    }

    private void init_wangluo(final int uu) {
        please_wait.setVisibility(View.VISIBLE);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_lol2;
        RequestParams requestParams = new RequestParams();
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                        System.out.println(uu+"进来一次");
                    if ("true".equals(jsonObject.getString("status"))) {
                        if (!jsonObject.has("end_min")) {
                            init_wangluo(uu);
                            return;
                        }
                        time_text_f = Integer.parseInt(jsonObject.getString("end_min"));
                        time_text_s = Integer.parseInt(jsonObject.getString("end_ss"));
//                        time_text_f = 1;
//                        time_text_s = 1;
                        if (time_text_f == 0 && time_text_s == 0) {
//                            System.out.println(time_text_f + "开始" + time_text_s);
                            sayaya = true;
                            init_wangluo(uu);
                            return;
                        } else {
                            init_user_info();
                            String opening = jsonObject.getString("opening");
                            now_expect = jsonObject.getString("expect");
                            next_expect = jsonObject.getString("next_expect");
                            xuyaos_qishu = next_expect;
                            if ("true".equals(opening)) {
                                dengdaikaijiang.setVisibility(View.VISIBLE);
                                xianshikaijiang.setVisibility(View.GONE);
                                now_expect = (Integer.parseInt(now_expect) + 1) + "";
                                next_expect = (Integer.parseInt(next_expect) + 1) + "";
                                xuyaos_qishu = now_expect;
//                                init_xiaoshuaxin();
                            }
                            clearer();
//                            System.out.println(time_text_f + "{}" + time_text_s);
                            startTime();

                            /* */
                            zongxia.setText(jsonObject.getString("sumBanker"));
                            yixia.setText(jsonObject.getString("sumBet"));

                            /**
                             * 绘制英雄1次就够。
                             */
                            if(list_hero==null){
                                JSONArray hero_list = jsonObject.getJSONArray("hero");
                                list_hero=new ArrayList<hero_item>();
                                for (int i=0;i<hero_list.length();i++){
                                    hero_item item=new hero_item();
                                    item.setName(hero_list.getJSONObject(i).getString("name"));
                                    item.setNumber(hero_list.getJSONObject(i).getString("sort"));
                                    item.setUrl(hero_list.getJSONObject(i).getString("imgUrl"));
                                    item.setUrl(hero_list.getJSONObject(i).getString("imgUrl"));
                                    item.setImgbig(hero_list.getJSONObject(i).getString("imgBigUrl"));
                                    item.setIsAttack(hero_list.getJSONObject(i).getString("isAttack"));
                                    item.setIsBlue(hero_list.getJSONObject(i).getString("isBlue"));
                                    item.setIsHit(hero_list.getJSONObject(i).getString("isHit"));
                                    list_hero.add(item);
                                }
//                                draw_hero();//英雄不用画了
                            }

                            JSONArray open_jiang = jsonObject.getJSONArray("open");
                            list_kaijiang_results = new ArrayList<kaijiang_result_hero>();
                            for (int i = 0; i < open_jiang.length(); i++) {
                                kaijiang_result_hero item=new kaijiang_result_hero();
                                item.setExpect(open_jiang.getJSONObject(i).getString("expect"));
                                item.setCode_1(open_jiang.getJSONObject(i).getString("code_1"));
                                item.setCode_2(open_jiang.getJSONObject(i).getString("code_2"));
                                item.setCode_3(open_jiang.getJSONObject(i).getString("code_3"));
                                item.setCode_4(open_jiang.getJSONObject(i).getString("code_4"));
                                item.setCode_all(open_jiang.getJSONObject(i).getString("position"));
                                item.setLast_code_all(open_jiang.getJSONObject(i).getString("oldPosition"));
                                item.setResult(open_jiang.getJSONObject(i).getString("result"));
                                item.setName(open_jiang.getJSONObject(i).getString("name"));
                                list_kaijiang_results.add(item);
                            }
                            int[] a={Integer.parseInt(list_kaijiang_results.get(0).getCode_1()),Integer.parseInt(list_kaijiang_results.get(0).getCode_2()),Integer.parseInt(list_kaijiang_results.get(0).getCode_3()),Integer.parseInt(list_kaijiang_results.get(0).getCode_4())};
                           System.out.println(uu+"你确定是这个");
//                            if(uu==1){
//                                System.out.println("网络访问1："+jsonObject);
//                                sezi(Integer.parseInt(list_kaijiang_results.get(0).getCode_all()),a);
//                                init_user_info();
//                            }else {
//                                System.out.println("网络访问2："+jsonObject);
////                                for (int i=0;i<list_view_hero_holder.size();i++){
////                                    list_view_hero_holder.get(i).setBackgroundColor(getResources().getColor(R.color.touming));
////                                }
////                                list_view_hero_holder.get(Integer.parseInt(list_kaijiang_results.get(0).getCode_all())-1).setBackgroundColor(getResources().getColor(R.color.touming2));
//                                init_jbd(now_expect, next_expect);
//                            }

                           hero_item item= list_hero.get(Integer.valueOf(list_kaijiang_results.get(0).getCode_all())-1);
                            ImageLoader.getInstance().displayImage(item.getImgbig(),show_hero);
                            name2.setText(list_kaijiang_results.get(0).getName());
                            shuxin2.setText(list_kaijiang_results.get(0).getResult());
                            bianhao2.setText(""+(Integer.valueOf(list_kaijiang_results.get(0).getCode_all())-1));
                            init_jbd(now_expect, next_expect);
                            JSONArray hm_list = jsonObject.getJSONArray("hm");
                            list_Hm.clear();
                            //list_Hm -------得到的号码数字
                            for (int i = 0; i < hm_list.length(); i++) {
                                Hm hm = new Hm();
//                                hm.setHMLX(hm_list.getJSONObject(i).getString("HMLX"));
                                hm.setAllBet(hm_list.getJSONObject(i).getString("canBet"));
                                hm.setHasBet(hm_list.getJSONObject(i).getString("hasBet"));
                                hm.setHMMC(hm_list.getJSONObject(i).getString("HMMC"));
                                hm.setHMPL(hm_list.getJSONObject(i).getString("PL"));
                                hm.setUuid(hm_list.getJSONObject(i).getString("UUID"));
                                if (hm_list.getJSONObject(i).has("SY")) {
                                    hm.setSY(String.valueOf(hm_list.getJSONObject(i).getInt("SY")));
                                } else {
                                    hm.setSY(moer_50);
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
                        Toast.makeText(guess_hero2.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
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

    private List<View> list_view_hero;
    private List<View> list_view_hero_holder;



    boolean limit_boolen = false;//改成true就可以了

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
        //06
        jincai_group_num.setText(list_kaijiang_results.get(0).getName()+" "+list_kaijiang_results.get(0).getCode_all());
        list_qs_view.clear();

        all_guess_open_number.removeAllViews();
        for (int i = 0; i < list_kaijiang_results.size(); i++) {
            View qs_view = mInflater.inflate(R.layout.guess_qshero_xiangp, null);
            TextView qs= (TextView) qs_view.findViewById(R.id.jincai_line1_1);
            TextView op_number= (TextView) qs_view.findViewById(R.id.jincai_line1_2);
            TextView name= (TextView) qs_view.findViewById(R.id.jincai_line1_3);
            TextView jieguo1= (TextView) qs_view.findViewById(R.id.jincai_line1_4_1);
            TextView jieguo2= (TextView) qs_view.findViewById(R.id.jincai_line1_4_2);
            TextView jieguo3= (TextView) qs_view.findViewById(R.id.jincai_line1_4_3);
            qs.setText(list_kaijiang_results.get(i).getExpect());
//            String str="<font color=red>"+list_kaijiang_results.get(i).getLast_code_all()+"</font>"+"+"+list_kaijiang_results.get(i).getCode_1()+"+"+list_kaijiang_results.get(i).getCode_2()+"+"+list_kaijiang_results.get(i).getCode_3()+"+"+list_kaijiang_results.get(i).getCode_4()+"=>"+list_kaijiang_results.get(i).getCode_all();
            String str=list_kaijiang_results.get(i).getCode_all();
//            op_number.setText(Html.fromHtml(str));
            op_number.setText(str);
            name.setText(list_kaijiang_results.get(i).getName());
            String result=list_kaijiang_results.get(i).getResult();
            String[] aa=result.split(",");
            for (int j=0;j<aa.length;j++){
                switch (aa[j]){
                    case "有蓝" :jieguo1.setTextColor(getResources().getColor(R.color.blue));
                        jieguo1.setText("有蓝,");
                        break;
                    case "无蓝" :jieguo1.setTextColor(getResources().getColor(R.color.red));
                        jieguo1.setText("无蓝,");
                        break;
                    case "近战" :jieguo2.setTextColor(getResources().getColor(R.color.blue));
                        jieguo2.setText("近战,");
                        break;
                    case "远程" :jieguo2.setTextColor(getResources().getColor(R.color.red));
                        jieguo2.setText("远程,");
                        break;
                    case "打脸" :jieguo3.setTextColor(getResources().getColor(R.color.blue));
                        jieguo3.setText("打脸");
                        break;
                    case "打你妹" :jieguo3.setTextColor(getResources().getColor(R.color.red));
                        jieguo3.setText("打你妹");
                        break;
                }
            }

            all_guess_open_number.addView(qs_view);
            list_qs_view.add(qs_view);
        }
        View more_qs_view = mInflater.inflate(R.layout.item_more_kaijiang_qishu, null);
        more_qs_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(guess_hero2.this, more_qs_hero2.class);
                startActivity(intent);
                finish();

            }
        });
        all_guess_open_number.addView(more_qs_view);
        /**
         * 不能点了
         */
//        for (int i = 0; i < list_qs_view.size(); i++) {
//            final int a = i;
//            list_qs_view.get(i).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(guess_hero.this, guess_open.class);
//                    intent.putExtra("kaijiang_results", list_kaijiang_results.get(a));
//                    startActivity(intent);
//                    finish();
//                }
//            });
//        }
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
    List<TextView> list_yx;

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
        list_yx=new ArrayList<TextView>();
        for (int i = 0; i < list_Hm.size(); i++) {
            if (i < 3) {
                LinearLayout re = new LinearLayout(this);//一个item
                re.setGravity(Gravity.CENTER_HORIZONTAL);
                re.setOrientation(LinearLayout.VERTICAL);
                re.setBackgroundResource(R.drawable.guess_but_skin);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(whith_rct_3, whith_rct+20);
                lp.setMargins(30, 2, 30, 2);
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
                name.setTextColor(getResources().getColor(R.color.white));
                name.setTextSize(20);
                name.setGravity(Gravity.CENTER_HORIZONTAL);

                TextView yx = new TextView(this);
                yx.setTextColor(getResources().getColor(R.color.white));
                yx.setTextSize(10);
                yx.setGravity(Gravity.CENTER_HORIZONTAL);
                yx.setText("1赔" + list_Hm.get(i).getHMPL());

//                TextView zx = new TextView(this);
//                zx.setTextColor(getResources().getColor(R.color.white));
//                zx.setTextSize(10);
//                zx.setGravity(Gravity.CENTER_HORIZONTAL);
//                zx.setText("以下");

                TextView pl = new TextView(this);
                pl.setTextSize(10);
                pl.setTextColor(getResources().getColor(R.color.white));
                name.setText(list_Hm.get(i).getHMMC());

                pl.setText(list_Hm.get(i).getHasBet()+"/"+list_Hm.get(i).getAllBet());

                LinearLayout re_img = new LinearLayout(this);
                re_nei.addView(name);
                ImageView imageView = new ImageView(this);
                re_img.setGravity(Gravity.CENTER_HORIZONTAL);
                re_img.addView(imageView);
                RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                r1.addView(pl, lp2);
                r1.addView(re_img, lp1);
                re_nei.addView(r1);
                re.addView(yx);
                re.addView(re_nei);
                list_fang_difang.add(imageView);
                list_12.add(re);
                xiajiemia_1.addView(re);
                list_yx.add(pl);
            }
            if (i <6 && i >=3) {
                LinearLayout re = new LinearLayout(this);//一个item
                re.setGravity(Gravity.CENTER_HORIZONTAL);
                re.setOrientation(LinearLayout.VERTICAL);
                re.setBackgroundResource(R.drawable.guess_but_skin);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(whith_rct_3, whith_rct+20);
                lp.setMargins(30, 2, 30, 2);
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
                TextView yx = new TextView(this);
                yx.setTextColor(getResources().getColor(R.color.white));
                yx.setTextSize(10);
                yx.setGravity(Gravity.CENTER_HORIZONTAL);
                yx.setText("1赔" + list_Hm.get(i).getHMPL());
                pl.setTextSize(10);
                pl.setTextColor(getResources().getColor(R.color.white));
                name.setText(list_Hm.get(i).getHMMC());

                pl.setText(list_Hm.get(i).getHasBet()+"/"+list_Hm.get(i).getAllBet());
                LinearLayout re_img = new LinearLayout(this);
                re_nei.addView(name);
                ImageView imageView = new ImageView(this);
                re_img.setGravity(Gravity.CENTER_HORIZONTAL);
                re_img.addView(imageView);
                RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                r1.addView(pl, lp2);
                r1.addView(re_img, lp1);
                re_nei.addView(r1);
                re.addView(yx);
                re.addView(re_nei);
                list_fang_difang.add(imageView);
                list_12.add(re);
                xiajiemia_2.addView(re);
                list_yx.add(pl);
            }

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
                lp.setMargins(10, 0, 10, 0);
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
                lp.setMargins(10, 0, 10, 0);
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
                    playSound1(4,0);
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
                public void onClick(final View v) {

                    if (limit_boolen){
                        case_panduan(a);
                    }

                    for (int i = 0; i < list_jd.size(); i++) {
                        if (list_jd_select_panduna.get(i)) {
                            zi_sy[0] = list_money.get(a) + Integer.parseInt(list_jd.get(i).getJDGS());
                        }
                    }
                    //判断够不够
                    if ((zi_sy[0] > Integer.parseInt(list_Hm.get(a).getSY()))) {
                        Toast.makeText(guess_hero2.this, "单注上限"+moer_50+"万", Toast.LENGTH_SHORT).show();
                    } else {
  /*
                    这个网络用来解释可不可以下
                     */
                        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                        asyncHttpClient.setTimeout(20000);
                        String url = all_url.url_lol_xiahzuxianzhu2;
                        RequestParams requestParams = new RequestParams();
                        requestParams.put("JD", String.valueOf(zi_sy[0]));
                        requestParams.put("SPID", list_Hm.get(a).getUuid());
                        requestParams.put("EXPECT", jincai_qs_next.getText().toString());
                        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(JSONObject jsonObject) {
                                super.onSuccess(jsonObject);
                                try {
                                    if ("true".equals(jsonObject.getString("status"))) {
                                        list_yx.get(a).setText(jsonObject.getString("hasBet")+"/"+jsonObject.getString("canBet"));
                                        Log.v("每个按钮的限制:",jsonObject.toString());
                                        zongxia.setText(jsonObject.getString("sumBanker"));
                                        yixia.setText(jsonObject.getString("allBet"));
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

                                    } else {
                                        Toast.makeText(guess_hero2.this,jsonObject.getString("result"),Toast.LENGTH_SHORT).show();
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
    private SoundPool sp=null;//声明一个SoundPool的引用
    private HashMap<Integer,Integer> hm;//声明一个HashMap来存放声音资源

    //金豆飞
    private void fling(int aa) {
        playSound1(1,0);
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
        location2[0] = location2[0] + whith_rct_3 / 3;
//        location2[1]=location2[1]+whith_rct/2;
        top_kaijiang = top_height / 50 * 65;
        Animation fliiinf = new TranslateAnimation(location[0], location2[0], location[1] - DensityUtil.dip2px(guess_hero2.this, 115), location2[1] - DensityUtil.dip2px(guess_hero2.this, 100));
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
                    for (int i = 0; i < list_12.size(); i++) {
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
//                    guess_but_down.setImageResource(R.mipmap.all_guess_shangla);
                }
            }
            if (e1.getY() - e2.getY() > 50) {
                if (flag) {
                    for (int i = 0; i < list_12.size(); i++) {
                        list_12.get(i).setEnabled(true);
                    }
                    int height = all_guess_open_number_da.getMeasuredHeight();
                    down_move_shuxingdonghua(guess_xiajiemian_all, 0);
//                    up_move();
//                    int height = all_guess_open_number.getMeasuredHeight();
//                    slideview(0,-height);
                    flag = false;
//                    guess_but_down.setImageResource(R.mipmap.all_guess_xiala);
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
                    for (int i = 0; i < list_12.size(); i++) {
                        list_12.get(i).setEnabled(false);
                    }
                    int height = all_guess_open_number_da.getMeasuredHeight();
                    down_move_shuxingdonghua(guess_xiajiemian_all, height);
                    flag = true;
//                    guess_but_down.setImageResource(R.mipmap.all_guess_shangla);
                } else {
                    for (int i = 0; i < list_12.size(); i++) {
                        list_12.get(i).setEnabled(true);
                    }
                    int height = all_guess_open_number_da.getMeasuredHeight();
                    down_move_shuxingdonghua(guess_xiajiemian_all, 0);
//                    up_move();
//                    int height = all_guess_open_number.getMeasuredHeight();
//                    slideview(0,-height);
                    flag = false;
//                    guess_but_down.setImageResource(R.mipmap.all_guess_xiala);
                }
                if (limit_boolen) {

                    init_limit();//用来限制
                }
            }
        });
    }

    int time_shi=0;
    int time_miao=5;
    /*
    得到时间
     */
    private void wangluo_config(){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_hero_time;
        asyncHttpClient.post(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        time_shi=jsonObject.getJSONObject("config").getInt("PZSJ");
                        time_miao=jsonObject.getJSONObject("config").getInt("PZZ");
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
    boolean bbq = false;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.arg1 != -2) {
                if (bbq) {
                    bbq = false;
                }
                if(msg.arg1==time_miao&&msg.arg2==time_shi){
                    Message m2 = lhandler.obtainMessage();
                    m2.arg2 = 2;
                    if (guess_hero2.this != null) {
                        lhandler.sendMessage(m2);
                    }
                }
                if (msg.arg1 < 10) {
                    guess_time.setText(msg.arg2 + ":0" + msg.arg1);

                } else {
                    guess_time.setText(msg.arg2 + ":" + msg.arg1);

                }
            } else if (msg.arg1 == -2) {
                System.out.println("");
                if (!bbq) {
                    bbq = true;
                    timer.cancel();
                    init_wangluo(1);

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
                Toast.makeText(guess_hero2.this, next_expect + "停止下注", Toast.LENGTH_SHORT).show();
            }

        }
    };
    Thread thread1;




    boolean biaoshi_hero=true;
    @Override
    public void onBackPressed() {
        biaoshi_hero=false;
        Intent intent = new Intent(guess_hero2.this, MainActivity.class);
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

//                System.out.println(time_text_f+","+time_text_s);
                if (time_text_s == 0) {
                    if (time_text_f == 0 && (!yigesuo[0])) {
                        yigesuo[0] = true;
                        message.arg1 = -2;
                        System.out.println("有没有到这里");
                        mhandler.sendMessage(message);
                        return;
                    }
                    time_text_s =60;
                    time_text_f--;

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
                onBackPressed();
            }
        });
    }

    private View right;
    private TextView xuyaochuandehua;
    private TextView cent_text;
    private void init() {
        name2= (TextView) findViewById(R.id.name2);
        shuxin2= (TextView) findViewById(R.id.shuxin2);
        bianhao2= (TextView) findViewById(R.id.bianhao2);
        show_hero= (ImageView) findViewById(R.id.show_hero);
        yixia= (TextView) findViewById(R.id.yixia);
        zongxia= (TextView) findViewById(R.id.zongxia);
        shengqingkaicai= (Button) findViewById(R.id.shengqingkaicai);
        kaicailiebiao= (Button) findViewById(R.id.kaicailiebiao);
        jincai_group_name= (TextView) findViewById(R.id.jincai_group_name);
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

        jincai_group_num = (TextView) findViewById(R.id.jincai_group_num);

        whith_rct = (MainActivity.screenWidth - 5 * DensityUtil.dip2px(guess_hero2.this, 30)) / 4;
        whith_rct_3 = (MainActivity.screenWidth - 4 * DensityUtil.dip2px(guess_hero2.this, 30)) / 3;
        whith_rct_7 = (MainActivity.screenWidth - 6 * DensityUtil.dip2px(guess_hero2.this, 30)) / 5;

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
        back = guess_topbar.findViewById(R.id.logreg_left);
        right = guess_topbar.findViewById(R.id.logreg_right);
        cent_text= (TextView) guess_topbar.findViewById(R.id.logreg_center);


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
        Typeface type = Typeface.createFromAsset(guess_hero2.this.getAssets(), "yjsz.ttf");
        guess_time.setTypeface(type);

        all_guess_open_number = (LinearLayout) findViewById(R.id.all_guess_open_number);
        all_guess_open_number_da = (LinearLayout) findViewById(R.id.all_guess_open_number_da);
        guess_xiajiemian_all = (RelativeLayout) findViewById(R.id.guess_xiajiemian_all);
        guess_but_down = findViewById(R.id.guess_but_down);
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
