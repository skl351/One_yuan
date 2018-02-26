package com.yiyuangou.android.one_yuan.page1.huanlegou;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/5/23.
 */
public class zhuanpan extends Activity {

    private View top_bar;
    private View back;
    private int width_kuang;

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private ImageView img6;
    private ImageView img7;
    private ImageView img8;
    private List<ImageView> list_img8=new ArrayList<ImageView>();//八个图片
    private ImageButton zhuan_center;

    private Thread th_start;

    private ImageButton select_1;
    private ImageButton select_2;
    private ImageButton select_3;
    private ImageButton select_4;
    private ImageButton select_5;
    private ImageButton select_6;
    private ImageButton select_7;
    private ImageButton select_8;
    private List<ImageButton> list_select=new ArrayList<ImageButton>();
    private String id_jiang;
    private String mes;

    private View jiegongge_zhongjiandekuang;
    private String name;
    private String info;
    private String roomId;

    private TextView jd_jiug;
    private TextView rwjd;
    private TextView jiugong_jd_info;
    private List<String> list_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.zhuanpan_view);
        name=getIntent().getStringExtra("name");
        info=getIntent().getStringExtra("info");
        roomId=getIntent().getStringExtra("roomId");
        init();
        init_back();
        init_wangluo();
        init_user_info();
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

                        jd_jiug.setText(new DecimalFormat("#,##0").format(new Double(jsonObject.getString("useJd"))));//金豆
                        rwjd.setText(new DecimalFormat("#,##0").format(new Double(jsonObject.getJSONObject("user").getString("signJindou"))));//金豆
                        jiugong_jd_info.setText(new DecimalFormat("#,##0").format(new Double(jsonObject.getJSONObject("user").getString("commissionPoints"))));//金豆
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

    private void init_wangluo() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_zhuanpaninfo;
        RequestParams requestParams=new RequestParams();
        requestParams.put("roomId", roomId);
        asyncHttpClient.post(url, requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    System.out.println(jsonObject);
                    if ("true".equals(jsonObject.getString("status"))){
                        JSONArray jsonArray=jsonObject.getJSONArray("award");
                        list_type.clear();
                        for (int i=0;i<8;i++){
                            ImageLoader.getInstance().displayImage(jsonArray.getJSONObject(i).getString("img"),list_img8.get(i));
                            list_type.add(jsonArray.getJSONObject(i).getString("type"));
                        }
                        for(int i=0;i<8;i++){
                            if(!jsonArray.getJSONObject(i).getString("type").equals("0")){
                                list_text.get(i).setText(jsonArray.getJSONObject(i).getString("name"));

                            }
                        }
                        init_start();
                    }else{
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

    private void init_start() {
        zhuan_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zhuan_center.setEnabled(false);
                init_start_wangluo();
            }
        });
    }
    private void init_start_wangluo() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_kaijiang;
        RequestParams requestParams=new RequestParams();
        requestParams.put("roomId",roomId);
        requestParams.put("userId", User.getuser().getUser_uuid());
        asyncHttpClient.post(url, requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    System.out.println(jsonObject);
                    if ("true".equals(jsonObject.getString("status"))){
                        id_jiang=jsonObject.getString("id");
                        if("0".equals(list_type.get(Integer.valueOf(id_jiang)-1))){
                            mes=jsonObject.getString("mes")+",请前往我的订单中领取";
                        }else{
                            mes=jsonObject.getString("mes");
                        }
                        i=-1;
                        jj=19;
                        zhuan_center.setEnabled(false);
                        th_start=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i=0;i<5;i++){
                                    try {
                                        Message msg=handler.obtainMessage();
                                        msg.arg1=1;
                                        handler.sendMessage(msg);
                                        Thread.sleep(400);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                while ((--jj)>=0){
                                    try {

                                        Message msg=handler.obtainMessage();
                                        msg.arg1=1;
                                        handler.sendMessage(msg);
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                int aa=Integer.valueOf(id_jiang);
                                while ((--aa)>=0){
                                    try {

                                        Message msg=handler.obtainMessage();
                                        msg.arg1=1;
                                        handler.sendMessage(msg);
                                        Thread.sleep(400);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                Message msg=handler.obtainMessage();
                                if(!flag){
                                    msg.arg1=2;
                                    handler.sendMessage(msg);
                                }

                            }
                        });
                        th_start.start();


                    }else{
                        Toast.makeText(zhuanpan.this,jsonObject.getString("result"),Toast.LENGTH_SHORT).show();
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

    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    boolean flag=false;

    @Override
    public void onBackPressed() {
        System.out.println("关闭了");
        flag=true;
        super.onBackPressed();
    }

    private RelativeLayout zhuan_left_top_re;
    private RelativeLayout zhuan_center_top_re;
    private RelativeLayout zhuan_left_bottow_re;
    private RelativeLayout zhuan_right_re;
    private RelativeLayout zhuan_left_re;
    private RelativeLayout zhuan_right_top_re;
    private RelativeLayout zhuan_center_bottow_re;
    private RelativeLayout zhuan_right_bottow_re;

    private TextView img_1;
    private TextView img_2;
    private TextView img_3;
    private TextView img_4;
    private TextView img_5;
    private TextView img_6;
    private TextView img_7;
    private TextView img_8;
    private List<TextView> list_text;
    private void init() {
        list_type=new ArrayList<String>();
        jiugong_jd_info= (TextView) findViewById(R.id.jiugong_jd_info);
        rwjd= (TextView) findViewById(R.id.rwjd);
        list_text=new ArrayList<TextView>();
        img_1= (TextView) findViewById(R.id.img_1);
        img_2= (TextView) findViewById(R.id.img_2);
        img_3= (TextView)findViewById(R.id.img_3);
        img_4= (TextView)findViewById(R.id.img_4);
        img_5= (TextView)findViewById(R.id.img_5);
        img_6= (TextView)findViewById(R.id.img_6);
        img_7= (TextView)findViewById(R.id.img_7);
        img_8= (TextView)findViewById(R.id.img_8);
        list_text.add(img_1);
        list_text.add(img_2);
        list_text.add(img_3);
        list_text.add(img_5);
        list_text.add(img_8);
        list_text.add(img_7);
        list_text.add(img_6);
        list_text.add(img_4);

        jd_jiug= (TextView) findViewById(R.id.jd_jiug);

        select_1= (ImageButton) findViewById(R.id.select_1);
        select_2= (ImageButton) findViewById(R.id.select_2);
        select_3= (ImageButton) findViewById(R.id.select_3);
        select_4= (ImageButton) findViewById(R.id.select_4);
        select_5= (ImageButton) findViewById(R.id.select_5);
        select_6= (ImageButton) findViewById(R.id.select_6);
        select_7= (ImageButton) findViewById(R.id.select_7);
        select_8= (ImageButton) findViewById(R.id.select_8);
        list_select.add(select_1);
        list_select.add(select_2);
        list_select.add(select_3);
        list_select.add(select_5);
        list_select.add(select_8);
        list_select.add(select_7);
        list_select.add(select_6);
        list_select.add(select_4);


        zhuan_center= (ImageButton) findViewById(R.id.zhuan_center);
        top_bar=findViewById(R.id.jiugongge_topbar);
        back=top_bar.findViewById(R.id.logreg_left);
        TextView textView= (TextView) top_bar.findViewById(R.id.logreg_center);
        textView.setText(name);
        img1= (ImageView) findViewById(R.id.zhuan_left_top);
        img2=(ImageView) findViewById(R.id.zhuan_center_top);
        img3=(ImageView) findViewById(R.id.zhuan_right_top);
        img4=(ImageView) findViewById(R.id.zhuan_left);
        img5=(ImageView) findViewById(R.id.zhuan_right);
        img6=(ImageView) findViewById(R.id.zhuan_left_bottow);
        img7=(ImageView) findViewById(R.id.zhuan_center_bottow);
        img8=(ImageView) findViewById(R.id.zhuan_right_bottow);
        list_img8.add(img1);
        list_img8.add(img2);
        list_img8.add(img3);
        list_img8.add(img5);
        list_img8.add(img8);
        list_img8.add(img7);
        list_img8.add(img6);
        list_img8.add(img4);

        jiegongge_zhongjiandekuang=  findViewById(R.id.jiegongge_zhongjiandekuang);
        RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) jiegongge_zhongjiandekuang.getLayoutParams();
        lp.width= MainActivity.screenWidth*5/6;
        lp.height=MainActivity.screenWidth*5/6;
        width_kuang=lp.width;
        jiegongge_zhongjiandekuang.setLayoutParams(lp);
        for (int i=0;i<list_img8.size();i++){
            list_img8.get(i).getLayoutParams().width=width_kuang/4;
            list_img8.get(i).getLayoutParams().height=width_kuang/4;

        }
        zhuan_left_top_re= (RelativeLayout) findViewById(R.id.zhuan_left_top_re);
        zhuan_center_top_re= (RelativeLayout)findViewById(R.id.zhuan_center_top_re);
        zhuan_right_top_re= (RelativeLayout) findViewById(R.id.zhuan_right_top_re);
        zhuan_left_re= (RelativeLayout) findViewById(R.id.zhuan_left_re);
        zhuan_right_re= (RelativeLayout) findViewById(R.id.zhuan_right_re);
        zhuan_left_bottow_re= (RelativeLayout) findViewById(R.id.zhuan_left_bottow_re);
        zhuan_center_bottow_re= (RelativeLayout) findViewById(R.id.zhuan_center_bottow_re);
        zhuan_right_bottow_re= (RelativeLayout) findViewById(R.id.zhuan_right_bottow_re);

        zhuan_right_bottow_re.getLayoutParams().height=width_kuang/4;
        zhuan_right_bottow_re.getLayoutParams().width=width_kuang/4;
        zhuan_center_bottow_re.getLayoutParams().height=width_kuang/4;
        zhuan_center_bottow_re.getLayoutParams().width=width_kuang/4;
        zhuan_left_bottow_re.getLayoutParams().height=width_kuang/4;
        zhuan_left_bottow_re.getLayoutParams().width=width_kuang/4;
        zhuan_right_re.getLayoutParams().height=width_kuang/4;
        zhuan_right_re.getLayoutParams().width=width_kuang/4;
        zhuan_left_re.getLayoutParams().height=width_kuang/4;
        zhuan_left_re.getLayoutParams().width=width_kuang/4;
        zhuan_right_top_re.getLayoutParams().height=width_kuang/4;
        zhuan_right_top_re.getLayoutParams().width=width_kuang/4;
        zhuan_center_top_re.getLayoutParams().height=width_kuang/4;
        zhuan_center_top_re.getLayoutParams().width=width_kuang/4;
        zhuan_left_top_re.getLayoutParams().height=width_kuang/4;
        zhuan_left_top_re.getLayoutParams().width=width_kuang/4;

    }
    int i=-1;
    int jj=19;


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1==1){
                for (int i=0;i<list_select.size();i++){
                    list_select.get(i).setSelected(false);
                    list_img8.get(i).setBackgroundResource(R.mipmap.zhuan_bg_8);
                }

                list_select.get(++i%8).setSelected(true);
                list_img8.get(i%8).setBackgroundResource(R.mipmap.zhuan_gaibiande_img_bg);
            }
            if(msg.arg1==2){
                init_user_info();
                zhuan_center.setEnabled(true);


                    Toast.makeText(zhuanpan.this,mes,Toast.LENGTH_SHORT).show();

            }
        }
    };

}

