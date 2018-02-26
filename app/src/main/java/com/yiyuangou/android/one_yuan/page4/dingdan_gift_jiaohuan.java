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
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.adapter.dingdan_jingcai_adapter;
import com.yiyuangou.android.one_yuan.adapter.gift_duihuan_dingdan_adapter;
import com.yiyuangou.android.one_yuan.bean.gift_dingdan_item;
import com.yiyuangou.android.one_yuan.bean.item_order;
import com.yiyuangou.android.one_yuan.bean.jingcai_item;
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
public class dingdan_gift_jiaohuan extends Fragment {
    private View view;
    GridView gridView;
    private View zhanwushuju;
    PullToRefreshLayout.OnRefreshListener re;
    List<gift_dingdan_item> list;
    gift_duihuan_dingdan_adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.dingdan_gift, container, false);
        gridView = (GridView) view.findViewById(R.id.content_view);
        list=new ArrayList<gift_dingdan_item>();
        zhanwushuju=view.findViewById(R.id.zhanwushuju);
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
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                // 千万别忘了告诉控件加载完毕了哦！
                init_wangluo_init2();
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);


            }
        };

    }

    int flag=1;
    protected void init_wangluo_init() {
        flag=1;
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.getUrl_gift_duihuan_dingdan;
        RequestParams requestParams=new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        requestParams.put("PAGE", ""+flag);
        requestParams.put("NUM", "10");
        asyncHttpClient.post(url,requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());
                JSONArray jsonArray;
                try {
                   if("true".equals(arg1.getString("status"))){
                       jsonArray = arg1.getJSONArray("giftLog");
                       list.clear();
                       for (int i = 0; i < jsonArray.length(); i++) {
                           gift_dingdan_item gift_1=new gift_dingdan_item();
                           gift_1.setImg(jsonArray.getJSONObject(i).getString("IMG"));
                           gift_1.setJd(jsonArray.getJSONObject(i).getString("PRICE"));
                           gift_1.setName(jsonArray.getJSONObject(i).getString("NAME"));
                           gift_1.setStatus(jsonArray.getJSONObject(i).getString("status"));
                           list.add(gift_1);
                       }
				/*
				 * 适配器和布局在这里初始化好
				 */
                       adapter=new gift_duihuan_dingdan_adapter(getActivity(), list);
                       gridView.setAdapter(adapter);
                   }else {
                       zhanwushuju.setVisibility(View.VISIBLE);
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
                Toast.makeText(getActivity(),"网络繁忙",Toast.LENGTH_LONG).show();
            }


        });

    }
    protected void init_wangluo_init2() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.getUrl_gift_duihuan_dingdan;
        RequestParams requestParams=new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        flag=flag+1;
        requestParams.put("PAGE", ""+flag);
        requestParams.put("NUM", "10");
        asyncHttpClient.post(url,requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());
                JSONArray jsonArray;
                try {
                    if("true".equals(arg1.getString("status"))){
                        jsonArray = arg1.getJSONArray("giftLog");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            gift_dingdan_item gift_1=new gift_dingdan_item();
                            gift_1.setImg(jsonArray.getJSONObject(i).getString("IMG"));
                            gift_1.setJd(jsonArray.getJSONObject(i).getString("PRICE"));
                            gift_1.setName(jsonArray.getJSONObject(i).getString("NAME"));
                            gift_1.setStatus(jsonArray.getJSONObject(i).getString("status"));
                            list.add(gift_1);
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
                Toast.makeText(getActivity(),"网络繁忙",Toast.LENGTH_LONG).show();
            }


        });

    }

}
