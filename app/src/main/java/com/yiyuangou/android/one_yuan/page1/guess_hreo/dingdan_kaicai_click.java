package com.yiyuangou.android.one_yuan.page1.guess_hreo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.socks.library.KLog;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.adapter.dingdan_hero_adapter;
import com.yiyuangou.android.one_yuan.adapter.dingdan_kaicai_order_adapter;
import com.yiyuangou.android.one_yuan.bean.item_order;
import com.yiyuangou.android.one_yuan.bean.jingcai_item;
import com.yiyuangou.android.one_yuan.bean.kaicai_order_item;
import com.yiyuangou.android.one_yuan.page4.PullToRefreshLayout;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/7.
 */
public class dingdan_kaicai_click extends Fragment {
    private View view;
    dingdan_kaicai_order_adapter adapter ;
    GridView gridView;
    PullToRefreshLayout.OnRefreshListener re;
    List<kaicai_order_item> qs_s;
    private View please_wait;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=  inflater.inflate(R.layout.dingdan_hero, container, false);
        gridView = (GridView) view.findViewById(R.id.content_view);
        please_wait=view.findViewById(R.id.please_wait);
        qs_s=new ArrayList<kaicai_order_item>();
        init_wangluo_init();
        /*
		 * 在布局中找到一个自定义了的控件，其实已经写好了，只要给他设置一个监听器实现两个功能。---3
		 */
        ((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
                .setOnRefreshListener(getlin());

        return view;
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
            public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        System.out.println("上拉加载");
                        // 千万别忘了告诉控件加载完毕了哦！
                        init_wangluo_init2(pullToRefreshLayout);

                    }
                }.sendEmptyMessageDelayed(0, 1000);


            }
        };

    }
    int flag=1;
    protected void init_wangluo_init() {
        please_wait.setVisibility(View.VISIBLE);
        flag=1;
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_kaicai_ingo;
        RequestParams requestParams=new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        requestParams.put("PAGE", ""+flag);
        requestParams.put("NUM", "10");
        asyncHttpClient.post(url,requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                KLog.json("kaicai_dingdan",arg1.toString());
                JSONArray jsonArray;
                try {
                   if("true".equals(arg1.getString("status"))){
                       jsonArray = arg1.getJSONArray("orders");
                       qs_s.clear();
                           for (int j=0;j<jsonArray.length();j++){
                               kaicai_order_item jc_item=new kaicai_order_item();
                               if (j==0){
                                   jc_item.setXz_time(jsonArray.getJSONObject(j).getString("addTime"));
                               }else{
                                   jc_item.setXz_time("false");
                               }
                               jc_item.setKcjd(""+Math.round(Double.parseDouble(jsonArray.getJSONObject(j).getString("kcjd"))));
                               jc_item.setShow_time(jsonArray.getJSONObject(j).getString("addTime"));
                               jc_item.setSy1(jsonArray.getJSONObject(j).getString("sy1"));
                               jc_item.setSy2(jsonArray.getJSONObject(j).getString("sy2"));
                               jc_item.setSy3(jsonArray.getJSONObject(j).getString("sy3"));
                               jc_item.setSy4(jsonArray.getJSONObject(j).getString("sy4"));
                               jc_item.setSy5(jsonArray.getJSONObject(j).getString("sy5"));
                               jc_item.setSy6(jsonArray.getJSONObject(j).getString("sy6"));
                               jc_item.setSyjd(jsonArray.getJSONObject(j).getString("sy"));
                               jc_item.setTotal_jd(jsonArray.getJSONObject(j).getString("totalJd"));
                               jc_item.setExpect(jsonArray.getJSONObject(j).getString("expect"));

                               qs_s.add(jc_item);
                           }
				/*
				 * 适配器和布局在这里初始化好
				 */
                       adapter=new dingdan_kaicai_order_adapter(getActivity(), qs_s);
                       gridView.setAdapter(adapter);
                   }else{

                   }
                    please_wait.setVisibility(View.GONE);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Throwable arg0) {
                // TODO Auto-generated method stub
                super.onFailure(arg0);
                please_wait.setVisibility(View.GONE);
            }


        });

    }
    protected void init_wangluo_init2(final PullToRefreshLayout pullToRefreshLayout) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_kaicai_ingo;
        RequestParams requestParams=new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        flag++;
        requestParams.put("PAGE", ""+flag);
        requestParams.put("NUM", "10");
        asyncHttpClient.post(url,requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                KLog.json("kaicai_dingdan",arg1.toString());
                JSONArray jsonArray;
                try {
                    if("true".equals(arg1.getString("status"))){
                        jsonArray = arg1.getJSONArray("orders");
                        for (int j=0;j<jsonArray.length();j++){
                            kaicai_order_item jc_item=new kaicai_order_item();
                            if (j==0){
                                jc_item.setXz_time(jsonArray.getJSONObject(j).getString("addTime"));
                            }else{
                                jc_item.setXz_time("false");
                            }
                            jc_item.setKcjd(jsonArray.getJSONObject(j).getString("kcjd"));
                            jc_item.setShow_time(jsonArray.getJSONObject(j).getString("addTime"));
                            jc_item.setSy1(jsonArray.getJSONObject(j).getString("sy1"));
                            jc_item.setSy2(jsonArray.getJSONObject(j).getString("sy2"));
                            jc_item.setSy3(jsonArray.getJSONObject(j).getString("sy3"));
                            jc_item.setSy4(jsonArray.getJSONObject(j).getString("sy4"));
                            jc_item.setSy5(jsonArray.getJSONObject(j).getString("sy5"));
                            jc_item.setSy6(jsonArray.getJSONObject(j).getString("sy6"));
                            jc_item.setSyjd(jsonArray.getJSONObject(j).getString("sy"));
                            jc_item.setTotal_jd(jsonArray.getJSONObject(j).getString("totalJd"));
                            jc_item.setExpect(jsonArray.getJSONObject(j).getString("expect"));

                            qs_s.add(jc_item);
                        }
				/*
				 * 适配器和布局在这里初始化好
				 */
                        adapter.notifyDataSetChanged();
                    }else{

                    }
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    please_wait.setVisibility(View.GONE);
                } catch (JSONException e) {
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


}
