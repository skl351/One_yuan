package com.yiyuangou.android.one_yuan.page3;

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
import com.yiyuangou.android.one_yuan.adapter.guess_more_hm_adapter;
import com.yiyuangou.android.one_yuan.bean.kaijiang_result;
import com.yiyuangou.android.one_yuan.bean.open_qs;
import com.yiyuangou.android.one_yuan.page4.PullToRefreshLayout;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/7.
 */
public class qs_guess extends Fragment {
    private View view;
    private View kaijiang_topbar;
    private View back;
    GridView gridView;
    PullToRefreshLayout.OnRefreshListener re;
    private List<open_qs> list_open_qs=new ArrayList<open_qs>();
    guess_more_hm_adapter adapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.qs_guess, container, false);
        init();
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

    private int flag;
    private List<kaijiang_result> list_kaijiang_results= new ArrayList<kaijiang_result>();
    protected void init_wangluo_init() {
        flag=1;
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_open_more;
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
                        list_open_qs.clear();
                        list_kaijiang_results.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            open_qs o1 = new open_qs();
                            o1.setKJQS(jsonArray.getJSONObject(i).getString("expect"));
                            o1.setGroup_1(jsonArray.getJSONObject(i).getString("group_1"));
                            o1.setGroup_2(jsonArray.getJSONObject(i).getString("group_2"));
                            o1.setGroup_3(jsonArray.getJSONObject(i).getString("group_3"));
                            o1.setGroup_num(jsonArray.getJSONObject(i).getString("group_num"));
                            String result = jsonArray.getJSONObject(i).getString("result");
                            String[] test = result.split(",");
                            System.out.println(test[0] + "," + test[1]);
                            o1.setResult_1(test[0]);
                            o1.setResult_2(test[1]);
                            list_open_qs.add(o1);
                            kaijiang_result kai1 = new kaijiang_result();
                            kai1.setQs(jsonArray.getJSONObject(i).getString("expect"));
                            kai1.setGroup_1(jsonArray.getJSONObject(i).getString("group_1"));
                            kai1.setGroup_2(jsonArray.getJSONObject(i).getString("group_2"));
                            kai1.setGroup_3(jsonArray.getJSONObject(i).getString("group_3"));
                            kai1.setGroup_num(jsonArray.getJSONObject(i).getString("group_num"));
                            kai1.setResult(jsonArray.getJSONObject(i).getString("result"));
                            List<String> list=new ArrayList<String>();
                            for (int j = 1; j <= 20; j++) {
                                list.add(jsonArray.getJSONObject(i).getString("code_"+j));
                            }
                            kai1.setList_20(list);
                            list_kaijiang_results.add(kai1);
                        }
                        //list_open_qs可以前面
                        //list_kaijiang_results后面

                        adapter = new guess_more_hm_adapter(getActivity(), list_open_qs,list_kaijiang_results);
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
        String url = all_url.url_open_more;
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
                        for (int i = 0; i < jsonArray.length(); i++) {
                            open_qs o1 = new open_qs();
                            o1.setKJQS(jsonArray.getJSONObject(i).getString("expect"));
                            o1.setGroup_1(jsonArray.getJSONObject(i).getString("group_1"));
                            o1.setGroup_2(jsonArray.getJSONObject(i).getString("group_2"));
                            o1.setGroup_3(jsonArray.getJSONObject(i).getString("group_3"));
                            o1.setGroup_num(jsonArray.getJSONObject(i).getString("group_num"));
                            String result = jsonArray.getJSONObject(i).getString("result");
                            String[] test = result.split(",");
                            System.out.println(test[0] + "," + test[1]);
                            o1.setResult_1(test[0]);
                            o1.setResult_2(test[1]);
                            list_open_qs.add(o1);
                            kaijiang_result kai1 = new kaijiang_result();
                            kai1.setQs(jsonArray.getJSONObject(i).getString("expect"));
                            kai1.setGroup_1(jsonArray.getJSONObject(i).getString("group_1"));
                            kai1.setGroup_2(jsonArray.getJSONObject(i).getString("group_2"));
                            kai1.setGroup_3(jsonArray.getJSONObject(i).getString("group_3"));
                            kai1.setGroup_num(jsonArray.getJSONObject(i).getString("group_num"));
                            kai1.setResult(jsonArray.getJSONObject(i).getString("result"));
                            List<String> list=new ArrayList<String>();
                            for (int j = 1; j <= 20; j++) {
                                list.add(jsonArray.getJSONObject(i).getString("code_"+j));
                            }
                            kai1.setList_20(list);
                            list_kaijiang_results.add(kai1);
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
        kaijiang_topbar=view.findViewById(R.id.kaijiang_topbar);
        gridView = (GridView)view.findViewById(R.id.content_view);
    }



}
