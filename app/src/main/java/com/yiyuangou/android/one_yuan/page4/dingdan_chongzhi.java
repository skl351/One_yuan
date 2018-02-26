package com.yiyuangou.android.one_yuan.page4;
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
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.adapter.dingdan_chongzhi_adapter;
import com.yiyuangou.android.one_yuan.bean.chongzhi_info;
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
public class dingdan_chongzhi extends Fragment {
    private View view;
    GridView gridView;
    dingdan_chongzhi_adapter adapter;
    PullToRefreshLayout.OnRefreshListener re;
    private View xiangyaotoum;
    private View zhanwushuju;
    int flag=1;
    List<chongzhi_info> list ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.dingdan_chongzhi, container, false);
        init();
        init_wangluo_init();
        /*
		 * 在布局中找到一个自定义了的控件，其实已经写好了，只要给他设置一个监听器实现两个功能。---3
		 */
        ((PullToRefreshLayout) view.findViewById(R.id.refresh_view))
                .setOnRefreshListener(getlin());
        return view;
    }
    private void init() {
        xiangyaotoum=view.findViewById(R.id.xiangyaotoum);
        zhanwushuju=view.findViewById(R.id.zhanwushuju);
        gridView = (GridView) view.findViewById(R.id.content_view);
        list=new ArrayList<chongzhi_info>();
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

    protected void init_wangluo_init() {
        flag=1;
        xiangyaotoum.setVisibility(View.VISIBLE);
        zhanwushuju.setVisibility(View.GONE);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_chongzhijilu;//-----------充值记录
        RequestParams requestParams=new RequestParams();
        requestParams.put("ZFYHID", User.getuser().getUser_uuid());
        requestParams.put("PAGE", ""+flag);
        requestParams.put("NUM", "10");
        asyncHttpClient.post(url,requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());
                try {
                    if("true".equals(arg1.getString("status"))){
                        JSONArray jsonArray;
                        jsonArray = arg1.getJSONArray("payLogs");
                        list.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            chongzhi_info irem=new chongzhi_info();
                            irem.setDDHM(jsonArray.getJSONObject(i).getString("DDBH"));
                            irem.setCZJE(jsonArray.getJSONObject(i).getString("ZFJE"));
                            irem.setCZFS(jsonArray.getJSONObject(i).getString("ZFFS"));
                            irem.setCZZT(jsonArray.getJSONObject(i).getString("ZFZT"));
                            irem.setXDSJ(jsonArray.getJSONObject(i).getString("ZFSJ"));
                            irem.setZFLX(jsonArray.getJSONObject(i).getString("ZFLX"));
                            list.add(irem);

                        }
				/*
				 * 适配器和布局在这里初始化好
				 */
                        adapter=new dingdan_chongzhi_adapter(getActivity(), list);
                        gridView.setAdapter(adapter);
                    }else{
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
            }
        });
    }
    protected void init_wangluo_init2() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_chongzhijilu;//-----------充值记录
        RequestParams requestParams=new RequestParams();
        requestParams.put("ZFYHID", User.getuser().getUser_uuid());
        flag=flag+1;
        requestParams.put("PAGE", ""+flag);
        requestParams.put("NUM", "10");
        asyncHttpClient.post(url,requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());
                try {
                    if("true".equals(arg1.getString("status"))){
                        JSONArray jsonArray;
                        jsonArray = arg1.getJSONArray("payLogs");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            chongzhi_info irem=new chongzhi_info();
                            irem.setDDHM(jsonArray.getJSONObject(i).getString("DDBH"));
                            irem.setCZJE(jsonArray.getJSONObject(i).getString("ZFJE"));
                            irem.setCZFS(jsonArray.getJSONObject(i).getString("ZFFS"));
                            irem.setCZZT(jsonArray.getJSONObject(i).getString("ZFZT"));
                            irem.setXDSJ(jsonArray.getJSONObject(i).getString("ZFSJ"));
                            irem.setZFLX(jsonArray.getJSONObject(i).getString("ZFLX"));
                            list.add(irem);

                        }
				/*
				 * 适配器和布局在这里初始化好
				 */
                        adapter.notifyDataSetChanged();
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

            }
        });
    }
}
