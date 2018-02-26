package com.yiyuangou.android.one_yuan.page1.all_guess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.adapter.guess_more_hm_adapter_pk;
import com.yiyuangou.android.one_yuan.bean.pk_more_qs;
import com.yiyuangou.android.one_yuan.page1.pk_ten.pk_ten;
import com.yiyuangou.android.one_yuan.page4.PullToRefreshLayout;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/19.
 */
public class more_qs_pk extends Activity {
    private View kaijiang_topbar;
    private View back;
    GridView gridView;
    PullToRefreshLayout.OnRefreshListener re;
    List<pk_more_qs> list=new ArrayList<pk_more_qs>();
    guess_more_hm_adapter_pk adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.more_qs_pk);
        init();
        init_back();
        init_wangluo_init();
         /*
		 * 在布局中找到一个自定义了的控件，其实已经写好了，只要给他设置一个监听器实现两个功能。---3
		 */
        ((PullToRefreshLayout) findViewById(R.id.refresh_view))
                .setOnRefreshListener(getlin());

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


    private int flag;
    protected void init_wangluo_init() {
        flag=1;
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_more;
        RequestParams requestParams=new RequestParams();
        requestParams.put("PAGE", "" + flag);
        requestParams.put("NUM", "20");
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());
                try {
                    if ("true".equals(arg1.getString("status"))) {
                        JSONArray jsonArray;
                        jsonArray = arg1.getJSONArray("more");
                        list.clear();
                        for (int i=0;i<jsonArray.length();i++){
                            pk_more_qs item=new pk_more_qs();
                            item.setExpect(jsonArray.getJSONObject(i).getString("expect"));
                            List<String> list_1=new ArrayList<String>();
                            for (int j = 1; j <= 10; j++) {
                                list_1.add(jsonArray.getJSONObject(i).getString("code_" + j));
                            }
                            item.setHm_list(list_1);
                            list.add(item);
                        }

                        adapter = new guess_more_hm_adapter_pk(more_qs_pk.this, list);
                        gridView.setAdapter(adapter);
                    } else {
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
        String url = all_url.url_more;
        RequestParams requestParams=new RequestParams();
        flag++;
        requestParams.put("PAGE", "" + flag);
        requestParams.put("NUM", "20");
        asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                System.out.println(arg1.toString());
                try {
                    if ("true".equals(arg1.getString("status"))) {
                        JSONArray jsonArray;
                        jsonArray = arg1.getJSONArray("more");
                        for (int i=0;i<jsonArray.length();i++){
                            pk_more_qs item=new pk_more_qs();
                            item.setExpect(jsonArray.getJSONObject(i).getString("expect"));
                            List<String> list_1=new ArrayList<String>();
                            for (int j = 1; j <= 10; j++) {
                                list_1.add(jsonArray.getJSONObject(i).getString("code_" + j));
                            }
                            item.setHm_list(list_1);
                            list.add(item);
                        }
                        adapter .notifyDataSetChanged();
                    } else {
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



    private void init() {
        kaijiang_topbar=findViewById(R.id.kaijiang_topbar);
        back=findViewById(R.id.logreg_left);
        gridView = (GridView)findViewById(R.id.content_view);
    }

    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);

                } catch (IOException e) {
                    Log.e("Exception when doBack", e.toString());
                }
            }
        });
    }
    @Override
    public void onBackPressed() {

        finish();
        Intent intent=new Intent(more_qs_pk.this,pk_ten.class);
        startActivity(intent);
    }

}
