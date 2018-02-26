package com.yiyuangou.android.one_yuan.page4;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.adapter.dingdan_jingcai_adapter;
import com.yiyuangou.android.one_yuan.bean.item_order;
import com.yiyuangou.android.one_yuan.bean.jingcai_item;
import com.yiyuangou.android.one_yuan.bean.jingcai_item_info;
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
public class dingdan_jincai_click extends Fragment {
    private View view;
    dingdan_jingcai_adapter adapter ;
    GridView gridView;
    PullToRefreshLayout.OnRefreshListener re;
    List<item_order> list ;
    List<jingcai_item> qs_s;
    private View please_wait;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=  inflater.inflate(R.layout.dingdan_jingcai, container, false);
        gridView = (GridView) view.findViewById(R.id.content_view);
        please_wait=view.findViewById(R.id.please_wait);
        list=new ArrayList<item_order>();
        qs_s=new ArrayList<jingcai_item>();
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
        String url = all_url.url_jincaidingdan;
        RequestParams requestParams=new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        requestParams.put("PAGE", ""+flag);
        requestParams.put("NUM", "5");
        asyncHttpClient.post(url,requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());
                JSONArray jsonArray;
                try {
                   if("true".equals(arg1.getString("status"))){
                       jsonArray = arg1.getJSONArray("orders");
                       list.clear();
                       qs_s.clear();
                       for (int i = 0; i < jsonArray.length(); i++) {
                           //总共-多少
                           item_order item1=new item_order();
                           item1.setDate(jsonArray.getJSONObject(i).getString("time"));
                           JSONArray infos=jsonArray.getJSONObject(i).getJSONArray("infos");
                           for (int j=0;j<infos.length();j++){
                               jingcai_item jc_item=new jingcai_item();
                               if (j==0){
                                   jc_item.setDate(jsonArray.getJSONObject(i).getString("time"));
                               }else{
                                   jc_item.setDate("false");
                               }
                               jc_item.setHMMC(infos.getJSONObject(j).getString("HMMC"));
                               jc_item.setQs(infos.getJSONObject(j).getString("EXPECT"));
                               jc_item.setDazt(infos.getJSONObject(j).getString("DDZT"));
                               jc_item.setGmzj(String.valueOf(infos.getJSONObject(j).getInt("GMZJ")));
                               jc_item.setSyjd(String.valueOf(infos.getJSONObject(j).getInt("SYJD")));
                               jc_item.setJt_date(infos.getJSONObject(j).getString("DDCSSJ"));
                               jc_item.setUUID(infos.getJSONObject(j).getString("UUID"));
                               qs_s.add(jc_item);
                           }
                           item1.setQs_s(qs_s);
                           list.add(item1);

                       }

				/*
				 * 适配器和布局在这里初始化好
				 */
                       adapter=new dingdan_jingcai_adapter(getActivity(), qs_s);
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
        String url = all_url.url_jincaidingdan;
        RequestParams requestParams=new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        flag++;
        requestParams.put("PAGE", ""+flag);
        requestParams.put("NUM", "5");
        asyncHttpClient.post(url,requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());
                JSONArray jsonArray;
                try {

                    if ("true".equals(arg1.getString("status"))){
                        jsonArray = arg1.getJSONArray("orders");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            //总共-多少

                            item_order item1=new item_order();
                            item1.setDate(jsonArray.getJSONObject(i).getString("time"));
                            JSONArray infos=jsonArray.getJSONObject(i).getJSONArray("infos");
                            for (int j=0;j<infos.length();j++){
                                jingcai_item jc_item=new jingcai_item();
                                if (j==0){
                                    jc_item.setDate(jsonArray.getJSONObject(i).getString("time"));
                                }else{
                                    jc_item.setDate("false");
                                }
                                jc_item.setHMMC(infos.getJSONObject(j).getString("HMMC"));
                                jc_item.setQs(infos.getJSONObject(j).getString("EXPECT"));
                                jc_item.setDazt(infos.getJSONObject(j).getString("DDZT"));
                                jc_item.setGmzj(String.valueOf(infos.getJSONObject(j).getInt("GMZJ")));
                                jc_item.setSyjd(String.valueOf(infos.getJSONObject(j).getInt("SYJD")));
                                jc_item.setJt_date(infos.getJSONObject(j).getString("DDCSSJ"));
                                jc_item.setUUID(infos.getJSONObject(j).getString("UUID"));
                                qs_s.add(jc_item);
                            }
                            item1.setQs_s(qs_s);
                            list.add(item1);

                        }

				/*
				 * 适配器和布局在这里初始化好
				 */
                        adapter.notifyDataSetChanged();

                    }else {

                    }
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
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
