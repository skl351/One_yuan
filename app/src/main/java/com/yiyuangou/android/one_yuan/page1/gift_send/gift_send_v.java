package com.yiyuangou.android.one_yuan.page1.gift_send;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.MainActivity;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.bean.item_gift_send_v;
import com.yiyuangou.android.one_yuan.util.FileService;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;
import com.yiyuangou.android.one_yuan.util.btn_cls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/3/31.
 */
public class gift_send_v extends Fragment {
    MyAdapter adapter;
    List<String> list ;
    GridView gridView;
    PullToRefreshLayout.OnRefreshListener re;
    PullToRefreshLayout view;
    List<item_gift_send_v> items = new ArrayList<item_gift_send_v>();

    private View big_view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        big_view=  inflater.inflate(R.layout.gift_send, container, false);

        list=new ArrayList<String>();
        init();
        init_wangluo();
        view=(PullToRefreshLayout)big_view.findViewById(R.id.gift_refresh_view);
        view.setOnRefreshListener(getlin());
        return big_view;
    }
    int add_flag=1;

    private void init_wangluo(){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_gift;
        RequestParams requestParams = new RequestParams();
        requestParams.put("PAGE", ""+add_flag);
        requestParams.put("NUM", "10");
        if(!gift_send_activity.flag.equals("skl_quan")){
            requestParams.put("categoryId", gift_send_activity.flag);
        }
        asyncHttpClient.post(url,requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))){
                        System.out.println(jsonObject);
                        JSONArray js=jsonObject.getJSONArray("giftList");
                        items.clear();
                        for(int i=0;i<js.length();i++){
                            item_gift_send_v item=new item_gift_send_v();
                            item.setName(js.getJSONObject(i).getString("NAME"));
                            item.setPrice(js.getJSONObject(i).getString("PRICE"));
                            item.setInfo(js.getJSONObject(i).getString("INFO"));
                            item.setGift_info(js.getJSONObject(i).getString("GIFT_INFO"));
                            item.setIMG(js.getJSONObject(i).getString("IMG"));
                            item.setUUID(js.getJSONObject(i).getString("UUID"));
                            item.setIMg_5(js.getJSONObject(i).getString("IMG53"));
                            items.add(item);

                        }
                        adapter=new MyAdapter(getActivity(),items);
                        gridView.setAdapter(adapter);
                    }else{

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
//                please_wait.setVisibility(View.GONE);
//                btn_cls.bt_endisable(login_submit);
                Toast.makeText(getActivity(),"网络繁忙，请重试",Toast.LENGTH_SHORT).show();
            }


        });

    }
    private void init_wangluo2(){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_gift;
        add_flag++;
        RequestParams requestParams = new RequestParams();
        requestParams.put("PAGE", ""+add_flag);
        requestParams.put("NUM", "10");
        if(!gift_send_activity.flag.equals("skl_quan")){
            requestParams.put("categoryId", gift_send_activity.flag);
        }
        asyncHttpClient.post(url,requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))){
                        System.out.println(jsonObject);
                        JSONArray js=jsonObject.getJSONArray("giftList");
                        for(int i=0;i<js.length();i++){
                            item_gift_send_v item=new item_gift_send_v();
                            item.setName(js.getJSONObject(i).getString("NAME"));
                            item.setPrice(js.getJSONObject(i).getString("PRICE"));
                            item.setInfo(js.getJSONObject(i).getString("INFO"));
                            item.setGift_info(js.getJSONObject(i).getString("GIFT_INFO"));
                            item.setIMG(js.getJSONObject(i).getString("IMG"));
                            item.setUUID(js.getJSONObject(i).getString("UUID"));
                            item.setIMg_5(js.getJSONObject(i).getString("IMG53"));
                            items.add(item);

                        }
                        adapter=new MyAdapter(getActivity(),items);
                        gridView.setAdapter(adapter);
                    }else{

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
//                please_wait.setVisibility(View.GONE);
//                btn_cls.bt_endisable(login_submit);
                Toast.makeText(getActivity(),"网络繁忙，请重试",Toast.LENGTH_SHORT).show();
            }


        });

    }

    private PullToRefreshLayout.OnRefreshListener getlin() {
        return re=new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
//                        init_wangluo(pullToRefreshLayout);
                        init_wangluo();
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);

                    }
                }.sendEmptyMessageDelayed(0, 1000);

            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                System.out.println("上拉加载");
                // 千万别忘了告诉控件加载完毕了哦！
                init_wangluo2();
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);

            }
        };
    }

    private void init() {
        gridView= (GridView) big_view.findViewById(R.id.gift_content_view);
    }

}
