package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.adapter.user_present_adapter;
import com.yiyuangou.android.one_yuan.bean.User_present;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/9.
 */
public class user_zengsong1 extends Fragment {
    private View user_sengdong_topbar;
    private View view;

    private user_present_adapter adapter;
    GridView gridView;
    PullToRefreshLayout.OnRefreshListener re;
    List<User_present> list=new ArrayList<User_present>();
    private View xiangyaotoum;
    private View zhanwushuju;
    int flag=1;
    private TextView zengdsong_jd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.user_zengsong1, container, false);
        init();
        init_wangluo_init();
          /*
		 * 在布局中找到一个自定义了的控件，其实已经写好了，只要给他设置一个监听器实现两个功能。---3
		 */
        ((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
                .setOnRefreshListener(getlin());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        init_wangluo_init();
    }

    protected void init_wangluo_init() {
        flag=1;
        xiangyaotoum.setVisibility(View.VISIBLE);
        zhanwushuju.setVisibility(View.GONE);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_send_index;
        RequestParams requestParams=new RequestParams();
        requestParams.put("userId",User.getuser().getUser_uuid());
        requestParams.put("PAGE", "" + flag);
        requestParams.put("NUM", "10");
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());
                try {
                    if ("true".equals(arg1.getString("status"))) {
                        JSONArray jsonArray;
                        jsonArray = arg1.getJSONArray("donate");
                        list.clear();
                        zengdsong_jd.setText(arg1.getString("useJd"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            User_present user_present1 = new User_present();
                            user_present1.setSPJG(jsonArray.getJSONObject(i).getString("money"));
                            user_present1.setSPMC(jsonArray.getJSONObject(i).getString("name"));
                            user_present1.setUrl_img(jsonArray.getJSONObject(i).getString("img"));
                            user_present1.setUuid(jsonArray.getJSONObject(i).getString("uuid"));
                            user_present1.setShouxufei(String.valueOf(jsonArray.getJSONObject(i).getLong("actualJd")));
                            int fa = Integer.parseInt(jsonArray.getJSONObject(i).getString("jd"));
                            if (fa >= 0) {
                                user_present1.setOk_xia("true");
                            } else {
                                user_present1.setOk_xia("false");
                            }

                            list.add(user_present1);
                        }
				/*
				 * 适配器和布局在这里初始化好
				 */
                        adapter = new user_present_adapter(getActivity(), list);
                        gridView.setAdapter(adapter);
                    } else {
                        zhanwushuju.setVisibility(View.VISIBLE);
                    }
                    xiangyaotoum.setVisibility(View.GONE);


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Throwable arg0) {
                // TODO Auto-generated method stub
                super.onFailure(arg0);
                xiangyaotoum.setVisibility(View.GONE);
                zhanwushuju.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),"网络繁忙",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        zengdsong_jd= (TextView) view.findViewById(R.id.zengdsong_jd);
        gridView = (GridView) view.findViewById(R.id.content_view);
        xiangyaotoum=view.findViewById(R.id.xiangyaotoum);
        zhanwushuju=view.findViewById(R.id.zhanwushuju);
    }

    /**
     * 在其中实现上拉和下拉的功能-----4----最主要的地方
     * @return
     */
    private PullToRefreshLayout.OnRefreshListener getlin() {
        return re=new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        init_wangluo_init();
                        pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1000);


            }


            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                init_wangluo_init2();
                // 千万别忘了告诉控件加载完毕了哦！
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);

            }
        };

    }

    protected void init_wangluo_init2() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_send_index;
        RequestParams requestParams=new RequestParams();
        requestParams.put("userId",User.getuser().getUser_uuid());
        flag=flag+1;
        requestParams.put("PAGE", "" + flag);
        requestParams.put("NUM", "10");
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());
                try {
                    if ("true".equals(arg1.getString("status"))) {
                        JSONArray jsonArray;
                        jsonArray = arg1.getJSONArray("donate");
                        zengdsong_jd.setText(arg1.getString("useJd"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            User_present user_present1=new User_present();
                            user_present1.setSPJG(jsonArray.getJSONObject(i).getString("money"));
                            user_present1.setSPMC(jsonArray.getJSONObject(i).getString("name"));
                            user_present1.setUrl_img(jsonArray.getJSONObject(i).getString("img"));
                            user_present1.setUuid(jsonArray.getJSONObject(i).getString("uuid"));
                            user_present1.setShouxufei(jsonArray.getJSONObject(i).getString("actualJd"));
                            int fa= Integer.parseInt(jsonArray.getJSONObject(i).getString("jd"));
                            if(fa>=0){
                                user_present1.setOk_xia("true");
                            }else{
                                user_present1.setOk_xia("false");
                            }

                            list.add(user_present1);
                        }
				/*
				 * 适配器和布局在这里初始化好
				 */
                        adapter .notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Throwable arg0) {
                // TODO Auto-generated method stub
                super.onFailure(arg0);
                Toast.makeText(getActivity(),"网络繁忙",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
