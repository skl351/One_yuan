package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.Pay.pay_wangye;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;
import com.yiyuangou.android.one_yuan.util.btn_cls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/9.
 */
public class user_chongzhi extends Activity {
    private View chongzhi_10;
    private View chongzhi_50;
    private View chongzhi_100;
    private View chongzhi_300;
    private View chongzhi_500;
    private EditText chongzhi_qita;
    List<View> list_money;
    List<Boolean> list_money_bollen;

    private View chongzhi_topbar;
    private View back;
    private View jinfdong_zhifu;
    private View weix_zhifu;
    private ImageView jinfdong_panduan;
    private ImageView weixing_panduan;
    private String zfje="";
    private String zffs="";
private Button myself_chongzhi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.user_chongzhi);
        init();
        init_6_click();
        init_zffs();
        init_ok();
        init_back();

    }

    private void init_ok() {
        myself_chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_cls.bt_disable(myself_chongzhi);
                //得到金额数
                for (int i = 0; i < list_money_bollen.size(); i++) {
                    if (list_money_bollen.get(i)) {
                        switch (i) {
                            case 0:
                                zfje = "10";
                                break;
                            case 1:
                                zfje = "50";
                                break;
                            case 2:
                                zfje = "100";
                                break;
                            case 3:
                                zfje = "300";
                                break;
                            case 4:
                                zfje = "500";
                                break;
                            case 5:
                                zfje = chongzhi_qita.getText().toString();
                                break;
                        }
                    }
                }
                if ("0".equals(zfje)) {
                    btn_cls.bt_endisable(myself_chongzhi);
                    return;
                }
                if (zfje.isEmpty()) {
                    btn_cls.bt_endisable(myself_chongzhi);
                    Toast.makeText(user_chongzhi.this,"金额不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (zffs.isEmpty()) {
                    btn_cls.bt_endisable(myself_chongzhi);
                    Toast.makeText(user_chongzhi.this,"请选择支付方式",Toast.LENGTH_SHORT).show();
                    return;
                }

                init_user_info();

            }
        });
    }
    private void init_user_info() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_chongzhi;
        RequestParams requestParams = new RequestParams();
        requestParams.put("ZFYHID", User.getuser().getUser_uuid());
        requestParams.put("ZFJE", zfje);
        requestParams.put("ZFFS", zffs);
        requestParams.put("ZFLX", "1");
        requestParams.put("SJLX", "2");
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))) {
                        System.out.println(jsonObject);
                        String DDBH = jsonObject.getString("DDBH");
                        String ZFDDID = jsonObject.getString("ZFDDID");

                        if ("1".equals(zffs)) {
                            send_jd(DDBH, ZFDDID);
                        }

                    } else {
                        Toast.makeText(user_chongzhi.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }
                    btn_cls.bt_endisable(myself_chongzhi);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                Toast.makeText(user_chongzhi.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void send_jd(String ddbh, String zfddid) {
        Intent intent=new Intent(user_chongzhi.this,pay_wangye.class);

        intent.putExtra("ddbh", ddbh);
        intent.putExtra("zfddid", zfddid);
        startActivity(intent);
    }

    private void init_zffs() {
        for (int i=0;i<list_zffs.size();i++){
            final int a=i;
            list_zffs.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i=0;i<list_zffs_img.size();i++){
                       btn_cls.bt_disselected(list_zffs_img.get(i));
                    }
                    btn_cls.bt_enselected(list_zffs_img.get(a));
                    zffs= String.valueOf(a+1);
                }
            });
        }
    }
    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void init_6_click() {
        for (int i=0;i<list_money.size();i++){
            final  int a=i;
            list_money.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j=0;j<list_money.size();j++){
                        list_money.get(j).setSelected(false);
                        list_money_bollen.set(j, false);
                        if (j!=5){
                            Button bt=(Button)list_money.get(j);
                            bt.setTextColor(getResources().getColor(R.color.black));
                        }
                    }
                    v.setSelected(true);
                    list_money_bollen.set(a,true);
                    if (a!=5){
                        Button bt=(Button)list_money.get(a);
                        bt.setTextColor(getResources().getColor(R.color.home_orange));
                    }

                }
            });
        }
    }

    List<View> list_zffs;
    List<ImageView> list_zffs_img;
    private void init() {
        chongzhi_topbar=findViewById(R.id.chongzhi_topbar);
        back=chongzhi_topbar.findViewById(R.id.logreg_left);
        list_zffs=new ArrayList<View>();
        list_zffs_img=new ArrayList<ImageView>();
        myself_chongzhi= (Button) findViewById(R.id.myself_chongzhi);

        weixing_panduan= (ImageView) findViewById(R.id.weixing_panduan);
        jinfdong_panduan= (ImageView) findViewById(R.id.jinfdong_panduan);
        list_zffs_img.add(jinfdong_panduan);
        list_zffs_img.add(weixing_panduan);
        weix_zhifu=findViewById(R.id.weix_zhifu);
        jinfdong_zhifu=findViewById(R.id.jinfdong_zhifu);
        list_zffs.add(jinfdong_zhifu);
        list_zffs.add(weix_zhifu);

        list_money=new ArrayList<View>();
        chongzhi_qita= (EditText) findViewById(R.id.chongzhi_qita);
        chongzhi_qita.setInputType(InputType.TYPE_CLASS_NUMBER);
        chongzhi_10=findViewById(R.id.chongzhi_10);
        chongzhi_50=findViewById(R.id.chongzhi_50);
        chongzhi_100=findViewById(R.id.chongzhi_100);
        chongzhi_300=findViewById(R.id.chongzhi_300);
        chongzhi_500=findViewById(R.id.chongzhi_500);
        list_money.add(chongzhi_10);
        list_money.add(chongzhi_50);
        list_money.add(chongzhi_100);
        list_money.add(chongzhi_300);
        list_money.add(chongzhi_500);
        list_money.add(chongzhi_qita);
        list_money_bollen=new ArrayList<Boolean>();
        for (int i=0;i<6;i++){
            list_money_bollen.add(false);
        }
    }
}
