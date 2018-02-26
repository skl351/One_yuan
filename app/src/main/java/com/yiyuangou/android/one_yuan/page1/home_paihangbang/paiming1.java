package com.yiyuangou.android.one_yuan.page1.home_paihangbang;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.paihangbang_item;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/14.
 */
public class paiming1 extends Fragment {
    private View view;
    List<paihangbang_item> list;
    private ImageView paiming2_user_img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.paiming1, container, false);

        init();
        if(MainActivity.IS_login){
            init_userinfo();
//            init_wangluo();
        }else{
//            init_wangluo2();
        }


        return  view;
    }

    private void init_userinfo() {
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
                    System.out.println(jsonObject);
                    if ("true".equals(jsonObject.getString("status"))) {
                        if (jsonObject.getJSONObject("user").has("faceImg")) {
                            ImageLoader.getInstance().displayImage(jsonObject.getJSONObject("user").getString("faceImg"), paiming2_user_img);
                        }
                    }
                } catch (Exception e) {
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
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_xiazhubang;
        RequestParams requestParams = new RequestParams();
        requestParams.put("number", "100");
        requestParams.put("userId", User.getuser().getUser_uuid());
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    System.out.println(jsonObject);
                    if ("true".equals(jsonObject.getString("status"))) {
                        if (!jsonObject.getString("pm").equals("未上榜")){
                            paiming2_mc.setText("第"+String.valueOf(jsonObject.getInt("pm"))+"名");
                        }else{
                            paiming2_mc.setText("未上榜");
                        }

                        paiming2_jd.setText(String.valueOf(jsonObject.getInt("xz")));
                        JSONArray jsonArray = jsonObject.getJSONArray("bankingList");
                        list.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            paihangbang_item p1 = new paihangbang_item();
                            p1.setName(jsonArray.getJSONObject(i).getString("userName"));
                            p1.setMoney(String.valueOf(jsonArray.getJSONObject(i).getInt("xz")));
                            if (jsonArray.getJSONObject(i).has("faceImg")) {
                                p1.setImg(jsonArray.getJSONObject(i).getString("faceImg"));
                            } else {
                                p1.setImg("false");
                            }
                            list.add(p1);
                        }
                        init_bangdan(list);
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);

            }

        });
    }
    private void init_wangluo2() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_xiazhubang;
        RequestParams requestParams = new RequestParams();
        requestParams.put("number", "100");
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    System.out.println(jsonObject);
                    if ("true".equals(jsonObject.getString("status"))) {


                        JSONArray jsonArray = jsonObject.getJSONArray("bankingList");
                        list.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            paihangbang_item p1 = new paihangbang_item();
                            p1.setName(jsonArray.getJSONObject(i).getString("userName"));
                            p1.setMoney(String.valueOf(jsonArray.getJSONObject(i).getInt("xz")));
                            if (jsonArray.getJSONObject(i).has("faceImg")) {
                                p1.setImg(jsonArray.getJSONObject(i).getString("faceImg"));
                            } else {
                                p1.setImg("false");
                            }

                            list.add(p1);
                        }
                        init_bangdan(list);
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);

            }

        });
    }

    private void init_bangdan(List<paihangbang_item> list) {


        for (int i=0;i<list.size();i++){
            if(i<3){
                switch (i){
                    case 0:
                        View v1= view.findViewById(R.id.item_paiming_caifubang_num1);
                        v1.setVisibility(View.VISIBLE);
                        ImageView img= (ImageView) v1.findViewById(R.id.item_paiming_caifubang_num1_img);
                        TextView name= (TextView) v1.findViewById(R.id.item_paiming_caifubang_num1_name);
                        TextView money= (TextView) v1.findViewById(R.id.item_paiming_caifubang_num1_money);
                        name.setText(list.get(i).getName());
                        money.setText(list.get(i).getMoney());
                        if(!"false".equals(list.get(i).getImg())){
                            ImageLoader.getInstance().displayImage(list.get(i).getImg(),img);

                        }
                        //图片再说
                        break;
                    case 1:
                        View v2= view.findViewById(R.id.item_paiming_caifubang_num2);
                        v2.setVisibility(View.VISIBLE);
                        ImageView img2= (ImageView) v2.findViewById(R.id.item_paiming_caifubang_num2_img);
                        TextView name2= (TextView) v2.findViewById(R.id.item_paiming_caifubang_num2_name);
                        TextView money2= (TextView) v2.findViewById(R.id.item_paiming_caifubang_num2_money);
                        name2.setText(list.get(i).getName());
                        money2.setText(list.get(i).getMoney());
                        if(!"false".equals(list.get(i).getImg())){
                            ImageLoader.getInstance().displayImage(list.get(i).getImg(),img2);
                        }
                        //图片再说
                        break;
                    case 2:
                        View v3= view.findViewById(R.id.item_paiming_caifubang_num3);
                        v3.setVisibility(View.VISIBLE);
                        ImageView img3= (ImageView) v3.findViewById(R.id.item_paiming_caifubang_num3_img);
                        TextView name3= (TextView) v3.findViewById(R.id.item_paiming_caifubang_num3_name);
                        TextView money3= (TextView) v3.findViewById(R.id.item_paiming_caifubang_num3_money);
                        name3.setText(list.get(i).getName());
                        money3.setText(list.get(i).getMoney());
                        if(!"false".equals(list.get(i).getImg())){
                            ImageLoader.getInstance().displayImage(list.get(i).getImg(),img3);
                        }
                        //图片再说
                        break;

                }

            }else{
                View view_4_wuq= LayoutInflater.from(getActivity()).inflate(R.layout.item_paiming_caifubang_2, null);
                TextView paiming_text1_num= (TextView) view_4_wuq.findViewById(R.id.paiming_text1_num);
                ImageView item_paiming2_img= (ImageView) view_4_wuq.findViewById(R.id.item_paiming2_img);
                TextView item_paiming2_name= (TextView) view_4_wuq.findViewById(R.id.item_paiming2_name);
                TextView item_paiming2_money= (TextView) view_4_wuq.findViewById(R.id.item_paiming2_money);
                int a=i+1;
                paiming_text1_num.setText(""+a);
                item_paiming2_name.setText(list.get(i).getName());
                item_paiming2_money.setText(list.get(i).getMoney());
                if(!"false".equals(list.get(i).getImg())){
                    ImageLoader.getInstance().displayImage(list.get(i).getImg(),item_paiming2_img);
                    item_paiming2_img.setBackgroundResource(0);
                }
                caifubang_huangdong_liebiao.addView(view_4_wuq);
            }



        }


    }

    private LinearLayout caifubang_huangdong_liebiao;
    private TextView paiming2_jd;
    private TextView paiming2_mc;
    private void init() {

        paiming2_user_img= (ImageView) view.findViewById(R.id.paiming2_user_img);
        paiming2_mc= (TextView) view.findViewById(R.id.paiming2_mc);
        paiming2_jd= (TextView) view.findViewById(R.id.paiming2_jd);
        list=new ArrayList<paihangbang_item>();
        caifubang_huangdong_liebiao= (LinearLayout) view.findViewById(R.id.caifubang_huangdong_liebiao);
    }
}
