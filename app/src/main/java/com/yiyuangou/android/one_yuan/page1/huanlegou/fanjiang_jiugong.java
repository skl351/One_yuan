package com.yiyuangou.android.one_yuan.page1.huanlegou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.adapter.jiugong_guess_adapter;
import com.yiyuangou.android.one_yuan.bean.room;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/5/25.
 */
public class fanjiang_jiugong extends Activity {

    private GridView jiugong_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(R.layout.fangjian_big_jiugong);
        init();
        init_wangluo();
        init_back();
        init_but();
    }

    private void init_but() {
        guize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(fanjiang_jiugong.this,zhuanpan_guize.class);
                startActivity(intent);


            }
        });
    }

    private  List<room> list_room;
    private void init_wangluo() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        String url = all_url.url_room;
        asyncHttpClient.post(url,requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))){
                        final List<View> list_view=new ArrayList<View>();
                        JSONArray rooms=jsonObject.getJSONArray("room");
                        list_room=new ArrayList<room>();
                        for(int i=0;i<rooms.length();i++){
                            room r1=new room();
                            r1.setId(rooms.getJSONObject(i).getString("uuid"));
                            r1.setVal(rooms.getJSONObject(i).getString("val"));
                            r1.setInfo(rooms.getJSONObject(i).getString("info"));
                            r1.setName(rooms.getJSONObject(i).getString("name"));
                            list_room.add(r1);
                        }
                        jiugong_list.setAdapter(new jiugong_guess_adapter(fanjiang_jiugong.this,list_room));
                        jiugong_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent=new Intent(fanjiang_jiugong.this,zhuanpan.class);
                                intent.putExtra("roomId",list_room.get(position).getId());
                                intent.putExtra("info",list_room.get(position).getInfo());
                                intent.putExtra("name",list_room.get(position).getName());
                                startActivity(intent);
                            }
                        });


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable arg0) {
                super.onFailure(arg0);
            }
        });
    }
    private void init_back() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private View top_bar;
    private View back;
    private View guize;
    private void init() {
        top_bar=findViewById(R.id.fangjiang_topbar);
        back=top_bar.findViewById(R.id.logreg_left);
        guize=top_bar.findViewById(R.id.logreg_right);
        jiugong_list= (GridView) findViewById(R.id.jiugong_list);
    }
}
