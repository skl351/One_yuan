package com.yiyuangou.android.one_yuan.page1.guess_hreo;

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
import com.yiyuangou.android.one_yuan.adapter.guess_more_hm_adapter_hero;
import com.yiyuangou.android.one_yuan.bean.kaijiang_result_hero;
import com.yiyuangou.android.one_yuan.bean.open_qs;
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
public class more_qs_hero extends Activity {
    private View kaijiang_topbar;
    private View back;
    GridView gridView;
    PullToRefreshLayout.OnRefreshListener re;
    private List<open_qs> list_open_qs=new ArrayList<open_qs>();
    guess_more_hm_adapter_hero adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.more_qs_hero);
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
    private List<kaijiang_result_hero> list_kaijiang_results= new ArrayList<kaijiang_result_hero>();
    protected void init_wangluo_init() {
        flag=1;
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_open_more_lol;
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
                        JSONArray open_jiang;
                        open_jiang = arg1.getJSONArray("more");
                        list_open_qs.clear();
                        list_kaijiang_results.clear();
                        for (int i = 0; i < open_jiang.length(); i++) {
                            kaijiang_result_hero item=new kaijiang_result_hero();
                            item.setExpect(open_jiang.getJSONObject(i).getString("expect"));
                            item.setCode_1(open_jiang.getJSONObject(i).getString("code_1"));
                            item.setCode_2(open_jiang.getJSONObject(i).getString("code_2"));
                            item.setCode_3(open_jiang.getJSONObject(i).getString("code_3"));
                            item.setCode_4(open_jiang.getJSONObject(i).getString("code_4"));
                            item.setCode_all(open_jiang.getJSONObject(i).getString("position"));
                            item.setLast_code_all(open_jiang.getJSONObject(i).getString("oldPosition"));
                            item.setResult(open_jiang.getJSONObject(i).getString("result"));
                            item.setName(open_jiang.getJSONObject(i).getString("name"));
                            list_kaijiang_results.add(item);
                        }

                        adapter = new guess_more_hm_adapter_hero(more_qs_hero.this,list_kaijiang_results);
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
        String url = all_url.url_open_more_lol;
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
                        JSONArray open_jiang;
                        open_jiang = arg1.getJSONArray("more");
                        for (int i = 0; i < open_jiang.length(); i++) {
                            kaijiang_result_hero item=new kaijiang_result_hero();
                            item.setExpect(open_jiang.getJSONObject(i).getString("expect"));
                            item.setCode_1(open_jiang.getJSONObject(i).getString("code_1"));
                            item.setCode_2(open_jiang.getJSONObject(i).getString("code_2"));
                            item.setCode_3(open_jiang.getJSONObject(i).getString("code_3"));
                            item.setCode_4(open_jiang.getJSONObject(i).getString("code_4"));
                            item.setCode_all(open_jiang.getJSONObject(i).getString("code"));
                            item.setResult(open_jiang.getJSONObject(i).getString("result"));
                            item.setName(open_jiang.getJSONObject(i).getString("name"));
                            list_kaijiang_results.add(item);
                        }
                        //list_open_qs可以前面
                        //list_kaijiang_results后面

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
        Intent intent=new Intent(more_qs_hero.this,guess_hero.class);
        startActivity(intent);
    }

}
