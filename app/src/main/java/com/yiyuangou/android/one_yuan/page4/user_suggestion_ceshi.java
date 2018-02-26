package com.yiyuangou.android.one_yuan.page4;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.adapter.talk_adapter;
import com.yiyuangou.android.one_yuan.bean.talk_bean;
import com.yiyuangou.android.one_yuan.util.User;
import com.yiyuangou.android.one_yuan.util.all_url;
import com.yiyuangou.android.one_yuan.util.btn_cls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/4/11.
 */
public class user_suggestion_ceshi extends Activity {
    private View suggest_topbar;
    private View back;
    private List<talk_bean> list_talk;
    talk_adapter adapter;
    GridView gridView;
    int flag=1;
    private Button suggest_send;
    PullToRefreshLayout.OnRefreshListener re;
    private EditText neirong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_suggestion_ceshi);
        init_wangluo_init();
        init();
        init_back();
        submit();
         /*
		 * 在布局中找到一个自定义了的控件，其实已经写好了，只要给他设置一个监听器实现两个功能。---3
		 */
        ((PullToRefreshLayout) findViewById(R.id.refresh_view))
                .setOnRefreshListener(getlin());
    }

    private void submit() {
        suggest_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_cls.bt_disable(suggest_send);
                String suggest = neirong.getText().toString();
                System.out.println(suggest);
                if (suggest.isEmpty()) {
                    Toast.makeText(user_suggestion_ceshi.this, "意见不能为空", Toast.LENGTH_SHORT).show();
                    btn_cls.bt_endisable(suggest_send);
                    return;
                }

                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                String url = all_url.url_suggest;
                RequestParams requestParams = new RequestParams();
                requestParams.put("FKNR", suggest);
                requestParams.put("SJLX", "2");
                requestParams.put("userId", User.getuser().getUser_uuid());
                asyncHttpClient.post(url, requestParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        super.onSuccess(jsonObject);
                        try {
                            System.out.println(jsonObject);
                            if ("true".equals(jsonObject.getString("status"))) {
                                Toast.makeText(user_suggestion_ceshi.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                                neirong.setText("");
//                                Intent intent = new Intent(user_suggestion_ceshi.this, MainActivity.class);
//                                startActivity(intent);
                                init_wangluo_init();
                            } else {
                                Toast.makeText(user_suggestion_ceshi.this, jsonObject.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                            btn_cls.bt_endisable(suggest_send);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        super.onFailure(throwable);
                        Toast.makeText(user_suggestion_ceshi.this, "网络繁忙，请重试", Toast.LENGTH_SHORT).show();
                        btn_cls.bt_endisable(suggest_send);
                    }
                });
            }
        });
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

    private void init_wangluo_init() {
        flag=1;
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_talk_list;
        RequestParams requestParams=new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        requestParams.put("PAGE", ""+flag);
        requestParams.put("NUM", "10");
        asyncHttpClient.post(url,requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                try {
                    if("true".equals(arg1.getString("status"))){
                        System.out.println(arg1.toString());
                        JSONArray jsonArray=arg1.getJSONArray("list");
                        list_talk=new ArrayList<talk_bean>();
                        for(int i=0;i<jsonArray.length();i++){
                            talk_bean item=new talk_bean();
                            item.setText(jsonArray.getJSONObject(i).getString("postText"));
                            item.setTime(jsonArray.getJSONObject(i).getString("time"));
                            if(jsonArray.getJSONObject(i).has("reveText")){
                                if(jsonArray.getJSONObject(i).getString("reveText").equals("0")){
                                    item.setRevetext("null");
                                    item.setRevetime("null");
                                }else{
                                    item.setRevetext(jsonArray.getJSONObject(i).getString("reveText"));
                                    item.setRevetime(jsonArray.getJSONObject(i).getString("reveTime"));
                                }


                            }else{
                                item.setRevetext("null");
                                item.setRevetime("null");
                            }
                            list_talk.add(item);

                        }
                        adapter=new talk_adapter(user_suggestion_ceshi.this,list_talk);
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
    private void init_wangluo_init2() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = all_url.url_talk_list;
        RequestParams requestParams=new RequestParams();
        requestParams.put("userId", User.getuser().getUser_uuid());
        flag=flag+1;
        requestParams.put("PAGE", ""+flag);
        requestParams.put("NUM", ""+10);
        asyncHttpClient.post(url,requestParams,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, JSONObject arg1) {
                super.onSuccess(arg0, arg1);
                try {
                    if("true".equals(arg1.getString("status"))){
                        System.out.println(arg1.toString());
                        JSONArray jsonArray=arg1.getJSONArray("list");
                        for(int i=0;i<jsonArray.length();i++){
                            talk_bean item=new talk_bean();
                            item.setText(jsonArray.getJSONObject(i).getString("postText"));
                            item.setTime(jsonArray.getJSONObject(i).getString("time"));
                            if(jsonArray.getJSONObject(i).has("reveText")){
                                if(jsonArray.getJSONObject(i).getString("reveText").equals("0")){
                                    item.setRevetext("null");
                                    item.setRevetime("null");
                                }else{
                                    item.setRevetext(jsonArray.getJSONObject(i).getString("reveText"));
                                    item.setRevetime(jsonArray.getJSONObject(i).getString("reveTime"));
                                }

                            }else{
                                item.setRevetext("null");
                                item.setRevetime("null");
                            }
                            list_talk.add(item);

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

    private void init() {
        neirong= (EditText) findViewById(R.id.neirong);

        suggest_send= (Button) findViewById(R.id.suggest_send);
        suggest_topbar=findViewById(R.id.suggest_topbar);
        back=suggest_topbar.findViewById(R.id.logreg_left);
        gridView = (GridView) findViewById(R.id.content_view);
    }
    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
