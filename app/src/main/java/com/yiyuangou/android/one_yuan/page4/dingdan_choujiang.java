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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.adapter.dingdan_choujiang_adapter;
import com.yiyuangou.android.one_yuan.adapter.dingdan_jingcai_adapter;
import com.yiyuangou.android.one_yuan.bean.item_order;
import com.yiyuangou.android.one_yuan.bean.item_zhuanpan;
import com.yiyuangou.android.one_yuan.bean.jingcai_item;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/5/24.
 */
public class dingdan_choujiang extends Fragment {


    private View view;
    GridView gridView;
    PullToRefreshLayout.OnRefreshListener re;
    List<item_zhuanpan> list;
    private dingdan_choujiang_adapter adapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.dingdan_choujiang, container, false);
        gridView = (GridView) view.findViewById(R.id.content_view);
        list=new ArrayList<item_zhuanpan>();
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
                System.out.println("上拉加载");
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
        String url = all_url.url_zhuanpan_dingdan;
        RequestParams requestParams=new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        requestParams.put("PAGE", ""+flag);
        requestParams.put("NUM", "10");
        asyncHttpClient.post(url,requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());

                try {
                    if("true".equals(arg1.getString("status"))){
                        JSONArray jsonArray=arg1.getJSONArray("awardLog");
                        list.clear();
                        for(int i=0;i<jsonArray.length();i++){
                            item_zhuanpan item=new item_zhuanpan();
                            item.setName(jsonArray.getJSONObject(i).getString("message"));
                            item.setImg(jsonArray.getJSONObject(i).getString("img"));
                            item.setJd(jsonArray.getJSONObject(i).getString("prizeJd"));
                            item.setType(jsonArray.getJSONObject(i).getString("type"));
                            item.setStatus(jsonArray.getJSONObject(i).getString("status"));
                            item.setUuid(jsonArray.getJSONObject(i).getString("uuid"));
                            item.setRoom_name(jsonArray.getJSONObject(i).getString("roomName"));
                            list.add(item);

                        }
                        adapter=new dingdan_choujiang_adapter(getActivity(),list);
                        gridView.setAdapter(adapter);



                    }else {

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
    protected void init_wangluo_init2() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_zhuanpan_dingdan;
        RequestParams requestParams=new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        flag++;
        requestParams.put("PAGE", ""+flag);
        requestParams.put("NUM", "10");
        asyncHttpClient.post(url,requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());
                try {
                    if("true".equals(arg1.getString("status"))){
                        JSONArray jsonArray=arg1.getJSONArray("awardLog");
                        for(int i=0;i<jsonArray.length();i++){
                            item_zhuanpan item=new item_zhuanpan();
                            item.setName(jsonArray.getJSONObject(i).getString("message"));
                            item.setImg(jsonArray.getJSONObject(i).getString("img"));
                            item.setJd(jsonArray.getJSONObject(i).getString("prizeJd"));
                            item.setType(jsonArray.getJSONObject(i).getString("type"));
                            item.setStatus(jsonArray.getJSONObject(i).getString("status"));
                            item.setUuid(jsonArray.getJSONObject(i).getString("uuid"));
                            item.setRoom_name(jsonArray.getJSONObject(i).getString("roomName"));
                            list.add(item);

                        }
                        adapter.notifyDataSetChanged();
                    }else {

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
