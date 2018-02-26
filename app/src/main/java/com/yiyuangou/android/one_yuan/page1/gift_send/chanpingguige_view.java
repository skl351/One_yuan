package com.yiyuangou.android.one_yuan.page1.gift_send;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yiyuangou.android.one_yuan.R;
import com.yiyuangou.android.one_yuan.util.all_url;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by android on 2016/5/26.
 */
public class chanpingguige_view extends Fragment {
    private View view;
    private LinearLayout add_chanpin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=  inflater.inflate(R.layout.chanpingguige_layout, container, false);
        System.out.println("开始3");
        add_chanpin= (LinearLayout) view.findViewById(R.id.add_chanpin);
        init_wangluo();



        return view;
    }
    static int height=0;
    private void init_wangluo() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setTimeout(20000);
        String url = all_url.url_tuwen;
        RequestParams requestParams = new RequestParams();
        requestParams.put("giftId", gift_send_v_xiangq.UUID);
        asyncHttpClient.post(url,requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                super.onSuccess(jsonObject);
                try {
                    if ("true".equals(jsonObject.getString("status"))){
                        System.out.println(jsonObject);
                        JSONArray js=jsonObject.getJSONArray("attributes");
                        for (int i=0;i<js.length();i++){
                            View view= LayoutInflater.from(getActivity()).inflate(R.layout.item_chanpingcanshu, null);
                            TextView name= (TextView) view.findViewById(R.id.canp_name);
                            TextView value= (TextView) view.findViewById(R.id.canp_value);
                            name.setText(js.getJSONObject(i).getString("name"));
                            value.setText(js.getJSONObject(i).getString("value"));
                            add_chanpin.addView(view);
                        }
                        int w = View.MeasureSpec.makeMeasureSpec(0,
                                View.MeasureSpec.UNSPECIFIED);
                        int h = View.MeasureSpec.makeMeasureSpec(0,
                                View.MeasureSpec.UNSPECIFIED);
                        view.measure(w, h);
                         height = view.getMeasuredHeight();
                        System.out.println(height+"---------------------------------3->");


                    }else{

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                Toast.makeText(getActivity(),"网络繁忙，请重试",Toast.LENGTH_SHORT).show();
            }


        });
    }
}
